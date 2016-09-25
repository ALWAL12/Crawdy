package ca.nexapp.crawdy.domain.documents;

import java.util.Objects;

public class HTMLDocument {

    public final String url;
    public final String source;

    public HTMLDocument(String url, String source) {
        this.url = url;
        this.source = source;
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, source);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof HTMLDocument)) {
            return false;
        }

        HTMLDocument other = (HTMLDocument) obj;
        return Objects.equals(url, other.url) && Objects.equals(source, other.source);
    }

    @Override
    public String toString() {
        return url;
    }
}
