# 初识linux



## Linux内核

linux系统的组成如下：

- Linux系统内核
- 系统级应用程序



- 内核提供系统最核心的功能，如：调度CPU、调度内存、调度文件系统、调度网络通讯、调度IO等。
- 系统级应用程序，可以理解为出厂自带程序，可供用户快速上手操作系统，如：文件管理器、任务管理器、图片查看、音乐播放

<img src="C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-03-15 134435.png" alt="屏幕截图 2025-03-15 134435" style="zoom:75%;" />



# 虚拟机

<img src="C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-03-15 135244.png" alt="屏幕截图 2025-03-15 135244" style="zoom: 67%;" />

## 虚拟机快照

通过快照可以实现虚拟机状态回滚。



## 虚拟机的克隆

右键虚拟机--管理--克隆

可以在本机克隆，也可以克隆到另一台计算机上



## 虚拟机的迁移删除

虚拟系统安装好了，它的本质就是文件，因此迁移和删除很方便，直接对文件夹进行操作就行了。



# Linux基础命令

## Linux的目录结构

Linux的目录结构是一个属性结构。Linux没有盘符的概念，只有一个根目录**/** ，所有文件都在它下面。

在windows系统中，路径之间的层级关系用\表示，而在Linux中，路径之间的层级关系用**/**来表示



linux下一切皆为文件，就算是硬件（如cpu）也被当作文件进行存储

## Linux命令入门

### Linux命令基础

<img src="C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-03-15 163307.png" alt="屏幕截图 2025-03-15 163307" style="zoom: 50%;" />



### ls命令入门

直接输入ls命令，表示列出当前工作目录(默认HOME目录)下的内容。

HOME目录：操作系统允许有多用户。每个Linux操作用户在Linux系统的个人账户目录，路径在`/home/用户名`



ls命令格式：`ls [-a -l -h] [Linux路径]`   ，中括号表示可选

- -a : 表示列出所有文件（包括隐藏的文件），而在Linux中，以 . 开头的是隐藏文件

- -l ：表示以列表（竖向）的形式展示内容，并展现更多信息

- -a/-al/-la :这三种都表示以列表形式展示所有文件

- -h：必须和-l 一起使用，用来更细致的展现文件的大小

命令是可以组合使用的，比如`ls -lah`，等同于`ls -a -l -h`



`ls -R`：递归的查看



`tree 目录`：树状显示目录

## 目录切换相关命令

通过cd更改当前工作目录，不写路径表示回到home目录



`pwd`命令查看当前所在工作目录的绝对路径。 (print work directory)



## 路径相关

绝对路径：以**根目录为起点**，描述路径的一种写法,路径描述以/开头

相对路径：以**当前目录为起点**，路径描述不需要/开头



特殊表示：

-  `.`  :表示当前目录
- `..`  : 表示上一级目录，`cd ../..`可返回上两级目录
- `~`：HOME目录



## 创建目录

`mkdir [-p] Linux路径`

mkdir :  make directory

- 参数必填
- -p可选，表示自动创建不存在的父目录，适用于创建连续多层级的目录



## 文件操作命令

`touch Linux路径` 创建文件

`cat [-n] Linux路径` 查看文件内容，全部显示，不能修改。-n表示显示行号

`cat ... | more`：分页浏览；enter来显示下一行，空格显示下一页

`more Linux路径` 查看文件内容，支持翻页。空格翻页，q退出查看

less:

<img src="C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-04-01 211938.png" alt="屏幕截图 2025-04-01 211938" style="zoom: 67%;" />



`cp [-r] 参数1 参数2`：

- -r : 用于复制文件夹使用，表示递归
- 参数一：被复制
- 参数二：复制目的地

`\cp`：用法同上，如果文件名相同覆盖目标文件夹中的文件

`mv 参数1 参数2`：移动文件/文件夹。在将文件（夹）移动至文件（夹）时，如果目标文件不存在，自身进行改名后移动



`rmdir 目录名`：只能删除空目录

`rm [-r -f] 参数1 ...参数n`：

- -r用于删除文件夹

- -f表示force，强制删除（不会提示确认信息）

  普通用户删除不会弹出提示，root会提示，所以一般用户用不到-f

  - 补充：用su -root切换root，用exit退出

- 参数用空格隔开

