# Crawdy
Lightweight web crawler made in Java, designed with extensibility and testability in mind.

## How to include it in your project
At the moment, this project is hosted on GitHub and not on Maven Central. In your `pom.xml`, you must add the GitHub repository as follows:

```xml
<repositories>
    <repository>
        <id>crawdy-mvn-repo</id>
        <url>https://raw.github.com/alwal12/crawdy/mvn-repo/</url>
        <snapshots>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
        </snapshots>
    </repository>
</repositories>
```

Then, you simply add the dependency as follows:
```xml
<dependency>
    <groupId>ca.nexapp</groupId>
    <artifactId>crawdy</artifactId>
    <version>0.0.1</version>
</dependency>
```

## How to use it
Basically, you declare a `WebCrawler` and you're ready to go. The `WebCrawler` use proxies in order to stay anonymous, so you need to provide a repository implementation to it. I've made default implementations to help you out.

```java
ProxyRepository proxyRepository = new ProxyPropertiesRepository("my/path/to/proxy.properties"); // See Appendix below
ProxyPicker proxyPicker = new RoundRobinProxyPicker(); // You can always create a new algorithm
WebCrawler webCrawler = new JsoupWebCrawler(proxyRepository, proxyPicker);

HTMLDocument document = webCrawler.crawl("http://www.google.ca");
System.out.println(document.url);
System.out.println(document.source);
```

### You can minify your documents
```java
HTMLMinifyer minifyer = new GoogleHTMLMinifyer(); // You can always create your own minifyer
HTMLDocument minified = minifyer.minify(document);
```

### Persist your documents
I've provided an interface to persist your documents with a JSON implementation. As always, you are free to add your own implementation. The `DocumentRepository<T>` is templated so you can provide an external class to use as the key.
```java
import java.nio.file.Path;
import java.nio.file.Paths;

Path path = Paths.get("my/path/to/my/documents.json");
DocumentRepository<Integer> repository = new DocumentJSONRepository<>(path);

int dummyId = 1090; 
HTMLDocument dummyDocument = new HTMLDocument("www.google.ca", "<html> <body> <p>Hello World!</p> </body> </html>");

repository.exists(dummyId); // false
repository.persist(dummyId, dummyDocument);
Optional<HTMLDocument> documentFound = repository.find(dummyId);
System.out.println(documentFound.get().url); // www.google.ca
repository.exists(dummyId); // true
```

### It is observable
The `WebCrawler` is observable, which means you can create a `Crawlable`'s implementation class to extend the behavior on `onCrawlSuccess`and `onCrawlFailed`.
For instance, I've made a [ProxyPerformanceReport](src/main/java/ca/nexapp/crawdy/domain/proxies/reports/ProxyPerformanceReport.java) class for showcase.

```java
WebCrawler crawler = ...;
ProxyPerformanceReport proxyPerformanceReport = new ProxyPerformanceReport();

crawler.attach(proxyPerformanceReport);

crawler.crawl("www.google.ca");
crawler.crawl("www.microsoft.ca");
crawler.crawl("www.apple.ca");
crawler.crawl("www.amazon.ca");
// ...

proxyPerformanceReport.report();
/*
47.88.104.219:80 fetched 20 times. 0 success/20 failure (0.0%). Average of time per crawl: 484 ms
117.135.250.71:80 fetched 19 times. 0 success/19 failure (0.0%). Average of time per crawl: 811 ms
117.135.250.88:80 fetched 20 times. 0 success/20 failure (0.0%). Average of time per crawl: 1143 ms
124.88.67.17:843 fetched 19 times. 6 success/13 failure (31.57894736842105%). Average of time per crawl: 1321 ms
*/
```

#### Appendix
**Proxy Property file** <br />
If you plan on using my implementation, the key must be the url and the value the port.
For instance, your file may look like this:
```
192.168.1.1:8080
192.168.1.2:9001
192.168.1.3:3001
```


