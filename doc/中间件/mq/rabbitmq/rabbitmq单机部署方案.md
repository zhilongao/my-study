> 参考文档` https://gper.club/articles/7e7e7f7ff4g56gceg6e`



> ###### 安装Erlang 21.3
>
> ① 先安装一些必要的依赖
>
> 注意：因为每个人的操作系统环境是不一样的，缺少的依赖不同，根据提示安装即可。
>
> ```tex
> yum -y install gcc glibc-devel make ncurses-devel openssl-devel xmlto perl wget
> ```
>
> https://www.erlang.org/downloads/21.3
> 如果下载太慢了，可以把地址贴到迅雷里面，下载到本机，再上传到虚拟机
>
> ```tex
> wget http://erlang.org/download/otp_src_21.3.tar.gz
> tar -xvf otp_src_21.3.tar.gz
> cd otp_src_21.3
> ./configure --prefix=/usr/local/erlang
> ```
>
> configure的过程如果有err，要解决依赖的问题。
> 如果有APPLICATIONS INFORMATION，DOCUMENTATION INFORMATION，没有影响。
>
> ```tex
> make && make install
> ```
>
> 如果提示缺少`socat`
>
> ```tex
> yum install -y socat
> ```
>
> 配置`erlang`环境变量
>
> ```tex
> vim /etc/profile
> ```
>
> 加入一行
>
> ```tex
> export PATH=$PATH:/usr/local/erlang/bin
> ```
>
> 编译生效
>
> ```tex
> source /etc/profile
> ```
>
> 输入`erl`，会出现版本信息，即安装成功



> ###### 安装`RabbitMQ 3.8.4`
>
> ```tex
> wget https://dl.bintray.com/rabbitmq/all/rabbitmq-server/3.8.4/rabbitmq-server-generic-unix-3.8.4.tar.xz
> xz -d rabbitmq-server-generic-unix-3.8.4.tar.xz
> tar -xvf rabbitmq-server-generic-unix-3.8.4.tar 
> ```
>
> 配置`RabbitMQ`环境变量
>
> 假设下载的目录在 `/usr/local`
>
> ```
> vim /etc/profile
> ```
>
> 添加一行：
>
> ```
> export PATH=$PATH:/usr/local/rabbitmq_server-3.8.4/sbin
> ```
>
> 编译生效
>
> ```tex
> source /etc/profile
> ```
>
> 启动`RabbitMQ`
>
> ```
> # 后台启动rabbitmq服务
> cd /usr/local/soft/rabbitmq_server-3.8.4/sbin
> ./rabbitmq-server -detached
> ```
>
> 或者
>
> ```
> ./rabbitmq-server start
> ```
>
> 或者
>
> ```
> service rabbitmq-server start
> ```
>
> 添加其他用户
>
> 因为guest用户只能在本机访问，添加一个`admin`用户，密码也是`admin`
>
> ```tex
> ./rabbitmqctl add_user admin admin
> ./rabbitmqctl set_user_tags admin administrator
> ./rabbitmqctl set_permissions -p / admin ".*" ".*" ".*"
> ```
>
> 启用管理插件
>
> ```
> ./rabbitmq-plugins enable rabbitmq_management
> ```
>
> 访问
> `http://虚拟机IP:15672`