- rm 命令支持通配符:
  - test*表示任何以test开头的内容
  - *test以test结尾
  - `*test*`包含test



## 查找命令

`which 要查找的命令 ` 命令其实是一个个可执行的程序，相当于exe。which只能查找命令的位置

`find 起始路径 -name "被查找的文件名"` 搜索指定文件。find也可以使用通配符

`find 起始路径 -size [+ -]n[单位] ` 按文件大小查找（+ -表示大于小于）。n表示大小数字。单位有k、M、G

`locate 文件`：快速定位。 它是把所有目录结构创建到数据库中进行查找，所以速度很快。并且因此再执行locate命令前，先要执行`updatedb`指令来创建locate数据库



## grep、wc、管道符

`grep [-n -i] 关键字 文件路径`:从文件中通过关键字过滤文件行

- -n表示在结果中显示匹配的行的的行号
- -i表示忽略大小写
- 如果关键字带有空格或其他符号，用""将关键字包围
- 文件路径也可以作为内容输入端口

`wc [-c -m -l -w] 文件路径`：

- -c 统计bytes数量
- -m统计字符数量
- -l统计行数
- -w统计单词数量

- 参数：被统计的文件，可以作为内容输入端口



 管道符`|`：将左边命令的结果作为右边命令的输入。于是可以将左边结果当作上面两个可以作为输入端口的参数。

比如`ls -l /user/bin | grep gtf`, 输出`-rwxr-xr-x. 1 root root       19592  2月 27 00:37 gtf`

- 管道符可以嵌套



## echo、tail和重定向符

`echo 输出的内容` :在命令行输出指定的内容。比如`echo $HOSTNAME`输出环境变量信息

在使用echo pwd时我们本意是要输出当前工作路径，但是pwd被当作文本输出

用``将pwd包围表示将pwd当作命令执行。



重定向符：

- `>` 表示将左侧命令的结果，覆盖写入符号右侧指定的文件中。如果文件不存在则创建
- `>>`表示将左侧命令的结果，追加写入右侧指定文件 



`tail [-f -num] 路径`

- 参数：被查看的文件路径
- -f：表示持续跟踪。在命令不停止的情况下，对文件进行更改，tail输出的内容会同步更新。通过`ctrl+c`退出追踪
- -num：查看尾部多少行，不填默认十行。填写数字

`head -n 数字 文件`：查看文件前n行

## vi编辑器

visual interface 是linux中最经典的文本编辑器

vim是vi的加强版本，不仅能编辑文本，还能编辑shell程序、以不同颜色的字体来辨别语法的正确性



### 三种工作模式

- 命令模式：按键都被理解为命令。此模式下不能自由进行文本编辑
- 输入模式：就是编辑模式、插入模式。此模式下可以对文件内容进行自由编辑
- 底线命令模式：以`:`开头，通常用于文件的保存、退出

<img src="C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-03-15 185118.png" alt="屏幕截图 2025-03-15 185118" style="zoom: 67%;" />

指正：上图的iao为i或a或o。并且用:wq保存退出。:w只保存不退出,  :q只退出



### 使用

`vi 文件路径`

`vim 文件路径`

使用如上命令能够使用vi/vim编辑器编辑文本。后续全部使用vim

- 如果文件表示的路径不存在，此命令会用于编辑新文件





### 命令模式快捷键

### <img src="C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-03-15 190307.png" alt="屏幕截图 2025-03-15 190307" style="zoom:75%;" />



<img src="C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-03-15 190424.png" alt="屏幕截图 2025-03-15 190424" style="zoom:75%;" />

注：G是跳到最后一行行首

​	数字+ `shift+g` 跳转到指定行



在底线命令模式下：

​	`:set nu` 显示行号

​	`:set nonu` 不显示行号

​	

# 关机重启、注销

`shutdown -h now` ：立刻关机

`shutdown -h 1`：一分钟后关机

`shutdown -r now`：现在重启

`halt`：立刻关机

`reboot`：立刻重启

`sync`：把内存的数据同步到磁盘



**注**：虽然目前的shutdown/reboot/halt等命令均已经在关机前进行了sync，但是还是应该在重启或关闭系统之前，先运行sync



logout：注销用户；这个命令在图形运行级别无效，在运行级别3下有效（以后讲）



