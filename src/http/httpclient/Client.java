package http.httpclient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Client {

    public static void main(String[] args) throws URISyntaxException, IOException {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse.BodyHandler<String> stringBodyHandler = HttpResponse.BodyHandlers.ofString();

        var path = Path.of("resources", "fileJson.json");
        byte[] data = Files.readAllBytes(path);
        String dataInString = Arrays.toString(data);

        //System.out.println("Request body from client: " + dataInString);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:30000"))
                .header("content-type", "application/json")
                //.POST(HttpRequest.BodyPublishers.ofString(dataInString))
                .POST(HttpRequest.BodyPublishers.ofFile(path))
                .build();


        CompletableFuture<HttpResponse<String>> result1 = client.sendAsync(request, stringBodyHandler);
        CompletableFuture<HttpResponse<String>> result2 = client.sendAsync(request, stringBodyHandler);
        CompletableFuture<HttpResponse<String>> result3 = client.sendAsync(request, stringBodyHandler);

        try{
            System.out.println("Response from server: " + result3.get().body());

        }
        catch (InterruptedException | ExecutionException e) {
            System.out.println("Error in Client class: " + e.getMessage());
        }


    }
}





