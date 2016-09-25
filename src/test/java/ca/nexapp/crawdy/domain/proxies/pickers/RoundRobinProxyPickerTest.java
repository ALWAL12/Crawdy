package ca.nexapp.crawdy.domain.proxies.pickers;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.nexapp.crawdy.domain.proxies.ProxyServer;

public class RoundRobinProxyPickerTest {

    private ProxyPicker picker;

    @Before
    public void setUp() {
        picker = new RoundRobinProxyPicker();
    }

    @Test
    public void givenOneProxy_ShouldAlwaysPickIt() {
        ProxyServer server = mock(ProxyServer.class);
        List<ProxyServer> proxies = Arrays.asList(server);

        assertThat(picker.pickNext(proxies)).isEqualTo(server);
        assertThat(picker.pickNext(proxies)).isEqualTo(server);
        assertThat(picker.pickNext(proxies)).isEqualTo(server);
    }

    @Test
    public void givenTwoProxies_WhenPickingTwoTimes_ShouldPickTheFirstThenTheSecond() {
        ProxyServer firstServer = mock(ProxyServer.class);
        ProxyServer secondServer = mock(ProxyServer.class);
        List<ProxyServer> proxies = Arrays.asList(firstServer, secondServer);

        assertThat(picker.pickNext(proxies)).isEqualTo(firstServer);
        assertThat(picker.pickNext(proxies)).isEqualTo(secondServer);
    }

    @Test
    public void givenTwoProxies_WhenReachingTheEnd_TheNextPickShouldRestartToTheFirst() {
        ProxyServer firstServer = mock(ProxyServer.class);
        ProxyServer secondServer = mock(ProxyServer.class);
        List<ProxyServer> proxies = Arrays.asList(firstServer, secondServer);

        assertThat(picker.pickNext(proxies)).isEqualTo(firstServer);
        assertThat(picker.pickNext(proxies)).isEqualTo(secondServer);
        assertThat(picker.pickNext(proxies)).isEqualTo(firstServer);
    }
}
