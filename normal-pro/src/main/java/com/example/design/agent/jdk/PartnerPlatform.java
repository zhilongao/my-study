package com.example.design.agent.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * 陪玩平台
 * @author gaozhilong
 */
public class PartnerPlatform {

    static List<Partner> partners = new ArrayList<>();

    static {
        partners.add(new IndividualPartner("安妮"));
        partners.add(new IndividualPartner("小米"));
        partners.add(new IndividualPartner("小明"));
    }


    public static Partner getPartner(int budget) {
        Partner partner = partners.remove(0);
        return (Partner)Proxy.newProxyInstance(
                partner.getClass().getClassLoader(),
                partner.getClass().getInterfaces(),
                new PlatformInvocationHandler(budget, partner));
    }

    public static class PlatformInvocationHandler implements InvocationHandler {
        private int budget;

        private Partner partner;

        private boolean status = false;

        public PlatformInvocationHandler(int budget, Partner partner) {
            this.budget = budget;
            this.partner = partner;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String methodName = method.getName();
            if ("receiveMoney".equals(methodName)) {
                int money = (int)args[0];
                // 平台抽成一半，好黑啊
                args[0] = money / 2;
                // 钱是否给够
                this.status = money >= budget;
            }
            if (status) {
                return method.invoke(partner, args);
            }
            return null;
        }
    }
}
