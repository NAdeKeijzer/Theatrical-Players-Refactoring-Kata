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

    public int amount() {
        return switch (play.type) {
            case TRAGEDY -> {
                int base = 40_000;
                yield getAudience() > 30
                        ? base + 1000 * (getAudience() - 30)
                        : base;
            }
            case COMEDY -> {
                int base = 30_000;
                if (getAudience() > 20) {
                    base += 10_000 + 500 * (getAudience() - 20);
                }
                yield base + 300 * getAudience();
            }
            case HISTORY -> {
                // define behavior for history
                int base = 50_000;
                yield base + 800 * getAudience();
            }
            default -> throw new IllegalArgumentException("unknown type: %s".formatted(play.type));
        };
    }

    public int volumeCredits() {
        int baseCredits = Math.max(getAudience() - 30, 0);

        return baseCredits + switch (play.type) {
            case COMEDY -> getAudience() / 5;
            case TRAGEDY, HISTORY -> 0;
        };
    }
}