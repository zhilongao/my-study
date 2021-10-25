
请求的流程
	1.构建请求->请求过滤器的处理->写出请求
	2.事件循环监听->select模型->监听到读写事件(相关的处理)    



        

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
