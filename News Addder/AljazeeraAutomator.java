import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
/**
 * Created by Same Michael ATR/2759/08 on 5/26/2018.
 */
public class AljazeeraAutomator extends Thread{

    public void run() {
        System.setProperty("webdriver.chrome.driver", "\\..\\chromedriver.exe");
        WebDriver reporterDriver = new ChromeDriver();
        WebDriver personalPage = new ChromeDriver();
        while (true) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            reporterDriver.get("https://www.aljazeera.com/topics/country/ethiopia.html");
            List<WebElement> articles = reporterDriver.findElements(By.className("topics-sec-item-cont"));
            personalPage.get("http://localhost:8000");
            int length = articles.size();
            System.out.println(length);
            for (int i = 0; i < length; i++) {

                try {
                    WebElement title = (reporterDriver.findElements(By.className("topics-sec-item-cont")).get(i)).findElements(By.tagName("a")).get(1);
                    String titleText = title.getText();
                    System.out.println(titleText);
                    //WebElement link=title.findElement(By.tagName("a"));
                    personalPage.get("http://localhost:8000/checkExists?title=" + title.getText());
                    WebElement status = personalPage.findElement(By.id("exists"));
                    if (status.getText() == "true") {
                        reporterDriver.get("https://www.aljazeera.com/topics/country/ethiopia.html");
                        //continue;
                    }
                    reporterDriver.get(title.getAttribute("href"));
                    WebElement article = reporterDriver.findElement(By.className("article-p-wrapper"));
                    personalPage.navigate().to("http://localhost:8000/");
                    WebElement titleInput = personalPage.findElement(By.name("title"));
                    titleInput.click();
                    titleInput.sendKeys(titleText);
                    WebElement contentInput = personalPage.findElement(By.name("content"));
                    contentInput.click();
                    contentInput.sendKeys(article.getText());
                    contentInput.submit();
                    reporterDriver.get("https://www.aljazeera.com/topics/country/ethiopia.html");
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Index out of bound " + i + "With size " + length);
                    continue;
                }


                }
            }
    }
}



