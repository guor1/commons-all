package commons.ratelimitj;

public @interface RateLimit {
    String path();

    int seconds();

    int limit();
}
