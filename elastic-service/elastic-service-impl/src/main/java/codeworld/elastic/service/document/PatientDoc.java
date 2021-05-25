package codeworld.elastic.service.document;

import java.io.Serializable;
import java.util.Date;

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
@Document(indexName = "patient")
public class PatientDoc implements Serializable {

    private static final long serialVersionUID = -3665283837992291576L;

    @Id
    private Long id;
    private String name;
    private Long aadharNo;
    private String address;
    private String report;
    private Date admitDate;
    private Date releaseDate;
    private Long amount;
    private String hospitalName;

}
