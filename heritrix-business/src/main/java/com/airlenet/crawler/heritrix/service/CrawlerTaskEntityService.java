package com.airlenet.crawler.heritrix.service;

import com.airlenet.crawler.heritrix.util.CrawlerBeansUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.archive.crawler.framework.Engine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.airlenet.repo.jpa.EntityService;
import com.airlenet.crawler.heritrix.entity.CrawlerTaskEntity;
import com.airlenet.crawler.heritrix.repo.CrawlerTaskEntityRepository;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.io.File;
import java.io.IOException;

/**
 * 爬取任务
 *
 * @author  airlenet
 * @version  2018-01-04
 */
@Service
public class CrawlerTaskEntityService extends EntityService<CrawlerTaskEntity, Long> {
	
	@Autowired
	private CrawlerTaskEntityRepository crawlerTaskEntityRepository;

    @Autowired
    private Engine engine;

    @Transactional(readOnly = false, value = "transactionManager")
    @Override
    public CrawlerTaskEntity save(CrawlerTaskEntity entity) {
        boolean entityNew = entity.isNew();
        CrawlerTaskEntity crawlerTaskEntity = super.save(entity);
        File newJobDir = new File(engine.getJobsDir(), entity.getTaskName());
        if (entityNew) {
            if (newJobDir.exists()) {
                try {
                    FileUtils.forceDelete(newJobDir);
                } catch (IOException e) {
                    // ignore  throw new DataIntegrityViolationException("Duplicate entry '"+entity.getJobName()+"' for key");
                }
            }
            engine.createNewJobWithDefaults(newJobDir);
            engine.findJobConfigs();
        }else{
            if (!newJobDir.exists()) {
                engine.createNewJobWithDefaults(newJobDir);
                engine.findJobConfigs();
            }
        }

        String url = "http://127.0.0.1";
        String jobName = crawlerTaskEntity.getTaskName();
        String jobDesc = StringUtils.isNotEmpty(crawlerTaskEntity.getTaskDesc())?crawlerTaskEntity.getTaskDesc():crawlerTaskEntity.getTaskName();
        String seeds = crawlerTaskEntity.getSeeds();


        File crawlerBeansCxml = new File(newJobDir, "crawler-beans.cxml");

        Document document = CrawlerBeansUtil.getRootDocument(crawlerBeansCxml);
        Element root = document.getDocumentElement();
        Element simpleOverrides = (Element) CrawlerBeansUtil.selectSingleNode("/beans/bean[@id='simpleOverrides']/property/value",
                root);
        simpleOverrides.setTextContent("\n" +
                "metadata.operatorContactUrl=" + url + "\n" +
                "metadata.jobName=" + jobName + "\n" +
                "metadata.description=" + jobDesc + "\n");
        Element longerOverrides = (Element) CrawlerBeansUtil.selectSingleNode("/beans/bean[@id='longerOverrides']/property/props/prop[@key='seeds.textSource.value']",
                root);

        longerOverrides.setTextContent(seeds);


        Element operatorContactUrl = (Element) CrawlerBeansUtil.selectSingleNode("/beans/bean[@id='metadata']/property[@name='operatorContactUrl']",
                root);
        operatorContactUrl.setAttribute("value", url);

        Element jobNameElement = (Element) CrawlerBeansUtil.selectSingleNode("/beans/bean[@id='metadata']/property[@name='jobName']",
                root);
        jobNameElement.setAttribute("value", jobName);

        Element descriptionElement = (Element) CrawlerBeansUtil.selectSingleNode("/beans/bean[@id='metadata']/property[@name='description']",
                root);
        descriptionElement.setAttribute("value", jobDesc);

        //取消robots下载，解析限制
        Element preconditionsElement = (Element) CrawlerBeansUtil.selectSingleNode("/beans/bean[@id='preconditions']", root);///beans/bean/[@id='preconditions']
        preconditionsElement.setAttribute("class", "com.airlenet.crawler.heritrix.modules.PreconditionEnforcer");

        Node beansNode = preconditionsElement.getParentNode();


        Element queueAssignmentPolicyElement = (Element) CrawlerBeansUtil.selectSingleNode("/beans/bean[@id='queueAssignmentPolicy']", root);///beans/bean/[@id='preconditions']
        if(queueAssignmentPolicyElement ==null){
            queueAssignmentPolicyElement =document.createElement("bean");
            queueAssignmentPolicyElement.setAttribute("id","queueAssignmentPolicy");
            beansNode.appendChild(queueAssignmentPolicyElement);
        }//
        //com.airlenet.crawler.heritrix.modules.ELFHashQueueAssignmentPolicy
        queueAssignmentPolicyElement.setAttribute("class", "org.archive.crawler.frontier.HostnameQueueAssignmentPolicy");



        //自定义抽取html文件，TODO 待修改
        Element extractorHtmlElement = (Element) CrawlerBeansUtil.selectSingleNode("/beans/bean[@id='extractorHtml']", root);
        extractorHtmlElement.setAttribute("class", "com.airlenet.crawler.heritrix.modules.PlayWebDriverExtractorHTML");

        //限制爬取范围id="acceptSurts"
        Element acceptSurtHtmlElement = (Element) CrawlerBeansUtil.selectSingleNode("/beans/bean[@id='acceptSurts']", root);

        Element decisionElement = (Element) CrawlerBeansUtil.selectSingleNode("property[@name='decision']", acceptSurtHtmlElement);
        if(decisionElement==null){
            decisionElement =document.createElement("property");
            decisionElement.setAttribute("name", "decision");
            decisionElement.setAttribute("value", "ACCEPT");
            acceptSurtHtmlElement.appendChild(decisionElement);
        }

        if(StringUtils.isEmpty(entity.getCrawlScope()) && decisionElement!=null){
            acceptSurtHtmlElement.removeChild(decisionElement);
        }

        Element surtsSourceElement = (Element) CrawlerBeansUtil.selectSingleNode("property[@name='surtsSource']", acceptSurtHtmlElement);
        Element surtsSourceBeanPropertyvalueElement = (Element) CrawlerBeansUtil.selectSingleNode("property[@name='surtsSource']/bean/property/value", acceptSurtHtmlElement);
        if(surtsSourceElement ==null){
            surtsSourceElement = document.createElement("property");
            surtsSourceElement.setAttribute("name", "surtsSource");

            Element surtsSourceBeanElement = document.createElement("bean");
            surtsSourceBeanElement.setAttribute("class","org.archive.spring.ConfigString");
            Element surtsSourceBeanPropertyElement = document.createElement("property");
            surtsSourceBeanPropertyElement.setAttribute("name","value");
            surtsSourceBeanPropertyvalueElement = document.createElement("value");

            surtsSourceElement.appendChild(surtsSourceBeanElement);
            surtsSourceBeanElement.appendChild(surtsSourceBeanPropertyElement);
            surtsSourceBeanPropertyElement.appendChild(surtsSourceBeanPropertyvalueElement);

            acceptSurtHtmlElement.appendChild(surtsSourceElement);
        }
        if(StringUtils.isEmpty( entity.getCrawlScope()) && surtsSourceElement!=null ){
            acceptSurtHtmlElement.removeChild(surtsSourceElement);
        }else{
            surtsSourceBeanPropertyvalueElement.setTextContent(entity.getCrawlScope());
        }


        ///XML解析
        Element xmlMapperElement = (Element) CrawlerBeansUtil.selectSingleNode("/beans/bean[@id='xmlMapper']", root);
        if (xmlMapperElement == null) {
            Element beansElement = (Element) CrawlerBeansUtil.selectSingleNode("/beans", root);
            xmlMapperElement = document.createElement("bean");
            xmlMapperElement.setAttribute("class", "com.fasterxml.jackson.dataformat.xml.XmlMapper");
            xmlMapperElement.setAttribute("id", "xmlMapper");
            beansElement.appendChild(xmlMapperElement);
        }

        if(entity.getTaskType() == CrawlerTaskEntity.TaskType.Download){//下载类任务
            Element writerHtmlElement = (Element) CrawlerBeansUtil.selectSingleNode("/beans/bean[@id='warcWriter']", root);
            writerHtmlElement.setAttribute("class", "org.archive.modules.writer.MirrorWriterProcessor");

        }else if(entity.getTaskType() == CrawlerTaskEntity.TaskType.Parse){//解析类任务

        }



//
//
//        Element fetchProcessorsPropertyListElement = (Element) CrawlerBeansUtil.selectSingleNode("/beans/bean[@id='fetchProcessors']/property[@name='processors']/list", root);
//
//        NodeList nodeList = fetchProcessorsPropertyListElement.getChildNodes();
//        for (int i = nodeList.getLength() - 1; i >= 0; i--) {
//            Node node = nodeList.item(i);
//            if (node.getNodeType() == Node.ELEMENT_NODE) {
//                String attribute = ((Node) node).getAttributes().getNamedItem("bean").getTextContent();
//                if ("extractorCss".equals(attribute)) {
//                    fetchProcessorsPropertyListElement.removeChild(node);
//                } else if ("extractorJs".equals(attribute)) {
//                    fetchProcessorsPropertyListElement.removeChild(node);
//                } else if ("extractorSwf".equals(attribute)) {
//                    fetchProcessorsPropertyListElement.removeChild(node);
//                }
//            }
//
//        }
        CrawlerBeansUtil.save(crawlerBeansCxml, document);

        try {
            FileUtils.writeStringToFile(new File(newJobDir, "task_config.xml"), entity.getConfig(), "UTF-8", false);
        } catch (IOException e) {
            // ignore
        }

        return crawlerTaskEntity;
    }

    @Transactional(readOnly = false, value = "transactionManager")
    @Override
    public void delete(CrawlerTaskEntity entity) {
        File newJobDir = new File(engine.getJobsDir(), entity.getTaskName());
        try {
            FileUtils.forceDelete(newJobDir);
        } catch (IOException e) {
            // ignore  throw new DataIntegrityViolationException("Duplicate entry '"+entity.getJobName()+"' for key");
        }
        super.delete(entity);
    }
}
