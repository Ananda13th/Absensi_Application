package example.com.absensiapp.view.utils;

public class MonthFormatter {

    private String result;
    public String StringToNumberMonth(String string) {
        if(string.equals("January"))
            result="01";
        if(string.equals("February"))
            result="02";
        if(string.equals("March"))
            result="03";
        if(string.equals("April"))
            result="04";
        if(string.equals("May"))
            result="05";
        if(string.equals("June"))
            result="06";
        if(string.equals("July"))
            result="07";
        if(string.equals("August"))
            result="08";
        if(string.equals("September"))
            result="09";
        if(string.equals("October"))
            result="10";
        if(string.equals("November"))
            result="11";
        if(string.equals("Desember"))
            result="12";

        return result;
    }
}
