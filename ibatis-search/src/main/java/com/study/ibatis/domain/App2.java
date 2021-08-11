package com.study.ibatis.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class App2 {
    public static void main(String[] args) {
        /*List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        list1.add("zt");
        list1.add("zt");
        list1.add("zt");
        list2.add("zt001");
        list2.add("zt002");
        list2.add("zt003");

        List<String> items1 = new ArrayList<>();
        List<String> items2 = new ArrayList<>();
        items1.add("zt");
        items1.add("zt");
        items2.add("zt001");
        items2.add("zt002");

        list1.removeAll(items1);
        list2.removeAll(items2);

        System.err.println(list1);
        System.err.println(list2);*/

        List<KuadiItem> list1 = new ArrayList<>();
        KuadiItem item1 = new KuadiItem("zt", "zt001");
        KuadiItem item2 = new KuadiItem("zt", "zt002");
        KuadiItem item3 = new KuadiItem("zt", "zt003");
        list1.add(item1);
        list1.add(item2);
        list1.add(item3);

        List<KuadiItem> itemList = new ArrayList<>();
        KuadiItem m1 = new KuadiItem("zt", "zt001");
        itemList.add(m1);

        list1.removeAll(itemList);

        System.err.println(list1);
    }


    private static class KuadiItem {
        private String kuaidiCom;
        private String kuaidiNum;

        public KuadiItem(String kuaidiCom, String kuaidiNum) {
            this.kuaidiCom = kuaidiCom;
            this.kuaidiNum = kuaidiNum;
        }

        public String getKuaidiCom() {
            return kuaidiCom;
        }

        public void setKuaidiCom(String kuaidiCom) {
            this.kuaidiCom = kuaidiCom;
        }

        public String getKuaidiNum() {
            return kuaidiNum;
        }

        public void setKuaidiNum(String kuaidiNum) {
            this.kuaidiNum = kuaidiNum;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            KuadiItem kuadiItem = (KuadiItem) o;
            return Objects.equals(kuaidiCom, kuadiItem.kuaidiCom) && Objects.equals(kuaidiNum, kuadiItem.kuaidiNum);
        }

        @Override
        public int hashCode() {
            return Objects.hash(kuaidiCom, kuaidiNum);
        }
    }
}
