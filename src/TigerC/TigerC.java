package TigerC;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TigerC {

    public static void main(String[] args) {
        TigerC client = new TigerC("127.0.0.1", 9484);
    }

    public TigerC(String address, int port){

        try {

            //Open socket and initialize readers and writers
            Socket socket = new Socket(address, port);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //Read and write to socket
            String line;
            writer.println("tconnect 127.0.0.1 1 124");
            System.out.println(reader.readLine());
            writer.println("tget testGet.txt");
            System.out.println(reader.readLine());
            for(;;);

        }catch (Exception e){
            System.out.println("Exception encountered in TigerC Constructor!");
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }
}
