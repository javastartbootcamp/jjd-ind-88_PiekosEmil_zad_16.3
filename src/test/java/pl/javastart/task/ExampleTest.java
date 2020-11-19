package pl.javastart.task;

import org.junit.jupiter.api.*;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class ExampleTest {

    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @DisplayName("2022-10-23 14:15:51")
    @Test
    void shouldWorkForExerciseExample() {
        // given
        provideInput("2022-10-23 14:15:51");

        // when
        Main.main(new String[]{});

        // then
        assertThat(outContent.toString()).contains("Czas lokalny: 2022-10-23 14:15:51");
        assertThat(outContent.toString()).contains("UTC: 2022-10-23 12:15:51");
        assertThat(outContent.toString()).contains("Londyn: 2022-10-23 13:15:51");
        assertThat(outContent.toString()).contains("Los Angeles: 2022-10-23 05:15:51");
        assertThat(outContent.toString()).contains("Sydney: 2022-10-23 23:15:51");
    }

    @DisplayName("2022-10-23 10:00:00")
    @Test
    void shouldWorkForOtherPatternInput() {
        // given
        provideInput("2022-10-23 10:00:00");

        // when
        Main.main(new String[]{});

        // then
        assertThat(outContent.toString()).contains("Czas lokalny: 2022-10-23 10:00:00");
        assertThat(outContent.toString()).contains("UTC: 2022-10-23 08:00:00");
        assertThat(outContent.toString()).contains("Londyn: 2022-10-23 09:00:00");
        assertThat(outContent.toString()).contains("Los Angeles: 2022-10-23 01:00:00");
        assertThat(outContent.toString()).contains("Sydney: 2022-10-23 19:00:00");
    }

    @DisplayName("2022-10-23")
    @Test
    void shouldWorkForWithoutHour() {
        // given
        provideInput("2022-10-23");

        // when
        Main.main(new String[]{});

        // then
        assertThat(outContent.toString()).contains("Czas lokalny: 2022-10-23 00:00:00");
        assertThat(outContent.toString()).contains("UTC: 2022-10-22 22:00:00");
        assertThat(outContent.toString()).contains("Londyn: 2022-10-22 23:00:00");
        assertThat(outContent.toString()).contains("Los Angeles: 2022-10-22 15:00:00");
        assertThat(outContent.toString()).contains("Sydney: 2022-10-23 09:00:00");
    }

    @DisplayName("05.12.2015 22:00:00")
    @Test
    void shouldWorkForOtherFormat() {
        // given
        provideInput("05.12.2015 22:00:00");

        // when
        Main.main(new String[]{});

        // then
        assertThat(outContent.toString()).contains("Czas lokalny: 2015-12-05 22:00:00");
        assertThat(outContent.toString()).contains("2015-12-05 21:00:00");
        assertThat(outContent.toString()).contains("Londyn: 2015-12-05 21:00:00");
        assertThat(outContent.toString()).contains("Los Angeles: 2015-12-05 13:00:00");
        assertThat(outContent.toString()).contains("Sydney: 2015-12-06 08:00:00");
    }

    @DisplayName("t")
    @Test
    void shouldWorkForNow() {
        // given
        provideInput("t");

        // when
        Main.main(new String[]{});

        // then
        ZonedDateTime now = ZonedDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        assertThat(outContent.toString()).contains("Czas lokalny: " + formatter.format(now));
    }

    @DisplayName("t+2d")
    @Test
    void shouldWorkForNowPlus2Days() {
        // given
        provideInput("t+2d");

        // when
        Main.main(new String[]{});

        // then
        ZonedDateTime dateTime = ZonedDateTime.now().plusDays(2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        assertThat(outContent.toString()).contains("Czas lokalny: " + formatter.format(dateTime));
    }

    @DisplayName("t-2d")
    @Test
    void shouldWorkForNowMinus2Days() {
        // given
        provideInput("t-2d");

        // when
        Main.main(new String[]{});

        // then
        ZonedDateTime dateTime = ZonedDateTime.now().minusDays(2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        assertThat(outContent.toString()).contains("Czas lokalny: " + formatter.format(dateTime));
    }

    @DisplayName("t+3h")
    @Test
    void shouldWorkForNowMinus3Hours() {
        // given
        provideInput("t+3h");

        // when
        Main.main(new String[]{});

        // then
        ZonedDateTime dateTime = ZonedDateTime.now().plusHours(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        assertThat(outContent.toString()).contains("Czas lokalny: " + formatter.format(dateTime));
    }

    @DisplayName("t+1y-20m")
    @Test
    void shouldWorkForNowPlus1YearMinus20Minutes() {
        // given
        provideInput("t+1y-20m");

        // when
        Main.main(new String[]{});

        // then
        ZonedDateTime dateTime = ZonedDateTime.now().plusYears(1).minusMinutes(20);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        assertThat(outContent.toString()).contains("Czas lokalny: " + formatter.format(dateTime));
    }

    @DisplayName("t+1y-4M+2d+14h-20m+59s")
    @Test
    void shouldWorkForNowWithEveryChange() {
        // given
        provideInput("t+1y-4M+2d+14h-20m+59s");

        // when
        Main.main(new String[]{});

        // then
        ZonedDateTime dateTime = ZonedDateTime.now()
                .plusYears(1)
                .minusMonths(4)
                .plusDays(2)
                .plusHours(14)
                .minusMinutes(20)
                .plusSeconds(59);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        assertThat(outContent.toString()).contains("Czas lokalny: " + formatter.format(dateTime));
    }

    @BeforeEach
    void init() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void cleanup() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    private void provideInput(String... lines) {
        String input = String.join("\r\n", lines);
        input += "\r\n";

        ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
    }

}
