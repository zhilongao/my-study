###### 	1.从【tomcat】到【spring security】

```tex
1.一个请求到达【tomcat】时,会先经过父容器【Pipeline】中【Value】元素的层层处理,最终调用到【StandardWrapper】【Pipeline】中【Value】元素【StandardWrapperValue】的【invoke】方法。

2.该方法先是调用了【ApplicationFilterFactory】【createFilterChain】方法,创建一个【ApplicationFilterChain】,然后调用【ApplicationFilterChain】的【doFilter】方法。

3.【ApplicationFilterChain】内部持有【Filter】列表和【Servlet】,先调用每个【Filter】的【doFilter】方法,然后在调用【Servlet】【service】方法。

4.【Spring Security】的原理就是在【ApplicationFilterChain】中添加的过滤器【DelegatingFilterProxy】【delegate】->【FilterChainProxy】,对经过它的请求进行主要的【认证】和【授权】处理。
```

```tex
1.【DelegatingFilterProxy】从【Spring】上下文中找到名称为【springSecurityFilterChain】,类型为【Filter】的【bean】,设置为【Filter】【delegate】属性。该【delegate】类型为【FilterChainProxy】。

2.【FilterChainProxy】【doFilterInternal】方法根据【ServletRequest】【request】,从【List<SecurityFilterChain> filterChains】中找到一个匹配该【request】的【SecurityFilterChain】,并调用它的【getFilters】获取一个【Filter】列表。

3.【VirtualFilterChain】被创建并携带获取到的【Filter】列表以及【Tomcat】的调用链以及【request】。该类的【doFilter】方法也是挨个调用【Filter】列表中的【doFilter】方法。
```



###### 2.【spring security】初始化流程

```tex
2.1 开始说【ApplicationFilterFactory】【createFilterChain】方法创建了【ApplicationFilterChain】,所以它内部的【Fiter】也可能是这时候被放入进去的。

2.2 【StandardContext】中有【ContextFilterMaps filterMaps】【Map<String, ApplicationFilterConfig> filterConfigs】两个属性。【ContextFilterMaps】其实是一个【FilterMap】数组,每一个【FilterMap】可以看成是请求的【url】或者【servletName】和【filterName】的映射关系。而【filterConfigs】就是【filterName】和【ApplicationFilterConfig】的映射关系,且【ApplicationFilterConfig】里面包含具体的【Filter】实例。

2.3 【ApplicationFilterFactory】【createFilterChain】方法,先是用【StandardContext】【findFilterMaps】拿到【filterMaps】,循环每一个【FilterMap】判断是否和本次请求的【url】或者【servlet】匹配。若是匹配的话,在用【StandardContext】【findFilterConfig】方法获取一个具体的【ApplicationFilterConfig】,将它加入到【ApplicationFilterChain】中。

2.4 既然我们所用到的【Filter】是从【StandardContext】中拿到的,所以我们追踪其来源也必然是何时注入到【StandardContext】中的这两个属性中开始。
```

```tex
2.5 最终追踪到【StandardContext】【startInternal】方法,逻辑中有一个步骤是拿到它的【Map<ServletContainerInitializer, Set<Class<?>>> initializers】属性,循环调用【ServletContainerInitializer】【onStartup】方法,这个【Map】中只有一个元素【TomcatStarter】。而【TomcatStarter】【onStartup】又会拿到它内部的【ServletContextInitializer[] initializers】列表,挨个调用他们的【onStartup】方法。
```

```tex
2.7【TomcatStarter】中的【ServletContextInitializer[] initializers】
【AnnotationConfigServletWebServerApplicationContext】【createWebServer】方法会用【TomcatServletWebServerFactory】【getWebServer】方法创建一个【WebServer】。【getWebServer】方法接收【ServletContextInitializer... initializers】数组参数,只有一个【lumbda】表达是,具体逻辑在调用的时候分析。

2.8 在【TomcatServletWebServerFactory】【prepareContext】方法中,会通过【mergeInitializers】方法,又增加了一些额外的【ServletContextInitializer】。
	2.8.1 第一个是给【ServletContext】设置参数的【ServletContextInitializer】,拿到【WebServerFactory】中的【initParameters】,设置到【ServletContext】中,以【lumbda】表达式的方式呈现。
	2.8.2 第二个是创建了一个【SessionConfiguringInitializer】,添加到【List<ServletContextInitializer>】列表中。
	2.8.3 第三个是把步骤2中的【ServletContextInitializer】添加到列表中。
	2.8.4 第四个是把【WebServerFactory】中的【List<ServletContextInitializer>】添加到列表中。
	2.8.5 转换为数组的形式返回。

2.9【TomcatServletWebServerFactory】的【configureContext】方法中创建的【TomcatStarter】对象	,并且【configureContext】将方法参数【ServletContextInitializer[] initializers】传给了【TomcatStarter】。随后【StandardContext】【addServletContainerInitializer】将【TomcatStarter】加入到【Map<ServletContainerInitializer, Set<Class<?>>> initializers】属性中。
```

