package com.airlenet.crawler.heritrix.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;

/**
 * @author airlenet
 * @version 2018-01-13
 */

public class CrawlerBeansUtil {


    public static Document getRootDocument(File crawlerBeansCxml){
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringElementContentWhitespace(true);
        try {
            // 从XML文档中获取DOM文档实例
            DocumentBuilder db = dbf.newDocumentBuilder();
            // 获取Document对象
            Document xmldoc = db.parse(crawlerBeansCxml);
            // 获取根节点
            Element root = xmldoc.getDocumentElement();
            return xmldoc;
        }catch (Exception e){
            // ignore
        }
        return  null;
    }

    public static void modify(Document xmldoc, String url, String jobName, String jobDesc, String seeds) {
        try {
            Element root = xmldoc.getDocumentElement();
            Element simpleOverrides = (Element) selectSingleNode("/beans/bean[@id='simpleOverrides']/property/value",
                    root);
            simpleOverrides.setTextContent("metadata.operatorContactUrl="+url+"\n" +
                    "metadata.jobName="+jobName+"\n" +
                    "metadata.description="+jobDesc);
            Element longerOverrides = (Element) selectSingleNode("/beans/bean[@id='longerOverrides']/property/props/prop[@key='seeds.textSource.value']",
                    root);

            longerOverrides.setTextContent(seeds);


            Element operatorContactUrl = (Element) selectSingleNode("/beans/bean[@id='metadata']/property[@name='operatorContactUrl']",
                    root);
            operatorContactUrl.setAttribute("value",url);

            Element jobNameElement = (Element) selectSingleNode("/beans/bean[@id='metadata']/property[@name='jobName']",
                    root);
            jobNameElement.setAttribute("jobName",jobName);

            Element descriptionElement = (Element) selectSingleNode("/beans/bean[@id='metadata']/property[@name='description']",
                    root);
            descriptionElement.setAttribute("description",jobDesc);


        } catch (Exception e) {

        }

    }

    public static void save(File crawlerBeansCxml,  Document xmldoc ){
        try {
            // 保存
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer former = factory.newTransformer();
            former.transform(new DOMSource(xmldoc), new StreamResult(crawlerBeansCxml));
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

    }

    // 修改节点信息
    public static Node selectSingleNode(String express, Element source) {
        Node result = null;
        //创建XPath工厂
        XPathFactory xpathFactory = XPathFactory.newInstance();
        //创建XPath对象
        XPath xpath = xpathFactory.newXPath();
        try {
            result = (Node) xpath.evaluate(express, source, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }
}
