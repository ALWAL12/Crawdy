package ca.nexapp.crawdy.domain.crawlers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import ca.nexapp.crawdy.domain.proxies.ProxyServer;
import ca.nexapp.math.units.Duration;

public class CrawlableObserverTest {

    private static final Duration A_DURATION = Duration.seconds(3);

    private Crawlable aCrawlable;
    private Crawlable anotherCrawlable;
    private ProxyServer proxy;

    private CrawlableObserver observer;

    @Before
    public void setUp() {
        aCrawlable = mock(Crawlable.class);
        anotherCrawlable = mock(Crawlable.class);
        proxy = mock(ProxyServer.class);

        observer = new CrawlableObserver();
    }

    @Test
    public void whenNotifyingSuccess_ShouldNotifyEveryAttachedObservers() {
        observer.attach(aCrawlable);
        observer.attach(anotherCrawlable);

        observer.notifySuccess(proxy, A_DURATION);

        verify(aCrawlable).onCrawlSuccess(proxy, A_DURATION);
        verify(anotherCrawlable).onCrawlSuccess(proxy, A_DURATION);
    }

    @Test
    public void givenADetachedObserver_WhenNotifyingSuccess_ShouldNotNotifyTheDetachedObservers() {
        observer.attach(aCrawlable);
        observer.attach(anotherCrawlable);
        observer.detach(aCrawlable);

        observer.notifySuccess(proxy, A_DURATION);

        verify(aCrawlable, never()).onCrawlSuccess(proxy, A_DURATION);
        verify(anotherCrawlable).onCrawlSuccess(proxy, A_DURATION);
    }

    @Test
    public void whenNotifyingFailure_ShouldNotifyEveryAttachedObservers() {
        observer.attach(aCrawlable);
        observer.attach(anotherCrawlable);

        observer.notifyFailure(proxy, A_DURATION);

        verify(aCrawlable).onCrawlFailed(proxy, A_DURATION);
        verify(anotherCrawlable).onCrawlFailed(proxy, A_DURATION);
    }

    @Test
    public void givenADetachedObserver_WhenNotifyingFailure_ShouldNotNotifyTheDetachedObservers() {
        observer.attach(aCrawlable);
        observer.attach(anotherCrawlable);
        observer.detach(aCrawlable);

        observer.notifyFailure(proxy, A_DURATION);

        verify(aCrawlable, never()).onCrawlFailed(proxy, A_DURATION);
        verify(anotherCrawlable).onCrawlFailed(proxy, A_DURATION);
    }
}
