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
@Document(indexName = "country")
public class ECountry implements Serializable {

    private static final long serialVersionUID = -1466116934268275895L;

    @Id
    private Long id;
    private String name;
    private String region;
    private Long population;

    // @Field(type = FieldType.Nested,
    // includeInParent = true)
    // private List<EState> states;

}
