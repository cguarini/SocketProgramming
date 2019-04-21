package TigerS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class Session implements Runnable {

    private Socket client;
    private int sessionID;

    public Session(Socket client, int sessionID){
        this.client = client;
        this.sessionID = sessionID;
    }

    @Override
    public void run() {

        log("Opened!");

        boolean authenticated = false;
        try {
            PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

            //Session loop, handles Server-Client communication
            String line;
            while ((line = reader.readLine()) != null) {
                String status = "";
                log("Server Received Command: " + line);
                String tokens[] = line.split(" ");

                //Handle TCONNECT command
                if(tokens[0].toUpperCase().equals("TCONNECT")){
                    log("Authenticating User " + tokens[2]);
                    authenticated = TigerProtocol.tconnect(tokens[1], tokens[2], tokens[3]);

                    //check authentication
                    if(authenticated){//User authenticated, all is well
                        log("User " + tokens[2] +" Authenticated!");
                        status = "User " + tokens[2] +" Authenticated!";
                    }
                    else{//Authentication failed, close connection
                        log("Invalid username or password!");
                        status = "Invalid username or password! Closing connection";
                        writer.println(status);
                        break;//closes connection
                    }
                }

                else if(!authenticated){
                    log("Client must be authenticated before giving files commands.");
                    status = "Unauthenticated! Run the TCONNECT command before using file commands.";
                }


                //Handle TGET command
                else if(tokens[0].toUpperCase().equals("TGET")){
                    log("Attempting to return file " + tokens[1]);
                    BufferedReader file = TigerProtocol.tget(tokens[1]);

                    //loop through file line by line, adding contents to memory
                    String fileLine;
                    StringBuilder entireFile = new StringBuilder();
                    while((fileLine = file.readLine()) != null){
                        entireFile.append(fileLine);
                    }
                    //send file contents to client
                    writer.print(entireFile.toString());
                    log("File " + tokens[1] + " returned successfully.");
                    status = "File " + tokens[1] + " returned successfully.";
                }

                //Handle TPUT command
                else if(tokens[0].toUpperCase().equals("TPUT")){
                    log("Attempting to write file " + tokens[1]);

                    //Read file contents from client
                    StringBuilder entireFile = new StringBuilder();
                    String fileLine;
                    //Loop through buffer line by line
                    while ((fileLine = reader.readLine()) != null) {
                        entireFile.append(fileLine);
                    }

                    //Save file to server
                    TigerProtocol.tput(tokens[1], entireFile.toString());
                    log("File " + tokens[1] + "written to server!");
                    status = "File " + tokens[1] + "written to server!";

                }

                //Return status
                writer.println(status);
            }
            log("Closed!");
        }catch (SocketException ex){
            log("Closed!");
        }
        catch (Exception e){
            log("Exception encountered in Session constructor!");
            log(e.toString());
            e.printStackTrace();
        }
    }

    /**Helper function
     * Logs the string to standard out
     * @param str - string to log
     */
    private void log(String str){
        System.out.println("Session " + sessionID +": " + str);
    }
}
