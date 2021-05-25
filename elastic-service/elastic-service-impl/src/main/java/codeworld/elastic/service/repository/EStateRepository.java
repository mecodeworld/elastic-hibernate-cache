package codeworld.elastic.service.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import codeworld.elastic.service.document.EState;

@Repository
public interface EStateRepository extends ElasticsearchRepository<EState, Long> {

}
