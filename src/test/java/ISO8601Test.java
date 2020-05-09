import static org.hamcrest.text.MatchesPattern.matchesPattern;
import static org.junit.Assert.assertThat;

import Time.ISO8601;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ISO8601Test {
    final String iso8601Regex = "(\\d{4})-(\\d{2})-(\\d{2})T(\\d{2})\\:(\\d{2})\\:(\\d{2})Z";
    private static ISO8601 iso8601;

    @BeforeAll
    static void beforeAll() {
        iso8601 = new ISO8601();
    }

    @Test
    void checkGetTimeNow() {
        String timeNow = iso8601.getTimeNow();
        assertThat(timeNow, matchesPattern(iso8601Regex));
    }
}
