public class Item {

    private String name;
    private Long price;
    private Float quantity;
    private Long sum;

    public Item(){}

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }

    public Float getQuantity() {
        return quantity;
    }

    public Long getSum() {
        return sum;
    }
}
