package com.study.resource;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class ResourceApp {

    public static void main(String[] args) {
        String file = "<?xml version='1.0' encoding='GBK'?>"
                + "<计算机书籍列表><书><书名>疯狂Java讲义"
                + "</书名><作者>李刚</作者></书><书><书名>"
                + "轻量级Java EE企业应用实战</书名><作者>李刚"
                + "</作者></书></计算机书籍列表>";
        byte[] fileBytes = file.getBytes();
        Resource resource = new ByteArrayResource(fileBytes);
        System.err.println(resource.getDescription());
        SAXReader reader = new SAXReader();
        try {
            Document doc = reader.read(resource.getInputStream());
            Element el = doc.getRootElement();
            List l = el.elements();
            for(Iterator it = l.iterator(); it.hasNext(); ) {
                Element book = (Element)it.next();
                List ll = book.elements();
                for (Iterator it2 = ll.iterator(); it2.hasNext(); ) {
                    Element ee = (Element)it2.next();
                    System.err.println(ee.getText());
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
