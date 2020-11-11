#!/bin/sh

# kongx serve db info
kongx_serve_db_url="jdbc:mysql://kongx_db:3306/kongx_serve?useUnicode=true&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true"
kongx_serve_db_username="root"
kongx_serve_db_password=""

# =============== Please do not modify the following content =============== #
# go to script directory
cd "${0%/*}"

cd ..

# package  kongx-serve
echo "==== starting to build kongx ===="

mvn clean package -DskipTests -pl kongx-serve -am -Dspring_datasource_url=$kongx_serve_db_url -Dspring_datasource_username=$kongx_serve_db_username -Dspring_datasource_password=$kongx_serve_db_password

echo "==== building kongx finished ===="
