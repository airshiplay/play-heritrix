package com.airlenet.crawler.heritrix.repo;

import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.airlenet.crawler.heritrix.entity.CrawlerTaskEntity;
import com.airlenet.crawler.heritrix.entity.QCrawlerTaskEntity;
import com.airlenet.repo.jpa.EntityRepository;

public interface CrawlerTaskEntityRepository extends EntityRepository<CrawlerTaskEntity, Long>, QuerydslBinderCustomizer<QCrawlerTaskEntity> {

	@Override
	default void customize(QuerydslBindings bindings, QCrawlerTaskEntity root) {
		//bindings.bind(root.title).first((path, value) -> path.contains(value));
	}
}
