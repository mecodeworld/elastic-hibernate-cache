package codeworld.elastic.service.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import codeworld.elastic.service.document.ECountry;

@Repository
public interface ECountryRepository extends ElasticsearchRepository<ECountry, Long> {

}
