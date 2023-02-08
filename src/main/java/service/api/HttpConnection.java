package service.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnection {
    private final HttpURLConnection connection;

    private HttpConnection(String url) throws IOException {
        connection = (HttpURLConnection) new URL(url).openConnection();
    }

    public static HttpConnection createConnection(String url) throws IOException {
        return new HttpConnection(url);
    }

    public InputStream getContent() throws IOException {
        return connection.getInputStream();
    }

    public String getContentEncoding() {
        return connection.getContentEncoding();
    }

    public int getResponseCode() throws IOException {
        return connection.getResponseCode();
    }
}
