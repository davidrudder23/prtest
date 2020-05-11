import static org.hamcrest.text.MatchesPattern.matchesPattern;
import static org.junit.Assert.assertThat;

import Time.GenerateTime;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ISO8601Test {
    final String iso8601Regex = "(\\d{4})-(\\d{2})-(\\d{2})T(\\d{2})\\:(\\d{2})\\:(\\d{2})Z";
    private static GenerateTime iso8601;

    @BeforeAll
    static void beforeAll() {
        iso8601 = new GenerateTime();
    }

    @Test
    void checkGetTimeNow() {
        String timeNow = iso8601.getISO8601TimeNow();
        assertThat(timeNow, matchesPattern(iso8601Regex));
    }
}
