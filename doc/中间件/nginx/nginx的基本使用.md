>nginx的安装和配置
>
>```tex
>1. 下载包
>wget http://nginx.org/download/nginx-1.14.0.tar.gz
>2. 解压
>tar -zxvf nginx-1.14.0.tar.gz 
>3. 创建一个单独的目录，安装nginx
>mkdir /usr/local/src/nginx
>4. 进入到nginx解压根目录，配置
>cd /usr/local/src/nginx-1.14.0
>./configure --prefix=/usr/local/src/nginx
>5. 编译安装
>make && make install
>
>配置过程中可能会遇到缺少相关的依赖,安装相应的依赖即可，如
>yum install pcre-devel
>yum install zlib-devel
>安装完需要重新执行步骤4(配置)
>
>编译安装过程中，可能会遇到相应错误，参考下面解决方法
>https://blog.csdn.net/qq_43326744/article/details/109907803
>```
>
>nginx的启动和停止
>
>```tex
>1. 进入到安装目录
>cd /usr/local/src/nginx
>2. 进入到sbin目录执行
>./nginx          启动nginx
>./nginx -s stop  停止nginx
>3. 重新加载nginx配置文件
>./nginx -s reload
>4. 查看nginx配置参数
>./nginx -V
>```
>
>nginx虚拟主机配置
>
>```tex
>基于端口号的虚拟主机配置
>修改nginx.config，添加如下内容
>server {
>    listen 8080;
>    server_name localhost;
>    location / {
>        root     html;
>        index    index.html index.htm;
>    }
>}	
>```
>
>```tex
>基于ip的虚拟主机配置
>```
>
>```tex
>基于域名的虚拟主机配置
>修改nginx.config,添加如下内容
>server {
>    listen 80;
>    server_name bbs.gupao.com;
>    location / {
>    root   html;
>    index  bbs.html;
>    }
>}
>server {
>    listen 80;
>    server_name ask.gupao.com;
>    location / {
>    root    html;
>    index   ask.html;
>    }
>}
>```
>
>nginx的模块
>
>```tex
>nginx的模块可分为三类
>	1. 核心模块 (ngx_http_core_module)
>    2. 标准模块 (http模块)
>    3. 第三方模块  (http_stub_status_module    http_random_index_module)
>```
>
>```tex
>核心模块在nginx.config中的配置，如server段
>server{
>}
>```
>
>```tex
>ngx_http_access_module
>// 1. allow  2.deny
>server {
>    listen 80;
>    server_name ask.gupao.com;
>    location / {
>        deny all;
>        root    html;
>        index   ask.html;
>    }
>}
>```
>
>```tex
>如何添加第三方模块
>1. 原来所安装的配置，必须在重新安装新模块的时候，加上
>2. 不能直接make install
>安装方法
>./configure --prefix=/安装目录 --add-module=/第三方模块
>安装两个模块
>./configure --prefix=/usr/local/src/nginx --with-http_stub_status_module --with-http_random_index_module
>
>http_stub_status_module(监控nginx的状态信息)
>http_random_index_module(随机首页)
>
>配置
>location /status {
>	stub_status;
>}
>
>location / {
>	root html;
>	random_index on;
>	index   index.html index.htm;
>}
>```
>
>

>nginx反向代理功能配置
>
>```tex
>/usr/local/src/nginx/conf/nginx.conf文件配置
>
>#user  nobody;
>worker_processes  1;
>
>#error_log  logs/error.log;
>#error_log  logs/error.log  notice;
>#error_log  logs/error.log  info;
>
>#pid        logs/nginx.pid;
>events {
>    worker_connections  1024;
>}
>http {
>   include mime.types;
>   default_type application/octet-stream;
>   sendfile on;
>   keepalive_timeout 60;
>   include extra/*.conf;
>}
>
>/usr/local/src/nginx/conf/extra/http.conf文件配置
>
>upstream tomcat {
>    server 192.168.43.128:8080;
>    server 192.168.43.203:8080;
>}
>server {
>    listen 80;
>    server_name localhost;
>    location / {
>       #proxy_pass http://192.168.43.203:8080;
>       proxy_pass http://tomcat;
>       proxy_set_header Host $host;
>       proxy_set_header X-Real-IP $remote_addr;
>       proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
>    }
>    # 拦截静态资源，访问statci-resource目录
>    location ~ .*\.(js|css|png|svg|ico|jpg)$ {
>       root static-resource; 
>    }
>}
>
>```

>防盗链
>
>```tex
>网站资源被转载到其他平台，通过其他平台访问(图片等资源)。而这些资源是放置在cdn上的，cdn通过流量来收费的，所以会造成网站的损失。防盗链技术就是为了防止其他平台访问这些资源。
>valid_refers
>```
>
>

>跨域访问
>
>```tex
>配置header字段
>```
>
>