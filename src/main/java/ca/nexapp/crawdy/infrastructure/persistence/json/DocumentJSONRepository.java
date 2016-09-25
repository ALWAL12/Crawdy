package ca.nexapp.crawdy.infrastructure.persistence.json;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import ca.nexapp.crawdy.domain.documents.DocumentRepository;
import ca.nexapp.crawdy.domain.documents.HTMLDocument;

public abstract class DocumentJSONRepository<T> extends DocumentRepository<T> {

    private final Path path;

    public DocumentJSONRepository(Path path) {
        this.path = path;
    }

    @Override
    public void persist(T key, HTMLDocument document) {
        try {
            Map<T, HTMLDocument> documents = readFile();
            documents.put(key, document);
            writeToFile(documents);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<HTMLDocument> find(T key) {
        Map<T, HTMLDocument> documents = readFile();
        if (!documents.containsKey(key)) {
            return Optional.empty();
        }
        return Optional.of(documents.get(key));
    }

    @Override
    public void clear() {
        try {
            writeToFile(new HashMap<>());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void writeToFile(Map<T, HTMLDocument> documents) throws IOException {
        String json = gson().toJson(documents);
        Files.deleteIfExists(path);
        Files.write(path, json.getBytes(), StandardOpenOption.CREATE);
    }

    private Map<T, HTMLDocument> readFile() {
        try {
            String json = new String(Files.readAllBytes(path));
            if (json.isEmpty()) {
                return new HashMap<>();
            }
            Map<String, HTMLDocument> rawData = gson().fromJson(json, type());
            return rawData.entrySet()
                    .stream()
                    .collect(Collectors.toMap(entry -> toKey(entry.getKey()),
                            entry -> entry.getValue()));
        } catch (IOException e) {
            return new HashMap<>();
        }
    }

    protected abstract T toKey(String rawKey);

    private Gson gson() {
        return new GsonBuilder().create();
    }

    private Type type() {
        return new TypeToken<Map<String, HTMLDocument>>() {
        }.getType();
    }

}
