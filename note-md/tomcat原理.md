# 文件结构

- bin目录用于存放可执行的二进制文件，其中`startup.bat`用于启动服务器。`shutdown.bat`用于停止服务器。
- conf目录用于存放配置文件，`server.xml`存放服务器的关键配置信息
- webapps目录存放的是默认主机的网站内容
  - **这里的主机并非一台电脑，而是一台tomcat管理的虚拟主机，在访问时，端口号前的域名就是主机的名字。**
  - **每一台虚拟主机的内容都存放于自己的文件，正是因为它是一个文件夹，而不是真正的电脑，所以被称为虚拟主机**







# Server.xml内容（tomcat结构）

<img src="C:\Users\14693\Desktop\note\assets\屏幕截图 2025-04-19 133114.png" alt="屏幕截图 2025-04-19 133114" style="zoom: 50%;" />

这是一个树形目录，根节点是`server`，server就是tomcat所对应的类，它只会被实例化一次。



`server`节点下可能会有多个`service`节点，但我们通常只有一个，叫做`Catalina`。service组件的作用就是对外提供服务。

`service`内部包括一组连接器(Connector)和一个引擎（engine），连接器负责通信，引擎负责请求的处理。

- 在配置文件中，需要指定connector监听的端口和采用的通信协议。默认情况下，采用http协议的connector监听的端口是80端口。采用https协议的连接器监听的端口是443端口。



连接器的功能：

- 监听网络端口
- 接收网络请求
- 读取请求中的网络字节流
- 将请求字节流转换成request对象
- 以request为参数调用servlet容器（在引擎中），获取引擎返回的response对象
- 将response对象转换成响应字节流
- 将响应字节流发给浏览器



引擎：

- 引擎是个容器，里面包含一个或多个host对象（也即虚拟主机）

- 在配置文件中，应该指定虚拟主机的主机名以及它内容所存放的文件夹（比如webapps存放默认，再创建一个wptwebapps文件夹用于存放新的虚拟主机内容）

  <img src="C:\Users\14693\Desktop\note\assets\屏幕截图 2025-04-19 134548.png" alt="屏幕截图 2025-04-19 134548" style="zoom: 67%;" />

- 为了使得浏览器在输入`www.captainjack.com`主机名时访问本机的IP地址，需要在`C:\Windows\System32\drivers\etc`文件夹下的hosts文件中加入`127.0.0.1 www.captainjack.com`,用来在本地进行域名解析。

  这样一来，无论是输入`localhost`还是`www.captainjack.com`都会被解析为127.0.0.1，

  <img src="C:\Users\14693\Desktop\note\assets\屏幕截图 2025-04-19 134919.png" alt="屏幕截图 2025-04-19 134919" style="zoom: 33%;" />

  也就是说，我们通过访问相同的IP地址和相同的端口号，访问到了两个不同的虚拟主机，这是怎么做到的呢?

  通过f12网络抓包可以发现，消息请求头中会带主机名。依此进行判断。

  请求消息中的主机名会被连接器给识别出来，并且放到request对象中；引擎根据request对象中的目标主机名将request对象派发给响应的主机来处理。

  这就是一台电脑同一个端口能部署多个网站的原因。



一台虚拟主机上通常又挂有多个Web应用，一个应用对应着一个context对象

在配置文件中，要将应用作为主机的子节点：

<img src="C:\Users\14693\Desktop\note\assets\屏幕截图 2025-04-19 135741.png" alt="屏幕截图 2025-04-19 135741" style="zoom: 50%;" />

两个关键参数：

- `docBase`：它的值是应用文件夹所在的物理地址
- `path`：应用所对应的URL地址



一个context又包含多个不同的servlet，每一个servlet可以有一个或多个实例，同一个servlet的所有实例会被放在容器wrapper中进行管理。

<img src="C:\Users\14693\Desktop\note\assets\屏幕截图 2025-04-19 140203.png" alt="屏幕截图 2025-04-19 140203" style="zoom: 50%;" />

在这里，engine、host、context、wrapper都是容器，通过容器，电脑上的servlet被进行了详细的分类管理。

