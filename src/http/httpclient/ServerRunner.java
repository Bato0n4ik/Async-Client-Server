package http.httpclient;

import java.util.concurrent.CountDownLatch;

public class ServerRunner {

    public static void main(String[] args) {

        CountDownLatch countDownLatch = new CountDownLatch(3);

        Server server = new Server(30000, 100, countDownLatch);

        Thread stopServerThread = new Thread(new StopServerClass(countDownLatch, Server.getStopped()));
        stopServerThread.start();



        System.out.println("Server started");

        server.run();

        System.out.println("ServerRunner stopped!");
    }

}
