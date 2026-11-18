package be.sos.enumeration;

public enum Language {
    FR("fr"),
    NL("nl");

    private final String label;

    Language(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
