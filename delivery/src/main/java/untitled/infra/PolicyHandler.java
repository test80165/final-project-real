package untitled.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import untitled.config.kafka.KafkaProcessor;
import untitled.domain.*;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    DeliveryRepository deliveryRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='StockDecreased'"
    )
    public void wheneverStockDecreased_AddDeliveryList(
        @Payload StockDecreased stockDecreased
    ) {
        StockDecreased event = stockDecreased;
        System.out.println(
            "\n\n##### listener AddDeliveryList : " + stockDecreased + "\n\n"
        );

        // Sample Logic //
        Delivery.addDeliveryList(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='OrderCanceled'"
    )
    public void wheneverOrderCanceled_CancelDeliveryStatus(
        @Payload OrderCanceled orderCanceled
    ) {
        OrderCanceled event = orderCanceled;
        System.out.println(
            "\n\n##### listener CancelDeliveryStatus : " +
            orderCanceled +
            "\n\n"
        );

        // Sample Logic //
        Delivery.cancelDeliveryStatus(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
