package pl.javastart.task;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        Main main = new Main();
        main.run(new Scanner(System.in));
    }

    public void run(Scanner scanner) {
        System.out.println("Podaj datę lub modyfikuj aktualną datę:");
        System.out.println("Modyfikator w formacie t+/-n, gdzie n to dowolna liczba,");
        System.out.println("y - dla lat,");
        System.out.println("M - dla miesięcy,");
        System.out.println("d - dla dni,");
        System.out.println("h - dla godzin,");
        System.out.println("m - dla minut,");
        System.out.println("s - dla sekund.");
        String dateInput = scanner.nextLine();

        if (dateInput.startsWith("t")) {
            LocalDateTime modifiedDateTime = DateTimeUtils.modifyInputDateTime(dateInput);
            System.out.println(modifiedDateTime.format(formatter));
            if (dateInput.equals("t")) {
                LocalDateTime now = LocalDateTime.now();
                System.out.println(now.format(formatter));
            }
        } else {

            ZonedDateTime zonedDateTimeDefault = ZonedDateTime.of(createDateTimeOf(dateInput), ZoneId.systemDefault());
            ZonedDateTime zonedDateTimeUtc = zonedDateTimeDefault.withZoneSameInstant(ZoneId.of("UTC"));
            ZonedDateTime zonedDateTimeLosAngeles = zonedDateTimeDefault.withZoneSameInstant(ZoneId.of("America/Los_Angeles"));
            ZonedDateTime zonedDateTimeLondon = zonedDateTimeDefault.withZoneSameInstant(ZoneId.of("Europe/London"));
            ZonedDateTime zonedDateTimeSydney = zonedDateTimeDefault.withZoneSameInstant(ZoneId.of("Australia/Sydney"));

            System.out.println("Czas lokalny: " + zonedDateTimeDefault.format(formatter));
            System.out.println("UTC: " + zonedDateTimeUtc.format(formatter));
            System.out.println("Los Angeles: " + zonedDateTimeLosAngeles.format(formatter));
            System.out.println("Londyn: " + zonedDateTimeLondon.format(formatter));
            System.out.println("Sydney: " + zonedDateTimeSydney.format(formatter));
            scanner.close();
        }
    }

    private LocalDateTime createDateTimeOf(String dateInput) {
        DateTimeFormatter formatter;
        LocalDateTime localDateTime = null;
        if (dateInput.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            localDateTime = LocalDateTime.parse(dateInput, formatter);
        } else if (dateInput.matches("\\d{4}-\\d{2}-\\d{2}")) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate d = LocalDate.parse(dateInput, formatter);
            localDateTime = LocalDateTime.of(d, LocalTime.of(0, 0));
        } else if (dateInput.matches("\\d{2}.\\d{2}.\\d{4} \\d{2}:\\d{2}:\\d{2}")) {
            formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
            localDateTime = LocalDateTime.parse(dateInput, formatter);
        } else {
            System.out.println("Błędny format");
        }
        return localDateTime;
    }
}