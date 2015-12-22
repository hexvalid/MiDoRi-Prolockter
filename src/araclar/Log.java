package araclar;

/**
 * Hata ayıklamak için kullanışlı bir sınıf. Sadece terminale yazar.
 * Eğer DEBUG=false olursa, hiç çıktı vermez. Aksi halde hata ayıklamaya
 * çok yardımcı olur.
 */
//TODO: DEBUG boolean'i eklenecek.
public class Log {

    /**
     * Log Türleri. Hepsinin farklı renkleri vardır.
     */
    public enum Tur {
        BILGI, UYARI, HATA, BASARILI, ARAYUZ, CIKTI
    }

    static String ANSI_RESET = "\u001B[0m";
    static String ANSI_KIRMIZI = "\u001B[31m";
    static String ANSI_YESIL = "\u001B[32m";
    static String ANSI_MOR = "\u001B[35m";
    static String ANSI_CIYAN = "\u001B[36m";
    static String ANSI_SARI = "\u001B[33m";

    public static void yaz(String log, Tur tur) {
        //TODO: Hatalar sayılacak, havuz'a kaydedilecek
        //TODO: Hata verdiğinde program kapatılacak, bir popupta bu belirtilecek!
        switch (tur) {
            case BILGI:
                System.out.println(Zaman.al() + " BİLGİ: " + log);
                break;
            case UYARI:
                System.out.println(Zaman.al() + ANSI_SARI
                        + " UYARI: " + log + ANSI_RESET);
                break;
            case HATA:
                System.out.println(Zaman.al() + ANSI_KIRMIZI
                        + " HATA: " + log + ANSI_RESET);
                break;
            case BASARILI:
                System.out.println(Zaman.al() + ANSI_YESIL
                        + " BAŞARILI: " + log + ANSI_RESET);
                break;
            case ARAYUZ:
                System.out.println(Zaman.al() + ANSI_MOR
                        + " ARAYÜZ: " + log + ANSI_RESET);
                break;
            case CIKTI:
                System.out.println(Zaman.al() + ANSI_CIYAN
                        + " HARİCİ: " + log + ANSI_RESET);
                break;
        }

    }

}
