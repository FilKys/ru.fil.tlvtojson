import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UTest {

    Order testOrder, testOrder1;

    @Before
    public void initTest() throws Exception {
        ReadTLV readTLV = new ReadTLV();
        testOrder = readTLV.readFile("test.TLV");
        testOrder1 = readTLV.readFile("test1.TLV");
    }

    @Test
    public void readDateFile() throws Exception {
        Assert.assertEquals("10 Jan 2016 10:30:00 GMT", testOrder.getDataTime().toGMTString());
    }

    @Test
    public void readOrderNumberFile() {
        Integer correctOrderNumber = 160004;
        Assert.assertEquals(correctOrderNumber, testOrder.getOrderNumber());
    }

    @Test
    public void readCustomerName() {
        Assert.assertEquals("ООО Ромашка", testOrder.getCustomerName());
    }

    @Test
    public void quantityItems() {
        Assert.assertEquals(1, testOrder.getItems().size());
    }

    @Test
    public void readItemName() {
        Assert.assertEquals("Дырокол", testOrder.getItems().get(0).getName());
    }

    @Test
    public void readItemPrice() {
        Long correctPrice = 20000L;
        Assert.assertEquals(correctPrice, testOrder.getItems().get(0).getPrice());
    }

    @Test
    public void readItemQuantity(){
        Float correctQuantity = (float) 2;
        Assert.assertEquals(correctQuantity, testOrder.getItems().get(0).getQuantity());
    }

    @Test
    public void readItemSum(){
        Long correctSum = 40000L;
        Assert.assertEquals(correctSum, testOrder.getItems().get(0).getSum());
    }

    @Test
    public void readItemSum1(){
        Long correctSum = 40000L;
        Assert.assertNull(testOrder1.getItems().get(0).getSum());
    }

}
