package ca.nexapp.crawdy.domain.proxies.pickers;

import java.util.List;

import ca.nexapp.crawdy.domain.proxies.ProxyServer;

public interface ProxyPicker {

    ProxyServer pickNext(List<ProxyServer> proxies);

}
