public class Main {

    public static void main(String[] args) throws Exception {
        String putIn = "test.TLV", putOut = "testOut.json";
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
