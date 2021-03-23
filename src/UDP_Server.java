import java.io.*;
import java.net.*;

public class UDP_Server {

    public static void main(String[] args)  throws SocketException, UnknownHostException, IOException{
        // Step 1 : Create a socket to listen at port
        DatagramSocket socket = new DatagramSocket(2800);
        byte[] receive = new byte[65535];

        DatagramPacket packetReceived = null;
        boolean sent = false;
        while (sent != true) {

            // Step 2 : create a DatgramPacket to receive the data.
            packetReceived = new DatagramPacket(receive, receive.length);

            // Step 3 : revieve the data in byte buffer.
            socket.receive(packetReceived);
            System.out.println("message received");
            socket.send(packetReceived);
            System.out.println("Client:-" + data(receive));


            sent = true;
            // Exit the server if the client sends "bye"
//            if (data(receive).toString().equals("bye"))
//            {
//                System.out.println("Client sent bye.....EXITING");
//
//            }

            // Clear the buffer after every message.
            receive = new byte[65535];
        }
    }

    // A utility method to convert the byte array
    // data into a string representation.
    public static StringBuilder data(byte[] a)
    {
        if (a == null)
            return null;
        StringBuilder ret = new StringBuilder();
        int i = 0;
        while (a[i] != 0)
        {
            ret.append((char) a[i]);
            i++;
        }
        return ret;
    }

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
}