```tex
----->【TomcatStarter】的【onStart】<-----
3.0 上面步骤分析到在【AnnotationConfigServletWebServerApplicationContext】【createWebServer】方法调用的时候,传入了一个【lumbda】,现在分析它的主要逻辑。

3.1 该【lumbda】在【ServletWebServerApplicationContext】中以【selfInitialize】方法呈现。【getServletContextInitializerBeans】方法获取一个类型为【ServletContextInitializer】的对象【ServletContextInitializerBeans】,然后在调用它的【onStartup】方法。

3.2 【ServletContextInitializerBeans】的创建逻辑
	3.2.1 【BeanFactory】中获取类型为【ServletContextInitializer】的【bean】,键【key】为【name】,【value】为【bean】放入到【map】中。
	3.2.2 【map】中的两个【bean】分别是【name】为【securityFilterChainRegistration】的【DelegatingFilterProxyRegistrationBean】,它的【targetBeanName】为【springSecurityFilterChain】。【name】为【dispatcherServletRegistration】的【DispatcherServletRegistrationBean】。	
	3.2.3 将这两个【bean】对象加入到一个【MultiValueMap<Class<?>, ServletContextInitializer> initializers】中
		【Filter -> DelegatingFilterProxyRegistrationBean】
		【Servlet -> DispatcherServletRegistrationBean】
	3.2.4 调用【addAdaptableBeans】,从【BeanFactory】中获取类型为【Filter】的，他们分别是
			【requestContextFilter】 -> 【OrderedRequestContextFilter】
			【hiddenHttpMethodFilter】 -> 【OrderedHiddenHttpMethodFilter】
			【formContentFilter】 -> 【OrderedFormContentFilter】
			【springSecurityFilterChain】 -> (上一步已经被加载过了)
			【characterEncodingFilter】 -> 【OrderedCharacterEncodingFilter】
	3.2.5 将上面这几个加入封装成为【FilterRegistrationBean】,然后在加入到【MultiValueMap<Class<?>, ServletContextInitializer> initializers】中。
	3.2.6 适配【EventListener】->目前上下文中没有，先略过。
	3.2.7 上面的步骤已经创建好了【ServletContextInitializerBeans】,由于它实现了集合抽象类,所以将挨个调用上面封装好的【RegistrationBean】的【onStartup】方法。		
            
```



```tex
3.3 【ServletContextInitializerBeans】【onStartup】逻辑
	
	3.3.1 【filter characterEncodingFilter】【FilterRegistrationBean】注册到【ServletContext】
		3.3.1.1 先获取到【Filter】,然后调用【ServletContext】的【addFilter】方法,将该【Filter】加入到【ServletContext】【Map<String, FilterDef> filterDefs】属性中。
		3.3.1.2 加入完成以后会返回一个【ApplicationFilterRegistration】,然后调用【configure】方法对它进行处理,其实就是将【Filter】和【url】的映射关系加入到【ServletContext】【ContextFilterMaps】中。

	3.3.2 【Spring Security】【DelegatingFilterProxyRegistrationBean】注册到【ServletContext】
		3.3.2.1 先获取【Filter】,该【Filter】的类型为【DelegatingFilterProxy】,它的【targetBeanName】属性值为【springSecurityFilterChain】,然后调用【ServletContext】的【addFilter】方法,将该【Filter】加入到【ServletContext】【Map<String, FilterDef> filterDefs】属性中。
		3.3.2.2 加入完成以后会返回一个【ApplicationFilterRegistration】,然后调用【configure】方法对它进行处理,其实就是将【Filter】和【url】的映射关系加入到【ServletContext】【ContextFilterMaps】中。

	3.3.3 【Spring MVC】【DispatcherServletRegistrationBean】注册到【ServletContext】
		3.3.3.1 先将具体的【DispatcherServlet】加入到【StandardContext】【HashMap<String, Container> children】属性中,返回一个【ApplicationServletRegistration】。
		3.3.3.2 将【url】和【servlet name】的映射关系加入到【ServletContext】的【Map<String, String> servletMappings】属性中。

```

