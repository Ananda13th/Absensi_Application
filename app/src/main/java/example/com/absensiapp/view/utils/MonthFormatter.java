package example.com.absensiapp.view.utils;

public class MonthFormatter {

    String result;
    public String StringToNumberMonth(String string) {
        if(string.equals("January"))
            result="1";
        if(string.equals("February"))
            result="2";
        if(string.equals("March"))
            result="3";
        if(string.equals("April"))
            result="4";
        if(string.equals("May"))
            result="5";
        if(string.equals("June"))
            result="6";
        if(string.equals("July"))
            result="7";
        if(string.equals("August"))
            result="8";
        if(string.equals("September"))
            result="9";
        if(string.equals("October"))
            result="10";
        if(string.equals("November"))
            result="11";
        if(string.equals("Desember"))
            result="12";

        return result;
    }
}
