package com.airlenet.crawler.heritrix.entity;

import com.airlenet.play.main.entity.AdminUserEntity;
import com.airlenet.repo.jpa.DataEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author airlenet
 * @version 2018-01-04
 */
@Entity
@Table(name = "crawler_job_data")
public class CrawlerJobXpathDataEntity extends DataEntity<AdminUserEntity,Long>{


    private String xpath;

    private String colName;

    private Boolean isKey;

    public String getXpath() {
        return xpath;
    }

    public void setXpath(String xpath) {
        this.xpath = xpath;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public Boolean getKey() {
        return isKey;
    }

    public void setKey(Boolean key) {
        isKey = key;
    }
}
