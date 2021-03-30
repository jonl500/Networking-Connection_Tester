import java.io.*;
import java.net.*;
import java.util.Scanner;

public class UDP_Client {

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

        //System.out.println(blid.toString());
        return blid.toString();
    }

    public static void main(String[] args) throws SocketException, UnknownHostException, IOException {

        InetAddress host = InetAddress.getByName("pi.cs.oswego.edu");
        DatagramSocket socket = new DatagramSocket();
        DatagramPacket packet;
        Scanner sc = new Scanner(System.in);
        byte[] sendBuffer = null;
        boolean sent = false;
        long nanoStartTime = 0;

        System.out.println("How many packets would you like to send:");
        String packetNum = sc.nextLine();
        System.out.println("how big should the packets be?");
        String input = sc.nextLine();
        while (sent != true){

            nanoStartTime = System.nanoTime();
            for (int i = 0; i < Integer.parseInt(packetNum); i++) {
                sendBuffer = xor(createMsg(Integer.parseInt(input))).getBytes();
                packet = new DatagramPacket(sendBuffer, sendBuffer.length, host, 2800);

                socket.send(packet);
                System.out.println("sent");
                byte[] buff = new byte[8];
                DatagramPacket ack = new DatagramPacket(buff, 8, host, 2800);
                socket.receive(ack);
                byte[] buffer = new byte[sendBuffer.length];
                packet = new DatagramPacket(buffer, buffer.length, host, 2800);
                socket.receive(packet);
                System.out.println("received" + i);
            }

            sent = true;
        }
//        for (int i = 0; i < Integer.parseInt(packetNum); i++) {
//
//        }

        long nanoEndTime = System.nanoTime();
        long RTT = nanoEndTime - nanoStartTime;
        socket.close();
        System.out.println("RTT: " + RTT + " Nano seconds");

   }
}
