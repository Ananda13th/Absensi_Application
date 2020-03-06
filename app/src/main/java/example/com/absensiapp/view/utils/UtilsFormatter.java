package example.com.absensiapp.view.utils;

public class UtilsFormatter {

    private String result;
    public String StringToNumberMonth(String string) {
        if(string.equals("Januari"))
            result="01";
        if(string.equals("Februari"))
            result="02";
        if(string.equals("Maret"))
            result="03";
        if(string.equals("April"))
            result="04";
        if(string.equals("Mei"))
            result="05";
        if(string.equals("Juni"))
            result="06";
        if(string.equals("Juli"))
            result="07";
        if(string.equals("Agustus"))
            result="08";
        if(string.equals("September"))
            result="09";
        if(string.equals("Oktober"))
            result="10";
        if(string.equals("November"))
            result="11";
        if(string.equals("Desember"))
            result="12";
        return result;
    }

    public String ActionInputFormatter(String string) {
        if(string.equals("Masuk"))
            result="I";
        if(string.equals("Keluar"))
            result="O";
        return result;
    }
}
