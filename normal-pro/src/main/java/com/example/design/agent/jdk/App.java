package com.example.design.agent.jdk;

/**
 * app端
 * @author gaozhilong
 */
public class App {

    public static void main(String[] args) {
        Player player = new Player("jack");
        Partner partner = PartnerPlatform.getPartner(20);
        partner.receiveMoney(20);
        partner.playWith(player);
    }

}
