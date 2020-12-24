> 分布式事务解决方案
> 1. 什么是分布式事务
> 2. 分布式事务解决方案
> 
```text
    事务(数据库事务database transaction)
        A(原子性)  事务必须是原子的工作单元。—>通过日志实现的。
        C(一致性)  事务在完成的时候，必须保证数据的状态是一致性的。 ->通过日志实现的。
        I(隔离性)  并发事务所做的修改，必须和其它事务所做的修改是隔离的。->通过锁来实现的。
        D(持久性)  事务完成后对系统的影响是永久性的。->通过日志实现的。
        undo日志
        redo日志
           1. mysql记录redo和undo文件，确保在磁盘上持久化。
           2. 更新数据记录。
           3. 提交事务(redo)。
    分布式事务
        数据库分库分表
        SOA化(服务拆分)
```
> 1. 扣除库存 
> 2. 更新订单状态

单点问题  数据一致性问题

> X/OpenDTP事务模型
> ```text
    X/Open Dirtributed Transaction Processing Reference Model
    X/Open 是一个组织机构，定义出一套事务标准，定义规范API接口，具体实现由厂商来实现。
    J2EE遵循了X/Open DTP规范，设计并实现了java里面的分布式事务编程接口规范JTA。
    XA是X/Open DTP定义的中间件和数据库之间的接口规范，XA接口函数由数据库厂商提供。
    X/Open DTP角色
    AP application(应用)
    RM resource manager(资源管理器:数据库)
    TM transaction manager(事务管理器，事务协调者)
    AP:负责触发分布式事务的指令到TM。
    TM:指令分发到RM。
    CAP(一致性 可用性 分区容错性)
    2PC(two-phase-commit)：用来保证分布式事务的完整性。
        阶段1：
            1. TM向所有AP发送事务内容，询问是否可以提交事务请求，并且等待各个AP的响应。
            2. 各个节点执行事务操作，将undo和redo信息记录到事务日志中，记录到事务日志中。
            3. 各个AP向TM反馈事务询问的想用。
        阶段2：
            1. 执行事务提交                
            2. 中断事务    
     存在的问题
        1. 数据一致性问题
        2. 同步阻塞   
        
     3PC(three-phase-commit):
        阶段1：canCommit
            
        阶段2: preCommit
            
        阶段3: doCommit
```