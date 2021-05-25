package codeworld.elastic.service.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "codeworld.elastic.service.*")
@ComponentScan(basePackages = { "codeworld.elastic.service.*" })
public class ElasticConfiguration {

}
