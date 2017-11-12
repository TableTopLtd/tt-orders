package si.fri.tabletop.order.models;

import org.eclipse.persistence.annotations.UuidGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name = "orders")
@NamedQueries(value =
        {
                @NamedQuery(name = "Order.getAll", query = "SELECT o FROM orders o"),
        })
@UuidGenerator(name = "idGenerator")
public class Order {

    @Id
    @GeneratedValue(generator = "idGenerator")
    private String id;

    @Column(name = "order_time")
    private Date orderTime;

    @Column(name = "table_num")
    private int table;


    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public int getTable() {
        return table;
    }

    public void setTable(int table) {
        this.table = table;
    }

}
