package untitled.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import untitled.PayApplication;
import untitled.domain.PayApproval;
import untitled.domain.PayCanceled;

@Entity
@Table(name = "Pay_table")
@Data
//<<< DDD / Aggregate Root
public class Pay {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer payAmount;

    private String userId;

    private String status;

    private Integer point;

    @PostPersist
    public void onPostPersist() {
        PayApproval payApproval = new PayApproval(this);
        payApproval.publishAfterCommit();

        PayCanceled payCanceled = new PayCanceled(this);
        payCanceled.publishAfterCommit();
    }

    public static PayRepository repository() {
        PayRepository payRepository = PayApplication.applicationContext.getBean(
            PayRepository.class
        );
        return payRepository;
    }

    //<<< Clean Arch / Port Method
    public static void calPay(OrderPlaced orderPlaced) {
        //implement business logic here:

        /** Example 1:  new item 
        Pay pay = new Pay();
        repository().save(pay);

        PayApproval payApproval = new PayApproval(pay);
        payApproval.publishAfterCommit();
        PayCanceled payCanceled = new PayCanceled(pay);
        payCanceled.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(orderPlaced.get???()).ifPresent(pay->{
            
            pay // do something
            repository().save(pay);

            PayApproval payApproval = new PayApproval(pay);
            payApproval.publishAfterCommit();
            PayCanceled payCanceled = new PayCanceled(pay);
            payCanceled.publishAfterCommit();

         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void cancelPay(OrderCanceled orderCanceled) {
        //implement business logic here:

        /** Example 1:  new item 
        Pay pay = new Pay();
        repository().save(pay);

        PayCanceled payCanceled = new PayCanceled(pay);
        payCanceled.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(orderCanceled.get???()).ifPresent(pay->{
            
            pay // do something
            repository().save(pay);

            PayCanceled payCanceled = new PayCanceled(pay);
            payCanceled.publishAfterCommit();

         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
