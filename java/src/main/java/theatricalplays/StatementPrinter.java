package theatricalplays;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

public class StatementPrinter {

    public String print(Invoice invoice, Map<String, Play> plays) {
        StatementData statementData = new StatementData(invoice, plays);

        return renderPlainText(statementData);
    }

    private static String renderPlainText(StatementData statementData) {
        StringBuilder result = new StringBuilder(
                String.format("Statement for %s%n", statementData.getInvoice().customer)
        );

        for (var perf : statementData.getPerformances()) {
            result.append(String.format("  %s: %s (%s seats)%n",
                    perf.getPlayName(),
                    formatAsUSD(perf.amountFor()),
                    perf.getAudience()));
        }

        result.append(String.format("Amount owed is %s%n",
                formatAsUSD(statementData.totalAmountFor())));
        result.append(String.format("You earned %s credits%n",
                statementData.totalVolumeCreditsFor()));

        return result.toString();
    }

    private static String renderHTML(StatementData statementData) {
        //TODO: build HTML rendering. TEST first!!!
        return "";
    }

    private static String formatAsUSD(int amount) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(amount / 100);
    }
}
