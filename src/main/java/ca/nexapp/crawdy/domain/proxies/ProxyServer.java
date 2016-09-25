package ca.nexapp.crawdy.domain.proxies;

import java.util.Objects;

public class ProxyServer {

    public final String host;
    public final String port;

    public ProxyServer(String host, String port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, port);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ProxyServer)) {
            return false;
        }

        ProxyServer other = (ProxyServer) obj;
        return Objects.equals(host, other.host) && Objects.equals(port, other.port);
    }

    @Override
    public String toString() {
        return host + ":" + port;
    }
}
