package untitled.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import untitled.domain.*;
import untitled.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class PayCanceled extends AbstractEvent {

    private Long id;
    private Integer payAmount;
    private String userId;
    private String status;
    private Integer point;

    public PayCanceled(Pay aggregate) {
        super(aggregate);
    }

    public PayCanceled() {
        super();
    }
}
//>>> DDD / Domain Event
