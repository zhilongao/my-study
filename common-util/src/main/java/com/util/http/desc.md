
请求的流程
	1.构建请求->请求过滤器的处理->写出请求
	2.事件循环监听->select模型->监听到读写事件(相关的处理)
	
1. client的start方法做了哪些事情
    启动了线程，进行IO事件的监听

2. client的execute方法又做了哪些事情
    2.1 交给这个方法的其实就两个参数,一个request,一个FutureCallback的封装。最终到达底层的实现类，已经是拥有四个参数的方法了。
        这四个参数分别是
            HttpAsyncRequestProducer requestProducer:请求生产这回调,用于生成请求消息并流式输出其内容,而无需在内存中缓存。
            HttpAsyncResponseConsumer responseConsumer:响应消费者回调,用于处理相应消息,而无需在内存中缓冲其内容。
            HttpContext context:上下文
            FutureCallback callback:回调
            
            两个callback
            RequestProducerImpl->BasicAsyncRequestProducer->HttpAsyncRequestProducer
            BasicAsyncResponseConsumer->AbstractAsyncResponseConsumer->HttpAsyncResponseConsumer
            
            
    2.2 然后client又封装了一系列的参数,如连接管理器,连接重用策略,连接保持策略,内部客户端执行器,给到了DefaultClientExchangeHandlerImpl(handler).
        然后调用了handler的start方法。
    2.3 而在handler内部,首先要做的工作就是调用内部执行器MainClientExec(exec)的prepare()方法。而prepare()方法又调用了内部的prepareRequest()方法来处理。
    2.4 prepareRequest()方法的执行逻辑,其实就是利用了ImmutableHttpProcessor(processor),并调用其process()方法处理
    2.5 而process()方法就是利用了其持有的拦截器处理,其内部有一个HttpRequestInterceptor数组，挨个对请求进行处理
           RequestDefaultHeaders
           RequestContent
           RequestTargetHost
           RequestClientConnControl
           RequestUserAgent
           RequestExpectContinue
           RequestAddCookies
           RequestAuthCache     

    至于具体的怎么将请求搞成二进制流的形式写出去的,还需要在研究一下。
            
            
            
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
