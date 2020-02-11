import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        String putIn, putOut;
        System.out.println("Write put to file *.tlv");
        putIn = in.nextLine();
        System.out.println("Write name file *.json");
        putOut = in.nextLine();
        ReadTLV readTLV = new ReadTLV();
        try {
            Order order = readTLV.readFile(putIn);
            WriteJSON writeJSON = new WriteJSON();
            writeJSON.writeFile(putOut, order);
            System.out.println("FINISH!!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
