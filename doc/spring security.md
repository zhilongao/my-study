###### 请求如何到达【spring security】

```tex
-----------------------------------------具体认证和授权流程的分析----------------------------------------------------
1.当一个请求到达【Tomcat】【ApplicationFilterChain】,挨个循环获取它所持有的【ApplicationFilterConfig[] filters】,调用【ApplicationFilterConfig】【getFilter】方法获取到具体的【Filter】,调用【doFilter】方法。
【SpringSecurity】先是获取到【DelegatingFilterProxy】,然后从【Spring】上下文中找到名称为【springSecurityFilterChain】,类型为【Filter】的【bean】,设置为它的【Filter】【delegate】属性。该【delegate】类型为【FilterChainProxy】

2.【FilterChainProxy】【doFilterInternal】方法根据【ServletRequest】【request】,从【List<SecurityFilterChain> filterChains】中找到一个匹配该【request】的【SecurityFilterChain】,并调用它的【getFilters】获取一个【Filter】列表。

3.一个【VirtualFilterChain】被创建并携带获取到的【Filter】列表以及【Tomcat】的调用链以及【request】。该类的【doFilter】方法也是挨个调用【Filter】列表中的【doFilter】方法。
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

