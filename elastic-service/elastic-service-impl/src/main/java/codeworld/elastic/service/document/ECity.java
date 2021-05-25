package codeworld.elastic.service.document;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "city")
public class ECity implements Serializable {

    private static final long serialVersionUID = 4604671787376676658L;

    @Id
    private Long id;
    private String name;
    private Long population;

}
