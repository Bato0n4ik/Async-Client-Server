package http.httpclient;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.atomic.AtomicBoolean;

public class SocketTimer implements Runnable {

    private final ServerSocket serverSocket;
    private final AtomicBoolean stopped;

    public SocketTimer(ServerSocket serverSocket, AtomicBoolean stopped) {
        this.serverSocket = serverSocket;
        this.stopped = stopped;
    }


    @Override
    public void run() {

        /*Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(socket == null && stopped.get()) {
                    try {
                        serverSocket.close();
                    } catch (IOException e) {
                        System.out.println("Error ServerSocketStop.class in serverSocket.close() method");
                    }
                }
            }}, 5000);*/

        while(true) {
            if(stopped.get()) {
                try {
                    serverSocket.close();
                    break;
                } catch (IOException e) {
                    System.out.println("Error ServerSocketStop.class in serverSocket.close() method");
                }
            }
        }
    }
}
