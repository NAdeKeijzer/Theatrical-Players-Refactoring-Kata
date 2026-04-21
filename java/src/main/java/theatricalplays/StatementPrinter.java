package theatricalplays;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

public class StatementPrinter {

    public String print(Invoice invoice, Map<String, Play> plays) {
        var totalAmount = 0;
        var volumeCredits = 0;
        StringBuilder result = new StringBuilder(String.format("Statement for %s%n", invoice.customer));

        NumberFormat frmt = NumberFormat.getCurrencyInstance(Locale.US);

        for (var perf : invoice.performances) {

            volumeCredits += volumeCreditsFor(perf, playForPerformance(plays, perf));

            // print line for this order
            result.append(String.format("  %s: %s (%s seats)%n", playForPerformance(plays, perf).name, frmt.format(amountFor(perf, playForPerformance(plays, perf)) / 100), perf.audience));
            totalAmount += amountFor(perf, playForPerformance(plays, perf));
        }
        result.append(String.format("Amount owed is %s%n", frmt.format(totalAmount / 100)));
        result.append(String.format("You earned %s credits%n", volumeCredits));
        return result.toString();
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

}
