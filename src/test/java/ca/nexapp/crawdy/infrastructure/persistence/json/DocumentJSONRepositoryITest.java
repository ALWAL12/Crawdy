package ca.nexapp.crawdy.infrastructure.persistence.json;

import java.nio.file.Path;
import java.nio.file.Paths;

import ca.nexapp.crawdy.domain.documents.DocumentRepository;
import ca.nexapp.crawdy.domain.documents.DocumentRepositoryTest;

public class DocumentJSONRepositoryITest extends DocumentRepositoryTest<String> {

    private static final String FILE_PATH = "/ca/nexapp/crawdy/json/documents.json";

    @Override
    protected String createAKey() {
        return "DUMMY.KEY";
    }

    @Override
    protected DocumentRepository<String> createRepository() {
        Path path = Paths.get(getClass().getResource(FILE_PATH).getFile().substring(1));
        return new DummyRepository(path);
    }

    private class DummyRepository extends DocumentJSONRepository<String> {

        public DummyRepository(Path path) {
            super(path);
        }

        @Override
        protected String toKey(String rawKey) {
            return rawKey;
        }
    }
}
