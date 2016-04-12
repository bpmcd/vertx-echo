package com.nerdery.vertx;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;

import java.time.LocalDate;

public class Server {

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }

    public void run() {
        Vertx vertx = Vertx.vertx();
        HttpServer server = vertx.createHttpServer();

        LocalDate now = LocalDate.now();
        String format = "{\"capitalizedMessage\":\"%1$s\",\"name\":\"%2$s\",\"squaredNumber\":%3$d,\"year\":" + now.getYear() +",\"month\":" + now.getMonthValue() + ",\"day\":" + now.getDayOfMonth() + "}";

        server.requestHandler(request -> {
            // This handler gets called for each request that arrives on the server
            HttpServerResponse response = request.response();
            if (request.path().contains("/echo") && request.method().equals(HttpMethod.GET)) {
                response.putHeader("content-type", "application/json");

                String message = request.getParam("message");
                String name = request.getParam("name");
                // System.out.println("param: number = " + request.getParam("number"));
                Integer number = Integer.parseInt(request.getParam("number"));

                String json = String.format(format, message, name, number * number);
                // Write to the response and end it
                response.end(json);
            } else {
                System.out.println("Request: " + request.path());
                request.response().end();
            }
        });

        server.listen(8888);
    }
}
