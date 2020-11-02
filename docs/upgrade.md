## 升级向导
- 1.3.0发布(2020/11/10)
> 1. 增加certificate及ca_certificate
> 2. 增加upstream被动健康检查的配置
> 3. 所有页面由弹窗改为面包屑导航
> 4. 增加版本自适应功能，1.3.0可适配至kong所有版本（1.2.x、1.3.x,1.4.x,1.5.x,2.0.x,2.1.x)

## 1. 升级到1.3.0
参考Kongx [Quick Start](https://www.kancloud.cn/raoxiaoyan/kongx/1984323)
## 2. 1.2.x升级到1.3.0
由1.2.x升级到1.3.0只需要两个步骤：

2.1、 下载1.3.0的kongx安装包，具体安装步骤参考 [Quick Start](https://www.kancloud.cn/raoxiaoyan/kongx/1984323)

2.2、 下载数据库升级脚本，[1.2.xto1.3.0.sql](./upgrade/1.2.xto1.3.xlog.md)，在原1.2.x版本数据库基础上执行即可。
