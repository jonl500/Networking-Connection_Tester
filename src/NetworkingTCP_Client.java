
//This is the client
import java.util.Random;
import java.io.*;
import java.net.*;

public class NetworkingTCP_Client {
    static final  int portNumber = 2800;

    public static String createMsg(int input){
        StringBuilder msg = new StringBuilder("");
        for (int i = 0; i < input/2; i++) {
           msg.append('a');
        }

       return msg.toString();
    }
    //int key = 0;
    public static String xor(String message){
        //XOR key
        int key = 7;
        // Define String to store encrypted/decrypted String
        String outputStr = "";
        int len = message.length();
        // perform XOR operation of key
        // with every character in string
        for (int i = 0; i < len; i++){
           Character.toString((char)(message.charAt(i) ^ key));

        }

        System.out.println(outputStr);
        return outputStr;
    }

        public static void main(String[] args) throws IOException {

            String hostName = "rho.cs.oswego.edu";


            try (Socket echoSocket = new Socket(hostName, portNumber);
                    PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
                    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {
                String userInput;
                long nanoStartTime = System.nanoTime();
                boolean sent = false;
                while (sent != true) {
                    System.out.println("Specify the amount of bytes you'd like to send:");
                    userInput = stdIn.readLine();
                    //this is where I switch to bytes
                    out.write(xor(createMsg(Integer.parseInt(userInput))));
                    //out sends out
                    System.out.println(in.readLine());
                    sent = true;
                    //in receives from server
                }

                long nanoEndTime = System.nanoTime();
                long RTT = nanoEndTime - nanoStartTime;
                echoSocket.close();
                System.out.println(RTT);
            } catch (UnknownHostException e) {
                System.err.println("Don't know about host " + hostName);
                System.exit(1);
            } catch (IOException e) {
                System.err.println("Couldn't get I/O for the connection to " +
                        hostName);
                System.exit(1);
            }
        }
    }