```tex
后续我们在创建【ServletContextInitializerBeans】的过程中,会获取到【DelegatingFilterProxyRegistrationBean】,该对象的创建逻辑也很关键。
3.4【SecurityFilterAutoConfiguration】类的【securityFilterChainRegistration】方法向spring上下文中注册一个【DelegatingFilterProxyRegistrationBean】对象,该【bean】【name】属性是【springSecurityFilterChain】,【targetBeanName】属性是【springSecurityFilterChain】。

3.5 而该对象的创建逻辑是在上下文中存在名为【springSecurityFilterChain】的【bean】时才创建的。

3.6【springSecurityFilterChain】是通过配置类【WebSecurityConfiguration】【springSecurityFilterChain】方法创建的,该方法会调用【WebSecurity】【build】方法创建一个【Filter】对象。

3.7【WebSecurity】是通过配置类【WebSecurityConfiguration】【setFilterChainProxySecurityConfigurer】方法创建的。
```



```tex
【Spring Security】初始化流程->【WebSecurity】的构建
3.8 我们在使用【Spring Security】的时候,自定义配置一般是继承自【WebSecurityConfigurerAdapter】,重写相关的方法去添加一些自定义的配置。【WebSecurityConfiguration】【setFilterChainProxySecurityConfigurer】方法在创建完【WebSecurity】后。

3.9 会调用【apply】方法将这个自定义的【SecurityConfigurer】加入到它的【LinkedHashMap<Class<? extends SecurityConfigurer<O, B>>, List<SecurityConfigurer<O, B>>> configurers】中去。
```

```tex
4.0 在【WebSecurity】创建好之后,接下来就是调用【WebSecurityConfiguration】【springSecurityFilterChain】方法,通过【WebSecurity】【build】方法创建一个【Filter】对象。

4.1 详细的流程就是要分析【WebSecurity】【build】方法了。
	【AbstractSecurityBuilder】【build】方法算是一个空壳,具体的创建流程在【AbstractConfiguredSecurityBuilder】【doBuild】方法,【doBuild】方法会执行【beforeInit】【init】【beforeConfigure】【configure】【performBuild】这几个方法。

	【beforeInit】方法没有任何执行逻辑。

	【init】方法拿到提前配置好的【List<SecurityConfigurer】,就是我们重写的那个【WebSecurityConfigurerAdapter】对象,调用该对象的【init】方法。
	该配置的【init】方法执行了两个动作。①拿到【HttpSecurity】,调用【WebSecurity】【addSecurityFilterChainBuilder】方法,将该【HttpSecurity】加入到【WebSecurity】【List<SecurityBuilder<? extends SecurityFilterChain>> securityFilterChainBuilders】属性中来。
	②加入进来以后,还会给该【WebSecurity】设置一个【Runnable postBuildAction】动作,该动作的逻辑是从【HttpSecurity】中拿到【FilterSecurityInterceptor】,设置给【WebSecurity】【filterSecurityInterceptor】属性。

	【beforeConfigure】方法没有任何执行逻辑。

	【configure】方法拿到提前配置好的【List<SecurityConfigurer】,就是我们重写的那个【WebSecurityConfigurerAdapter】对象,调用该对象的【configure】方法。在没有重写自定义逻辑的情况下,该方法也没有执行任何的逻辑。

	【performBuild】方法,拿出刚才在【WebSecurity】【List<SecurityBuilder<? extends SecurityFilterChain>> securityFilterChainBuilders】属性中加入的【HttpSecurity】,调用它的【build】方法返回一个【SecurityFilterChain】,构建成【FilterChainProxy】然后返回。
	这一步其实主要的是【HttpSecurity】【build】方法。

4.2 这一步其实还有一点需要分析一下,就是在适配类【init】方法中【getHttp】获取【HttpSecurity】的过程。(******************************这一块很重要,明天早上重点分析一下***********************************)
	这一步是拿到【WebSecurity】	中的配置类【WebSecurityConfigurerAdapter】,然后调用它的【init】方法。这个【init】的主体逻辑是将一些配置类加入到【HttpSecurity】的配置类列表中去,现在分析下这些主要的配置类。(分析的前提是我们现在是在【WebSecurityConfigurerAdapter】中)。

	第一个是【AuthenticationManager】,通过【authenticationManager】方法获取它,该方法的内部先是执行了一个【configure】方法,参数是【AuthenticationManagerBuilder】【localConfigureAuthenticationBldr】,该方法只是将变量【disableLocalConfigureAuthenticationBldr】改为【true】。
	然后我们根据变量【disableLocalConfigureAuthenticationBldr】是否为【true】,执行不同的逻辑。 
	若是为【true】的话,【AuthenticationManager】的获取将会调用AuthenticationConfiguration】【authenticationConfiguration】属性的【getAuthenticationManager】方法。
	若是为【false】的话,【AuthenticationManager】的获取将会调用【AuthenticationManagerBuilder】【localConfigureAuthenticationBldr】【build】方法获取。(此步骤前提是【disableLocalConfigureAuthenticationBldr】不为【true】)
	执行完成之后,给变量【AuthenticationManagerBuilder】【authenticationBuilder】设置属性【AuthenticationManager】【parentAuthenticationManager】。
	【new】【HttpSecurity】同时将【AuthenticationManagerBuilder】【authenticationBuilder】设置到【HttpSecurity】的【Map<Class<? extends Object>, Object> sharedObjects】属性中。

	第二个是将一些默认过滤器的配置类加入到【HttpSecurity】的配置类列表中。(如【DefaultLoginPageConfigurer】【CsrfConfigurer】等等)。

	第三个是从【spring】上下文中获取类型为【AbstractHttpConfigurer】的对象,使用【HttpSecurity】【apply】方法加入到它的配置类列表中。

	返回【HttpSecurity】之前还有一个【configure】方法,也是对【HttpSecurity】加入一些配置类。

	此后【HttpSecurity】加入到【WebSecurity】的某个属性中。
```



