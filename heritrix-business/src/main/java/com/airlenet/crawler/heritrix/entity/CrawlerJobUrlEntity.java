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
@Table(name = "crawler_job_url")
public class CrawlerJobUrlEntity extends DataEntity<AdminUserEntity,Long>{
    /**
     * 任务名称
     */
    @Column(unique = true)
    private String jobName;

    /**
     * 任务名称
     */
    @Column(unique = true)
    private String url;

}
