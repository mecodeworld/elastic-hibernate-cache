package codeworld.health.service.jms;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JMSMessage implements Serializable {

    private static final long serialVersionUID = 4523604583220901215L;

    private Long id;
    private JMSOperation jmsOperation;
    private EntityType entityType;
}
