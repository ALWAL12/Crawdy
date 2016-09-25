package ca.nexapp.crawdy.domain.documents;

public abstract class HTMLMinifyer {

    public HTMLDocument minify(HTMLDocument document) {
        String minifiedSource = minify(document.source);
        return new HTMLDocument(document.url, minifiedSource);
    }

    protected abstract String minify(String plainHTML);
}
