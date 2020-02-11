import javax.naming.ldap.SortResponseControl;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Scanner;

public class ReadTLV {
    private static String[] massString;

    public ReadTLV() {
    }

    public Order readFile(String in) throws IOException {
        File file = new File(in);
        System.out.println("Can read - " + file.canRead());
        FileReader fileReader = new FileReader(file);
        Scanner scanner = new Scanner(fileReader);
        StringBuilder tlvString = new StringBuilder("");
        while (scanner.hasNext()) {
            tlvString.append(scanner.nextLine());
            if (scanner.hasNext())
                tlvString.append(" ");
        }
        fileReader.close();
        System.out.println(tlvString);
        massString = tlvString.toString().split(" ");
        String items;
        Order order = new Order();
        Item item = new Item();
        int tag = 0, length = 0, tag2, length1, length2;
        try {
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
                        order.setOrderNumber((long) Integer.parseUnsignedInt(getData(i, length)
                                .replaceAll(" ", ""), 16));
//                        System.out.println("orderNumber - " + order.getOrderNumber());
                        break;
                    case 3:
                        order.setCustomerName(getString(getData(i, length)));
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
//                        System.out.println("name - " + item.getName());
                        break;
                    case 12:
                        if (item.getPrice() != null) {
                            order.setItem(item);
                            item = new Item();
                        }
                        item.setPrice(Long.parseUnsignedLong(
                                getData(i, length).replaceAll(" ", ""), 16));
//                        System.out.println("price - " + item.getPrice());
                        break;
                    case 13:
                        if (item.getQuantity() != null) {
                            order.setItem(item);
                            item = new Item();
                        }
                        item.setQuantity((float) Integer.parseUnsignedInt(getData(i, length)
                                .replaceAll(" ", "")) / 100);
//                        System.out.println("quantity - " + item.getQuantity());
                        break;
                    case 14:
                        if (item.getSum() != null) {
                            order.setItem(item);
                            item = new Item();
                        }
                        item.setSum(Long.parseUnsignedLong(getData(i, length).replaceAll(" ", ""), 16));
//                        System.out.println("sum - " + item.getSum());
                        break;
                }
                i += length;
            }
        } catch (Exception e) {
            e.printStackTrace();
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