# 运行级别

![屏幕截图 2025-04-01 203907](C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-04-01 203907.png)



`systemctl get-default`：查看默认级别

`systemctl set-default TARGET.target`：设置默认级别。`multi-user.target`为等级3.`graphical.target`为等级5。



# 用户和权限





## root用户

在linux中，权限最大的账户为root（超级管理员）



`su [-] [用户名]`：

- `-`是可选的，表示是否在切换用户后加载环境变量，建议带上
- 省略用户名表示切换到root
- exit或`ctrl+d`退回上一个用户



`sudo 其他命令`

- 在其它命令之前，带上sudo，即可为这一条命令临时赋予root授权
- 经过sudo认证的普通用户才能使用sudo



配置sudo权限:

- 切换到root用户，执行visudo命令
- 在文件最后添加  `用户名 ALL=(ALL)       NOPASSWORD:ALL`

- 通过:wq保存







## 用户和用户组

Linux系统中可以：

- 配置多个用户
- 配置多个用户组
- 用户可以加入多个用户组

Linux中关于权限的管控级别有两个，分别是：

- 针对用户的权限控制
- 针对用户组的权限控制

比如，针对某文件，可以管控用户的权限，也可以控制用户组的权限



**以下命令需root执行**

- 创建用户组：`groupadd 组名`
- 删除：`groupdel 组名`
- `groupmod`：修改已有用户组的信息



- `useradd [-g -d] 用户名`
  - -g指定用户的组，不指定会创建用户的同名组并自动加入。
  - -d指定用户的HOME路径，不指定，默认在/home/用户名
- `userdel [-r] 名`：-r表示删除HOME目录。不加-r在删除用户时HOME目录保存
- `usermod -aG 用户组 用户名`：将指定用户加入指定用户组
- `usermod -d 目录名 用户名`：改变用户登录进入的初始目录



- `getent passwd`：查看当前系统中有哪些用户
- `getent group`：查看组



- `passwd 用户名`：修改密码。修改自己密码可以，修改别人密码需要root权限
- `passwd -l`：锁定账户
- `passwd -u`：解锁账户



- /etc/passwd文件：用户的配置文件，记录用户的各种信息
- /etc/shadow文件：口令的配置文件
- /etc/group文件：组的配置文件，记录Linux包含的组的信息
- 查看这些文件需要root权限



## 查看用户信息

- `id [用户名]`：查看用户信息，不写用户名查看自己
- `whoami`：查看登陆时使用的用户，使用了su命令后显示不变



## 查看权限控制信息

<img src="C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-03-15 200215.png" alt="屏幕截图 2025-03-15 200215" style="zoom:67%;" />

序号一的具体含义：

- 第一位：d表示文件夹，l表示软链接，-表示文件，c表示字符设备文件（如鼠标键盘），b是块设备（如硬盘）
- 2-4位：表示本用户对此文件的权限（r读、w写、x执行）
- 5-7位：表示本用户组其他用户对本文件的权限
- 8-10位：表示其他用户的权限

  <img src="C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-04-01 230333.png" alt="屏幕截图 2025-04-01 230333" style="zoom: 50%;" />

所以，如果要对目录内的文件进行操作，必须要对该目录有一定的权限

## 修改权限控制

### chmod

chmod命令修改文件、文件夹的权限信息

**只有文件所属用户及root可以修改**

`chmod [-R] 权限 文件或文件夹`：-R表示对文件夹内所有文件执行操作

示例:`chmod u=rwx,g=rx,o=x hello.txt`

简洁写法：`chomod 751 文件名`  。r为4，w为2，x为1



`chmod o+w 文件/目录名`：给其他组加上w权限

`chomd a-x 文件/目录名`：给所有去掉x权限

### chown

可以修改文件、文件夹所属用户和用户组。（只有root）



`chown [-R] [用户][:][用户组] 文件/文件夹`

- -R：同chmod规则

<img src="C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-03-15 202326.png" alt="屏幕截图 2025-03-15 202326" style="zoom:75%;" />



# 实用操作

## 小技巧

`ctrl+c`：强制停止/输入错误直接终止输入跳到下一行

`ctrl+d`：退出当前账户的登录/退出某些特定程序的专属页面（不能用于vi/vim）

