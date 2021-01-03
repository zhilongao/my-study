

###### `zk的集群搭建步骤`

>1.准备三台虚拟机(192.168.43.4，192.168.43.93，192.168.43.128)，虚拟机上提前安装好`jdk`。
>
>2.下载`zookeeper`的程序包。
>
>```tex
>https://mirror.bit.edu.cn/apache/zookeeper/zookeeper-3.6.2/apache-zookeeper-3.6.2-bin.tar.gz 
>```
>
>3.解压到指定的目录下
>
>```tex
>cd /usr/local/src
>tar -zxvf apache-zookeeper-3.6.2-bin.tar.gz
>```
>
>4.进入到`zk`安装目录的`conf`目录，在该目录下复制一份`zoo_sample.cfg`文件，并重命名成`zoo.cfg`。
>
>```tex
>cd /usr/local/src/apache-zookeeper-3.6.2-bin/conf/
>cp zoo_sample.cfg zoo.cfg
>```
>
>5.打开`zoo.cfg`文件,可自由配置数据目录`dataDir`、服务启动的端口号`clientPort`、日志数据目录`dataLogDir`。
>
>```tex
>clientPort=2181
>dataDir=/usr/local/src/apache-zookeeper-3.6.2-bin/data
>dataLogDir=/usr/local/src/apache-zookeeper-3.6.2-bin/dataLog
>```
>
>6.进入到`dataDir`所配置的目录，创建`myid`文件，并在该文件中定义该`zk`节点的`id`【配置范围1~255】。每个节点的`id`不同（三个节点分别配置成1 2 3）。
>
>```tex
>cd /usr/local/src/apache-zookeeper-3.6.2-bin/data
>vi myid
>```
>
>7.再次编辑`zoo.cfg`文件，按`server.A=ip:port1:port2`的格式配置；其中，`A `指的是每台`zk`服务定义的`myid`;`ip` 为`zk`服务所对应虚拟机的`ip`地址；`port1`为这台服务器与集群中的`Leader`服务器交换信息的端口；`port2` 表示的是万一集群中的`Leader`服务器挂了，需要一个端口来重新进行选举，选出一个新的`Leader`，这个端口就是用来执行选举时服务器相互通信的端口。
>
>```tex
>cd /usr/local/src/apache-zookeeper-3.6.2-bin/conf/
>vi zoo.cfg
>
>server.1=192.168.43.4:8888:9999
>server.2=192.168.43.93:8888:9999
>server.3=192.168.43.128:8888:9999
>```
>
>注意1：每个`zk`节点都需配置上所有节点对应的服务信息【即3个服务节点每个都要在`zoo.cfg`文件中配置`server.1`、`server.2`、`server.3`的内容。
>
>注意2：如果是伪集群搭建【即在一台机器上搭建集群，每个server的端口`port1`和`port2`配置都不可重复。
>
>8.启动集群
>
>进入每个`zk`节点安装目录下的`bin`文件夹，输入命令 `./zkServer.sh start`,启动`zk`服务。
>启动所有`zk`服务节点之后，输入`./zkServer.sh status` 查看状态。