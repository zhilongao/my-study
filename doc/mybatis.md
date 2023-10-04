###### mybatis里面的核心对象

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

###### 使用sql全限定名查询

```java
@Test
public void testSelectListV1() throws IOException {
    try (SqlSession sqlSession = MyBatisUtils.openSession()){
        // 执行sql语句
        List<User> userList = sqlSession.selectList("com.study.mybatis.mapper.UserMapper.selectList");
        // 遍历数据
        userList.forEach(System.out::println);
    } catch (Exception e) {
    	e.printStackTrace();
    }
}
// 1.直接使用SqlSession封装好的方法,使用mapper的namespace加上sql的id去查询数据

```



###### 接口开发的查询流程

```java
@Test
public void testSelectListV2() {
    try (SqlSession sqlSession = MyBatisUtils.openSession()){
        // 获取mapper接口
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        // 查询数据
        List<User> userList = userMapper.selectList();
        // 遍历数据
        userList.forEach(System.out::println);
    } catch (Exception e) {
        e.printStackTrace();
    }
}
//1.通过sqlSession.getMapper获取到接口代理对象
//2.调用接口的方法查询数据
```



##### 查询流程分析

###### UserMapper代理对象的创建

```tex
1.【SqlSession】【getMapper】调用到【Configuration】【getMapper】方法。【Configuration】内部的【MapperRegistry】【mapperRegistry】属性调用【getMapper】方法。

2.【MapperRegistry】【mapperRegistry】方法分析
	2.1 先从内部【Map<Class<?>, MapperProxyFactory<?>> knownMappers】属性中获取到【UserMapper】对应的【MapperProxyFactory】。
	2.2 调用【MapperProxyFactory】【newInstance】创建代理对象。
	
3.【MapperProxyFactory】【newInstance】方法分析
	3.1 先创建一个【MapperProxy】对象。
	3.2 使用jdk的代理创建方法【Proxy】【newProxyInstance】方式创建代理对象。
	使用jdk创建代理需要一个接口和一个【InvocationHandler】,此处创建的代理接口为【UserMapper】，【InvocationHandler】为【MapperProxy】。
```



###### UserMapper代理对象的查询逻辑

```tex
1.【UserMapper】【selectList】方法分析
	4.1 因为创建的【UserMapper】是一个代理对象,所以方法会调用到【MapperProxy】【invoke】方法。
	
2.【MapperProxy】【invoke】方法分析
	5.1 如果是【Object】上面定义的方法,直接调用。
	5.2 调用【cachedInvoker】方法，返回一个【MapperMethodInvoker】【PlainMethodInvoker】对象,内部持有一个【MapperMethod】对象属性。
	5.3 调用【PlainMethodInvoker】【invoke】方法，实际上调用到【MapperMethod】【execute】方法。

3.【MapperMethod】【execute】方法分析
	6.1 根据内部的一个【SqlCommand】【command】指令,判断执行哪种类型的操作。
	6.2 根据判断,拿到相关参数,调用【SqlSession】里面直接封装好的方法。相当于使用【sql全限定名查询】的第一步。
```



###### SqlSession的查询逻辑大体流程

```tex
1.根据【id】从【Configuration】的【Map<String, MappedStatement> mappedStatements】属性中获取到一个【MappedStatement】对象(该属性中的对象在解析阶段放入)。

2.【DefaultSqlSession】调用【Executor】【executor】属性的【query】方法查询。内部【Executor】【executor】属性的类型为【CachingExecutor】,它的内部有一个【Executor】【delegate】属性,类型为【SimpleExecutor】。

【CachingExecutor】->从【MappedStatement】获取一个【Cache】对象。若是该对象为空,执行【SimpleExecutor】【query】方法；若是该对象非空,执行从【Cache】中获取数据的逻辑。

【SimpleExecutor】->首先是从它的父类【BaseExecutor】【PerpetualCache】【localCache】本地缓存中查找,若是没有找到，则执行【SimpleExecutor】【doQuery】方法。

3.【SimpleExecutor】【doQuery】方法分析
	3.1 通过【Configuration】【newStatementHandler】方法创建一个【StatementHandler】【RoutingStatementHandler】对象。
	
	3.2 【prepareStatement】方法,获取到【Connection】并使用刚才创建好的【StatementHandler】准备要执行的【Statement】。(这里的准备工作包括调用【StatementHandler】【prepare】方法和【parameterize】方法)。
		3.2.1 【StatementHandler】【RoutingStatementHandler】【prepare】方法
			直接调用它内部【StatementHandler】【delegate】【PreparedStatementHandler】的【prepare】方法,创建一个【Statement】对象。
			
		3.2.2 【StatementHandler】【RoutingStatementHandler】【parameterize】方法
			直接调用它内部【StatementHandler】【delegate】【PreparedStatementHandler】的【parameterize】方法，而这个方法会委托给它的属性【ParameterHandler】【parameterHandler】【DefaultParameterHandler】的【setParameters】方法。	
	
	3.3 调用刚才创建好的【StatementHandler】【RoutingStatementHandler】【query】方法
		3.3.1 直接调用它内部【StatementHandler】【delegate】【PreparedStatementHandler】的【query】方法,执行查询
	
	3.4 【StatementHandler】【PreparedStatementHandler】的【query】方法
		3.4.1 调用【PreparedStatement】【execute】方法。
		3.4.2 调用内部属性【ResultSetHandler】【resultSetHandler】【DefaultResultSetHandler】的【handleResultSets】方法处理	  【PreparedStatement】执行后的结果并返回。
	

```



##### 一些核心对象的详细方法分析





##### mybatis插件相关的东西



