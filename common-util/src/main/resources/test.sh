#!/bin/bash

# jdk安装步骤
# 1. 确保上传工具已经安装
yum install -y lrzsz

# 2. 开始上传tar.gz包
cd /usr/local/src

rz

# 此处有个问题,如何获取到用户上传包的文件名
read -s "输入上传jdk jar包名称" fileName
echo "you upload file name $fileName"

# 3. 解压包
curPath=echo "$(pwd)"

mkdir jdk8

tar -zxvf ${fileName} jdk8

tar -czvf jdk-8u11-linux-x64.tar.gz  /usr/local/src/jdk8

# 4. 修改/etc/profile文件
sourceFile=/etc/profile

varJavaHome='export JAVA_HOME=/usr/java/jdk1.8.0_152'
varJreHome='export JRE_HOME=${JAVA_HOME}/jre'
varClassPath='export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib'
varPath='export PATH=${JAVA_HOME}/bin:$PATH'

echo ${varJavaHome} >> ${sourceFile}
echo ${varJreHome} >> ${sourceFile}
echo ${varClassPath} >> ${sourceFile}
echo ${varPath} >> ${sourceFile}

# 5. 使/etc/profile文件生效
source /etc/profile


# 6. 验证
echo "jdk install has finished"
echo "$(java -version)"
