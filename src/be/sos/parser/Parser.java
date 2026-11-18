package be.sos.parser;

public interface Parser<T> {
    T parse(String input);
    String format(T input);
}
