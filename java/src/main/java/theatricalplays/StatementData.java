package theatricalplays;

import java.util.List;
import java.util.Map;

public final class StatementData {
    private final Invoice invoice;
    private final List<PerformanceData> performances;

    public StatementData(final Invoice invoice, final Map<String, Play> plays) {
        this.invoice = invoice;
        this.performances = invoice.performances.stream()
                .map(perf -> new PerformanceData(perf, plays.get(perf.playID)))
                .toList();
    }

    public int totalAmount() {
        int totalAmount = 0;

        for (final PerformanceData perf : performances) {
            totalAmount += perf.amountFor();
        }

        return totalAmount;
    }

    public int totalVolumeCredits() {
        int volumeCredits = 0;

        for (final PerformanceData perf : performances) {
            volumeCredits += perf.volumeCreditsFor();
        }

        return volumeCredits;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public List<PerformanceData> getPerformances() {
        return performances;
    }
}