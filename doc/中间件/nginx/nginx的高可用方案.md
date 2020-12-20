###### `keepalived`

>`nginx`的高可用方案
>
>```tex
>keepalived:
>	轻量级的高可用方案基。
>	lvs四层负载均衡软件。
>	监控lvs集群系统中的各个服务节点的状态。
>	VRRP协议(虚拟路由冗余协议)。
>	linux2.4以后，lvs内置在linux内核中的。
>```
>
>`keepalived`配置步骤
>
>```tex
>1. 下载keepalived中间件, keepalived-2.0.7.tar.gz
>2. 解压
>	tar -zxvf keepalived-2.0.7.tar.gz
>3. 创建文件夹
>	mkdir /usr/local/src/keepalived
>4. 进入到keepalived解压目录
>	cd /usr/local/src/keepalived-2.0.7
>5. 配置
>	./configure --prefix=/usr/local/src/keepalived --sysconf=/etc
>6. 若是配置报错，安装相关的包
>	yum install openssl-devel
>7. 继续执行下步骤5
>6. 执行下面命令安装
>	make && make install
>	
>7. 进入到安装目录
>	cd /usr/local/src/keepalived
>8. 创建软连接
>	ln -s sbin/keepalived /sbin/
>9. 执行下面的命令	
>	cp /usr/local/src/keepalived-2.0.7/keepalived/etc/init.d/keepalived /etc/init.d/
>10. 执行下面的命令
>	chkconfig --add keepalived
>11. 执行下面的命令
>	chkconfig keepalived on
>12. 启动服务	
>	service keepalived start
>	service keepalived status
>13. 配置文件
>	vi /etc/keepalived/keepalived.conf
>14. MASTER节点和BACKUP节点的配置信息如下
>```
>
>现在有两台机器 `192.168.43.4` `192.168.43.93`。两台机器上分别部署`keepalived`和`nginx`。在`192.168.43.4`上面部署`keepalived`为`MASTER`，代理`192.168.43.4`上面的`nginx`。在`192.168.43.93`上面部署的`keepalived`为`BACKUP`，代理`192.168.43.93`上面的`nginx`。两个节点上面的`keepalived`的配置如下。由于需要检测`nginx`是否挂掉，提供了`nginx`状态检查的脚本，当脚本检测到`nginx`挂掉，会自动停止掉`MASTER`，后续请求落到`BACKUP`。
>
>```tex
>! Configuration File for keepalived
>global_defs {
>   router_id LVS_DEVEL
>   enable_script_security
>}
># 定义处理脚本
>vrrp_script nginx_status_process {
>   	script "/usr/local/src/nginx/sbin/nginx_status_check.sh"
>   	user root
>   	interval 3	
>}
>vrrp_instance VI_1 {
>    	state MASTER
>    	interface ens33
>    	virtual_router_id 51
>    	priority 100
>    	advert_int 1
>    	authentication {
>        		auth_type PASS
>        		auth_pass 1111
>    	}
>        virtual_ipaddress {
>            192.168.11.100
>        }
>        # 定义触发脚本
>        track_script {
>           nginx_status_process
>        }
>}
>virtual_server 192.168.11.100 80 {
>        delay_loop 6
>        lb_algo rr
>        lb_kind NAT
>        persistence_timeout 50
>        protocol TCP
>        real_server 192.168.43.4 80 {
>            weight 1
>            TCP_CHECK {
>                connect_timeout 3
>                retry 3
>                delay_before_retry 3
>            }
>        }
>}
>```
>
>```tex
>! Configuration File for keepalived
>global_defs {
>   	router_id LVS_DEVEL
>}
>vrrp_instance VI_1 {
>        state BACKUP
>        interface ens33
>        virtual_router_id 51
>        priority 50
>        advert_int 1
>        authentication {
>            auth_type PASS
>            auth_pass 1111
>        }
>        virtual_ipaddress {
>            192.168.11.100
>        }
>}
>virtual_server 192.168.11.100 80 {
>        delay_loop 6
>        lb_algo rr
>        lb_kind NAT
>        persistence_timeout 50
>        protocol TCP
>        real_server 192.168.43.93 80 {
>            weight 1
>            TCP_CHECK {
>                connect_timeout 3
>                retry 3
>                delay_before_retry 3
>            }
>        }
>}
>```
>
>`nginx`健康检查脚本`nginx_status_check.sh`
>
>```tex
>#!bin/sh
>A=`ps -C nginx --no-header | wc -l`
>if [ $A -eq 0 ]
>then
> service keepalived stop
>   fi
>     ```
>
>

###### `Openresty`

