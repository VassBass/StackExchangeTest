package service.api;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;

import static org.apache.commons.lang3.StringUtils.EMPTY;

public class GZIPResponseWorker implements ResponseWorker {
    private static final String ENCODING_TYPE = "gzip";

    private final HttpConnection connection;

    public GZIPResponseWorker(HttpConnection connection) {
        this.connection = connection;
    }

    @Override
    public int getResponseCode() throws IOException {
        return connection.getResponseCode();
    }

    @Override
    public String getResponseJsonContent() throws IOException {
        String result = EMPTY;
        String currentEncodingType = connection.getContentEncoding();
        if (currentEncodingType != null && currentEncodingType.equals(ENCODING_TYPE)) {
            InputStream responseContent = new GZIPInputStream(connection.getContent());
            result = new Scanner(responseContent, StandardCharsets.UTF_8)
                    .useDelimiter("\\A")
                    .next();
            responseContent.close();
        }
        return result;
    }
}
