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

        statementData.getPerformances().stream().map(perf -> String.format("  %s: %s (%s seats)%n",
                perf.getPlayName(),
                formatAsUSD(perf.amount()),
                perf.getAudience())).forEach(result::append);

        result.append(String.format("Amount owed is %s%n",
                formatAsUSD(statementData.totalAmount())));
        result.append(String.format("You earned %s credits%n",
                statementData.totalVolumeCredits()));

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
