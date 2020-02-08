import java.util.ArrayList;
import java.util.Date;

public class Order {
    private Date dataTime;
    private Long orderNumber;
    private String customerName;
    private ArrayList<Item> items;

    public Order(Date dataTime, Long orderNumber, String customerName) {
        this.dataTime = dataTime;
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        items = new ArrayList<>();
    }

    public Order(){
        items = new ArrayList<>();
    }

    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public Date getDataTime() {
        return dataTime;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public ArrayList<Item> getItems() {
        return items;
    }
}
