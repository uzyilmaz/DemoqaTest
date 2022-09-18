package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utilities.Driver;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Hooks {
    /*
   Cucumber'da step definitions package'i içerisinde
@before @after gibi bir notasyon varsa
extends testBase dememize gerek kalmadan
her scenario'dan önce ve/veya sonra bu methodlar çalışacaktır

Bu da bizim isteyeceğimiz bir durum değildir

Cucumber'da @before @after kullanma ihtiyacımız olursa
bunu stepDefinitions package'i altında oluşturacağımız
Hooks class'ına koyarız


Biz her scenario'dan sonra test sonucunu kontrol edip failed olan
scenario'lar için screenshoot olmasi amaciyla
@After methodu kullanacağız
     */
    public static String getScreenshot(String name) throws IOException {
        // naming the screenshot with the current date to avoid duplication
        String date = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        // TakesScreenshot is an interface of selenium that takes the screenshot
        TakesScreenshot ts = (TakesScreenshot) Driver.getDriver();
        File source = ts.getScreenshotAs(OutputType.FILE);
        // full path to the screenshot location
        String target = System.getProperty("user.dir") + "target/Screenshots" + name + date + ".png";
        File finalDestination = new File(target);
        // save the screenshot to the path given
        FileUtils.copyFile(source, finalDestination);
        return target;
    }


    @After
    public void tearDown(Scenario scenario) throws IOException {
        final byte[] screenshot = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BYTES);
        if (scenario.isFailed()) {
            // scenario.attach(screenshot, "image/png","screenshots");
            getScreenshot("test_failed");
        }
        if (Driver.getDriver() != null) {
            Driver.closeDriver();
        }
    }
}

