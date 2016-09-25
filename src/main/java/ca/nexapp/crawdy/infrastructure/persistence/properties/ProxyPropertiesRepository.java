package ca.nexapp.crawdy.infrastructure.persistence.properties;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import ca.nexapp.crawdy.domain.proxies.ProxyRepository;
import ca.nexapp.crawdy.domain.proxies.ProxyServer;

public class ProxyPropertiesRepository implements ProxyRepository {

    private final Properties properties;

    public ProxyPropertiesRepository(String filepath) throws IOException {
        properties = new Properties();
        properties.load(this.getClass().getResourceAsStream(filepath));
    }

    @Override
    public List<ProxyServer> findAll() {
        List<ProxyServer> proxies = new ArrayList<>();
        for (Entry<Object, Object> entry : properties.entrySet()) {
            String host = String.valueOf(entry.getKey());
            String port = String.valueOf(entry.getValue());
            proxies.add(new ProxyServer(host, port));
        }
        return proxies;
    }
}
