public class Item {

    private String name;
    private Integer price;
    private Float quantity;
    private Integer sum;

    public Item (String name, Integer price, Float quantity, Integer sum){
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.sum = sum;
    }

    public Item(){}

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Float getQuantity() {
        return quantity;
    }

    public Integer getSum() {
        return sum;
    }
}
