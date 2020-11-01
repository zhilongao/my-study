java序列化
    序列化:将java对象转化为特定的数据格式，然后以流的形式发送。
    发序列化:将特定格式转化为对象。
    
java序列化的目的
    跨jvm传输
   
java序列化的方式
    原生的java序列化
        1.被序列化的对象的类要实现Serializable接口
        2.每个类需要定义一个版本好SerializableID
        3.java关键字transient可以禁止某个字段被序列化
        4.可以通过重写类的readObject和writeObject改变序列化的行为
        5.java中和对象序列化相关的流，ObjectInputStream和ObjectOutputStream
        
分布式架构下的序列化技术
    需要考虑的问题
        1.序列化的大小，影响传输的效率
        2.跨语言，json/xml
    服务与服务之间的通信(一般仅考虑数据传输的大小) 
    常见的序列化方式
        json
        xml   
        hassian
        msgpack
        protobuf
        dubbo(hassian2)
        avro
        kyro(hive, storm)
        
        