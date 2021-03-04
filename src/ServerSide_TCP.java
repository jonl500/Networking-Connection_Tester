
import java.net.*;
import java.io.*;

public class ServerSide_TCP {
    static final int portNumber = 2800;

    public static String xor(String message){
        //XOR key
        String key = "abcdefh";
        StringBuilder blid = new StringBuilder();
        // Define String to store encrypted/decrypted String
        String outputStr = "";
        int len = message.length();
        // perform XOR operation of key
        // with every character in string
        int j =0;
        for (int i = 0; i < len; i++){
            blid.append((char)(message.charAt(i) ^ key.charAt(i%key.length())));
            j++;
            if(j%8 == 0){
                j = 0;
            }

        }

        System.out.println(blid.toString());
        return blid.toString();
    }
    public static void main(String[] args) throws IOException {
        System.out.println("Server is open");
        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("port 2800 established");
            for (;;) {
                Socket client = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                System.out.println("listening for input");

                String cmd = in.readLine();
                while (cmd != null) {
                    System.out.println("reading line");
                    //String reply = cmd;
                    int len = cmd.length();


                    out.println("Content-Length: " + len);

                    out.println(xor(cmd));
                }
                    out.close();
                    in.close();
                    client.close();

            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
