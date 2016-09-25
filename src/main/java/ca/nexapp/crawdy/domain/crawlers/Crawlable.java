package ca.nexapp.crawdy.domain.crawlers;

import ca.nexapp.crawdy.domain.proxies.ProxyServer;
import ca.nexapp.math.units.Duration;

public interface Crawlable {

    void onCrawlSuccess(ProxyServer proxy, Duration duration);

    void onCrawlFailed(ProxyServer proxy, Duration duration);
}