连接器给出的request对象里包含了servlet的详细路径（主机名、应用名、servlet名），所以各级容器收到request对象后能够将其正确派发给下一级容器，最后达到目标servlet对象。这个层层转发过程实际上就是函数的层层调用过程。

调用完成后，连接器会收到response对象，连接器再将response对象转换为字节流返回给浏览器。





# 连接器

Tomcat 连接器框架——[Coyote](https://zhida.zhihu.com/search?content_id=168382580&content_type=Article&match_order=1&q=Coyote&zhida_source=entity)

### 连接器核心功能

一、监听网络端口，接收和响应网络请求。

二、网络字节流处理。将收到的网络字节流转换成 Tomcat Request 再转成标准的 [Servlet](https://zhida.zhihu.com/search?content_id=168382580&content_type=Article&match_order=1&q=Servlet&zhida_source=entity)Request 给容器，同时将容器传来的 ServletResponse 转成 Tomcat Response 再转成网络字节流。

### 连接器模块设计

为满足连接器的两个核心功能，我们需要一个通讯端点来监听端口；需要一个处理器来处理网络字节流；最后还需要一个适配器将处理后的结果转成容器需要的结构。

<img src="C:\Users\14693\Desktop\note\assets\屏幕截图 2025-04-19 141101.png" alt="屏幕截图 2025-04-19 141101" style="zoom: 67%;" />

对应的源码包路径 `org.apache.coyote` 。对应的结构图如下

<img src="C:\Users\14693\Desktop\note\assets\屏幕截图 2025-04-19 141107-1745050133120.png" alt="屏幕截图 2025-04-19 141107" style="zoom: 67%;" />





# 容器

Tomcat 容器框架——[Catalina](https://zhida.zhihu.com/search?content_id=168382580&content_type=Article&match_order=1&q=Catalina&zhida_source=entity)

### 容器结构分析

每个 Service 会包含一个容器。容器有一个引擎可以管理多个虚拟主机。每个虚拟主机可以管理多个 Web 应用。每个 Web 应用会有多个 Servlet 包装器。Engine、Host、Context 和 Wrapper，四个容器之间属于父子关系。

<img src="C:\Users\14693\Desktop\note\assets\v2-be875676edea11c75bb86e4ccdef35f9_1440w.jpg" alt="img" style="zoom: 80%;" />

对应的源码包路径 `org.apache.coyote` 。对应的结构图如下

<img src="C:\Users\14693\Desktop\note\assets\v2-ac73403bc24ad4f16fee47cd87a5ea45_1440w.jpg" alt="img" style="zoom:80%;" />

### 容器请求处理

容器的请求处理过程就是在 Engine、Host、Context 和 Wrapper 这四个容器之间层层调用，最后在 Servlet 中执行对应的业务逻辑。各容器都会有一个通道 Pipeline，每个通道上都会有一个 Basic Valve（如StandardEngineValve）， 类似一个闸门用来处理 Request 和 Response 。其流程图如下。

<img src="C:\Users\14693\Desktop\note\assets\屏幕截图 2025-04-19 141107-1745050631554.png" alt="屏幕截图 2025-04-19 141107" style="zoom:67%;" />

# SpringBoot如何启动内嵌的Tomcat

SpringBoot 一键启动服务的功能，让有很多刚入社会的朋友都忘记 Tomcat 是啥。随着硬件的性能越来越高，普通中小项目都可以直接用内置 Tomcat 启动。但是有些大一点的项目可能会用到 Tomcat 集群和调优，内置的 Tomcat 就不一定能满足需求了。

我们先从源码中分析 SpringBoot 是如何启动 Tomcat，以下是 SpringBoot 2.x 的代码。

代码从 main 方法开始，执行 run 方法启动项目。

```java
SpringApplication.run
```

从 run 方法点进去，找到刷新应用上下文的方法。

```java
this.prepareContext(context, environment, listeners, applicationArguments, printedBanner);
this.refreshContext(context);
this.afterRefresh(context, applicationArguments);
```

从 refreshContext 方法点进去，找 refresh 方法。并一层层往上找其父类的方法。

```java
this.refresh(context);
```

在 AbstractApplicationContext 类的 refresh 方法中，有一行调用子容器刷新的逻辑。

```java
this.postProcessBeanFactory(beanFactory);
this.invokeBeanFactoryPostProcessors(beanFactory);
this.registerBeanPostProcessors(beanFactory);
this.initMessageSource();
this.initApplicationEventMulticaster();
this.onRefresh();
this.registerListeners();
this.finishBeanFactoryInitialization(beanFactory);
this.finishRefresh();
```

从 onRefresh 方法点进去，找到 ServletWebServerApplicationContext 的实现方法。在这里终于看到了希望。

```text
protected void onRefresh() {
    super.onRefresh();

    try {
        this.createWebServer();
    } catch (Throwable var2) {
        throw new ApplicationContextException("Unable to start web server", var2);
    }
}
```

从 createWebServer 方法点进去，找到从工厂类中获取 WebServer的代码。

```java
if (webServer == null && servletContext == null) {
    ServletWebServerFactory factory = this.getWebServerFactory();
    // 获取 web server 
    this.webServer = factory.getWebServer(new ServletContextInitializer[]{this.getSelfInitializer()});
} else if (servletContext != null) {
    try {
        // 启动 web server
        this.getSelfInitializer().onStartup(servletContext);
    } catch (ServletException var4) {
        throw new ApplicationContextException("Cannot initialize servlet context", var4);
    }
}
```

从 getWebServer 方法点进去，找到 TomcatServletWebServerFactory 的实现方法，与之对应的还有 Jetty 和 Undertow。这里配置了基本的连接器、引擎、虚拟站点等配置。

```java
public WebServer getWebServer(ServletContextInitializer... initializers) {
    Tomcat tomcat = new Tomcat();
    File baseDir = this.baseDirectory != null ? this.baseDirectory : this.createTempDir("tomcat");
    tomcat.setBaseDir(baseDir.getAbsolutePath());
    Connector connector = new Connector(this.protocol);
    tomcat.getService().addConnector(connector);
    this.customizeConnector(connector);
    tomcat.setConnector(connector);
    tomcat.getHost().setAutoDeploy(false);
    this.configureEngine(tomcat.getEngine());
    Iterator var5 = this.additionalTomcatConnectors.iterator();

    while(var5.hasNext()) {
        Connector additionalConnector = (Connector)var5.next();
        tomcat.getService().addConnector(additionalConnector);
    }

    this.prepareContext(tomcat.getHost(), initializers);
    return this.getTomcatWebServer(tomcat);
}
```



服务启动后会打印日志

```java
o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8900 (http)
o.apache.catalina.core.StandardService   : Starting service [Tomcat]
org.apache.catalina.core.StandardEngine  : Starting Servlet Engine: Apache Tomcat/8.5.34
o.a.catalina.core.AprLifecycleListener   : The APR based Apache Tomcat Native library which allows optimal ...
o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 16858 ms
```





# 请求完整过程总结

1、用户点击网页内容，请求被发送到本机端口8080，被在那里监听的Coyote HTTP/1.1 Connector获得。



2、Connector把该请求交给它所在的Service的Engine来处理，并等待Engine的回应。



3、Engine获得请求localhost/test/index.jsp，匹配所有的虚拟主机Host。



4、Engine匹配到名为localhost的Host（即使匹配不到也把请求交给该Host处理，因为该Host被定义为该Engine的默认主机），名为localhost的Host获得请求/test/index.jsp，匹配它所拥有的所有的Context。Host匹配到路径为/test的Context（如果匹配不到就把该请求交给路径名为“ ”的Context去处理）。



5、path=“/test”的Context获得请求/index.jsp，在它的mapping table中寻找出对应的Servlet。Context匹配到URL PATTERN为*.jsp的Servlet,对应于JspServlet类。



6、构造HttpServletRequest对象和HttpServletResponse对象，作为参数调用JspServlet的doGet（）或doPost（）.执行业务逻辑、数据存储等程序。



7、Context把执行完之后的HttpServletResponse对象返回给Host。



8、Host把HttpServletResponse对象返回给Engine。



9、Engine把HttpServletResponse对象返回Connector。



10、Connector把HttpServletResponse对象返回给客户Browser。