```tex
4.3【Spring Security】初始化流程->【HttpSecurity】的构建
	4.3.1【HttpSecurity】【build】方法其实和【WebSecurity】一样,也是【doBuild】方法执行的【beforeInit】【init】【beforeConfigure】【configure】【performBuild】这几个步骤分析
	
  【beforeInit】方法其实啥也没有做。

  【init】方法拿到提前配置好的【List<SecurityConfigurer】,然后挨个调用【init】方法。

  【beforeConfigure】方法获取到【AuthenticationManagerBuilder】对象,然后调用它的【build】方法创建一个【AuthenticationManager】对象。

  【configure】方法,主要就是拿到配置【Filter】的那些工具类,创建好【Filter】,然后将【Filter】加入到【HttpSecurity】的过滤器列表中去。

  【performBuild】方法创建一个【DefaultSecurityFilterChain】对象,内部包含【List<Filter> filters】。
```



###### 关于【spring security】基础组件

```tex
```













###### 关于【spring security】过滤器

```tex
```



###### 关于【spring security】注解

```tex
1.当使用【spring security】的时候,我们可以在某些业务类或者方法上面添加类似【@PreAuthorize("hasRole('ACTIVITI_USER')")】的注解，以达到对具体某一个类或者某一个方法的权限校验。

2.此处,有两个疑问值得我们去思考 ①方法的权限校验我们如何实现  ②方法的具体校验流程是什么
	
3.最近在学习【actviti7】的东西,发现【actviti7】与【springboot】集成时,依赖了【spring security】。现以其中接口【ProcessRuntime】【ProcessRuntimeImpl】做为分析对象来分析。首先在【ProcessRuntimeImpl】的类上面标注了【@PreAuthorize("hasRole('ACTIVITI_USER')")】注解，当我们在调用它的方法时,【spring security】会校验当前登录的用户是否有角色【ACTIVITI_USER】，如果有正常调用,没有则抛出相关的异常。
```



