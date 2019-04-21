import TigerC.TigerC;
import TigerS.TigerS;

public class Exercise1 {

    public static void main(String[] args) {
        TigerS server = new TigerS(9484);
        TigerC client = new TigerC("127.0.0.1",9484);
    }

}
