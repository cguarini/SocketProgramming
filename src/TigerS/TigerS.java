package TigerS;

import java.net.ServerSocket;
import java.net.Socket;

public class TigerS {

    private ServerSocket server;
    private int sessionID = 0;

    public static void main(String[] args) {
        TigerS server = new TigerS(9484);
    }

    public TigerS(int port){

        System.out.println("Starting TigerS Server on Port: " + port);


            try {
                server = new ServerSocket(port);
                while(true) {
                    Socket client = server.accept();
                    Thread t = new Thread(new Session(client, sessionID));
                    t.start();
                    sessionID++;
                }

            } catch (Exception e) {
                System.out.println("Exception encountered in TigerS constructor!");
                System.out.println(e.toString());
                e.printStackTrace();
            }

    }

}