```tex
【关于权限校验如何实现问题分析】
	一般常用的做法就是创建代理类，我们通过调用代理类进行方法的拦截。在【spring】【BeanFactory】中对【bean】创建生命周期的各个阶段都有扩展，我们可以通过这些扩展来实现对其【bean】创建代理的目标，在【spring】中也是这样做的。

	【spring】【BeanFactory】【bean】创建中,在【AbstractAutowireCapableBeanFactory】【applyBeanPostProcessorsAfterInitialization】方法是调用【BeanFactory】【BeanPostProcessor】【postProcessAfterInitialization】对创建的【bean】进行处理，其中【AOP】一个很关键的类【InfrastructureAdvisorAutoProxyCreator】,就是【spring】【AOP】挑选符合条件的【bean】,为其创建代理对象。

	下面分析【InfrastructureAdvisorAutoProxyCreator】如何对【bean】进行处理,并进行对象的创建。
	【InfrastructureAdvisorAutoProxyCreator】->【AbstractAdvisorAutoProxyCreator】->【AbstractAutoProxyCreator】->【ProxyProcessorSupport】->【ProxyConfig】【AopInfrastructureBean】

	【AbstractAutoProxyCreator】【postProcessAfterInitialization】先以【beanName】和【class】创建缓存【cacheKey】,判断从【Map<Object, Object> earlyProxyReferences】中取得的【bean】是否与当前【bean】相同,如果相同直接返回，如果不同，则调用【wrapIfNecessary】方法。
	
	【AbstractAutoProxyCreator】【wrapIfNecessary】方法->有下面几种条件的直接返回当前的【bean】
	①【Set<String> targetSourcedBeans】中包含【beanName】的。
	②【Map<Object, Boolean> advisedBeans】中【cacheKey】的值为【FALSE】的。
	③ 当前的【class】实现这几个接口的【Advice】【Pointcut】【Advisor】【AopInfrastructureBean】。
	④ 当前的【beanName】以【.ORIGINAL】结尾的。
	⑤ ③和④两种情况会在缓存【Map<Object, Boolean> advisedBeans】中以【cacheKey】为键,值为【FALSE】。
	然后是调用【AbstractAdvisorAutoProxyCreator】【getAdvicesAndAdvisorsForBean】方法获取一个【List<Advisor>】列表。如果该列表为空，会在缓存【Map<Object, Boolean> advisedBeans】中以【cacheKey】为键，【FALSE】为值存储，返回当前【bean】。如果该列表不为空，会在缓存【Map<Object, Boolean> advisedBeans】中以【cacheKey】为键，【TRUE】为值存储，并调用【createProxy】创建代理对象，同时在【Map<Object, Class<?>> proxyTypes】中存储【cacheKey】【proxy.getClass()】，并将代理对象返回。
	
	【AbstractAdvisorAutoProxyCreator】【getAdvicesAndAdvisorsForBean】方法分析
	该方法调用【AbstractAdvisorAutoProxyCreator】【findEligibleAdvisors】,通过【beanName】和【beanClass】获取到一个【List<Advisor> advisors】列表，直接返回。
	
	【AbstractAdvisorAutoProxyCreator】【findEligibleAdvisors】方法分析
	该方法先调用【AbstractAdvisorAutoProxyCreator】【findCandidateAdvisors】获取到一个【List<Advisor> candidateAdvisors】列表，然后在调用【AbstractAdvisorAutoProxyCreator】【findAdvisorsThatCanApply】方法,通过【beanClass】【beanName】过滤出可以应用于该【bean】的【Advisor】列表。在调用【extendAdvisors】方法将一些扩展的【Advisor】增加到列表中,对总的【Advisor】列表排序后返回。(此列表中目前只有一个对象，为【MethodSecurityMetadataSourceAdvisor】)。
	
	【AbstractAdvisorAutoProxyCreator】【findCandidateAdvisors】方法分析
	该方法是调用【BeanFactoryAdvisorRetrievalHelper】【advisorRetrievalHelper】属性的【findAdvisorBeans】方法，从【BeanFactory】中获取类型为【Advisor】的【bean】。(分别是【metaDataSourceAdvisor】【org.springframework.transaction.config.internalTransactionAdvisor】)。
	
	【AbstractAdvisorAutoProxyCreator】【findAdvisorsThatCanApply】方法分析
	该方法是调用的【AopUtils】【findAdvisorsThatCanApply】,通过【beanClass】从刚才获取到的【Advisor】列表中选取可以用于该【beanClass】的【Advisor】列表。 挑选的规则有两个->(Advisor实现了【IntroductionAdvisor】接口,并且调用【canApply】方法返回【true】; 【Advisor】没有实现【IntroductionAdvisor】接口,并且【canApply】方法返回【true】)。
	
	【AopUtils】【canApply】方法分析
	根据实现不同的接口,执行不同的判断逻辑。若是实现了【IntroductionAdvisor】接口,调用【return ((IntroductionAdvisor) advisor).getClassFilter().matches(targetClass);】代码块。 若是实现了【PointcutAdvisor】接口,调用【PointcutAdvisor pca = (PointcutAdvisor) advisor;return canApply(pca.getPointcut(), targetClass, hasIntroductions);】代码块。若是两个接口都没有实现,直接返回【true】。
	若是【MethodSecurityMetadataSourceAdvisor】,它实现了【PointcutAdvisor】接口,则将它转换为【PointcutAdvisor】，并调用该接口【getPointcut】方法，返回一个【Pointcut】【MethodSecurityMetadataSourcePointcut】对象。然后在【canApply】,参数为【Pointcut】【Class】【boolean】的方法中执行逻辑。 第一步是调用【Pointcut】【getClassFilter】方法返回了一个【TrueClassFilter】对象,用【matches】方法匹配【targetClass】,若返回【false】,则直接返回【false】。第二步是调用【Pointcut】【getMethodMatcher】方法返回了一个【MethodSecurityMetadataSourcePointcut】对象,若是获取到的【MethodMatcher】==【MethodMatcher.TRUE】，则直接返回【true】。   后面的判断逻辑是将该【targetClass】和它的接口放入到一个【Class】集合中,然后循环该集合。 每循环一次,从【CLass】中获取到【Method[]】数组，然后用【Class】和【Method】做为参数，调用【MethodSecurityMetadataSourcePointcut】的【matches】，如果该【matches】方法返回【true】,则直接返回。
	
	【MethodSecurityMetadataSourcePointcut】【matches】方法分析
	其实该方法也是拿到【MethodSecurityMetadataSourceAdvisor】【attributeSource】属性【getAttributes】方法,该属性的具体类型是【DelegatingMethodSecurityMetadataSource】。
	
	【DelegatingMethodSecurityMetadataSource】【getAttributes】方法分析
	方法参数为【targetClass】【method】。	先从【Map<DefaultCacheKey, Collection<ConfigAttribute>> attributeCache】缓存中去查找,查找的【key】由方法参数【Method】和【Class】创建。如果从缓存中找到,直接返回回去。如果没有找到,则执行第二步的逻辑。
    在【DelegatingMethodSecurityMetadataSource】中有一个【List<MethodSecurityMetadataSource>】,此步骤的查找逻辑就是挨个调用这几个【MethodSecurityMetadataSource】【getAttributes】方法,参数为【Method】和【Class】。只要在一个中找到了,就缓存在当前的缓存中,并直接返回。
	上述步骤中的【MethodSecurityMetadataSource】是下面这几个,逐个分析他们的【getAttributes】方法。
	【org.springframework.security.access.prepost.PrePostAnnotationSecurityMetadataSource】
	【org.springframework.security.access.annotation.SecuredAnnotationSecurityMetadataSource】
	【org.springframework.security.access.annotation.Jsr250MethodSecurityMetadataSource】
	
	【PrePostAnnotationSecurityMetadataSource】
	该类优先从方法,然后从类中依次去获取@PreFilter @PreAuthorize @PostFilter @PostAuthorize注解。若是这几个注解都为空,直接返回一个空集合回去。
通过这几个注解的相关属性,调用【ExpressionBasedAnnotationAttributeFactory】【createPreInvocationAttribute】方法创建一【PreInvocationAttribute】对象。而后在调用【ExpressionBasedAnnotationAttributeFactory】【createPostInvocationAttribute】方法创建一个【PostInvocationAttribute】对象。将这两个对象加入到一个列表中,然后直接返回。
	
	
	
	【AbstractAutoProxyCreator】【createProxy】方法分析
	先在【bean】对应的【BeanDefinition】先关属性中保存原始的【Class】。而后是使用【cglib】相关【api】创建代理对象,总之有一个很关键的类【MethodSecurityInterceptor】,在代理调用方法的过程中起到鉴权作用。
	(这块细致的地方,先整理完【spring】【AOP】【cglib】代理后再来细说)


	
```



