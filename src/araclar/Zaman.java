package araclar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Zaman araçları.
 */
public class Zaman {

    /**
     * Şuanki zamanı,
     * [21-11-2015 11:45:16.974] şeklinde çıktı verir.
     */
    public static String al() {
        DateFormat tarihFormati = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
        Calendar takvim = Calendar.getInstance();
        return "[" + tarihFormati.format(takvim.getTime()) + "]";
    }
}
