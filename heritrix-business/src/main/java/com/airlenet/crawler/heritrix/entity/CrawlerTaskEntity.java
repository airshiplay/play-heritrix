package com.airlenet.crawler.heritrix.entity;

import com.airlenet.play.main.entity.AdminUserEntity;
import com.airlenet.repo.jpa.DataEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @name 爬取任务
 * @author airlenet
 * @version 2018-01-04
 */
@Entity
@Table(name = "crawler_task")
@Data @EqualsAndHashCode(callSuper = true)
public class CrawlerTaskEntity extends DataEntity<AdminUserEntity,Long>{
    /**
     * 任务名称
     */
    @Column(unique = true,nullable = false)
    @NotNull
    private String taskName;


    /**
     * 任务类型
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private TaskType taskType;


    /**
     * 任务URL
     * @formtype url
     * @formtip 例如 http://www.example.com或http://www.example.com/path
     */
    @Column(nullable = false)
    @NotNull
    private String seeds;

    /**
     * 爬取范围
     * @formtype textarea
     * @formtip 例如 http://www.example.edu/path/ 或 http://(org,example,www)/path/
     */
    @Column
    private String crawlScope;

    /**
     * 任务配置
     * @formtype textarea
     */
    @Column
    @Lob()
    private String config;

    /**
     * 任务描述
     * @formtype textarea
     */
    @Column
    private String taskDesc;


    /**
     * 任务状态
     *
     */
    @Transient
    private String status;

    public static enum TaskType{
        /**下载类*/Download,/**解析类*/Parse
    }
}
