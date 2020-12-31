1.java的跨平台
    每种平台都会有单独的jvm，jvm接收字节码文件，编译成相应的机器码指令，然后运行。
2.jdk jre jvm
    jre包含jvm,jdk包含jre。
    jre中包含了常用的开发jar包。
    jdk中除了jre,还有相应的工具。
3.处理流程
    java文件->编译成class文件->类加载，加载进jvm。
    装载->解析->初始化
    类加载机制->双亲委派机制
4.jvm的运行时数据区
    线程共享: 
        堆    
        方法区(类的静态信息)
    线程独享
        jvm栈(栈帧:局部变量表  操作数栈  动态链接  方法返回)
        本地方法栈
        程序计数器       
5.jvm的垃圾收集
    垃圾的确定:引用计数  可达性分析
    垃圾回收算法:
        标记清除 标记整理(适用于老年代)
        复制算法(适用于新生代)
    垃圾收集器:
        SerialNew(新生代单线程垃圾收集器)
        SerialOld(老年代单线程垃圾收集器)
        ParallelNew(新生代多线程垃圾收集器)
        ParallelScanev     
        CMS(老年代垃圾收集器):初始标记 并发标记 重新标记 并发清除
        G1(新生代和老年代都适用的垃圾收集器): 内存不再分新生代和老年代，region的概念。
6.jvm内存调优常用的工具
   阿里的工具    