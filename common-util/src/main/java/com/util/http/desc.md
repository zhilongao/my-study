
请求的流程
	1.构建请求->请求过滤器的处理->写出请求
	2.事件循环监听->select模型->监听到读写事件(相关的处理)
	
1. client的start方法做了哪些事情
    启动了线程，进行IO事件的监听

2. client的execute方法又做了哪些事情
    2.1 交给这个方法的其实就两个参数,一个request,一个FutureCallback(封装相关的回调处理)。最终到达底层的实现类,已经是拥有四个参数的方法了。
            ①requestProducer(HttpAsyncRequestProducer): 请求生产者回调,用于生成请求消息并流式输出其内容,而无需在内存中缓存。
            ②responseConsumer(HttpAsyncResponseConsumer):响应消费者回调,用于处理响应消息,而无需在内存中缓冲其内容。
            ③context(HttpContext):上下文
            ④callback(FutureCallback):回调
            
            请求和响应回调的实现类     
            RequestProducerImpl->BasicAsyncRequestProducer->HttpAsyncRequestProducer
            BasicAsyncResponseConsumer->AbstractAsyncResponseConsumer->HttpAsyncResponseConsumer
                    
    2.2 client在内部维护了一系列的对象
            ①connmgr(NHttpClientConnectionManager)连接管理器
            ②connReuseStrategy(ConnectionReuseStrategy)连接重用策略
            ③keepaliveStrategy(ConnectionKeepAliveStrategy)连接保持策略
            ④exec(InternalClientExec)内部客户端执行器
            这几个参数交给了handler(DefaultClientExchangeHandlerImpl),由handler的start()方法执行下一步的操作。
    
    2.3 在handler的start的内部,执行了两个步骤
            第一步是调用执行器exec(MainClientExec)的prepare()方法。
                prepare()方法又调用了内部的prepareRequest()方法来处理。prepareRequest()方法的执行逻辑,其实就是利用了ImmutableHttpProcessor(processor),并调用其process()方法处理。
                而process()方法就是利用了其持有的拦截器处理,其内部有一个HttpRequestInterceptor数组，挨个对请求进行处理
                RequestDefaultHeaders
                RequestContent
                RequestTargetHost
                RequestClientConnControl
                RequestUserAgent
                RequestExpectContinue
                RequestAddCookies
                RequestAuthCache    
            第二步是调用requestConnection()方法。

    至于具体的怎么将请求搞成二进制流的形式写出去的,还需要在研究一下。
            
            
3. 分析下start()方法
    3.1 首先是在client内部有一个变量reactorThread(Thread),它的run()方法其实只做了一件事情,创建了一个ioEventDispatch(InternalIODispatch),将其
        交给connmgr(PoolingNHttpClientConnectionManager)的execute()方法执行。ioEventDispatch在创建的时候,构造函数里面会传一个值handler(HttpAsyncRequestExecutor)。
        
    3.2 在connmgr的内部,有一个ioreactor(DefaultConnectingIOReactor),在execute()方法内部,直接调用了ioreactor的execute(ioEventDispatch)方法, 改方法所做的工作
            ① 填充BaseIOReactor[] dispatchers;数组对象
            ② 填充Worker[] workers;数组对象 (Worker本质上是一个Runnable对象, 持有dispatcher和ioEventDispatch), 里面重写了run()方法
            ③ 填充Thread[] threads;数组对象 (thread里面持有的Runnable对象是Worker对象)
            ④ 启动threads里面的每一个线程,实际上是执行Worker里面的run方法
            ⑤ 在主线程里面调用Selector的selector()方法,获取准备好执行的事件数,调用processEvents()方法处理
            ⑥ 循环workers数组,检查是否有异常抛出,若是检测到异常,直接抛出
   
        主函数中processEvents()方法的分析
                        


        Worker线程中run()方法的分析

            
    交给后做了哪些事情	    
        InternalClientExec做了哪些事情
        


        

        CloseableHttpPipeliningClient
        
        CloseableHttpAsyncClientBase

        InternalHttpAsyncClient
        
        
        
***************************************************        
        
        
        AbstractClientExchangeHandler
               
        DefaultClientExchangeHandlerImpl
        
        
        
        
        
        InternalClientExec



HttpAsyncClients
HttpAsyncClientBuilder

    
    

MainClientExec

ImmutableHttpProcessor
    HttpRequestInterceptor[] requestInterceptors
    	RequestDefaultHeaders
    	RequestContent
    	RequestTargetHost
    	RequestClientConnControl
    	RequestUserAgent
    	RequestExpectContinue
    	RequestAddCookies
    	RequestAuthCache
    
    
    HttpResponseInterceptor[] responseInterceptors
    	ResponseProcessCookies
    
    
InternalRequestExecutor
DefaultNHttpClientConnection
InternalIODispatch
AbstractIODispatch

AbstractMultiworkerIOReactor.Worker
    BaseIOReactor dispatcher 
    
PoolingNHttpClientConnectionManager    
