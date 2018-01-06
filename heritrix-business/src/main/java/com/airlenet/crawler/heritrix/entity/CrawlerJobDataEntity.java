package com.airlenet.crawler.heritrix.entity;

import com.airlenet.play.main.entity.AdminUserEntity;
import com.airlenet.repo.jpa.DataEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author airlenet
 * @version 2018-01-04
 */
@Entity
@Table(name = "crawler_job_data")
public class CrawlerJobDataEntity extends DataEntity<AdminUserEntity,Long>{
    /**
     * 任务名称
     */
    @Column(unique = true)
    private String url;

    private String dataName;

    @OneToMany
    private List<CrawlerJobXpathDataEntity> xpathDataList;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public List<CrawlerJobXpathDataEntity> getXpathDataList() {
        return xpathDataList;
    }

    public void setXpathDataList(List<CrawlerJobXpathDataEntity> xpathDataList) {
        this.xpathDataList = xpathDataList;
    }
}
