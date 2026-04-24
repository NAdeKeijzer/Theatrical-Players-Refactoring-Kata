package theatricalplays;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

public class StatementPrinter {

    public String print(Invoice invoice, Map<String, Play> plays) {
        StatementData statementData = new StatementData(invoice, plays);

        return renderPlainText(statementData);
    }

    public String printHtml(Invoice invoice, Map<String, Play> plays) {
        StatementData statementData = new StatementData(invoice, plays);

        return renderHTML(statementData);
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
        StringBuilder result = new StringBuilder(
                String.format("<h1>Statement for %s</h1>%n", statementData.getInvoice().customer)
        );

        result.append("<table>\n");
        result.append("<tr><th>play</th><th>seats</th><th>cost</th></tr>\n");

        for (var perf : statementData.getPerformances()) {
            result.append(String.format("<tr><td>%s</td><td>%s</td><td>%s</td></tr>%n",
                    perf.getPlayName(),
                    perf.getAudience(),
                    formatAsUSD(perf.amount())));
        }

        result.append("</table>\n");

        result.append(String.format("<p>Amount owed is <em>%s</em></p>%n",
                formatAsUSD(statementData.totalAmount())));
        result.append(String.format("<p>You earned <em>%s</em> credits</p>%n",
                statementData.totalVolumeCredits()));

        return result.toString();

    }

    private static String formatAsUSD(int amount) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(amount / 100);
    }
}
