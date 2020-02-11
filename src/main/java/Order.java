import java.util.ArrayList;
import java.util.Date;

public class Order {
    private Date dataTime;
    private Integer orderNumber;
    private String customerName;
    private ArrayList<Item> items;

    public Order() {
        items = new ArrayList<>();
    }

    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setItem(Item items) {
        this.items.add(items);
    }

    public Date getDataTime() {
        return dataTime;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void clearItems() {
        items = new ArrayList<>();
    }
}
