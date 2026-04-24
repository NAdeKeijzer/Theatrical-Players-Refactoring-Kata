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

    static int totalAmountFor(StatementData statementData) {
        var totalAmount = 0;

        for (var perf : statementData.getPerformances()) {
            totalAmount += perf.amountFor();
        }

        return totalAmount;
    }

    static int totalVolumeCredits(StatementData statementData) {
        var volumeCredits = 0;

        for (var perf : statementData.getPerformances()) {
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