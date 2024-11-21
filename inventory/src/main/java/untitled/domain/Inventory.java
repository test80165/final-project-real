package untitled.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.LongAccumulator;

import javax.persistence.*;
import lombok.Data;
import untitled.InventoryApplication;
import untitled.domain.OutOfStock;
import untitled.domain.StockDecreased;
import untitled.domain.StockIncreased;

@Entity
@Table(name = "Inventory_table")
@Data
//<<< DDD / Aggregate Root
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String stockName;

    private Long stockCount;

    private String status;

    private Integer price;
    
    private Long orderId;

    @PostUpdate
    public void onPostUpdate() {
        OutOfStock outOfStock = new OutOfStock(this);
        outOfStock.publishAfterCommit();

        StockDecreased stockDecreased = new StockDecreased(this);
        stockDecreased.publishAfterCommit();

        StockIncreased stockIncreased = new StockIncreased(this);
        stockIncreased.publishAfterCommit();
    }

    public static InventoryRepository repository() {
        InventoryRepository inventoryRepository = InventoryApplication.applicationContext.getBean(
            InventoryRepository.class
        );
        return inventoryRepository;
    }

    //<<< Clean Arch / Port Method
    public static void decreaseStock(OrderPlaced orderPlaced) {
        //implement business logic here:

        /** Example 1:  new item 
        Inventory inventory = new Inventory();
        repository().save(inventory);

        OutOfStock outOfStock = new OutOfStock(inventory);
        outOfStock.publishAfterCommit();
        StockDecreased stockDecreased = new StockDecreased(inventory);
        stockDecreased.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(orderPlaced.get???()).ifPresent(inventory->{
            
            inventory // do something
            repository().save(inventory);

            OutOfStock outOfStock = new OutOfStock(inventory);
            outOfStock.publishAfterCommit();
            StockDecreased stockDecreased = new StockDecreased(inventory);
            stockDecreased.publishAfterCommit();

         });
        */

        repository().findById(orderPlaced.getProductId()).ifPresent(inventory -> {

            inventory.setStockCount(inventory.getStockCount() - orderPlaced.getQuantity());
            repository().save(inventory);

        });

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void increaseStock(DeliveryCanceled deliveryCanceled) {
        //implement business logic here:

        /** Example 1:  new item 
        Inventory inventory = new Inventory();
        repository().save(inventory);

        StockIncreased stockIncreased = new StockIncreased(inventory);
        stockIncreased.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(deliveryCanceled.get???()).ifPresent(inventory->{
            
            inventory // do something
            repository().save(inventory);

            StockIncreased stockIncreased = new StockIncreased(inventory);
            stockIncreased.publishAfterCommit();

         });
        */
        repository().findById(deliveryCanceled.getProductId()).ifPresent(inventory -> {

            inventory.setStockCount(inventory.getStockCount() + 10);
            repository().save(inventory);

        });

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
