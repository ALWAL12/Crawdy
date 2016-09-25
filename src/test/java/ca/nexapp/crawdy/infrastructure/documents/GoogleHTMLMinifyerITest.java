package ca.nexapp.crawdy.infrastructure.documents;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Before;
import org.junit.Test;

import ca.nexapp.crawdy.domain.documents.HTMLDocument;
import ca.nexapp.crawdy.domain.documents.HTMLMinifyer;

public class GoogleHTMLMinifyerITest {

    private HTMLMinifyer minifyer;

    @Before
    public void setUp() {
        minifyer = new GoogleHTMLMinifyer();
    }

    @Test
    public void canMinifyAHTMLPage() {
        HTMLDocument document = withSource("<html>  <head> </head>   <body> <p>Hello World!   </p>  </body>  </html>");

        HTMLDocument minified = minifyer.minify(document);

        assertThat(minified.source).isEqualTo("<html> <head> </head> <body> <p>Hello World! </p> </body> </html>");
    }

    @Test
    public void shouldKeepTheSameUrl() {
        HTMLDocument document = withSource("<html>  <head> </head>   <body> <p>Hello World!   </p>  </body>  </html>");

        HTMLDocument minified = minifyer.minify(document);

        assertThat(minified.url).isEqualTo(document.url);
    }

    private HTMLDocument withSource(String source) {
        return new HTMLDocument("http://www.google.com", source);
    }
}
