package ca.nexapp.crawdy.domain.crawlers;

import java.util.ArrayList;
import java.util.List;

import ca.nexapp.crawdy.domain.proxies.ProxyServer;
import ca.nexapp.math.units.Duration;

public class CrawlableObserver {

    private final List<Crawlable> crawlables = new ArrayList<>();

    public void attach(Crawlable crawlable) {
        crawlables.add(crawlable);
    }

    public void detach(Crawlable crawlable) {
        crawlables.remove(crawlable);
    }

    protected void notifySuccess(ProxyServer proxy, Duration duration) {
        crawlables.forEach(c -> c.onCrawlSuccess(proxy, duration));
    }

    protected void notifyFailure(ProxyServer proxy, Duration duration) {
        crawlables.forEach(c -> c.onCrawlFailed(proxy, duration));
    }
}
