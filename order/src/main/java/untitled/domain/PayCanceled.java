package untitled.domain;

import java.util.*;
import lombok.*;
import untitled.domain.*;
import untitled.infra.AbstractEvent;

@Data
@ToString
public class PayCanceled extends AbstractEvent {

    private Long id;
    private Integer payAmount;
    private Long userId;
    private String status;
    private Integer point;
}
