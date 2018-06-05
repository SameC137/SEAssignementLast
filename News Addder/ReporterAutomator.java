import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

/**
 * Created by Same Michael ATR/2759/08 on 5/26/2018.
 */
public class ReporterAutomator extends Thread {
    public void run(){
        while(true){
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.setProperty("webdriver.chrome.driver", "\\chromedriver.exe");
        WebDriver reporterDriver= new ChromeDriver();
        WebDriver personalPage=new ChromeDriver();

        reporterDriver.get("http://www.thereporterethiopia.com/news");
        List<WebElement> articles=reporterDriver.findElements(By.tagName("article"));
        int length=articles.size();
        for (int i=0; i<length;i++) {

            try{
                WebElement title=(reporterDriver.findElements(By.tagName("article")).get(i)).findElement(By.className("post-title"));
                String titleText=title.getText();
                WebElement link=title.findElement(By.tagName("a"));
                personalPage.get("http://localhost:8000/checkExists?title="+title.getText());
                WebElement status=personalPage.findElement(By.id("exists"));
                if(status.getText()=="true"){
                    reporterDriver.get("http://www.thereporterethiopia.com/news");
                    continue;
                }
                reporterDriver.get(link.getAttribute("href"));
                WebElement wrapper=reporterDriver.findElement(By.className("node__content"));
                WebElement article= wrapper.findElement(By.tagName("div"));
                personalPage.navigate().to("http://localhost:8000/");
                WebElement titleInput=personalPage.findElement(By.name("title"));
                titleInput.click();
                titleInput.sendKeys(titleText);
                WebElement contentInput=personalPage.findElement(By.name("content"));
                contentInput.click();
                contentInput.sendKeys(article.getText());
                contentInput.submit();
                reporterDriver.get("http://www.thereporterethiopia.com/news");
            }catch(IndexOutOfBoundsException e){
                System.out.println("Index out of bound "+i+"With size "+length);
                continue;
            }


        }
    }
    }
}
