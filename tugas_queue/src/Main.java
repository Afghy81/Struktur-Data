import com.sun.net.httpserver.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.util.*;

public class Main {

    static Queue<Antrian> queue = new LinkedList<>();
    static int nomor = 1;

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Endpoint ambil antrian (enqueue)
        server.createContext("/ambil", exchange -> {
            if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody());
                BufferedReader br = new BufferedReader(isr);
                String nama = br.readLine();

                Antrian a = new Antrian(nomor++, nama);
                queue.add(a); // enqueue

                String response = "Nomor antrian: " + a.nomor;
                sendResponse(exchange, response);
            }
        });

        // Endpoint lihat antrian
        server.createContext("/list", exchange -> {
            StringBuilder response = new StringBuilder();

            if (queue.isEmpty()) {
                response.append("Antrian kosong");
            } else {
                for (Antrian a : queue) {
                    response.append(a.nomor).append(" - ").append(a.nama).append("\n");
                }
            }

            sendResponse(exchange, response.toString());
        });

        // Endpoint panggil (dequeue)
        server.createContext("/panggil", exchange -> {
            String response;

            if (queue.isEmpty()) {
                response = "Tidak ada antrian";
            } else {
                Antrian a = queue.poll(); // dequeue
                response = a.nomor + "|" + a.nama;

                if (queue.isEmpty()) {
                    nomor = 1;
                }
            }

            sendResponse(exchange, response);
        });

        server.createContext("/", exchange -> {
            File file = new File("index.html");
            byte[] bytes = new byte[(int) file.length()];

            FileInputStream fis = new FileInputStream(file);
            fis.read(bytes);
            fis.close();

            exchange.getResponseHeaders().add("Content-Type", "text/html");
            exchange.sendResponseHeaders(200, bytes.length);

            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();

        });

        server.createContext("/style.css", exchange -> {
            File file = new File("style.css");
            byte[] bytes = new byte[(int) file.length()];

            FileInputStream fis = new FileInputStream(file);
            fis.read(bytes);
            fis.close();

            exchange.getResponseHeaders().add("Content-Type", "text/css");
            exchange.sendResponseHeaders(200, bytes.length);

            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();
        });

        server.setExecutor(null);
        server.start();

        System.out.println("Server jalan di http://localhost:8080");
    }

    static void sendResponse(HttpExchange exchange, String response) throws IOException {
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}