package repository.config;

import java.io.InputStream;
import java.util.Properties;

public class DefaultRepositoryConfigHolder implements RepositoryConfigHolder {
    private static volatile DefaultRepositoryConfigHolder instance;
    public static DefaultRepositoryConfigHolder getInstance() {
        if (instance == null) {
            synchronized (DefaultRepositoryConfigHolder.class) {
                if (instance == null) instance = new DefaultRepositoryConfigHolder();
            }
        }
        return instance;
    }

    private static final String PROPERTIES_FILE_PATH = "application.properties";
    private static final String KEY_MAX_RESPONSE_PAGES = "stackexchange.config.max_response_pages";

    private int maxResponsePages = 25;

    private DefaultRepositoryConfigHolder() {
        this(PROPERTIES_FILE_PATH);
    }
    public DefaultRepositoryConfigHolder(String propertiesFile) {
        try {
            InputStream in = DefaultRepositoryConfigHolder.class.getClassLoader()
                    .getResourceAsStream(propertiesFile);
            if (in != null){
                Properties properties = new Properties();
                properties.load(in);

                maxResponsePages = Integer.parseInt(properties.getProperty(KEY_MAX_RESPONSE_PAGES));
            }

            System.out.println("Configuration file read successfully.");
        } catch (Exception e) {
            System.err.println("Error reading configuration file, default configuration used.");
        }
    }

    @Override
    public int getMaxResponsePages() {
        return maxResponsePages;
    }
}
