package websites.house;

import excel.XLSFile;
import gui.swing.view.MainFrame;
import helperLogics.LogicHelper;
import model.Flat;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HouseParser {

    private static List<Flat> flats =  new ArrayList<Flat>();
    private String updatedDate;
    private String views;
    private LogicHelper logicHelper = new LogicHelper();
    private converter.Date date = new converter.Date();

    HouseParser(MainFrame mainFrame) throws IOException, NullPointerException {

        System.out.println("Started HouseParser.class.Parser");
        JTextField pagesOfHose = mainFrame.getPagesOfHouse();
        int pagesNumber = Integer.parseInt(pagesOfHose.getText());

        for(int i = 1; i <= pagesNumber; i++){

                Document documentOfList = Jsoup.connect("https://www.house.kg/kupit-kvartiru?page="+i).get();
                Elements itemOffereds = documentOfList.getElementsByAttributeValue("itemprop", "itemOffered");

             parsing(itemOffereds);

        }
        JTextPane textPane = mainFrame.getTextPane();
        textPane.setText("Загрузка данных house. Завершена" +  "\n");


        new XLSFile("house.kg", flats, "houseKg", mainFrame);



    }


    private void parsing(Elements itemOffereds) throws IOException {
        System.out.println("itemOffereds = " + itemOffereds.size());

        for (Element itemOffered: itemOffereds) {

            Elements leftImageElements = itemOffered.getElementsByAttributeValue("class", "left-image");
            Elements additionalInfoElements = itemOffered.getElementsByAttributeValue("class", "additional-info");

            for (Element e: additionalInfoElements) {
                Elements spanElements = e.getElementsByAttributeValue("class", "left-side");
                Elements span = spanElements.select("span");
                updatedDate = e.selectFirst("span").text();

                for (Element element: span) {
                    Elements classValueofViews = element.getElementsByAttributeValue("class", "fas fa-eye");
                    if(classValueofViews.hasClass("fas fa-eye")) views = element.text();
                }

            }

            Elements hrefElements = leftImageElements.select("a");
            String hrefUrl = hrefElements.attr("href");
            Document documentOfFlatPage = Jsoup.connect("https://www.house.kg" + hrefUrl).get();

            Elements priceDollar = documentOfFlatPage.getElementsByClass("price-dollar");
            Elements addressElements = documentOfFlatPage.getElementsByClass("adress");
            Elements createdDates = documentOfFlatPage.getElementsByClass("added-info");
            Elements phoneNumberElements = documentOfFlatPage.getElementsByClass("number");
            Elements infoRows = documentOfFlatPage.getElementsByAttributeValue("class", "info-row");
            Elements headerDetals = documentOfFlatPage.getElementsByAttributeValue("class", "details-header");
            Elements leftElements = headerDetals.select("h1");
            Elements descriptions = documentOfFlatPage.getElementsByAttributeValue("class", "description");

            String discription = descriptions.select("p").text();
            String series = infoRows.get(0).getElementsByAttributeValue("class", "info").text();
            String floor = infoRows.get(2).getElementsByAttributeValue("class", "info").text();
            String place = infoRows.get(3).getElementsByAttributeValue("class", "info").text();
            String phoneNumber = phoneNumberElements.text();
            String createdDate = createdDates.text();
            String address = addressElements.text();
            String price = priceDollar.text();
            String rooms = leftElements.text();

            updatedDate = dateForm(updatedDate);
            createdDate = dateForm(createdDate);

            System.out.println("Views = " + views);
            System.out.println("elements" + hrefUrl);
            System.out.println("Price" + price);
            System.out.println("address:   " + address);
            System.out.println("updatedDate = " + updatedDate);
            System.out.println("createdDate:   " + createdDate);
            System.out.println("phoneNumber:   " + phoneNumber);
            System.out.println("series:   " + series);
            System.out.println("floor:   " + floor);
            System.out.println("place:   " + place);
            System.out.println("rooms:   " + rooms);
            System.out.println("discription:   " + discription);
            System.out.println("infoRows:           " + infoRows.size());
            System.out.println("");
            System.out.println("");

            if(!(price.equals("")& phoneNumber.equals("") & rooms.equals("") & series.equals("") & place.equals("")
                    & floor.equals("") & createdDate.equals("") & updatedDate.equals("") & views.equals("") & discription.equals("")))
            flats.add(new Flat(price, phoneNumber, rooms, series, place, floor, createdDate,updatedDate, views, discription));

            System.out.println("rows: " + flats.size());
        }

    }


    public String dateForm(String dateText){

        String mainDate = dateText;

        if(logicHelper.check(dateText,"часов")
                ||logicHelper.check(dateText, "минуты")
                ||logicHelper.check(dateText, "минуту")
                ||logicHelper.check(dateText, "минут")
                ||logicHelper.check(dateText, "час")
                ||logicHelper.check(dateText, "секунд")){
            mainDate = date.getDate(0);
        }

        if(logicHelper.check(dateText,"месяцев")
                ||logicHelper.check(dateText, "месяца")
                ||logicHelper.check(dateText, "месяц") ){
            int numberInDateText = Integer.parseInt(logicHelper.extractDigits(dateText));
            int month = 30;
            int amount = numberInDateText - month;
            mainDate = date.getDate(-amount);
        }

        if(logicHelper.check(dateText,"день")
                ||logicHelper.check(dateText, "дней")
                ||logicHelper.check(dateText, "дня") ){
            int numberInDateText = Integer.parseInt(logicHelper.extractDigits(dateText));
            mainDate = date.getDate(-numberInDateText);
        }


        return mainDate;
    }


}
