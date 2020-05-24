package kea.nordicmotorhome.Model;

import org.springframework.stereotype.Component;

@Component
public class SearchForm {
    private String attribute;

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    @Override
    public String toString() {
        return "SearchForm{" +
                "attribute='" + attribute + '\'' +
                '}';
    }
}
