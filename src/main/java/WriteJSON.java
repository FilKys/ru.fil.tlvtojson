import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class WriteJSON {

    public WriteJSON() {
    }

    public void writeFile(String putOut, Order order) {

        JSONObject orderObject = new JSONObject();
        if (order.getDataTime() != null)
            orderObject.put("dateTime", order.getDataTime().toGMTString());
        if (order.getOrderNumber() != null)
            orderObject.put("orderNumber", order.getOrderNumber());
        if (order.getCustomerName() != null)
            orderObject.put("customerName", order.getCustomerName());
        JSONArray itemsJson = new JSONArray();
        JSONObject itemObject = new JSONObject();
        for (Item item : order.getItems()) {
            if (item.getName() != null)
                itemObject.put("name", item.getName());
            if (item.getPrice() != null)
                itemObject.put("price", item.getPrice());
            if (item.getQuantity() != null)
                itemObject.put("quantity", item.getQuantity());
            if (item.getSum() != null)
                itemObject.put("sum", item.getSum());
            itemsJson.add(itemObject);
        }
        orderObject.put("items", itemsJson);
        System.out.println(orderObject);

        try (FileWriter json = new FileWriter(putOut)) {
            json.write(orderObject.toJSONString());
            json.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
