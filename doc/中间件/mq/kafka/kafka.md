```tex
1.客户端消息发送完成后的确认机制
	发送出去就确认
	leader落盘就确认
	所有follower同步完确认
	
2.客户端何时触发消息批量的发送
	batch.size -> 消息的大小(满足设定的大小触发消息发送)
	linger.ms  -> 批量发送的等待时间(超过等待时间发送)
	buffer.memory -> 客户端缓冲区大小(消息占满缓冲区发送)
	

	
```



###### 参数解读

```tex
参数->server段的基本配置
broker.id 每个broker的id是唯一的
############################# Server Basics #############################
# The id of the broker. This must be set to a unique integer for each broker.
broker.id=0

参数->socket的一些参数配置
############################# Socket Server Settings #############################
# The address the socket server listens on. It will get the value returned from 
# java.net.InetAddress.getCanonicalHostName() if not configured.
#   FORMAT:
#     listeners = listener_name://host_name:port
#   EXAMPLE:
#     listeners = PLAINTEXT://your.host.name:9092
#listeners=PLAINTEXT://:9092

# Hostname and port the broker will advertise to producers and consumers. If not set, 
# it uses the value for "listeners" if configured.  Otherwise, it will use the value
# returned from java.net.InetAddress.getCanonicalHostName().
#advertised.listeners=PLAINTEXT://your.host.name:9092

# Maps listener names to security protocols, the default is for them to be the same. See the config documentation for more details
#listener.security.protocol.map=PLAINTEXT:PLAINTEXT,SSL:SSL,SASL_PLAINTEXT:SASL_PLAINTEXT,SASL_SSL:SASL_SSL

# The number of threads that the server uses for receiving requests from the network and sending responses to the network
num.network.threads=3

# The number of threads that the server uses for processing requests, which may include disk I/O
num.io.threads=8

# The send buffer (SO_SNDBUF) used by the socket server
socket.send.buffer.bytes=102400

# The receive buffer (SO_RCVBUF) used by the socket server
socket.receive.buffer.bytes=102400

# The maximum size of a request that the socket server will accept (protection against OOM)
socket.request.max.bytes=104857600


参数->日志的基本配置
log.dirs  日志文件的存储目录
num.partitions 每个主题的默认分区数

############################# Log Basics #############################
# A comma separated list of directories under which to store log files
log.dirs=/tmp/kafka-logs

# The default number of log partitions per topic. More partitions allow greater
# parallelism for consumption, but this will also result in more files across
# the brokers.
num.partitions=1

# The number of threads per data directory to be used for log recovery at startup and flushing at shutdown.
# This value is recommended to be increased for installations with data dirs located in RAID array.
num.recovery.threads.per.data.dir=1


参数->日志保留策略
log.retention.hours 日志存留的最长时间(小时)
log.retention.bytes 满足删除日志的最小大小
log.segment.bytes  一个日志段的最大大小
log.retention.check.interval.ms  日志被检查的时间间隔

问题:删除的时候是如何删除的,比如一个文件大小满足删除条件了,是直接将文件删除还是删除文件中的内容

############################# Log Retention Policy #############################

# The following configurations control the disposal of log segments. The policy can
# be set to delete segments after a period of time, or after a given size has accumulated.
# A segment will be deleted whenever *either* of these criteria are met. Deletion always happens
# from the end of the log.

# The minimum age of a log file to be eligible for deletion due to age
log.retention.hours=168

# A size-based retention policy for logs. Segments are pruned from the log unless the remaining
# segments drop below log.retention.bytes. Functions independently of log.retention.hours.
#log.retention.bytes=1073741824

# The maximum size of a log segment file. When this size is reached a new log segment will be created.
log.segment.bytes=1073741824

# The interval at which log segments are checked to see if they can be deleted according
# to the retention policies
log.retention.check.interval.ms=300000

```

