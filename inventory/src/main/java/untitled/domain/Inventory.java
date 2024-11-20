package untitled.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
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

    @PostPersist
    public void onPostPersist() {
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

        
        repository().findById(orderPlaced.getProductId()).ifPresent(inventory->{
            
            // repository().save(inventory);

            // OutOfStock outOfStock = new OutOfStock(inventory);
            // outOfStock.publishAfterCommit();
            // StockDecreased stockDecreased = new StockDecreased(inventory);
            // stockDecreased.publishAfterCommit();
            
            // 잔여량에서 주문한 갯수 뺴기.
            inventory.setStockCount(inventory.getStockCount() - orderPlaced.getQuantity());
            repository().save(inventory); // 더티체킹이라 굳이 필요 없는 로직임.

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

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
