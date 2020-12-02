## 升级向导
# 2.1.0 发布(2020/12/01)
### 1. 升级要点
> 1. 增加网关流水线功能
> 2. 修复网关插件表格显示错误问题
### 2. 直接升级到2.1.0
参考Kongx [Quick Start](https://www.kancloud.cn/raoxiaoyan/kongx/1984323)

### 3. 2.0.0升级到2.1.0
   3.1、 下载2.0.0的kongx安装包，具体安装步骤参考 [Quick Start](https://www.kancloud.cn/raoxiaoyan/kongx/1984323)
   
   3.2、 下载数据库升级脚本，[2.0.1 to 2.1.0.sql](upgrade/2.0.1to2.1.0log.md)，在原2.0.1版本数据库基础上执行即可。
### 4. 1.2.x 升级到2.1.0
   4.1 先由1.2.x升级到2.0.0
   
   4.2 再由2.0.0升级到2.1.0,参照 [3. 2.0.0升级到2.1.0]的升级步骤
# 2.0.2 发布(2020/11/26)
> 1. 修复issue#I26XHN:Kong 1.3.1 下添加路由失败
> 2. 修复插件下拉列表无法回显问题

直接升级即可
# 2.0.1 发布(2020/11/18)
### 1. 升级要点
> 1. 增加kong shell支持
> 2. 优化日志功能
### 2. 直接升级2.0.1
参考Kongx [Quick Start](https://www.kancloud.cn/raoxiaoyan/kongx/1984323)
### 3. 2.0.0升级到2.0.1
执行数据库脚本：
```sql
ALTER TABLE kongx_operation_log ADD ip VARCHAR (64);
```
# 2.0.0 发布(2020/11/10)
### 1.升级要点 
> 1. 增加certificate及ca_certificate
> 2. 增加upstream被动健康检查的配置
> 3. 所有页面由弹窗改为面包屑导航
> 4. 增加版本自适应功能，2.0.0可适配至kong所有版本（1.2.x、1.3.x,1.4.x,1.5.x,2.0.x)

### 2. 直接升级到2.0.0
参考Kongx [Quick Start](https://www.kancloud.cn/raoxiaoyan/kongx/1984323)
### 3. 1.2.x升级到2.0.0
由1.2.x升级到2.0.0只需要两个步骤：

2.1、 下载2.0.0的kongx安装包，具体安装步骤参考 [Quick Start](https://www.kancloud.cn/raoxiaoyan/kongx/1984323)

2.2、 下载数据库升级脚本，[1.2.x to 2.0.0.sql](upgrade/1.2.xto2.0.0log.md)，在原1.2.x版本数据库基础上执行即可。


