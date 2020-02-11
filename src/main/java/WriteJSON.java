import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class WriteJSON {

    public WriteJSON() {
    }

    public void writeFile(String putOut, Order order) {

        JSONObject orderObject = new JSONObject();
        orderObject.put("dateTime", order.getDataTime().toGMTString());
        orderObject.put("orderNumber", order.getOrderNumber());
        orderObject.put("customerName", order.getCustomerName());
        JSONArray itemsJson = new JSONArray();
        JSONObject itemObject = new JSONObject();
        for (Item item : order.getItems()) {
            itemObject.put("name", item.getName());
            itemObject.put("price", item.getPrice());
            itemObject.put("quantity", item.getQuantity());
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
