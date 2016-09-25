package ca.nexapp.crawdy.domain.proxies;

import java.util.List;

public interface ProxyRepository {

    List<ProxyServer> findAll();

}
