
//This is the client
//import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.io.*;
import java.net.*;

public class NetworkingTCP_Client {
    static final  int portNumber = 2800;

    public static String createMsg(int input){
        StringBuilder msg = new StringBuilder("");
        for (int i = 0; i < input/2; i++) {    //might need to divide input by 2/
           msg.append('a');
        }

       return msg.toString();
    }

    //make an arraylist of strings for the required sizes and just flush the entire array list through all once

    //int key = 0;
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

        public static void main(String[] args) throws IOException, UnknownHostException,InterruptedException {
            System.out.println("1");
            InetAddress hostName = InetAddress.getByName("rho.cs.oswego.edu");
            System.out.println("2");

            try (Socket echoSocket = new Socket(hostName, portNumber);
                    PrintWriter output = new PrintWriter(echoSocket.getOutputStream(), true);
                    BufferedReader input = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
                    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {
                System.out.println("3");
                String userInput;
                long nanoStartTime = System.nanoTime();
                boolean sent = false;
                int bitThrough = 0;
                while (sent != true) {
                    System.out.println("Specify the amount of bytes you'd like to send:");
                    userInput = stdIn.readLine();
                    bitThrough = Integer.parseInt(userInput);
                    //this is where I switch to bytes
                    output.write(xor(createMsg(Integer.parseInt(userInput))) + "\n");
                    System.out.println("Message sent");
                    //out sends out
                    output.flush();
                    System.out.println(xor(input.readLine()));
                    System.out.println("message returned");
                    sent = true;
                    //in receives from server
                }

                long nanoEndTime = System.nanoTime();
                long RTT = nanoEndTime - nanoStartTime;
                echoSocket.close();
                System.out.println("RTT: " + RTT + " Nano seconds");
                double throughput = ((bitThrough*8)/(RTT*10^9));
                System.out.println("Throughput: " + throughput + " bps");
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


