package service.api;

import java.io.IOException;

public interface ResponseWorker {
    int getResponseCode() throws IOException;
    String getResponseJsonContent() throws IOException;
}