`history`：查看输入的历史命令

`!命令前缀`：执行上一次匹配前缀的命令

`! 数字`：执行history展示出的指定行数的命令

`ctrl+r`：输入内容去匹配历史命令

`ctrl+a`跳到命令开头

`ctrl+e`:跳到命令结尾

`ctrl+l`或clear  ：清空



## 安装

yum：RPM包软件管理器

`yum [-y] [install remove search] 软件名`

- -y：自动确认

yum命令需要联网、root权限







## 软链接

软链接：将文件、文件夹链接到其他位置。（类似于win中的快捷方式，打开的是本体）

`ln -s 参数一 参数二`

- 参数1：原文件或目录
- 参数二：软链接名



## 日期

`date [+格式化字符串]`



<img src="C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-03-15 205849.png" alt="屏幕截图 2025-03-15 205849" style="zoom: 67%;" />

 `date "+%Y-%m-%d %H:%M:%S"` 



`date -s 字符串时间`：将字符串时间变成默认格式输出



`cal [选项]`：不加选项，显示本月日历



## 环境变量

$PATH :取出PATH环境变量的值

echo $PATH  :输出

- 临时设置：`export 变量名=变量值`
- 永久生效：
  - 针对当前用户：配置在~/bashirc文件中
  - 针对所有用户：配置在/etc/profile中，
  - 并通过`source 配置文件` 立刻生效



## 下载和上传

`rz`:从win上传文件

`sz 文件`：下载文件到win



## 压缩解压



![屏幕截图 2025-04-01 220437](C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-04-01 220437.png)





`zip [-r] 目标.zip 要压缩的文件或目录...`:

-r: 被压缩的包包含文件夹

`unzip [-d 目的地] .zip`

-d:指定要解压去的位置。不写默认解压到当前文件夹



# 任务调度

任务调度：是指系统在某个时间执行特定的命令或程序

任务调度分类：

1. 系统工作：有些重要任务必须周而复始的执行，比如病毒扫描
2. 个别用户工作：个别用户可能希望定时执行某些程序，比如对mysql数据库的备份

## crond



基本语法：

`crontab [选项]`

常用选项：

- -e：编辑crontab定时任务
- -l：查询crontab任务
- -r：删除当前用户的所有crontab任务

`service crond restart`：重启任务调度



举例：

我们要每一分钟将/etc/目录下的信息写入/tmp/to.text

`*/1****ls -l /etc/ > /tmp/to.txt`

![屏幕截图 2025-04-02 000935](C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-04-02 000935.png)

<img src="C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-04-02 001058.png" alt="屏幕截图 2025-04-02 001058" style="zoom:75%;" />

![屏幕截图 2025-04-02 001655](C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-04-02 001655.png)



## at定时任务

`at [选项] [时间]`

Ctrl+D结束at输入

atq查看有没有未执行任务

atrm+编号：删除已经设置的任务

![屏幕截图 2025-04-02 002350](C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-04-02 002350.png)



![屏幕截图 2025-04-02 002507](C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-04-02 002507.png)



![屏幕截图 2025-04-02 002707](C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-04-02 002707.png)





# 磁盘分区、挂载

## 磁盘分区机制

![屏幕截图 2025-04-02 003746](C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-04-02 003746.png)



`lsblk` ：查看所有设备挂载情况



## 增加磁盘应用示例

![屏幕截图 2025-04-02 004405](C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-04-02 004405.png)



![屏幕截图 2025-04-02 004625](C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-04-02 004625.png)



![屏幕截图 2025-04-02 004843](C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-04-02 004843.png)



![屏幕截图 2025-04-02 005235](C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-04-02 005235.png)



## 磁盘情况查询



`df [-h]` :硬盘使用情况

-h  :单位



`du -h /目录`：查询指定目录的磁盘占用情况，默认为当前目录

-s：指定目录占用大小汇总

-a：含文件

--max-depth=1：子目录深度

-c：列出明细的同时，增加汇总值



`iostat [-x] num1 num2`

- -x:显示更多信息
- num1：刷新间隔
- num2：刷新几次



# 网络配置

## NAT网络配置原理

linux能访问百度的原理图：

<img src="C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-04-02 093100.png" alt="屏幕截图 2025-04-02 093100" style="zoom:67%;" />



