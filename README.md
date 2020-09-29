初始化

##设置版本号
mvn versions:set -DnewVersion=1.0.1

##设置错误回滚
mvn versions:revert
##提交
mvn versions:commit