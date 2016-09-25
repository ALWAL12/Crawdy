package ca.nexapp.crawdy.domain.documents;

import java.util.Optional;

public abstract class DocumentRepository<T> {

    public boolean exists(T key) {
        return find(key).isPresent();
    }

    public abstract void persist(T key, HTMLDocument document);

    public abstract Optional<HTMLDocument> find(T key);

    public abstract void clear();
}
