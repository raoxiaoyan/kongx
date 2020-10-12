# Quick Start


## 一、环境准备
### 1.1 Java环境

由于Quick Start会在本地启动服务端(目前内置Servlet容器:Tomcat)，所以需要在本地安装Java 1.8+。

在配置好后，可以通过如下命令检查：

```shell script
java -version
```

样例输出：

```text
java version "1.8.0_144"
Java(TM) SE Runtime Environment (build 1.8.0_144-b01)
Java HotSpot(TM) 64-Bit Server VM (build 25.144-b01, mixed mode)

```
Windows用户请确保JAVA_HOME环境变量已经设置。

### 1.2 MySql

- 版本要求：5.6.5+

Kongx的表结构对timestamp使用了多个default声明，所以需要5.6.5以上版本。

连接上MySQL后，可以通过如下命令检查：
```shell script
SHOW VARIABLES WHERE Variable_name = 'version';
```
### 1.3 下载安装包
- 从gitee下载：点击[发布版本](https://gitee.com/raoxy/kongx/releases)下载kong-serve-xxx.zip

## 二、安装步骤
### 2.1 创建数据库 kongx_serve
```text
注意：如果你本地已经创建过数据库，请注意备份数据。我们准备的sql文件会清空Kongx相关的表。
```
创建kongx_serve，通过各种MySQL客户端[sql/kongx.sql](https://gitee.com/raoxy/kongx/blob/master/scripts/db/mysql/kongx-1.2.x.sql)即可。

下面以MySQL原生客户端为例：
```sql
source /your_local_path/sql/kongx.sql
```
导入成功后，可以通过执行以下sql语句来验证：
```sql
select * from kongx_user_info;
```
### 2.2 部署kongx
将步骤 '1.3 下载安装包' 安装文件通过以下命令进行解压：
```shell script
unzip kongx-xxx.zip
```
目录结构如下所示：
```shell script
drwxr-xr-x. 2 root root       36 Oct 12 00:06 config
-rwxr-xr-x. 1 root root 37725270 Oct 12  2020 kongx-serve-1.2.x.jar
-rwxr-xr-x. 1 root root  2887214 Oct 12  2020 kongx-serve-1.2.x-sources.jar
-rw-r--r--. 1 root root 37096703 Oct 12 00:05 kongx-serve-1.2.x.zip
-rw-r--r--. 1 root root       52 Sep 28 11:38 kongx-serve.conf
drwxr-xr-x. 2 root root     4096 Oct 12 00:06 logs
drwxr-xr-x. 2 root root       43 Sep 29 05:50 scripts
```
### 2.3 配置数据库
服务端需要知道如何连接到你前面创建的数据库，所以需要编辑$kongx_home/conf/application.properties，修改kongx相关的数据库连接串信息。
```text
注意：填入的用户需要具备对kongx_serve数据的读写权限
```
```text
spring.datasource.url= jdbc:mysql://localhost:3306/kongx_serve?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true
spring.datasource.username= root
spring.datasource.password=
```
## 三、启动kongx
### 3.1 确保端口8095未被占用
例如，在Linux/Mac下，可以通过如下命令检查： 
```shell script
lsof -i:8095
```
### 3.2 执行启动脚本
```shell script
cd scripts
./startup.sh
```
当看到如下输出后，就说明启动成功了！
```shell script
Mon Oct 12 02:46:27 EDT 2020 ==== Starting ==== 
Started [8578]
Waiting for server startup....
Mon Oct 12 02:46:48 EDT 2020 Server started in 20 seconds!
```
### 3.3 执行关闭脚本
```shell script
cd scripts
./shutdown.sh
```

### 3.4 查看日志
默认日志路径/var/logs/kongx
```shell script
cd /var/logs/kongx
tail -f *.log
```

## 四、使用kongx
打开 http://localhost:8095/kongx 

登录：输入用户名admin,密码 123456 后即可登录.