## 网络配置指令

windows环境的vmnet8的配置：

cmd输入`ipconfig`



查看linux网络配置:

`ifconfig`



## 网络请求和下载

`ping  [-c num] ip或主机名`: 检查指定的网络服务器是否可联通

- -c :检查的次数，不使用-c将无限次检查



`wget [-b] url` :在命令行内下载网络文件

- -b 后台下载



`curl [-O] url`：发送http网络请求，可用于下载文件、获取信息

- -O:用于下载文件，当url是下载链接时，可以使用此选项保存文件



## 网络环境配置

将linux的ip地址设置为静态的

具体查看教程（需要了解网络相关知识）





## 主机名和hosts映射

**主机名：**

`hostnamectl set-hostname 名`:设置主机名

`hostname`：查看主机名



**DNS（Domain Name System）**

DNS：域名系统，是互联网上作为域名和IP地址互相映射的一个分布式数据库



<img src="C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-04-02 122102.png" alt="屏幕截图 2025-04-02 122102" style="zoom:75%;" />

即 浏览器缓存-->本地DNS缓存-->hosts文件-->域名服务DNS

如果都没有，返回域名不存在



**hosts映射：**

hosts：一个文本文件，用来记录IP和主机名（hostname）的映射关系

通过主机名找到某个linux系统

<img src="C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-04-02 121037.png" alt="屏幕截图 2025-04-02 121037" style="zoom:67%;" />





# *进程管理

## 进程

- LINUX中，每个执行的程序都称为一个进程。每一个进程都分配一个ID号（pid，进程号）

- 每个进程都可能以两种方式存在：前台和后台。前台进程就是用户目前的屏幕上可以进行操作的；后台进程则是实际在操作，但屏幕上无法看到的进程，通常使用后台方式执行。
- 一般系统的服务都是以后台进程的方式存在，而且都会常驻在系统中，直到关机才结束。



`ps []`：查看进程信息

- -e :显示全部信息
- -f :以完全格式化的形式展示全部信息
- -a：显示当前终端所有进程信息
- -u：以用户的格式显示进程信息
- -x：显示后台进程运行的参数

一般结合|、more和grep使用

<img src="C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-04-02 130813.png" alt="屏幕截图 2025-04-02 130813" style="zoom: 67%;" />

<img src="C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-04-02 131648.png" alt="屏幕截图 2025-04-02 131648" style="zoom:67%;" />



`kill [-9] 进程ID`

- -9 :强制关闭
- 不会杀死子进程

`killall 进程名`：通过进程名杀死进程

- 会杀死子进程
- 支持通配符 
- 在系统因负载过大而变得很慢时很有用

<img src="C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-04-02 132952.png" alt="屏幕截图 2025-04-02 132952" style="zoom:75%;" />



`pstree []`：更直观的查看进程信息

- -p：显示进程PID
- -u：显示进程所属用户（不是所有进程都有所属用户）



## 服务管理

服务（service）本质就是进程，但是是运行在后台的，通常都会监听某个端口，等待其他程序的请求。比如(mysqld，sshd，防火墙等)。  因此我们又称之为守护进程。

<img src="C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-04-02 133800.png" alt="屏幕截图 2025-04-02 133800" style="zoom:75%;" />



### **service管理指令**

`service 服务名 [start stop restart reload status]`

Centos7后很多服务不再使用service，而是systemctl

`systemctl [start stop status restart] 服务名`

systemctl管理的服务在/usr/lib/systemd/system中查看 

被service管理的服务在/etc/init.d查看



`setup`：查看全部服务；前面带有`*`号的是随着linux启动而自动启动的，可以通过光标移动到`*`位置，再空格来取消*号





### 服务运行级别

<img src="C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-04-02 134951.png" alt="屏幕截图 2025-04-02 134951" style="zoom:75%;" />



linux开机流程：

<img src="C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-04-02 135113.png" alt="屏幕截图 2025-04-02 135113" style="zoom:75%;" />



运行级别的切换在上面的`运行级别`中讲过





**systemctl设置服务自启动状态**

因为3级别和5级别是常用的，所以以下指令在设置的时候默认是改变这两个级别的是否自启状态

`systemctl list-unit-files [|grep 服务名]`：查看服务是否开机自启

