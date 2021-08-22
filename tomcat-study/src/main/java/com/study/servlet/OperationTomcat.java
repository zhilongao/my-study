package com.study.servlet;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

public class OperationTomcat {
    private static int port = 8088;
    private static String contentPath = "/study";

    public static void main(String[] args) {
        try {
            startTomcat();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }

    private static void startTomcat() throws LifecycleException {
        //创建tomcat
        Tomcat tomcat = new Tomcat();
        //设置端口号
        tomcat.setPort(port);
        //是否自动部署
        tomcat.getHost().setAutoDeploy(false);
        //创建上下文
        StandardContext context = new StandardContext();
        context.setPath(contentPath);
        //监听上下文
        context.addLifecycleListener(new Tomcat.FixContextListener());

        //添加一个上下文
        tomcat.getHost().addChild(context);

        tomcat.addServlet(contentPath,"indexServlet",new IndexServlet());
        //添加servlet url映射
        context.addServletMappingDecoded("/index","indexServlet");
        //开启tomcat
        tomcat.start();
        System.out.println("tomcat 启动成功");
        //程序等待响应
        tomcat.getServer().await();
    }
}
