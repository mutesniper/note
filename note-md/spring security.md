好的！下面是一个**基于数据库的用户登录验证完整示例**，使用的是：

- **Maven**
- **Spring Boot**
- **MyBatis**
- **Spring Security**
- **MySQL**
- RESTful 风格（不使用 Thymeleaf 表单）

适合前后端分离项目（如前端使用 Vue、React）。

------

## 📦 1. Maven 依赖

```
xml复制编辑<dependencies>
    <!-- Spring Boot Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- Spring Security -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>

    <!-- MyBatis -->
    <dependency>
        <groupId>org.mybatis.spring.boot</groupId>
        <artifactId>mybatis-spring-boot-starter</artifactId>
        <version>3.0.2</version>
    </dependency>

    <!-- MySQL JDBC Driver -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <scope>runtime</scope>
    </dependency>

    <!-- lombok (可选) -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>
```

------

## ⚙️ 2. application.yml 配置

```
yaml复制编辑server:
  port: 8080

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mydb?useSSL=false&serverTimezone=UTC
    username: root
    password: yourpassword
    driver-class-name: com.mysql.cj.jdbc.Driver

mybatis:
  mapper-locations: classpath:/mapper/*.xml
  type-aliases-package: com.example.demo.entity

logging:
  level:
    com.example.demo: debug
```

------

## 🧩 3. 数据库结构（users 表）

```
sql复制编辑CREATE TABLE users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(100) NOT NULL,
  role VARCHAR(20) DEFAULT 'ROLE_USER'
);
```

------

## 🧊 4. 实体类 + Mapper 接口

### User 实体

```
java复制编辑@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String role;
}
```

### UserMapper 接口

```
java复制编辑@Mapper
public interface UserMapper {
    User loadUserByUsername(@Param("username") String username);
}
```

### UserMapper.xml

```
xml复制编辑<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.example.demo.mapper.UserMapper">
    <select id="loadUserByUsername" parameterType="string" resultType="com.example.demo.entity.User">
        SELECT * FROM users WHERE username = #{username}
    </select>
</mapper>
```

------

## 🔐 5. 实现自定义认证逻辑

### CustomUserDetailsService

```
java复制编辑@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.loadUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        return org.springframework.security.core.userdetails.User
            .withUsername(user.getUsername())
            .password(user.getPassword())
            .roles(user.getRole().replace("ROLE_", ""))
            .build();
    }
}
```

------

## 🛡️ 6. Security 配置（关闭默认登录页）

```
java复制编辑@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login").permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 不使用 session
            .httpBasic(Customizer.withDefaults()); // REST风格使用 basic 或 JWT
        
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
        AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}
```

------

## 🔑 7. 登录接口（REST API）

```
java复制编辑@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(auth);

            return ResponseEntity.ok(Map.of("message", "登录成功"));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "用户名或密码错误"));
        }
    }
}
```

### LoginRequest DTO

```
java复制编辑@Data
public class LoginRequest {
    private String username;
    private String password;
}
```

------

## ✅ 测试方式（Postman 或前端）

- URL: `POST /login`
- Content-Type: `application/json`
- Body:

```
json复制编辑{
  "username": "testuser",
  "password": "123456"
}
```

------

## 🧪 插入测试数据

你可以加密密码后插入测试用户：

```
java复制编辑public static void main(String[] args) {
    System.out.println(new BCryptPasswordEncoder().encode("123456"));
}
sql复制编辑INSERT INTO users (username, password, role)
VALUES ('testuser', '$2a$10$abc123...', 'ROLE_USER');
```

------

## 🚀 可选增强（进阶）

- ✅ JWT 令牌认证替代 Session（推荐）
- ✅ 注册接口（写入数据库并加密密码）
- ✅ 角色权限控制（使用 `@PreAuthorize`）
- ✅ 用户状态/锁定字段扩展
- ✅ 异常处理统一返回 JSON 格式

------

要不要我帮你加上 JWT 登录/鉴权的版本？这在 REST 接口里比 Basic Auth 更适合前后端分离部署。你也可以说下你目前的前端框架或具体需求。