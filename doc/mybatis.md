###### mybatis里面的重要对象

```tex
SqlSessionFactoryBuilder -> 调用build方法可以创建一个SqlSessionFactory

SqlSessionFactory -> 使用openSession方法可以创建一个SqlSession
	DefaultSqlSessionFactory

SqlSession -> 默认实现是DefaultSqlSession,里面封装了操作数据库的相关方法。

Configuration -> mybatis的核心配置类,解析xml文件获取到的对象都存储在里面。

Executor -> 执行器,有下面几个实现类
	CachingExecutor -> 直接实现Executor接口,只要是添加了一层缓存。
	BaseExecutor -> Executor的抽象类实现。(有实现缓存的部分)
	SimpleExecutor -> 实现真正的查询逻辑。
	
StatementHandler -> 处理Statement(创建和参数准备)
	RoutingStatementHandler	
	PreparedStatementHandler

ParameterHandler -> 处理参数
	DefaultParameterHandler
	
ResultSetHandler -> 处理结果集
	DefaultResultSetHandler
```



##### 简单的查询流程

```tex
mybatis的使用方式有两种
	指定映射文件
	
	指定mapper接口(Mapper接口开发)(常用)

```



```tex
1.根据【id】从【Configuration】的【Map<String, MappedStatement> mappedStatements】属性中获取到一个【MappedStatement】对象(该属性中的对象在解析阶段放入)。
2.【DefaultSqlSession】调用【Executor】【executor】属性的【query】方法查询。内部【Executor】【executor】属性的类型为【CachingExecutor】,它的内部有一个【Executor】【delegate】属性,类型为【SimpleExecutor】。

【CachingExecutor】->从【MappedStatement】获取一个【Cache】对象。若是该对象为空,执行【SimpleExecutor】【query】方法；若是该对象非空,执行从【Cache】中获取数据的逻辑。

【SimpleExecutor】

```

