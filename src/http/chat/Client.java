package http.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {

        //Inet4Address inetAddress = (Inet4Address) Inet4Address.getByName("localhost");
        Inet4Address inetAddress = (Inet4Address) Inet4Address.getLoopbackAddress();

        //SocketChannel socketChannel = SocketChannel.open();

        try(Socket socket = new Socket(inetAddress, 30000);
            var inputStream = new DataInputStream(socket.getInputStream());
            var outputStream = new DataOutputStream(socket.getOutputStream());
            Scanner scanner = new Scanner(System.in)){

            while(!socket.isClosed()) {

                System.out.print("> ");
                String request = scanner.nextLine();

                outputStream.writeUTF(request);
                System.out.println("\nServer: " + inputStream.readUTF());

            }
        }
        catch(SocketException e) {
            System.out.println("Socket timed out!");
        }
        catch(IOException e){
            System.out.println("Error: " + e.getMessage());
        }

    }
}
