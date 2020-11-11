@echo off

rem kongx serve db info
set kongx_serve_db_url="jdbc:mysql://kongx_db:3306/kongx_serve?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true"
set kongx_serve_db_username="root"
set kongx_serve_db_password=""

rem =============== Please do not modify the following content ===============
rem go to script directory
cd "%~dp0"

cd ..

rem package kongx-serve

echo "==== starting to build kongx ===="

call mvn clean package -DskipTests -pl kongx-serve -am -Dspring_datasource_url=%kongx_serve_db_url% -Dspring_datasource_username=%kongx_serve_db_username% -Dspring_datasource_password=%kongx_serve_db_password%

echo "==== building kongx finished ===="

pause
