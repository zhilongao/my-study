如何理解分布式
   手工->电子化->信息化->互联网金融->金融科技
存储 计算 通信   
    分片 副本机制
    
    异步 解耦 削峰
 
复杂的问题
    1.如何实现订单的延迟关闭
        方案1：先存储到数据库，定时任务扫描(不适合数据量大的场景)
        方案2：通过mq死信队列实现
            使用死信队列：一个队列创建是可以绑定一个死信交换机，当队列中的消息过期不被消费时，将会被发送到死信交换机，
            死信交换机绑定的队列存储这些消息，消费者去消费死信队列中的这些消息。
            此方案的缺陷：不同时间延迟投递需要创建大量的死信交换机。            
        方案3：使用mq插件的方式：(rabbit-delayed-message-exchange)消息延迟投递插件
    2. 什么样的消息会进入死信队列
    
    
    消息存储满了怎么办
    
    服务端流控制  
 
可靠性投递
    发送方到达exchange的可靠性
        事务模式（交互太多，消耗服务端的性能）
        confirm模式
            普通的确认模式(根事务差不多，也是消耗性能严重)
            批量的确认模式(不好确定确认的数量)
            异步的确认模式
    exchange到达queue的可靠性
        1. routingKey指定错误
        2. 队列不存在
        解决方案
            1.mandatory = true + ReturnListener
            2.指定交换机的备份交换机
    消息在队列中存储的可靠性
        创建是声明为持久化
    消费端消费队列的可靠性
        收到消息后的应答
            自动应答：一收到消息就发送ack
            手动应答：处理完成后在应答(可以丢弃，也可以重新入队)
         
发送方与消费方的确认
    1. 消费者回调
        消费者调用生产者预先定义好的api
        消费者回复一条消息
    2.补偿机制
        定义超时时间--重发（定时任务/消息表）
    3.最终一致性
    
    4.消息幂等性
    
    5.消息顺序性    
        
集群和高可用

实践经验 

// Please see diagnostics information and suggestions below 
//  Target node is unreachable (e.g. due to hostname resolution, TCP connection or firewall issues)        
//  CLI tool fails to authenticate with the server (e.g. due to CLI tool's Erlang cookie not matching that of the server)  
//  Target node is not running   
// In addition to the diagnostics info below 

// -detached
          
            