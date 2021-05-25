package codeworld.elastic.service.document;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

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
@Document(indexName = "state")
public class EState implements Serializable {

    private static final long serialVersionUID = 3494402655250133510L;

    @Id
    private Long id;
    private String name;
    private Long population;

    @Field(type = FieldType.Nested,
            includeInParent = true)
    private List<ECity> cities;

}