```tex

【关于方法的校验流程的分析】
	【spring security】在【url】认证的最后一步,有一个过滤【FilterSecurityInterceptor】,该过滤器继承了【AbstractSecurityInterceptor】抽象类。而该抽象类同时还有另外一个实现类【MethodSecurityInterceptor】,该类所处理的逻辑就是对具体所请求方法的过滤。
	
	【MethodSecurityInterceptor】【invoke】方法接收一个【MethodInvocation】参数，首先调用【AbstractSecurityInterceptor】【beforeInvocation】方法。
	
	【AbstractSecurityInterceptor】【beforeInvocation】方法分析
	该方法的第一步是通过【MethodSecurityMetadataSource】【securityMetadataSource】属性的【getAttributes】方法获取到【Collection<ConfigAttribute> attributes】列表,具体实现类是【DelegatingMethodSecurityMetadataSource】。当【attributes】属性不为空时,从【spring security】的上下文中获取到【Authentication】对象，交给【AccessDecisionManager】【accessDecisionManager】【decide】方法去处理。
	
	【DelegatingMethodSecurityMetadataSource】【getAttributes】方法分析。
	此方法的逻辑和前面创建代理寻找【Collection<ConfigAttribute>】的逻辑是一样的,所以就不分析了。
	
	【AccessDecisionManager】【accessDecisionManager】【decide】的认证方法分析。
 	这个是【spring securiity】的共性问题,无论是针对【url】的校验,还是针对具体某一个方法的校验,只要没有重写默认的逻辑,都会走该段。
```





