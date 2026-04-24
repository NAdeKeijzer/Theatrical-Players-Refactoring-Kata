package theatricalplays;

public class Play {
    public final String name;
    public final PlayType type;

    public Play(String name, String type) {
        this.name = name;
        this.type = PlayType.valueOf(type.toUpperCase());
    }
}