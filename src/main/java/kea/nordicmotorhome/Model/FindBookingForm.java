package kea.nordicmotorhome.Model;

import org.springframework.stereotype.Component;

@Component
public class FindBookingForm {
    //attributes the booking needs
    private String start_date;
    private String end_date;
    private String inputText;
    private String inputType;

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getInputText() {
        return inputText;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    @Override
    public String toString() {
        return "FindBookingForm{" +
                "start_date='" + start_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", inputText='" + inputText + '\'' +
                ", inputType='" + inputType + '\'' +
                '}';
    }
}
