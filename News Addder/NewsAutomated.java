import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

/**
 * Created by Same Michael ATR/2759/08 on 5/26/2018.
 */

public class NewsAutomated {
    public static void main(String args[]){

        Thread aljazeera=new Thread(new AljazeeraAutomator());
        Thread reporter=new Thread(new ReporterAutomator());
        aljazeera.start();
        reporter.start();
    }


}
