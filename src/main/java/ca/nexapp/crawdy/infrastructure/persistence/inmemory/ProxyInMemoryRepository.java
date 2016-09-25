package ca.nexapp.crawdy.infrastructure.persistence.inmemory;

import java.util.Arrays;
import java.util.List;

import ca.nexapp.crawdy.domain.proxies.ProxyRepository;
import ca.nexapp.crawdy.domain.proxies.ProxyServer;

public class ProxyInMemoryRepository implements ProxyRepository {

    @Override
    public List<ProxyServer> findAll() {
        ProxyServer localhost = new ProxyServer("127.0.0.1", "8080");
        return Arrays.asList(localhost);
    }

}