``systemctl  enable 服务名`：设置开机自启

``systemctl disable 服务名`：关闭

``systemctl  is-enabled 服务名`：查看某个服务是否自启



### firewall

生产环境中需要将防火墙打开，但是如果打开，外部请求数据包就不能跟服务器监听端口通讯。这时，需要打开指定的端口。比如80、22等。



测试连接：

`telnet Ip 端口`

查看协议:

`netstat -anp | more`



`firewall-cmd --permanent --add-port=端口号/协议`：打开端口

`firewall-cmd --permanent --remove-port=端口号/协议`：关闭端口

`firewall-cmd --reload`：重新载入才能生效

`firewall-cmd --query-port=端口号/协议`：查询端口是否开放

![屏幕截图 2025-04-02 144640](C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-04-02 144640.png)



### 动态监控管理

top与ps命令很相似，都用来显示正在执行的进程。top与ps最大的不同之处在于top可以动态更新。



`top []`：

- `-d 秒数`：指定每几秒更新，默认3秒
- -i：使top不显示任何闲置或僵死进程
- -p：通过指定进程id来仅仅监控某个进程的状态。



显示出的每一列表示的信息:

- PR:优先级，数值越小，优先级越高。
- NI：进程的 nice 值。nice 值用于调整进程的优先级。正值表示低优先级，负值表示高优先级。
- VIRT:进程使用的虚拟内存总量。通常以 KB 或 MB 显示。
- RES：进程使用的常驻内存（Resident Memory）。
- SHR:进程使用的共享内存大小。表示进程与其他进程共享的内存部分，例如共享库。

- S:进程状态



使用top后输入：

- P:以CPU使用率排序，默认就是此项
- M：以内存使用率排序
- N：以PID从大到小排序
- q：退出top
- u：再输入用户名，回车，可以得到用户的进程
- k：再输入要结束的进程ID。如果要输入信号量，输入9强制删除



### *监控网络状态

`netstat []`：

- -an：按照一定的顺序输出
- -p：显示哪个进程在调用

<img src="C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-04-02 160848.png" alt="屏幕截图 2025-04-02 160848" style="zoom:75%;" />

proto表示网络协议

而local address 和 foreign address就比如说sshd和xshell的关系。其中前者IP地址为linux的IP地址；后者的IP地址为windows上vmnet8的IP地址。

state代表状态，LISTEN代表监听，ESTABLISHED表示已建立连接



# Shell编程

 ## 快速入门

Shell 是一个命令行解释器，它为用户提供了一个向Linux内核发送请求以便运行程序的界面系统级程序；用户可以用shell来启动、挂起、停止或编写一些程序。

<img src="C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-04-02 222847.png" alt="屏幕截图 2025-04-02 222847" style="zoom: 67%;" />

```sh
#!/bin/bash
echo "hello world!"
```

`./程序`进行执行



## Shell变量

分类

 Linux Shell中的变量分为系统变量和用户自定义变量。

系统变量：$HOME、$PWD、$SHELL、$USER等等。

显示当前shell中所有变量：set



定义

shell变量的定义：

- 定义变量：`变量名=值`
- 撤销变量：`unset 变量`
- 声名静态变量：`readonly 变量`，注意：不能unset



举例

```sh
#!/bin/bash
A=100
echo A=$A

unset A
echo A=$A

readonly B=100
echo B=$B
```

输出：

```sh
A=100
A=
B=100
```



定义变量的规则

- 变量名可以由字母、数字和下划线组成，但是不能以数字开头。
- 等号两侧不能有空格
- 变量名称一般习惯大写



<img src="C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-04-02 225435.png" alt="屏幕截图 2025-04-02 225435" style="zoom:75%;" />





## 设置环境变量

/etc/profile文件下

`export 变量名=变量值` ：设置环境变量

`source 配置文件`：让修改后的配置信息生效

`echo $变量名`：查询环境变量的值



## 多行注释

`:<<!     多行内容        !`



## 位置参数变量

<img src="C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-04-02 230543.png" alt="屏幕截图 2025-04-02 230543" style="zoom: 67%;" />

其实就是传参

## 预定义变量

就是shell设计者事先定义好的变量，可以直接在shell中使用



`$$`：当前进程的进程号

`$!`：后台运行的最后一个进程的进程号

`$?`：最后一次执行命令的返回状态。如果这个变量的值为0，证明上一个命令正确执行；如果非0（具体哪个数，由命令自己来决定），则证明上一个命令执行不正确。



## 运算符

<img src="C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-04-02 231423.png" alt="屏幕截图 2025-04-02 231423" style="zoom:75%;" />



## 条件判断





## 定时备份数据库

![屏幕截图 2025-04-02 232013](C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-04-02 232013.png)



思路梳理：
<img src="C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-04-02 233151.png" alt="屏幕截图 2025-04-02 233151" style="zoom:75%;" />



shell脚本：

```sh
#!/bin/bash
#备份目录
BACKUP=/data/backup/db
#当前时间
DATETIME=$(date +%Y-%m-%d_%H%M%S)
echo $DATETIME

