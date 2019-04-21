package TigerS;

import java.io.*;

public class TigerProtocol {

    private static final String credentialsFile = "res\\Credentials.csv";
    private static final String fileDir = "res\\files\\";

    /**Authenticates the user against the credentials flat file
     * Super secure
     * @param serverAddress - IP Address of the server - unused by server
     * @param user - Username of client
     * @param password - Password of client
     * @return - True if authenticated, else false
     */
    public static boolean tconnect(String serverAddress, String user, String password){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(credentialsFile));

            String line;
            while((line = reader.readLine()) != null){
                String tokens[] = line.split(",");
                if(tokens[0].equals(user) && tokens[1].equals(password)){
                    return true;
                }

            }
        }catch (Exception e){
            System.out.println("Exception while attempting to authenticate user");
            System.out.println(e.toString());
            e.printStackTrace();
        }
        return false;
    }

    /**Gets a file from the file directory and returns a reader to it
     *
     * @param fileName - name of file to get
     * @return - reader to file
     * @throws FileNotFoundException - file is not in the directory
     */
    public static BufferedReader tget(String fileName) throws FileNotFoundException{

        BufferedReader file = new BufferedReader(new FileReader(fileDir + fileName));
        return file;
    }

    /**
     * Writes a file to the file directory
     * @param fileName - name of the file
     * @param file - file contents
     * @return true if write successful, else false
     */
    public static boolean tput(String fileName, String file){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileDir + fileName));
            writer.write(file);
            return true;
        }catch (IOException e){
            return false;
        }
    }

}
