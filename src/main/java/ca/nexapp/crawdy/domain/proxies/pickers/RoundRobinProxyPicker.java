package ca.nexapp.crawdy.domain.proxies.pickers;

import java.util.List;

import ca.nexapp.crawdy.domain.proxies.ProxyServer;

public class RoundRobinProxyPicker implements ProxyPicker {

    private int currentIndex = -1;

    @Override
    public ProxyServer pickNext(List<ProxyServer> proxies) {
        goToNext(proxies);
        return proxies.get(currentIndex);
    }

    private void goToNext(List<ProxyServer> proxies) {
        currentIndex++;
        if (currentIndex >= proxies.size()) {
            currentIndex = 0;
        }
    }

}
