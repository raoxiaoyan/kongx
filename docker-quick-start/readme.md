#Docker quick start
## 1. docker环境准备
docker及docker-compose的安装，请参考：

https://www.runoob.com/docker/centos-docker-install.html

https://www.runoob.com/docker/docker-compose.html
## 2. 安装准备
将docker-quick-start 上传到linux目录下（如：/opt/kongx）
## 3. 下载安装包
从gitee下载：点击[发布版本](https://gitee.com/raoxy/kongx/releases)下载kongx-serve-2.0.0.zip（此处仅示例，请根据需要下载对应的版本）
下载后，安装目录结构如下显示：
```text
./
├── conf
│   └── my.cnf
├── docker-compose.yml
├── Dockerfile
├── kongx-serve-2.0.0.zip
└── sql
    └── kongx.sql

```
## 4. 开始部署
```shell script
docker-compose build
```
```text
[root@localhost docker-quick-start]# docker-compose build
kongx_db uses an image, skipping
Building kongx_serve
Step 1/9 : FROM openjdk:8-jre-alpine
 ---> f7a292bbb70c
Step 2/9 : ENV KONGX_RUN_MODE "Docker"
 ---> Using cache
 ---> 2f0edf749734
Step 3/9 : ENV VERSION 2.0.0
 ---> Using cache
 ---> e800cc68a4aa
Step 4/9 : ENV SERVER_PORT 8095
 ---> Using cache
 ---> 5be1e9d2e646
Step 5/9 : RUN     echo "http://mirrors.aliyun.com/alpine/v3.8/main" > /etc/apk/repositories &&     echo "http://mirrors.aliyun.com/alpine/v3.8/community" >> /etc/apk/repositories  &&     apk update upgrade &&     apk add --no-cache procps curl bash unzip tzdata &&     ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime &&     echo "Asia/Shanghai" > /etc/timezone &&     mkdir -p /kongx-serve
 ---> Using cache
 ---> b608753f9c61
Step 6/9 : ADD kongx-serve-${VERSION}.zip /kongx-serve/kongx-serve-${VERSION}.zip
 ---> ee7d59225dff
Step 7/9 : RUN unzip /kongx-serve/kongx-serve-${VERSION}.zip -d /kongx-serve     && rm -rf /kongx-serve/kongx-serve-${VERSION}.zip     && chmod +x /kongx-serve/scripts/startup.sh
 ---> Running in 27e8844c565f
Archive:  /kongx-serve/kongx-serve-2.0.0.zip
   creating: /kongx-serve/scripts/
  inflating: /kongx-serve/kongx-serve.conf  
 extracting: /kongx-serve/kongx-serve-2.0.0-sources.jar  
  inflating: /kongx-serve/scripts/startup.sh  
  inflating: /kongx-serve/kongx-serve-2.0.0.jar  
  inflating: /kongx-serve/config/application.properties  
  inflating: /kongx-serve/scripts/shutdown.sh  
Removing intermediate container 27e8844c565f
 ---> 253a1bacf32d
Step 8/9 : EXPOSE $SERVER_PORT
 ---> Running in 7c4adb442eee
Removing intermediate container 7c4adb442eee
 ---> 865948e2336f
Step 9/9 : CMD ["/kongx-serve/scripts/startup.sh"]
 ---> Running in e688eb30d605
Removing intermediate container e688eb30d605
 ---> 2cbb91315c9d
```
## 5. 启动kongx
```shell script
docker-compose up -d
```
```text
[root@localhost docker-quick-start]# docker-compose up -d
Starting kongx_db ... done
Starting kongx_serve ... done
[root@localhost docker-quick-start]# 
```

## 6. 访问kongx
http://docker-host-ip:8095/

## 7. 访问数据库
http://docker-host-ip:13306/kongx_serve

用户：root 

密码：无 

端口号：13306