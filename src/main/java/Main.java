public class Main {

    public static void main(String[] args) throws Exception {
        String in = "test.TLV";
        ReadTLV readTLV = new ReadTLV();
        try {
            Order order = readTLV.readFile(in);
            System.out.println("FINISH!!!");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
