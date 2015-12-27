package bot;


import org.openqa.selenium.WebDriver;
import selenium.Tarayici;
import selenium.WebLockter;

/**
 * Created by erkanmdr on 26.12.2015.
 */
public class Test {


    public static void main(String[] args) {

        WebDriver driver = Tarayici.Light(false, true);
        boolean s = WebLockter.girisYap(driver, "KaranalpN", "kh2k9ae3n2k2", "qfc94639@iaoss.com");
        if (s) {
           // WebLockter.tweetAt(driver, "sdf!asd! HAHA");
            WebLockter.takipEt(driver, "akbank");
        }
    }


}
