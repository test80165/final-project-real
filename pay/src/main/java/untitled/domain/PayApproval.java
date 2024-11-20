package untitled.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import untitled.domain.*;
import untitled.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class PayApproval extends AbstractEvent {

    private Long id;
    private Integer payAmount;
    private String userId;
    private String status;
    private Integer point;

    public PayApproval(Pay aggregate) {
        super(aggregate);
    }

    public PayApproval() {
        super();
    }
}
//>>> DDD / Domain Event
