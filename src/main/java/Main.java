import java.io.*;
import java.lang.Object;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.FileHandler;

public class Main {
    static String[] massString;
    ArrayList<Order> orders;

    public static void main(String[] args) throws Exception {
        String f = "test.TLV";
        File file = new File(f);
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
        String dateTimeString, orderNumberString, customerName, items, name, price, quantity, sum;
        StringBuilder s = new StringBuilder();
        Date dateTimeDate;
        int tag = 0, length = 0, tag2, length1, length2, orderNumberInt;
        Long time;
        for (int i = 0; i < massString.length; ) {
            tag2 = i + 1;
            length1 = i + 2;
            length2 = i + 3;
            tag = getTagOrLength(massString[i], massString[tag2]);
            length = getTagOrLength(massString[length1], massString[length2]);
            System.out.println("tag - " + tag + " length - " + length);
            i += 4;
            switch (tag) {
                case 1:
                    dateTimeString = getData(i, length).replaceAll(" ", "");
//                    System.out.println("date - " + dateTime);
                    dateTimeDate = new Date(Long.parseUnsignedLong(dateTimeString, 16) * 1000);
                    System.out.println("date - " + dateTimeDate.toGMTString());
                    break;
                case 2:
                    orderNumberString = getData(i, length).replaceAll(" ", "");
                    orderNumberInt = Integer.parseUnsignedInt(orderNumberString, 16);
                    System.out.println("orderNumber - " + orderNumberInt);
                    break;
                case 3:
                    customerName = getString(getData(i, length));
                    System.out.println("customerName - " + customerName);
                    break;
                case 4:
                    items = getData(i, length);
                    System.out.println("items - " + items);
                    System.out.println("items:");
                    length = 0;
                    break;
                case 11:
                    name = getData(i, length);
                    System.out.println("name - " + name);
                    break;
                case 12:
                    price = getData(i, length);
                    System.out.println("price - " + price);
                    break;
                case 13:
                    quantity = getData(i, length);
                    System.out.println("quantity - " + quantity);
                    break;
                case 14:
                    sum = getData(i, length);
                    System.out.println("sum - " + sum);
                    break;
            }
            i += length;
        }
    }


    private static int getTagOrLength(String byte1, String byte2) throws Exception {
        if (byte1.equals("00") || !byte2.equals("00"))
            throw new Exception("Not correct byte for tag or length!!");
        return Integer.parseInt(byte1, 16);
    }

    private static String getData(int indStart, int length) {
        StringBuilder data = new StringBuilder();
        for (int i = length + indStart - 1; i >= indStart; i--) {
            data.append(massString[i]).append(" ");
        }
        return data.toString();
    }

    private static String getString(String binaryStr) throws UnsupportedEncodingException {
        String[] binaryMass = binaryStr.split(" ");
        StringBuilder stringBuilder = new StringBuilder("");
        for (String letter : binaryMass) {
            stringBuilder.append((char) (byte) letter);
            System.out.println(stringBuilder);
//            stringBuilder.append( Integer.toBinaryString(Integer.parseInt(letter,16))).append(" ");
        }
        return stringBuilder.toString();

//        binaryStr = binaryStr.replaceAll(" ", "");
//        byte[] bytes = binaryStr.getBytes();
//        binaryStr = new String(bytes, "UTF-16");
//        return binaryStr
    }
}
