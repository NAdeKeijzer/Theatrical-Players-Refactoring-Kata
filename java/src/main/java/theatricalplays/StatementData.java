package theatricalplays;

import java.util.List;
import java.util.Map;

public final class StatementData {
    private final Invoice invoice;
    private final List<PerformanceData> performances;

    public StatementData(Invoice invoice, Map<String, Play> plays) {
        this.invoice = invoice;
        this.performances = invoice.performances.stream()
                .map(perf -> new PerformanceData(perf, plays.get(perf.playID)))
                .toList();
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public List<PerformanceData> getPerformances() {
        return performances;
    }
}