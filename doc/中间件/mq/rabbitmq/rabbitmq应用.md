###### `MQ主要特性`

>① 是一个独立运行的服务。生产者发送消息，消费者接受消息，需要先根服务器建立连接。
>
>② 采用队列作为数据结构，有先进先出的特点。
>
>③ 具有发布订阅的模型（publish/subscribe）,消费者可以获取到自己需要的消息。

###### `MQ使用的场景`

>```tex
>异步   解耦（多个系统间解耦合） 削峰（流量削峰）
>```

###### `使用MQ带来的问题`

>```tex
>运维成本增加
>系统可用性降低
>系统复杂性提高
>```

###### `JMS`协议

>

###### `AMQP`协议

>`AMQP(Advanced Message Queuing Protocol）`，应用层的协议。

###### `RabbitMQ工作模型`

>`rabbitmq`的工作模型
>
>[![ra2mZD.jpg](https://s3.ax1x.com/2020/12/20/ra2mZD.jpg)](https://imgchr.com/i/ra2mZD)
>
>`rabbitmq`消费模型：同时支持`pull`和`push`。

###### `rabbitmq`的可靠性投递

>1.生产者发送消息到达Broker。
>
>```tex
>Transaction模式的确认机制
>Confirm模式的确认机制
>```
>
>2.消息从Exchange路由到Queue。
>
>```tex
>若是消息无法从exchange到到queue，提供了两种策略
>① 消息回发
>② 指定备份交换机
>```
>
>3.消息在Queue中的存储。
>
>```tex
>① 交换机持久化  队列持久化  消息持久化
>② 集群
>```
>
>4.消费端消费消息
>
>```tex
>① ack确认机制
>```
>
>

###### `rabbitmq`消息延迟投递实现方案1

>通过`rabbitmq`的死信队列实现
>
>```tex
>rabbitmq收发消息的流程
>1. 在rabbitmq中，生产者发送消息是发送到交换机(exchange)上的。
>2. 交换机上会绑定一些队列(queue)，exchange和queue之间的绑定关系是通过绑定键(bindingKey)来维护的。
>3. 生产者发送消息到达交换机(exchange),发送时会指定(routingKey),交换机(exchange)根据routingKey将消息路由到某些符合规则的队列。
>4. 消费者在从某个队列pull消息，或者服务端将消息push到消费者。
>
>rabbitmq的私信队列
>1. 一个队列(queue1)，消息由交换机(exchange)通过某种路由规则路由到该队列(queue1)。
>2. 若消息到了指定的过期时间，没有消费者消费消息，可以将该消息路由到某个指定的交换机(exchange1)，该指定的交换机(exchange1)在将消息发送到某个符合规则的队列(queue2)。
>3. 若是想要使用此规则，在创建队列(queue1)时，必须指定某个属性(x-dead-letter-exchange)，即指定死信交换机。
>4. 通过该方式可以实现消息的延迟投递。
>```

###### `rabbitmq`消息延迟投递实现方案2

>通过`rabbitmq`的插件`rabbitmq_delayed_message_exchange`实现
>
>```tex
>1. 通过安装rabbitmq_delayed_message_exchange插件，可以声明类型为x-delayed-message的交换机。
>2. 生产者在投递消息的时候，通过在属性中指定x-delay，可以实现消息的延迟投递。
>```

