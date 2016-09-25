package ca.nexapp.crawdy.infrastructure.persistence.properties;

import static com.google.common.truth.Truth.assertThat;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.nexapp.crawdy.domain.proxies.ProxyRepository;
import ca.nexapp.crawdy.domain.proxies.ProxyServer;

public class ProxyPropertiesRepositoryITest {

    private static final String FILE_PATH = "/ca/nexapp/crawdy/properties/test-proxies.properties";

    private ProxyRepository proxyRepository;

    @Before
    public void setUp() throws IOException {
        proxyRepository = new ProxyPropertiesRepository(FILE_PATH);
    }

    @Test
    public void canRetrieveProxies() {
        List<ProxyServer> proxies = proxyRepository.findAll();

        assertThat(proxies).contains(new ProxyServer("abc", "80"));
        assertThat(proxies).contains(new ProxyServer("def", "8080"));
    }
}
