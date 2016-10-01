import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.awt.*;
import java.util.Iterator;
import java.util.TreeMap;

/**
 * Created by tylkovich on 30.09.2016.
 */
public class MyInterviewProgect {


    @Test
    public void test1() throws AWTException {
        FirefoxDriver driver = new FirefoxDriver();

        driver.get("http://store.steampowered.com/tag/ru/%D0%AD%D0%BA%D1%88%D0%B5%D0%BD/#p=0&tab=Discounts");
        //  List<WebElement> discont = driver.findElements(By.className("discount_pct"));
        java.util.List<WebElement> discont = driver.findElementsByXPath("//div[contains(@id, '_content') and not(contains(@style,'none'))]//div[@class='discount_pct']");

        java.util.List<WebElement> clicklin = driver.findElementsByXPath("//div[contains(@id, '_content') and not(contains(@style,'none'))]//div[@class='tab_item'][*/div[contains(@class, 'discount_pct')]]/a[@class='tab_item_overlay']");
// Cортируем с помощью treeset массив
        TreeMap<Integer, WebElement> discontMap = new TreeMap<>();
// Cоздаем итератор так как массив discont_pct  размером 16 возможно неправильно подобран Classname  но я вроде все просмотрел и там удаляем строки с нулевым значением
        Iterator<WebElement> iterator = discont.iterator();
        while (iterator.hasNext()) {
            int discountValue = 0;
            try {
                discountValue = Integer.parseInt(iterator.next().getText().replace("%", "").replace("-", ""));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (discountValue == 0) iterator.remove();
        }
// сортируем и сопоставляем наше значение со ссылкой XPath
        for (int i = 0; i < discont.size(); i++) {
            WebElement current = discont.get(i);
            int discountValue = Integer.parseInt(current.getText().replace("%", "").replace("-", ""));
            discontMap.put(discountValue, i >= clicklin.size() ? null : clicklin.get(i));
        }
//Переходим на страницу игры с самой дешевой скидкой
        discontMap.lastEntry().getValue().click();

        int maxValue = discontMap.lastKey();
        String maxdiscont = Integer.toString(maxValue);
        String min = "-";
        String w = "%";
        String maxdisc = null;
        maxdisc = min + maxdiscont + w;

        //       maxdiscon1=maxdiscon1.replace("maxdiscont","-maxdiscont%");
        System.out.println(maxdisc);

        try //This function like feature removes error details.
        {
            driver.findElementByCssSelector("ageDay").isEnabled();


        }
        catch (NoSuchElementException e)
        {
            System.out.println("Page Found");

        }
        //Всплывающее сообщение
//        if (driver.findElementByCssSelector("").getText().equals(null)) {
//            Select dropdown = (Select) driver.findElementByCssSelector("ageDay");
//            dropdown.selectByValue("22");
//            Select dropdown2 = new org.openqa.selenium.support.ui.Select(driver.findElementByName("ageMonth"));
//            dropdown2.selectByValue("January");
//            Select dropdown3 = new org.openqa.selenium.support.ui.Select(driver.findElementById("ageYear"));
//            dropdown3.selectByValue("1988");
//            driver.findElementByCssSelector("a.btnv6_blue_hoverfade.btn_small > span").click();}
        String ss = driver.findElementByCssSelector("div.discount_pct").getText();
        if (ss.equals(maxdisc)) {
            System.out.println("Cкидки на двух сайтах равны");
        } else if (!ss.equals(maxdisc)) {
            System.out.println("Cкидки на двух сайтах   не равны");
        }

        driver.findElementByLinkText("Загрузить Steam").click();
        driver.findElementByCssSelector("#about_install_steam_link > span").click();


        // C учетом того что firefox настроен на автоматическую загрузку файлов без всплывающих уведомлений


    }

}