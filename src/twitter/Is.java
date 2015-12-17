package twitter;

import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * Created by erkanmdr on 17.12.2015.
 */
public class Is {
    public static void favRT(Twitter hesap, String id) {
        try {
            hesap.retweetStatus(Long.parseLong(String.valueOf(id)));
            System.out.println("RT Edildi");
            hesap.createFavorite(Long.parseLong(String.valueOf(id)));
            System.out.println("FAV Edildi");
        } catch (TwitterException e) {
            System.out.println("RT ya da FAV Edilemedi! " + e);
        }
    }
}
