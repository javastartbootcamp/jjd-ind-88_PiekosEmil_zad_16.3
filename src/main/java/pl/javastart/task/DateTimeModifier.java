package pl.javastart.task;

public class DateTimeModifier {
    private char sign;
    private int value;
    private char indicator;

    public DateTimeModifier(char sign, int value, char indicator) {
        this.sign = sign;
        this.value = value;
        this.indicator = indicator;
    }

    public char getSign() {
        return sign;
    }

    public int getValue() {
        return value;
    }

    public char getIndicator() {
        return indicator;
    }
}
