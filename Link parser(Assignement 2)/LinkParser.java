import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;


/**
 * Created by Same Michael ATR/2759/08 on 5/26/2018.
 */
public class LinkParser {
    public static void main(String args[]){

        linkFinder();
    }


    public static void linkFinder(){
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Same\\Desktop\\Automation1\\chromedriver.exe");
        WebDriver driver= new ChromeDriver();
        ArrayDeque<link> linkHref=new ArrayDeque<link>();
        ArrayDeque<link> checkedLinks=new ArrayDeque<link>();
        ArrayDeque<String> linksCheckedHref=new ArrayDeque<>();
        linkHref.add(new link("http://www.aau.edu.et",0));

        HttpURLConnection req;
        int status;
        link linkToAdd;
        while(!linkHref.isEmpty()){
            link currentLink=linkHref.remove();
            if(currentLink.level!=2 && !linksCheckedHref.contains(currentLink.getHref())){
                driver.get(currentLink.getHref());
                List<WebElement> aElements=driver.findElements(By.tagName("a"));
                for(WebElement i:aElements){
                    linkToAdd=new link(i.getAttribute("href"),currentLink.getLevel()+1);
                    linkHref.add(linkToAdd);
                    try {
                        /*linkToAdd.getHref()*/
                        if(linkToAdd.getHref().contains("http:")){
                            req = (HttpURLConnection)(new URL(linkToAdd.getHref()).openConnection());
                        }else{
                        req=(HttpsURLConnection)(new URL(linkToAdd.getHref()).openConnection());
                        }
                        req.setInstanceFollowRedirects(true);
                        req.setConnectTimeout(20000);
                        req.setRequestMethod("GET");
                        req.connect();
                        status = req.getResponseCode();
                        linkToAdd.up=true;
                        linkToAdd.type=req.getHeaderField("Content-Type");

                        System.out.println("The link "+linkToAdd.getHref()+" and type of "+linkToAdd.type+" up status: "+linkToAdd.up );
                    }catch (SocketTimeoutException x){

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                checkedLinks.add(currentLink);
                linksCheckedHref.add(currentLink.getHref());
            }
        }

        for(link i: checkedLinks){
            System.out.println("The link "+i.getHref()+" and type of "+i.type+" up status: "+i.up );
        }


    }
}
class link{
    String href;
    int level;
    boolean up;
    String type;
    public  link(String href,int level){
        this.href=href;
        this.level=level;
    }
    public String getHref(){
        return href;
    }
    public int getLevel(){
        return level;
    }
}