#数据库的地址
HOST=localhost
#数据库的用户名
DB_USER=root
#数据库密码
DB_PW=abc123
#备份的数据库名
DATABASE=muite

#创建备份目录,如果不存在的话
[ ! -d "${BACKUP}/${DATETIME}" ] && mkdir -p "${BACKUP}/${DATETIME}"

#备份数据库
mysqldump -u${DB_USER} -p${DB_PW} --host=${HOST} -q -R --databases ${DATABASE} | gzip > ${BACKUP}/${DATETIME}/$DATETIME.sql.gz

#将文件处理成tar.gz
cd ${BACKUP}
tar -zcvf $DATETIME.tar.gz ${DATETIME}
#删除对应的备份目录
rm -rf ${BACKUP}/${DATETIME}

# 删除10天前的备份文件
find ${BACKUP} -atime +10 -name "*.tar.gz" -exec rm -rf {} \;
echo "备份数据库${DATABASE}成功"

```



crond：

```
30 2 * * * /usr/sbin/mysql_db_backup.sh
```





# APT

## APT原理

advanced packaging tools

安装包管理工具，在ubuntu下，我们可以使用apt命令进行软件包的安装、删除、清理等，类似于windows中的软件管理工具。



原理示意图：

<img src="C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-04-03 080405.png" alt="屏幕截图 2025-04-03 080405" style="zoom:75%;" />



## 相关命令



`sudo apt-get update`：更新源

`sudo apt-get install package`：下载

`sudo apt-get remove package`：删除

`sudo apt-cache show package`：查看软件相关信息

`sudo apt-get source package`：下载该包的源代码



备份文件：

`sudo cp 文件 目标文件`



## 远程登录

ssh：
ssh是Secure Shell 的缩写，是建立在应用层和传输层基础上的安全协议

常用于远程登陆。如果A机器想被B机器远程控制，那么A机器需要安装SSH服务器，B机器需要安装SSH客户端。





从一台Linux远程登录到另一台Linux：（在创建服务器集群时，会使用到该技术）

基本语法：`ssh 用户名@IP`

`exit或logout`：登出



# *日志管理

/var/log是系统日志文件的保存位置



常用日志：
`/var/log/boot.log`：系统启动日志

`/var/log/cron`：记录与系统定时任务相关的日志

`/var/log/lasllog`：记录系统中所有用户最后一次登录时间。这个文件是二进制文件，要用lastlog查看

`/var/log/mailog`：记录邮件信息

`/var/log/message`：记录系统重要信息。如果系统出现问题，**首先要检查的就是这个日志**

`/var/log/secure`：记录验证和授权方面的信息，只要涉及**账户和密码**的程序都会记录。比如系统的登录、ssh的登陆、su切换用户、sudo授权，甚至添加用户和修改用户密码。

`/var/log/ulmp`：记录当前已经登录的用户的信息。这个文件会随着用户的登录和注销不断变化，**只记录当前登录用户的信息**。这个文件**不能用vi查看**，而要用w、who、users等命令查看。



## 日志管理服务

有一个rsyslogd后台程序（服务）帮我们记录日志信息。

<img src="C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-04-03 092119.png" alt="屏幕截图 2025-04-03 092119" style="zoom:75%;" />

## 日志服务配置文件

`ps aux |grep "rsyslog" |grep -v "grep"`：查询Linux中的`rsyslogd`服务有没有启动。其中`-v`表示查找不含"grep"的服务（用于过滤本条命令）

`systemctl list-unit-files | grep rsyslog`：查询`rsyslogd`服务的自启状态



可以在`/etc/rsyslog.d/50-default.conf`中查看日志记录默认规则

<img src="C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-04-03 092427.png" alt="屏幕截图 2025-04-03 092427" style="zoom: 67%;" />



<img src="C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-04-03 092444.png" alt="屏幕截图 2025-04-03 092444" style="zoom:67%;" />

## 自定义日志服务

可以在`/etc/log/rsyslog.d/`目录下创建新的.conf文件自定义日志服务。然后使用`sudo systemctl restart rsyslog`重新加载日志服务以刷新。

原理是在`/etc/rsyslog.conf`文件下使用了`$IncludeConfig /etc/rsyslog.d/*.conf`导入了日志记录规则



## 日志轮替

<img src="C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-04-03 101459.png" alt="屏幕截图 2025-04-03 101459" style="zoom:75%;" />





![屏幕截图 2025-04-03 101822](C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-04-03 101822.png)



![屏幕截图 2025-04-03 102649](C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-04-03 102649.png)



日志轮替机制：

<img src="C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-04-03 103036.png" alt="屏幕截图 2025-04-03 103036" style="zoom:75%;" />





## 内存日志

有些内存在系统运行时是写在内存中，没有写到文件中，比如内核相关的日志。重启会清空

<img src="C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-04-03 104036.png" alt="屏幕截图 2025-04-03 104036" style="zoom:75%;" />



# 定制自己的Linux

Linux启动流程：

<img src="C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-04-03 105853.png" alt="屏幕截图 2025-04-03 105853" style="zoom: 67%;" />



原理见：

【【小白入门 通俗易懂】韩顺平 一周学会Linux】https://www.bilibili.com/video/BV1Sv411r7vd?p=126&vd_source=c054be8430afebb3d00e5f2d0b77f9fc

后面画了一张原理图



实操见：

【【小白入门 通俗易懂】韩顺平 一周学会Linux】https://www.bilibili.com/video/BV1Sv411r7vd?p=127&vd_source=c054be8430afebb3d00e5f2d0b77f9fc





# 升级内核

`apt list --upgradable | grep linux-image`：查看可升级的版本，无输出则说明没有可用升级版本

`sudo apt install linux-image-6.8.0-57-generic`：升级指定版本

`dpkg --list | grep linux-image`：查看所有已安装的内核



# 备份与恢复

方式：

- 把需要的文件（或分区）用TAR打包，下次需要恢复的时候，再解压覆盖
- 使用dump和restore命令



参考：https://blog.csdn.net/Zheng__Huang/article/details/108325467

 ## 备份

### 示例操作

#### 1. 创建一个完整备份

假设你想对 `/home` 目录进行完整备份，并将备份文件保存为 `/backup/home.dump`:

```
sudo dump -0uf /backup/home.dump /home
```

- `-0u`: 指定备份级别为 0（完全备份），并且更新 `/etc/dumpdates` 文件。
- `-f`: 指定输出文件或设备。

#### 2. 创建一个增量备份

如果你已经创建了一个完整的备份，现在想创建一个增量备份（例如级别 1），可以运行：

```
sudo dump -1uf /backup/home_level1.dump /home
```

这会备份自上次级别 0 备份以来更改过的所有文件。

#### 3. 使用压缩选项

为了节省空间，你可以在备份时使用压缩：

```
sudo dump -0uj /backup/home.dump.gz /home
```

这里的 `-j` 选项告诉 `dump` 使用 bzip2 压缩备份数据。

#### 4.注意

只有分区支持增量备份，目录或文件不支持

上面对home进行增量备份也只有当home是分区时才能够成功执行。

#### 5.查看

`dump -W`：显示备份的文件及其最后一次备份的层级、时间、日期

`cat /etc/dumpdates`：查看各次备份的时间



## 恢复

<img src="C:\Users\14693\Desktop\Screenshots\屏幕截图 2025-04-03 131122.png" alt="屏幕截图 2025-04-03 131122" style="zoom:75%;" />

在使用 `restore` 恢复数据之前，运行 `restore -Cf` 可以帮助你确认备份文件是否完好无损，避免在恢复过程中遇到问题。

举例：
`restore -C -f  /root/boot.dump.1`