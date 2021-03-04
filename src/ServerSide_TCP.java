
import java.net.*;
import java.io.*;

public class ServerSide_TCP {
    static final int portNumber = 2800;
    public static void main(String[] args) throws IOException {
        System.out.println("Server is open");
        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("port 2800 established");
            for (;;) {
                Socket client = serverSocket.accept();

                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

                System.out.println("listening for input");

                String cmd;
                while ((cmd = in.readLine()) != null) {
                    System.out.println("reading line");
                    //String reply = cmd;
                    int len = cmd.length();


                    out.println("Content-Length: " + len);

                    out.println(cmd);
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
