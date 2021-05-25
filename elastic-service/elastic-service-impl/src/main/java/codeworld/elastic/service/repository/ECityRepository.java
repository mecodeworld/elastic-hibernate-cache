package codeworld.elastic.service.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import codeworld.elastic.service.document.ECity;

@Repository
public interface ECityRepository extends ElasticsearchRepository<ECity, Long> {

}
