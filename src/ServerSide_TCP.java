
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

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
        System.out.println("How many packet do you want to recieve?");
        int packetCounter = 0;
        Scanner scanner = new Scanner(System.in);
        int packetAmount = scanner.nextInt();
        ArrayList<String> packets = new ArrayList<String>();
        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("port 2800 established");
            for (;;) {
                Socket client = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                System.out.println("listening for input");
                boolean breaker = true;
                while (breaker == true) {
                    String cmd = in.readLine();
                    if (packetCounter == 0) {
                        packets.add(cmd);
                        packetCounter++;
                    } else if (packetCounter < packetAmount) {
                        packets.add(cmd);
                        packetCounter++;
                    }
                    //while (cmd != null) {
                    System.out.println("reading line");
                    if (packetCounter == packetAmount) {
                        packetCounter = 0;
                        out.println(packets.get(packetCounter).substring(0, 8));
                        out.flush();
                        // ACK in 8 bytes!!!!!!
                        for (packetCounter = 0; packetCounter < packetAmount; packetCounter++) {
                            out.println(packets.get(packetCounter));
                            out.flush();
                        }
                        breaker = false;
                    }
                    int len = cmd.length();
                }
                    //cmd = xor(cmd);



                    //out.println("Content-Length: " + len);

                 //   out.println(cmd);
                   // out.flush();
               // }
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
