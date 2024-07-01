package pl.javastart.task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateTimeUtils {
    public static LocalDateTime modifyInputDateTime(String dateInput) {
        LocalDateTime dateModified = LocalDateTime.now();
        List<DateTimeModifier> dateTimeModifiers = extractModifiers(dateInput);

        for (DateTimeModifier dateTimeModifier : dateTimeModifiers) {
            if (dateTimeModifier.getSign() == '+') {
                if (dateTimeModifier.getIndicator() == 'y') {
                    dateModified = dateModified.plusYears(dateTimeModifier.getValue());
                }
                if (dateTimeModifier.getIndicator() == 'M') {
                    dateModified = dateModified.plusMonths(dateTimeModifier.getValue());
                }
                if (dateTimeModifier.getIndicator() == 'd') {
                    dateModified = dateModified.plusDays(dateTimeModifier.getValue());
                }
                if (dateTimeModifier.getIndicator() == 'h') {
                    dateModified = dateModified.plusHours(dateTimeModifier.getValue());
                }
                if (dateTimeModifier.getIndicator() == 'm') {
                    dateModified = dateModified.plusMinutes(dateTimeModifier.getValue());
                }
                if (dateTimeModifier.getIndicator() == 's') {
                    dateModified = dateModified.plusSeconds(dateTimeModifier.getValue());
                }
            }

            if (dateTimeModifier.getSign() == '-') {
                if (dateTimeModifier.getIndicator() == 'y') {
                    dateModified = dateModified.minusYears(dateTimeModifier.getValue());
                }
                if (dateTimeModifier.getIndicator() == 'M') {
                    dateModified = dateModified.minusMonths(dateTimeModifier.getValue());
                }
                if (dateTimeModifier.getIndicator() == 'd') {
                    dateModified = dateModified.minusDays(dateTimeModifier.getValue());
                }
                if (dateTimeModifier.getIndicator() == 'h') {
                    dateModified = dateModified.minusHours(dateTimeModifier.getValue());
                }
                if (dateTimeModifier.getIndicator() == 'm') {
                    dateModified = dateModified.minusMinutes(dateTimeModifier.getValue());
                }
                if (dateTimeModifier.getIndicator() == 's') {
                    dateModified = dateModified.minusSeconds(dateTimeModifier.getValue());
                }
            }
        }
        return dateModified;
    }

    private static List<DateTimeModifier> extractModifiers(String modifier) {
        List<String> modifiersAsStrings = new ArrayList<>();
        List<DateTimeModifier> modifiers = new ArrayList<>();
        if (modifier.startsWith("t")) {
            String modifiersExtracted = modifier.substring(1);
            String regex = "([+-]?\\d+[yMdhms])";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(modifiersExtracted);
            while (matcher.find()) {
                modifiersAsStrings.add(matcher.group());
            }
        } else {
            System.out.println("Błędny format");
            return null;
        }
        for (String s : modifiersAsStrings) {
            char sign = s.charAt(0);
            int value = Integer.parseInt(s.substring(1, s.length() - 1));
            char indicator = s.charAt(s.length() - 1);
            modifiers.add(new DateTimeModifier(sign, value, indicator));
        }
        return modifiers;
    }
}
