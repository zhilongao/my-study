### 请求的流程
>1.异步客户端的创建(client初始化各种属性,开启boss线程)
>
>2.请求任务的提交(boss线程注册,处理connect事件)
>
>3.读写事件的循环(work线程处理读写事件)



#### 异步客户端的创建

```tex
1. 异步客户端创建都初始化了哪些重要成员?
    异步客户端创建一般使用工具类HttpAsyncClients创建,默认是创建了InternalHttpAsyncClient对象,该对象的构造方法初始化了以下属性
        connmgr(PoolingNHttpClientConnectionManager) 连接管理器
        connReuseStrategy(DefaultConnectionReuseStrategy) 连接重用策略
        keepaliveStrategy(DefaultConnectionKeepAliveStrategy) 连接保持策略
        exec(InternalClientExec) 内部客户端执行器
    InternalHttpAsyncClient的父类(CloseableHttpAsyncClientBase)中,构造reactorThread(Thread boss线程),并重写其run()方法。run()方法内部构造了一个InternalIODispatch对象,并将其
    交给connmgr的execute()方法。
    
    
2. boss线程是如何开启的?
    需要调用client的start()方法,该方法调用内部reactorThread的run()方法,开始运行boss线程。
    
  
```

#### 请求任务的提交
```tex
1. main线程中提交请求任务, 后续处理的流程是什么?
    在构造connmgr的时候,为其创建了ioreactor(DefaultConnectingIOReactor)属性。
    在构造connmgr的时候,为其创建了pool(CPool)属性,并将ioreactor传给了pool。
    ioreactor处理connect,其实是将request封装成为SessionRequestImpl,将入到了队列requestQueue,此时主线程的任务已经完成。

2. boss线程是何时从main线程接手任务的?
    boss线程在调用client的start()方法时已经在运行了。
    boss线程首先调用connmgr的execute()方法,而该方法会直接调用ioreactor的execute()方法。此时,main线程和boss线程操纵的相同对象已经出现,即ioreactor。
    ioreactor的execute()方法的入口处首先是初始化work线程组,然后启动这些工作线程。后面是进入到一段for(;;)循环,此处是处理两件事情
        第一件事情是将准备好的request注册到主selector上面去(订阅SelectionKey.OP_CONNECT事件)
        第二件事情是处理selector上准备好的SelectionKey.OP_CONNECT事件。
    
    
3. boss线程和work线程是如何协作的?
   work线程处理SelectionKey.OP_CONNECT事件时,从工作线程中随机选取一个work线程,将request构造成为一个ChannelEntry, 加入到工作线程的newChannels中。
   后续的工作由Worker线程完成。
   
```

#### 读写事件的循环
```tex
1. work线程是如何工作的?
    execute()方法内部的逻辑是一段for(;;)循环, 处理两件事情
        第一件事情是从selector中获取准备好的事件处理
        第二件事情是从newChannels中拿到ChanneEntry,注册SelectionKey.OP_READ事件
        
2. 接口返回的数据如何交给上层应用的(数据交给response)?


3. 回调何时触发的?
            
```

连接的实现类
    ManagedNHttpClientConnectionImpl



