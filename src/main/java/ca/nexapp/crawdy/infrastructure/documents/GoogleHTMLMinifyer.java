package ca.nexapp.crawdy.infrastructure.documents;

import com.googlecode.htmlcompressor.compressor.HtmlCompressor;

import ca.nexapp.crawdy.domain.documents.HTMLMinifyer;

public class GoogleHTMLMinifyer extends HTMLMinifyer {

    private final HtmlCompressor compressor = new HtmlCompressor();

    @Override
    protected String minify(String plainHTML) {
        return compressor.compress(plainHTML);
    }

}
