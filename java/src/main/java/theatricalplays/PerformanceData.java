package theatricalplays;

public final class PerformanceData {
    private final Performance performance;
    private final Play play;

    public PerformanceData(Performance performance, Play play) {
        this.performance = performance;
        this.play = play;
    }

    public int getAudience() {
        return performance.audience;
    }

    public String getPlayName() {
        return play.name;
    }

    public int amount() {
        return switch (play.type) {
            case "tragedy" -> {
                int base = 40_000;
                yield performance.audience > 30
                        ? base + 1000 * (performance.audience - 30)
                        : base;
            }
            case "comedy" -> {
                int base = 30_000;
                if (performance.audience > 20) {
                    base += 10_000 + 500 * (performance.audience - 20);
                }
                yield base + 300 * performance.audience;
            }
            default -> throw new IllegalArgumentException("unknown type: %s".formatted(play.type));
        };
    }

    public int volumeCredits() {
        int result = Math.max(performance.audience - 30, 0);

        if ("comedy".equals(play.type)) {
            result += performance.audience / 5;
        }

        return result;
    }
}