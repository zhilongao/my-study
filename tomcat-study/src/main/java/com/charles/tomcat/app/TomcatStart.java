package com.charles.tomcat.app;

import com.charles.tomcat.servlet.IndexServlet;
import com.charles.tomcat.servlet.OrderServlet;
import org.apache.catalina.*;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.*;
import org.apache.catalina.startup.Tomcat;

public class TomcatStart {
    private static int port = 8088;

    private static String indexContextPath = "/index";

    private static String orderContextPath = "/order";


    public static void main(String[] args) {
        try {
            startTomcat();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }

    private static void startTomcat() throws LifecycleException {
        // 创建tomcat
        Tomcat tomcat = new Tomcat();
        // 设置端口号
        tomcat.setPort(port);
        // 是否自动部署
        tomcat.getHost().setAutoDeploy(false);

        //创建上下文
        StandardContext indexContext = new StandardContext();
        indexContext.setPath(indexContextPath); //上下文路径
        indexContext.addLifecycleListener(new Tomcat.FixContextListener()); //监听上下文

        StandardContext orderContext = new StandardContext();
        orderContext.setPath(orderContextPath);
        orderContext.addLifecycleListener(new Tomcat.FixContextListener());

        // tomcat添加上下文
        tomcat.getHost().addChild(indexContext);
        tomcat.getHost().addChild(orderContext);

        // tomcat添加Servlet(到指定的Context下)
        tomcat.addServlet(indexContextPath,"indexServlet",new IndexServlet());
        tomcat.addServlet(orderContextPath, "orderServlet", new OrderServlet());

        // tomcat添加servlet和url映射
        indexContext.addServletMappingDecoded("/index","indexServlet");
        orderContext.addServletMappingDecoded("/index", "orderServlet");

        // 开启tomcat
        tomcat.start();
        System.out.println("tomcat 启动成功");
        // 程序等待响应
        tomcat.getServer().await();
    }

    private static void buildTomcat() throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        // Server

        // Service1
        Service service1 = new StandardService();
        service1.setName("service-1");
        Connector connector1 = new Connector();
        Connector connector2 = new Connector();
        connector1.setProtocolHandlerClassName("org.apache.coyote.http11.Http11NioProtocol");
        connector2.setProtocolHandlerClassName("org.apache.coyote.http11.Http11AprProtocol");
        // Service1 add connector
        service1.addConnector(connector1);
        service1.addConnector(connector2);
        // Service1 set engine
        Engine engine1 = new StandardEngine();
        Host host1 = new StandardHost();
        Host host2 = new StandardHost();
        engine1.addChild(host1);
        engine1.addChild(host2);
        service1.setContainer(engine1);

        // Service2
        Service service2 = new StandardService();
        service2.setName("service-2");
        Connector connector3 = new Connector();
        Connector connector4 = new Connector();
        connector3.setProtocolHandlerClassName("org.apache.coyote.http11.Http11NioProtocol");
        connector4.setProtocolHandlerClassName("org.apache.coyote.http11.Http11AprProtocol");
        // Service2 add connector
        service2.addConnector(connector3);
        service2.addConnector(connector4);
        // Service2 set engine
        Engine engine2 = new StandardEngine();
        Host host3 = new StandardHost();
        Host host4 = new StandardHost();
        engine2.addChild(host3);
        engine2.addChild(host4);
        service2.setContainer(engine2);

        // Server and Service relation
        tomcat.getServer().addService(service1);
        tomcat.getServer().addService(service2);

        // 优化相关
        tomcat.start();

        tomcat.getServer().await();
    }

    // Service




}
