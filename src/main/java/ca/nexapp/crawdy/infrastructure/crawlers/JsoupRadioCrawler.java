package ca.nexapp.crawdy.infrastructure.crawlers;

import org.jsoup.Jsoup;

import ca.nexapp.crawdy.domain.crawlers.WebCrawler;
import ca.nexapp.crawdy.domain.documents.HTMLDocument;
import ca.nexapp.crawdy.domain.proxies.ProxyRepository;
import ca.nexapp.crawdy.domain.proxies.ProxyServer;
import ca.nexapp.crawdy.domain.proxies.pickers.ProxyPicker;

public class JsoupRadioCrawler extends WebCrawler {

    public JsoupRadioCrawler(ProxyRepository proxyRepository, ProxyPicker proxyPicker) {
        super(proxyRepository, proxyPicker);
    }

    @Override
    protected HTMLDocument doCrawl(String url, ProxyServer proxyServer) throws Exception {
        useProxy(proxyServer);
        String body = Jsoup.connect(url).ignoreContentType(true).execute().body();
        return new HTMLDocument(url, body);
    }

    private void useProxy(ProxyServer proxyServer) {
        System.setProperty("http.proxyHost", proxyServer.host);
        System.setProperty("http.proxyPort", proxyServer.port);
    }

}
