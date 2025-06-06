package http.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) throws IOException {

        try(ServerSocket serverSocket = new ServerSocket(30000)){
                serverSocket.setPerformancePreferences(1,1,1);
                Socket socket = serverSocket.accept();
                try(socket;
                    var inputStream = new DataInputStream(socket.getInputStream());
                    var outputStream = new DataOutputStream(socket.getOutputStream());
                    Scanner scanner = new Scanner(System.in)){

                    socket.setSoTimeout(20000);

                    String request = inputStream.readUTF();

                    while(true){

                        System.out.println("User: " + request);

                        if(request.equals("exit")){
                            socket.close();
                            break;
                        }
                        System.out.print("\n> ");
                        String response = scanner.nextLine();
                        outputStream.writeUTF(response);

                        try{
                            request = inputStream.readUTF();
                        }
                        catch (SocketTimeoutException s) {
                            System.out.println("Socket timed out!");
                            socket.close();
                            break;
                        } catch (IOException e) {
                            System.out.println("I/O Error: " + e.getMessage());
                            socket.close();
                            break;
                        }

                    }


                }
        }


    }
}
