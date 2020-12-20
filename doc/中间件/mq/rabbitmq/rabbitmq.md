###### `MQ主要特性`

>① 是一个独立运行的服务。生产者发送消息，消费者接受消息，需要先根服务器建立连接。
>
>② 采用队列作为数据结构，有先进先出的特点。
>
>③ 具有发布订阅的模型（publish/subscribe）,消费者可以获取到自己需要的消息。

###### `MQ使用的场景`

>异步 
>
>解耦 （多个系统间解耦合）
>
>削峰（流量削峰）

###### `使用MQ带来的问题`

>运维成本增加
>
>系统可用性降低
>
>系统复杂性提高

###### `JMS`

>

###### `AMQP`

>`AMQP(Advanced Message Queuing Protocol）`，应用层的协议。

###### `RabbitMQ工作模型`

>`rabbitmq`的工作模型
>
>[![ra2mZD.jpg](https://s3.ax1x.com/2020/12/20/ra2mZD.jpg)](https://imgchr.com/i/ra2mZD)
>
>`rabbitmq`消费模型：同时支持`pull`和`push`。
>
>

