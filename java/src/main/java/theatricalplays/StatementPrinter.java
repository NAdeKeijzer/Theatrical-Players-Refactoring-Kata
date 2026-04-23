package theatricalplays;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

public class StatementPrinter {

    public String print(Invoice invoice, Map<String, Play> plays) {
        StringBuilder result = new StringBuilder(String.format("Statement for %s%n", invoice.customer));

        NumberFormat frmt = NumberFormat.getCurrencyInstance(Locale.US);

        for (var perf : invoice.performances) {
            // print line for this order
            result.append(String.format("  %s: %s (%s seats)%n", playForPerformance(plays, perf).name, formatAsUSD(frmt, amountFor(perf, playForPerformance(plays, perf))), perf.audience));
        }

        result.append(String.format("Amount owed is %s%n", frmt.format(totalAmountFor(invoice, plays) / 100)));
        result.append(String.format("You earned %s credits%n", totalVolumeCredits(invoice, plays)));
        return result.toString();
    }

    private static int totalAmountFor(Invoice invoice, Map<String, Play> plays) {
        var totalAmount = 0;
        for (var perf : invoice.performances) {
            totalAmount += amountFor(perf, playForPerformance(plays, perf));
        }
        return totalAmount;
    }

    private static int totalVolumeCredits(Invoice invoice, Map<String, Play> plays) {
        var volumeCredits = 0;
        for (var perf : invoice.performances) {

            volumeCredits += volumeCreditsFor(perf, playForPerformance(plays, perf));
        }
        return volumeCredits;
    }

    private static Play playForPerformance(Map<String, Play> plays, Performance perf) {
        return plays.get(perf.playID);
    }

    private static int volumeCreditsFor(Performance perf, Play play) {
        var result = 0;
        // add volume credits
        result += Math.max(perf.audience - 30, 0);
        // add extra credit for every five comedy attendees
        if ("comedy".equals(play.type)) {
            // Standard integer division truncates decimals, effectively "flooring" positive results
            result += perf.audience / 5;
        }
        return result;
    }

    private static int amountFor(Performance perf, Play play) {
        return switch (play.type) {
            case "tragedy" -> {
                int base = 40_000;
                yield perf.audience > 30 ? base + 1000 * (perf.audience - 30) : base;
            }
            case "comedy" -> {
                int base = 30_000;
                if (perf.audience > 20) {
                    base += 10_000 + 500 * (perf.audience - 20);
                }
                yield base + 300 * perf.audience;
            }
            default -> throw new IllegalArgumentException("unknown type: %s".formatted(play.type));
        };
    }
    private static String formatAsUSD(NumberFormat frmt, int amount) {
        return frmt.format(amount / 100);
    }

}
