package kea.nordicmotorhome.Model;

import org.springframework.stereotype.Component;

@Component
public class SearchForm {
    private String attribute;
    private String value;

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SearchForm{" +
                "attribute='" + attribute + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
