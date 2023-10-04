##### tomcat的初始化流程



##### tomcat的启动流程



##### tomcat的请求处理流程



###### Pipeline的处理

```tex
1.【Http11Processor】【service】方法内有一段逻辑【this.getAdapter().service(this.request, this.response)】,拿到【CoyoteAdapter】然后调用它的【service】方法。

2.【CoyoteAdapter】【service】方法内有一段逻辑【this.connector.getService().getContainer().getPipeline().getFirst().invoke(request, response)】，这里获取到的【Container】就是【StandardEngine】，也就是说要拿到【StandardEngine】的【Pipeline】【pipeline】【StandardPipeline】属性,拿到这个【Pipeline】里面的第一个【Valve】，调用它的【invoke】方法。
```

```te
【StandardEngine】
3.【StandardEngineValve】【invoke】方法,设置【Request】支持异步的属性,然后拿到【Host】【StandardHost】【Pipeline】属性,拿到这个【Pipeline】里面的第一个【Valve】，调用它的【invoke】方法。

【StandardHost】
4.【ErrorReportValve】

5.【StandardHostValve】

【TomcatEmbeddedContext】
6.【NonLoginAuthenticator】

7.【StandardContextValve】


【StandardWrapper】
8.【StandardWrapperValve】


9.【StandardWrapperValve】【invoke】方法最终会使用【ApplicationFilterFactory】【createFilterChain】创建一个【ApplicationFilterChain】。调用【ApplicationFilterChain】【doFilter】方法。
```



###### filter的处理

```tex
10.【ApplicationFilterChain】【doFilter】方法拿到创建时设置的属性【ApplicationFilterConfig[] filters】,挨个调用这些过滤器的【doFilter】方法。调用完成后执行【Servlet】【servlet】【service】方法。

11.【OrderedCharacterEncodingFilter】这个过滤器是给【request】和【response】设置【CharacterEncoding】属性。

12.【OrderedFormContentFilter】判断是否要解析【RequestBody】并进行解析。

13.【OrderedRequestContextFilter】设置【Context】。

14.【WsFilter】对【ws】请求的处理。
```



###### servlet的处理

```tex
请求到达了具体的【Servlet】,其中【FrameworkServlet】【DispatcherServlet】是【spring mvc】定义的类,而【HttpServlet】是【tomcat】提供的。
```

```te
15.请求到达【FrameworkServlet】【service】方法,该方法获取到【HttpMethod】,若是该【HttpMethod】不为【null】并且不是【PATCH】方法,就调用父类【HttpServlet】【service】方法。

16.【HttpServlet】【service】方法根据具体的请求方法类型,执行具体的【do】方法,如【doGet】【doPost】。具体的【do】方法是在【FrameworkServlet】中实现的。

17.【FrameworkServlet】【doGet】方法调用了【processRequest】方法。

18.【FrameworkServlet】【processRequest】处理一些参数后,将调用【doService】方法,该方法由子类【DispatcherServlet】实现。
```

```tex
后续【DispatcherServlet】【doService】方法的具体分析在【spring mvc】篇章分析。
```

