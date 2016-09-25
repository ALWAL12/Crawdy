package ca.nexapp.crawdy.domain.proxies.reports;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import ca.nexapp.crawdy.domain.crawlers.Crawlable;
import ca.nexapp.crawdy.domain.proxies.ProxyServer;
import ca.nexapp.math.units.Duration;
import ca.nexapp.math.units.Percentage;

public class ProxyPerformanceReport implements Crawlable {

    private final Map<ProxyServer, Stats> performances = new HashMap<>();

    @Override
    public void onCrawlSuccess(ProxyServer proxy, Duration duration) {
        if (!performances.containsKey(proxy)) {
            performances.put(proxy, new Stats());
        }

        Stats stats = performances.get(proxy);
        performances.put(proxy, stats.onSuccess(duration));
    }

    @Override
    public void onCrawlFailed(ProxyServer proxy, Duration duration) {
        if (!performances.containsKey(proxy)) {
            performances.put(proxy, new Stats());
        }

        Stats stats = performances.get(proxy);
        performances.put(proxy, stats.onFailure(duration));
    }

    public void report() {
        SortedMap<ProxyServer, Stats> sortedPerformances = sortByDuration();
        for (Map.Entry<ProxyServer, Stats> entry : sortedPerformances.entrySet()) {
            Stats stats = entry.getValue();
            String report = new StringBuilder()
                    .append(entry.getKey() + " fetched " + stats.count + " times. ")
                    .append(stats.success + " success/" + stats.failure + " failure (" + stats.successRate().toRatio() + "%). ")
                    .append("Average of time per crawl: " + stats.averageDuration.toMilliseconds() + " ms")
                    .toString();
            System.out.println(report);
        }
    }

    private SortedMap<ProxyServer, Stats> sortByDuration() {
        SortedMap<ProxyServer, Stats> sorted = new TreeMap<>(new DurationComparator(performances));
        sorted.putAll(performances);
        return sorted;
    }

    private class DurationComparator implements Comparator<ProxyServer> {

        private final Map<ProxyServer, Stats> map;

        public DurationComparator(Map<ProxyServer, Stats> map) {
            this.map = map;
        }

        @Override
        public int compare(ProxyServer o1, ProxyServer o2) {
            Stats s1 = map.get(o1);
            Stats s2 = map.get(o2);
            return s1.averageDuration.compareTo(s2.averageDuration);
        }

    }

    private class Stats {

        public int count;
        public int success;
        public int failure;
        public Duration averageDuration;

        public Stats() {
            count = 0;
            success = 0;
            failure = 0;
            averageDuration = Duration.milliseconds(0);
        }

        public Stats(int count, int success, int failure, Duration average) {
            this.count = count;
            this.success = success;
            this.failure = failure;
            averageDuration = average;
        }

        public Stats onSuccess(Duration duration) {
            return new Stats(count + 1, success + 1, failure, averageDuration.average(duration));
        }

        public Stats onFailure(Duration duration) {
            return new Stats(count + 1, success, failure + 1, averageDuration.average(duration));
        }

        public Percentage successRate() {
            if (count == 0) {
                return Percentage.ZERO_PERCENT;
            }
            return Percentage.fromFraction(success, count);
        }
    }

}
