###### CentOS安装kafka 2.5.0单机版

 >1.下载kafka并解压
 >
 >```shell
 >cd /usr/local/soft
 >wget https://mirror.bit.edu.cn/apache/kafka/2.5.0/kafka_2.13-2.5.0.tgz
 >tar -xzvf kafka_2.13-2.5.0.tgz
 >cd kafka_2.13-2.5.0
 >```
 >
 >2.启动zookeeper
 >
 >```tex
 >说明: 
 >	1. kafka需要依赖ZK，安装包中已经自带了一个ZK，也可以改成指定已运行的ZK。
 >	2. 如果改成指定的ZK需要修改kafka安装目录下的config/server.properties 文件中的 zookeeper.connect。这里使用自带的ZK。
 >```
 >
 >① 后台启动zk
 >
 >```shell
 >nohup ./bin/zookeeper-server-start.sh config/zookeeper.properties zookeeper.nohup &
 >```
 >
 >② 检查zookeeper是否启动成功
 >
 >```shell
 >ps -ef|grep zookeeper
 >```
 >
 >3.后台启动kafka
 >
 >① 修改相关配置
 >
 >```shell
 >vim config/server.properties
 >```
 >
 >② Broker ID启动以后就不能改了
 >
 >```shell
 >broker.id=1
 >```
 >
 >③  取消注释，改成本机IP
 >
 >```shell
 >listeners=PLAINTEXT://192.168.43.4:9092
 >```
 >
 >④ num.partitions后面增加2行。发送到不存在topic自动创建。允许永久删除topic。
 >
 >```shell
 >num.partitions=1
 >auto.create.topics.enable=true
 >delete.topic.enable=true
 >```
 >
 >⑤ 后台启动kafka（kafka安装目录下）
 >
 >```shell
 >nohup ./bin/kafka-server-start.sh ./config/server.properties & 
 >```
 >
 >⑥ 日志在logs目录下
 >
 >4.创建topic
 >
 >① 创建一个名为gptest的topic，只有一个副本，一个分区
 >
 >```shell
 >sh bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic gptest
 >```
 >
 >② 查看已经创建的 topic
 >
 >```shell
 >sh bin/kafka-topics.sh -list -zookeeper localhost:2181
 >```
 >
 >5.启动Producer
 >
 >① 打开一个窗口，在kafka解压目录下
 >
 >```shell
 >sh bin/kafka-console-producer.sh --bootstrap-server 192.168.43.4:9092 --topic gptest
 >```
 >
 >6.启动Consumer
 >
 >① 在一个新的远程窗口中
 >
 >```shell
 >sh bin/kafka-console-consumer.sh --bootstrap-server 192.168.43.4:9092 --topic gptest --from-beginning
 >```
 >
 >7.在Producer窗口发送消息，在Consumer窗口接受消息。
 >
 >8.删除kafka全部数据步骤
 >
 >```tex
 >① 停止每台机器上的kafka
 >② 删除kafka存储目录（server.properties文件log.dirs配置，默认为“/tmp/kafka-logs”）全部topic的数据目录；
 >③ 删除zookeeper上与kafka相关的znode节点；除了/zookeeper
 >④ 重启kafka。
 >```



+++

###### CentOS Kafka 2.5.0 单机集群安装

>现有四台虚拟机，`192.168.43.4` `192.168.43.128` `192.168.43.203`  `192.168.43.93`，其中前三台部署`kafka`，后一台部署`zk`。
>
>1.安装zk并启动。
>
>```tex
>参考zk的安装步骤。
>```
>
>2.下载kafka并解压。
>
>```tex
>cd /usr/local/soft
>wget https://mirror.bit.edu.cn/apache/kafka/2.5.0/kafka_2.13-2.5.0.tgz
>tar -xzvf kafka_2.13-2.5.0.tgz
>cd kafka_2.13-2.5.0
>```
>
>3.修改kafka的配置文件。
>
>`192.168.43.4`
>
>```tex
>broker.id=1
>listeners=PLAINTEXT://192.168.43.4:9092
>zookeeper.connect=192.168.43.93:2181
>```
>
>`192.168.43.128`
>
>```tex
>broker.id=2
>listeners=PLAINTEXT://192.168.43.128:9092
>zookeeper.connect=192.168.43.93:2181
>```
>
>`192.168.43.203`
>
>```tex
>broker.id=3
>listeners=PLAINTEXT://192.168.43.203:9092
>zookeeper.connect=192.168.43.93:2181
>```
>
>4.分别启动三台机器上的`kafka`。
>
>```shell
>./kafka-server-start.sh -daemon ../config/server.properties
>```
>
>5.集群环境下创建`topic`。
>
>① 在安装`kafka`的任意一台机器上创建一个`topic`，该`topic`三个副本，三个分区。
>
>```shell
>sh kafka-topics.sh --create --zookeeper 192.168.43.93:2181 --replication-factor 3 --partitions 3 --topic gptest
>```
>
>② 查看创建的topic
>
>```shell
>sh kafka-topics.sh --list --zookeeper 192.168.43.93:2181
>```
>
>