##### spring security系列文章

###### 1.认证失败后是如何跳转到默认登录页面的

```tex
1.【FormLoginConfigurer】作为【UsernamePasswordAuthenticationFilter】的配置类,在初始化【new】的时候,父类【AbstractAuthenticationFilterConfigurer】会设置一个【loginPage】属性的值为【/login】。同时创建属性【LoginUrlAuthenticationEntryPoint】【authenticationEntryPoint】,将【loginFormUrl】属性设置为【/login】。
```

```tex
2.【FormLoginConfigurer】【init】方法先调用父类【AbstractAuthenticationFilterConfigurer】【init】方法执行下面的逻辑。
2.1 调用【updateAuthenticationDefaults】方法
	设置一些默认的属性
	
2.2 调用【updateAccessDefaults】方法

2.3 调用【registerDefaultAuthenticationEntryPoint】方法
从【HttpSecurity】中拿到对象【ExceptionHandlingConfigurer】,调用【ExceptionHandlingConfigurer】【defaultAuthenticationEntryPointFor】方法将【LoginUrlAuthenticationEntryPoint】存储到它的【LinkedHashMap<RequestMatcher,AuthenticationEntryPoint> defaultEntryPointMappings】属性中。

2.4【FormLoginConfigurer】【init】方法调用【initDefaultLoginFilter】方法

```

```tex
3.【ExceptionHandlingConfigurer】【configure】方法
3.1 调用【getAuthenticationEntryPoint】方法从【LinkedHashMap<RequestMatcher, AuthenticationEntryPoint> defaultEntryPointMappings】属性中取到【AuthenticationEntryPoint】【LoginUrlAuthenticationEntryPoint】。

2.创建【ExceptionTranslationFilter】时,将【LoginUrlAuthenticationEntryPoint】传给【ExceptionTranslationFilter】的【authenticationEntryPoint】属性。
```

```tex
4.【ExceptionTranslationFilter】【doFilter】方法
4.1 在捕获到异常时，调用【handleSpringSecurityException】方法,进而调用到【sendStartAuthentication】方法,最终调用到【AuthenticationEntryPoint】【commence】方法。
```

```tex
5.【LoginUrlAuthenticationEntryPoint】【commence】方法
5.1 根据之前设置好的【loginFormUrl】属性,构建【redirectUrl】【http://127.0.0.1:8080/login】。
5.2 调用【RedirectStrategy】【redirectStrategy】【DefaultRedirectStrategy】的【sendRedirect】方法重定向。

6.【DefaultRedirectStrategy】【sendRedirect】
6.1 处理【sendRedirect】后调用【HttpServletResponse】【sendRedirect】方法
```



###### 2.spring-session

```tex
2.1 【spring】如何将【SessionRepositoryFilter】注册到【tomcat】的【ApplicationFilterChain】。

2.2 【SessionRepositoryFilter】持久化【session】到【redis】的作用原理。
```



###### 3.jwt

```tex
可以看做是token的一种生成方式,不过它的好处是不用再服务端存储,但是消耗比较大。
```





