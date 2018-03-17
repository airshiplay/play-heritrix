//package com.airlenet.crawler.heritrix;
//
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;
//import javax.xml.xpath.XPath;
//import javax.xml.xpath.XPathConstants;
//import javax.xml.xpath.XPathExpressionException;
//import javax.xml.xpath.XPathFactory;
//import java.io.File;
//
///**
// * @author airlenet
// * @version 2018-01-12
// */
//public class XmlBeanTest {
//    public static void main(String args[]){
//        String xmlPath ="/Users/lig/Documents/apache-tomcat-8.5.15/bin/jobs/55/crawler-beans.cxml";
//        // 创建文件工厂实例
//        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//        dbf.setIgnoringElementContentWhitespace(true);
//        try {
//            // 从XML文档中获取DOM文档实例
//            DocumentBuilder db = dbf.newDocumentBuilder();
//            // 获取Document对象
//            Document xmldoc = db.parse(xmlPath);
//
//            // 获取根节点
//            Element root = xmldoc.getDocumentElement();
//            // 定位id为001的节点
//            Element per = (Element) selectSingleNode("/beans/bean[@id='simpleOverrides']/property/value",
//                    root);
//            // 将age节点的内容更改为28
//            per.setTextContent("metadata.operatorContactUrl=ENTER_AN_URL_WITH_YOUR_CONTACT_INFO_HERE_FOR_WEBMASTERS_AFFECTED_BY_YOUR_CRAWL\n" +
//                    "metadata.jobName=basic\n" +
//                    "metadata.description=Basic crawl starting with useful defaults");
//            per.getElementsByTagName("age").item(0).setTextContent("28");
//            // 保存
//            TransformerFactory factory = TransformerFactory.newInstance();
//            Transformer former = factory.newTransformer();
//            former.transform(new DOMSource(xmldoc), new StreamResult(new File(
//                    xmlPath)));
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//
//    }
//
//    // 修改节点信息
//    public static Node selectSingleNode(String express, Element source) {
//        Node result = null;
//        //创建XPath工厂
//        XPathFactory xpathFactory = XPathFactory.newInstance();
//        //创建XPath对象
//        XPath xpath = xpathFactory.newXPath();
//        try {
//            result = (Node) xpath.evaluate(express, source, XPathConstants.NODE);
//            System.out.println(result);
//        } catch (XPathExpressionException e) {
//            System.out.println(e.getMessage());
//        }
//
//        return result;
//    }
//}
