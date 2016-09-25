package ca.nexapp.crawdy.domain.crawlers;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import ca.nexapp.crawdy.domain.documents.HTMLDocument;
import ca.nexapp.crawdy.domain.proxies.ProxyRepository;
import ca.nexapp.crawdy.domain.proxies.ProxyServer;
import ca.nexapp.crawdy.domain.proxies.pickers.ProxyPicker;
import ca.nexapp.math.units.Duration;

public abstract class WebCrawler extends CrawlableObserver {

    private final List<ProxyServer> proxies;
    private final ProxyPicker proxyPicker;

    public WebCrawler(ProxyRepository proxyRepository, ProxyPicker proxyPicker) {
        proxies = proxyRepository.findAll();
        this.proxyPicker = proxyPicker;
    }

    public HTMLDocument crawl(String url) {
        LocalDateTime start = LocalDateTime.now();
        ProxyServer proxyServer = proxyPicker.pickNext(proxies);

        try {
            HTMLDocument document = doCrawl(url, proxyServer);
            notifySuccess(proxyServer, durationOfCrawl(start));
            return document;
        } catch (Exception e) {
            notifyFailure(proxyServer, durationOfCrawl(start));
            return crawl(url); // will try with the next proxy
        }
    }

    private Duration durationOfCrawl(LocalDateTime start) {
        LocalDateTime now = LocalDateTime.now();
        long millis = start.until(now, ChronoUnit.MILLIS);
        return Duration.milliseconds(millis);
    }

    protected abstract HTMLDocument doCrawl(String url, ProxyServer proxyServer) throws Exception;
}