>`Openresty`安装步骤
>
>```tex
>1. 解压
>   tar -zxvf openresty-1.13.6.2.tar.gz
>2. 创建一个安装目录
>	mkdir /usr/local/src/openresty
>3. 配置
>	./configure --prefix=/usr/local/src/openresty
>4. 安装
>	make && make install
>5. 进入到相关目录
>	cd /usr/local/src/openresty/nginx/sbin
>6. 启动nginx
>	./nginx
>7. 访问	
>```
>
>`Openresty`案例1
>
>```tex
>1. 创建目录
>	mkdir /usr/local/src/openresty/calc
>2. 在calc目录下创建两个文件夹
>	cd /usr/local/src/openresty/calc
>	mkdir logs
>	mkdir conf
>3. 进入到conf目录，创建nginx.conf文件
>	cd /usr/local/src/openresty/calc/conf
>	vi nginx.conf
>4. nginx.conf的内容如下
>
>5. 进入到目录
>	cd /usr/local/src/openresty/nginx/sbin
>6. 执行下面命令，在指定的工作空间启动nginx
>	./nginx -p /usr/local/src/openresty/calc/
>	./nginx -s stop -p /usr/local/src/openresty/calc/  (停止指定工作空间的nginx)
>	./nginx -s reload -p /usr/local/src/openresty/calc/ (重新加载)
>7. 访问下面路径测试	
>	http://192.168.43.4/add?a=8&b=6
>	http://192.168.43.4/sub?a=8&b=6
>```
>
>`nginx.conf`
>
>```te
>worker_processes 1;
>error_log logs/error.log;
>events {
>  worker_connections 1024;
>}
>http {
>  server {
>    listen 80;
>    location /add {
>      content_by_lua_block {
>        local args=ngx.req.get_uri_args();
>        ngx.say(args.a+args.b);
>      }
>    }
>    location /sub {
>      content_by_lua_block {
>        local args=ngx.req.get_uri_args();
>        ngx.say(args.a-args.b);
>      }  
>    }
>  }
>}
>```
>
>`Openresty`案例2
>
>```tex
>1. 在下面目录创建lua文件夹
>	mkdir /usr/local/src/openresty/calc/lua
>2. 在lua文件夹下创建lua文件
>	add.lua  check.lua  params.lua  sub.lua
>3. 进入到下面目录，修改nginx.conf文件
>	cd /usr/local/src/openresty/calc/conf
>```
>
>`add.lua`
>
>```tex
>local args=ngx.req.get_uri_args();
>ngx.say(args.a+args.b);
>```
>
>`sub.lua`
>
>```tex
>local args=ngx.req.get_uri_args();
>ngx.say(args.a-args.b);
>```
>
>`params.lua`
>
>```tex
>local _M = {}
>function _M.is_number(...)
>  local arg={...};
>  local num;
>  for i,v in ipairs(arg) do
>    num=tonumber(v);
>    if nil == num then
>      return false;
>    end
>  end
>  return true;
>end
>return _M;
>```
>
>`check.lua`
>
>```tex
>local param=require("params");
>local args=ngx.req.get_uri_args();
>if not args.a or not args.b or not param.is_number(args.a,args.b) then
>  ngx.exit(ngx.HTTP_BAD_REQUEST);
>  return;
>end;
>```
>
>`nginx.conf`
>
>```tex
>worker_processes 1;
>error_log logs/error.log;
>events {
>  worker_connections 1024;
>}
>http {
>  lua_package_path '$prefix/lua/?.lua';
>  lua_code_cache off;
>  server {
>    listen 80;
>    location ~^/api/([-_a-zA-Z0-9/]+) {
>       access_by_lua_file lua/check.lua;
>       content_by_lua_file lua/$1.lua;
>    }
>  }
>}
>```
>
>`Openresty`案例3
>
>实现一个灰度发布的案例，现有一台`Openresty`机器`192.168.43.4`。两台tomcat服务器，`192.168.43.93`(普通服务)，`192.168.43.128`(灰度发布服务)。一台`redis`服务器`192.168.43.93`。
>
>客户端的请求首先到达`nginx`，`nginx`中的`lua`脚本会去判断客户端请求的`ip`地址，如果`ip`地址在`redis`的`gray`中，请求转发到`192.168.43.128`，如果没有，请求转发到`192.168.43.93`。
>
>```tex
>1. 创建gray目录
>	mkdir /usr/local/src/openresty/gray
>2. 在gray目录下创建三个目录
>	mkdir /usr/local/src/openresty/gray/conf
>	mkdir /usr/local/src/openresty/gray/logs
>	mkdir /usr/local/src/openresty/gray/lua
>3. 在lua文件夹下创建gray.lua文件
>	
>4. 在conf文件夹下创建nginx.conf文件
>
>```
>
>`gray.lua`
>
>```tex
>local redis=require "resty.redis";
>local red=redis:new();
>red:set_timeout(1000);
>local ok,err=red:connect("192.168.43.93", 6379);
>if not ok then
>  ngx.say("faile to connect redis");
>end
>local headers=ngx.req.get_headers();
>local local_ip = headers["X-Real-IP"] or ngx.var.remote_addr or "0.0.0.0";
>local ip_lists = red:get("gray");
>-- 若是灰名单中没有找到该ip,走普通服务
>if string.find(ip_lists,local_ip) == nil then
>   ngx.exec("@prod1");
>-- 灰名单中有该ip,走灰度发布服务
>else
>   ngx.exec("@prod2");
>end
>local ok,err=red:close();
>```
>
>```nginx.conf```
>
>```tex
>worker_processes 1;
>error_log logs/error.log;
>events {
>  worker_connections 1024;
>}
>http {
>  lua_package_path "$prefix/lualib/?.lua;;";
>  lua_package_cpath "$prefix/lualib/?.so;;";
>  upstream prod1{
>    server 192.168.43.93:8080;
>  }	
>  upstream prod2 {
>    server 192.168.43.128:8080;
>  }
>  server {
>    listen 80;
>    server_name localhost;
>    location / {
>       proxy_set_header X-Real-IP  $remote_addr;
>       content_by_lua_file lua/gray.lua;
>    }
>    location @prod1 {
>      proxy_pass http://prod1;
>    }
>    location @prod2 {
>      proxy_pass http://prod2;
>    }
>  }
>}
>```
>
>

