package ca.nexapp.crawdy.domain.documents;

import static com.google.common.truth.Truth.assertThat;

import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public abstract class DocumentRepositoryTest<T> {

    private DocumentRepository<T> documentRepository;

    @Before
    public void setUp() {
        documentRepository = createRepository();
    }

    @Test
    public void canFindAnExistingDocument() {
        HTMLDocument document = documentFrom("www.yahoo.com");
        documentRepository.persist(createAKey(), document);

        Optional<HTMLDocument> documentFound = documentRepository.find(createAKey());

        assertThat(documentFound.get()).isEqualTo(document);
    }

    @Test
    public void givenAnExistingDocument_WhenPersistingAgain_ShouldOverrideIt() {
        HTMLDocument document = documentFrom("www.yahoo.com");
        documentRepository.persist(createAKey(), document);

        HTMLDocument anotherDocument = anotherDocumentFrom("www.yahoo.com");
        documentRepository.persist(createAKey(), anotherDocument);

        Optional<HTMLDocument> documentFound = documentRepository.find(createAKey());
        assertThat(documentFound.get()).isEqualTo(anotherDocument);
    }

    @Test
    public void cannotFindANonExistingDocument() {
        Optional<HTMLDocument> documentFound = documentRepository.find(createAKey());

        assertThat(documentFound.isPresent()).isFalse();
    }

    @Test
    public void givenAnExistingDocument_ItShouldExists() {
        HTMLDocument document = documentFrom("www.yahoo.com");
        documentRepository.persist(createAKey(), document);

        boolean exists = documentRepository.exists(createAKey());

        assertThat(exists).isTrue();
    }

    @Test
    public void givenANonExistingDocument_ItShouldNotExists() {
        boolean exists = documentRepository.exists(createAKey());

        assertThat(exists).isFalse();
    }

    @After
    public void tearDown() {
        documentRepository.clear();
    }

    private HTMLDocument documentFrom(String url) {
        String html = "<html><head></head><body><p>Hello World!</p></body></html>";
        return new HTMLDocument(url, html);
    }

    private HTMLDocument anotherDocumentFrom(String url) {
        String html = "<html><head></head><body><p>Hello World!</p><p>One more sentence</p></body></html>";
        return new HTMLDocument(url, html);
    }

    protected abstract T createAKey();

    protected abstract DocumentRepository<T> createRepository();
}
