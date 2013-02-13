package com.rooney.Mess;

import java.io.StringReader;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import org.xml.sax.InputSource;
import static junit.framework.Assert.*;

public class XPathDemo {

     public static void main(String[] args) throws Exception {
        String xml = "<car><manufacturer>toyota</manufacturer></car>";
        String xpath = "/car/manufacturer";
        XPath xPath =  XPathFactory.newInstance().newXPath();
        assertEquals("toyota",xPath.evaluate(xpath, new InputSource(new StringReader(xml))));
    }
    
}
