package codeworld.elastic.service.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import codeworld.elastic.service.document.PatientDoc;

@Repository
public interface PatientRepository extends ElasticsearchRepository<PatientDoc, Long> {

}
