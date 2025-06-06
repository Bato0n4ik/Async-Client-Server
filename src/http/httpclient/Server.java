package http.httpclient;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Server {

    private final int port;
    private final ExecutorService clientPool;
    private final static AtomicBoolean stopped;
    private final CountDownLatch stopLatch;

    static{
        stopped = new AtomicBoolean(false);
    }

    public Server(int port, int poolSize, CountDownLatch stopLatch) {
        this.port = port;
        this.clientPool = Executors.newFixedThreadPool(poolSize);
        this.stopLatch = stopLatch;
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            //Runtime.getRuntime().addShutdownHook(new Thread(() -> {}))

            Thread socketTimer = new Thread(new SocketTimer(serverSocket, stopped));
            socketTimer.start();

            while (true) {

                Socket socket = serverSocket.accept();

                if(stopped.get()){
                    break;
                }

                if(socket == null){
                    System.out.println("Timeout detected: not have connections, create new server socket!");
                    continue;
                }


                System.out.println("Accepted connection from " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
                clientPool.submit(() -> {
                    try {
                        process(socket);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

        } catch (IOException e) {
            System.out.println("Server stopped by Error! - the ServerSocket.close() method was called!");
            System.exit(1);
        }
    }

    private void process(Socket socket) throws IOException {

        try (socket;
             InputStream inputStream = new DataInputStream(socket.getInputStream());
             OutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {

            System.out.println("Client@" + Thread.currentThread().getId() + " > ");

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            while(inputStream.available() > 0){
                byteArrayOutputStream.write(inputStream.readNBytes(1));
            }
            System.out.println(byteArrayOutputStream);


            /*var elem = inputStream.readNBytes(400);
            System.out.println(mapToString(elem));
            int contentSize = getContentLength(mapToString(elem));

            System.out.println("Content size: " + contentSize);

            var elem2 = inputStream.readNBytes(contentSize);

            String content = mapToString(elem) + mapToString(elem2);
            System.out.println(content);*/
            //System.out.println(getBody(content));

            byte[] body = Files.readAllBytes(Path.of("resources", "serverRequest.html"));

            byte[] header = """
                    HTTP/1.1 200 OK
                    Content-Type: text/html; charset=utf-8
                    Content-Length: %d
                    """.formatted(body.length).getBytes();

            outputStream.write(header);
            outputStream.write(System.lineSeparator().getBytes());
            outputStream.write(body);
            stopLatch.countDown();


        } /*catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            System.out.println("Error in result.get() method called from process() method in Server.class");
        }*/
    }

    public void stop() throws InterruptedException {
        stopped.set(true);
        clientPool.shutdown();
        try {
            boolean terminated = clientPool.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println("Error in Server.class stop() method: " + e.getMessage());
        }
        System.out.println("Server stopped");
    }

    private String mapToString(byte[] arr){
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : arr) {
            stringBuilder.append((char) b);
        }
        return stringBuilder.toString();
    }



    private int getContentLength(String data) {

        int length = 0;

        Pattern pattern = Pattern.compile("Content-Length: \\d+", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(data);
        String contentLength = matcher.find() ? matcher.group() : null;

        pattern = Pattern.compile("\\d+");
        assert contentLength != null;
        matcher = pattern.matcher(contentLength);

        if (matcher.find()) {
            length = Integer.parseInt(matcher.group());
        }

        return length;
    }

    private String getBody(String data) throws IOException, ExecutionException, InterruptedException {

        int length = getContentLength(data);

        String body = null;

        Pattern pattern = Pattern.compile("^\\s+$?");
        Matcher matcher = pattern.matcher(data);

        if (matcher.find()) {
            body = data.substring(matcher.end() + 1, length - 1);
        }

        return body;
    }

    public static AtomicBoolean getStopped() {
        return stopped;
    }
}
