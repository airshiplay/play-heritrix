package com.airlenet.crawler.heritrix;

import com.airlenet.crawler.heritrix.modules.Data;
import com.airlenet.crawler.heritrix.modules.Item;
import com.airlenet.crawler.heritrix.modules.Job;
import com.airlenet.crawler.heritrix.modules.Page;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * @author airlenet
 * @version 2018-01-06
 */
public class XpathTest {
    public static void main(String args[]){
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.setDefaultUseWrapper(false);
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        InputStream inputStream = XpathTest.class.getClassLoader().getResourceAsStream("xpath.xml");
        try {
            Job job = new Job();
            job.setName("aa");
            ArrayList<Page> pages = new ArrayList<Page>();
            Page page = new Page();
            page.setUrl("http://");
            ArrayList<Data> datas = new ArrayList<Data>();
            Data data = new Data();
            data.setType("list");
            ArrayList<Item> items = new ArrayList<Item>();
            Item item = new Item();
            item.setXpath("//xpaht");
            items.add(item);
            data.setItems(items);
            datas.add(data);
            page.setDatas(datas);
            pages.add(page);


            page = new Page();
            page.setUrl("http://");
            pages.add(page);
            job.setPages(pages);
          System.out.print(  xmlMapper.writeValueAsString(job));

            Job job2 = xmlMapper.readValue(inputStream, Job.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
