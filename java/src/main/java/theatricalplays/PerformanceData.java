package theatricalplays;

public final class PerformanceData {
    private final Performance performance;
    private final Play play;

    public PerformanceData(final Performance performance, final Play play) {
        this.performance = performance;
        this.play = play;
    }

    public int getAudience() {
        return performance.audience;
    }

    public String getPlayName() {
        return play.name;
    }

    public int amountFor() {
        return switch (play.type) {
            case "tragedy" -> {
                int base = 40_000;
                yield getAudience() > 30
                        ? base + 1000 * (getAudience() - 30)
                        : base;
            }
            case "comedy" -> {
                int base = 30_000;
                if (getAudience() > 20) {
                    base += 10_000 + 500 * (getAudience() - 20);
                }
                yield base + 300 * getAudience();
            }
            default -> throw new IllegalArgumentException("unknown type: %s".formatted(play.type));
        };
    }

    public int volumeCreditsFor() {
        int result = Math.max(getAudience() - 30, 0);

        if ("comedy".equals(play.type)) {
            result += getAudience() / 5;
        }

        return result;
    }
}