import java.io.*;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Scanner;

public class ReadTLV {
    private String[] massString;

    public ReadTLV() {
    }

    public Order readFile(String in) throws Exception {
        File file = new File(in);
        StringBuilder tlvString = new StringBuilder("");
        try (FileReader fileReader = new FileReader(file);) {
            Scanner scanner = new Scanner(fileReader);
            while (scanner.hasNext()) {
                tlvString.append(scanner.nextLine());
                if (scanner.hasNext())
                    tlvString.append(" ");
            }
        } catch (Exception e) {
            System.err.println("ERROR read file " + in);
        }
        massString = tlvString.toString().split(" ");
        Order order = new Order();
        Item item = new Item();
        int tag = 0, length = 0, tag2, length1, length2;
        for (int i = 0; i < massString.length; ) {
            tag2 = i + 1;
            length1 = i + 2;
            length2 = i + 3;
            tag = getTagOrLength(massString[i], massString[tag2]);
            length = getTagOrLength(massString[length1], massString[length2]);
//                System.out.println("tag - " + tag + " length - " + length);
            i += 4;
            switch (tag) {
                case 1:
                    order.setDataTime(new Date(Long.parseUnsignedLong(getData(i, length)
                            .replaceAll(" ", ""), 16) * 1000));
//                        System.out.println("date - " + order.getDataTime().toGMTString());
                    break;
                case 2:
                    order.setOrderNumber(Integer.parseUnsignedInt(getData(i, length)
                            .replaceAll(" ", ""), 16));
                    if (String.valueOf(Math.abs(order.getOrderNumber())).length() > 8) {
                        throw new Exception("ERROR!! Length order number > 8");
                    }
//                        System.out.println("orderNumber - " + order.getOrderNumber());
                    break;
                case 3:
                    order.setCustomerName(getString(getData(i, length)));
                    if (order.getCustomerName().length() > 1000) {
                        throw new Exception("ERROR!! Length customer name > 1000");
                    }
//                        System.out.println("customerName - " + order.getCustomerName());
                    break;
                case 4:
                    order.clearItems();
//                        items = getData(i, length);
//                        System.out.println("items - " + items);
//                        System.out.println("items:");
                    length = 0;
                    break;
                case 11:
                    if (item.getName() != null) {
                        order.setItem(item);
                        item = new Item();
                    }
                    item.setName(getString(getData(i, length)));
                    if (item.getName().length() > 200) {
                        throw new Exception("ERROR!! Length item name > 200");
                    }
//                        System.out.println("name - " + item.getName());
                    break;
                case 12:
                    if (item.getPrice() != null) {
                        order.setItem(item);
                        item = new Item();
                    }
                    item.setPrice(Long.parseUnsignedLong(
                            getData(i, length).replaceAll(" ", ""), 16));
                    if (String.valueOf(Math.abs(item.getPrice())).length() > 6) {
                        throw new Exception("ERROR!! Length item price > 6");
                    }
//                        System.out.println("price - " + item.getPrice());
                    break;
                case 13:
                    if (item.getQuantity() != null) {
                        order.setItem(item);
                        item = new Item();
                    }
                    item.setQuantity((float) Integer.parseUnsignedInt(getData(i, length)
                            .replaceAll(" ", "")) / 100);
                    if (String.valueOf(Math.abs(item.getQuantity())).length() > 8) {
                        throw new Exception("ERROR!! Length item quantity > 8");
                    }
//                        System.out.println("quantity - " + item.getQuantity());
                    break;
                case 14:
                    if (item.getSum() != null) {
                        order.setItem(item);
                        item = new Item();
                    }
                    item.setSum(Long.parseUnsignedLong(
                            getData(i, length).replaceAll(" ", ""), 16));
                    if (String.valueOf(Math.abs(item.getSum())).length() > 6) {
                        throw new Exception("ERROR!! Length item quantity > 6");
                    }
//                        System.out.println("sum - " + item.getSum());
                    break;
            }
            i += length;
        }
        order.setItem(item);
        return order;
    }

    private int getTagOrLength(String byte1, String byte2) throws Exception {
        if (byte1.equals("00") || !byte2.equals("00"))
            throw new Exception("Not correct byte for tag or length!!");
        return Integer.parseInt(byte1, 16);
    }

    private String getData(int indStart, int length) {
        StringBuilder data = new StringBuilder();
        for (int i = length + indStart - 1; i >= indStart; i--) {
            data.append(massString[i]).append(" ");
        }
        return data.toString();
    }

    private String getString(String binaryStr) throws UnsupportedEncodingException {
        String[] binaryMass = binaryStr.split(" ");
        Charset ch = Charset.forName("CP866");
        byte[] h = new byte[binaryMass.length];
        for (int i = 1; i <= binaryMass.length; i++) {
            h[binaryMass.length - i] = (byte) Integer.parseInt(binaryMass[i - 1], 16);
        }
        return new String(h, ch);
    }
}
