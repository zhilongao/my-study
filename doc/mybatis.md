```tex
SqlSession -> DefaultSqlSession


1.根据【id】从【Configuration】的【Map<String, MappedStatement> mappedStatements】属性中获取到一个【MappedStatement】对象(该属性中的对象在解析阶段放入)。
2.【DefaultSqlSession】调用【Executor】【executor】属性的【query】方法查询。内部【Executor】【executor】属性的类型为【CachingExecutor】,它的内部有一个【Executor】【delegate】属性,类型为【SimpleExecutor】。

【CachingExecutor】->从【MappedStatement】获取一个【Cache】对象。若是该对象为空,执行【SimpleExecutor】【query】方法；若是该对象非空,执行从【Cache】中获取数据的逻辑。

【SimpleExecutor】

```

