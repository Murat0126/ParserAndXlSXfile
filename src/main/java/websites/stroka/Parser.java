package websites.stroka;

import excel.XLSFile;
import gui.swing.view.MainFrame;
import model.Flat;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Parser {

    private List<List<String>> rows = new ArrayList<List<String>>();

    private static List<Flat> flats =  new ArrayList<Flat>();
    int count;

    public Parser(MainFrame mainFrame) throws IOException {

        Document doc = (Document) Jsoup.connect("http://stroka.kg").get();
        Element element = doc.getElementsByAttributeValue("class", "topics-list").first();

        System.out.print("Started stroka.class.Parser");
//        parsing(element);

        JTextField pagesOfStroka = mainFrame.getPagesOfStroka();
        int pagesNumber = Integer.parseInt(pagesOfStroka.getText());

        for(int i = 0; i <= pagesNumber; i++){
            if(i > 0){
                Document document = (Document) Jsoup.connect("https://stroka.kg/kupit-kvartiru/?&p=" + i + "#paginator").get();
                Element element1 = document.getElementsByAttributeValue("class", "topics-list").first();
                parsing(element1);
            }

        }

        JTextPane textPane = mainFrame.getTextPane();
        textPane.setText("Загрузка данных stroka. Завершена");


        new XLSFile("stroka.kg", flats, "StrokaKg", mainFrame);

    }

    private void parsing(Element element) throws IOException {

        System.out.println("Started method.parsing()");
        Elements h1Elements = element.select("tbody");
        System.out.println("h1Elements: " + h1Elements.size());


        for (Element e: h1Elements){
            String id = e.attr("data-id");

            if(!id.equals("")){
                Document doc = (Document) Jsoup.connect("https://stroka.kg/?page=topic-view&topic_id=" + id).get();
                org.jsoup.select.Elements title = doc.getElementsByClass("topic-view-best-topic_cost");
                org.jsoup.select.Elements phoneNumber = doc.getElementsByClass("topic-view-best-phones");
                org.jsoup.select.Elements countRooms = doc.getElementsByClass("topic-view-best-topic_rooms");
                org.jsoup.select.Elements places = doc.getElementsByClass("topic-view-best-topic_area");
                org.jsoup.select.Elements serie = doc.getElementsByClass("topic-view-best-topic_series");
                org.jsoup.select.Elements floors = doc.getElementsByClass("topic-view-best-topic_floor");
                org.jsoup.select.Elements floorOf = doc.getElementsByClass("topic-view-best-topic_floor_of");
                org.jsoup.select.Elements dateUpdate = doc.getElementsByClass("topic-view-date_list-item topic-view-topic_date_up ic_date_range_9c9c9c_24dp_2x");
                org.jsoup.select.Elements dateCreate = doc.getElementsByClass("topic-view-date_list-item topic-view-topic_date_create_row");
                org.jsoup.select.Elements viewName = doc.getElementsByClass("topic-best-view-name-parent");
                String floorof = floorOf.text();

                String  price = title.text();
                String rooms =countRooms.text();
                String phoneNumbers =phoneNumber.text().replace(',', ' ');
                String series =serie.text();
                String floor =floors.text() + "/" + floorof;
                String updateDate =  extractDigits(dateUpdate.text(), "Дата продления:");
                String createDate = extractDigits(dateCreate.text(), "Дата создания:") ;
                String headText = viewName.text().replace(',', ' ');
                String place= "";
                if (!places.text().equals("")) place = places.text();

                String views = "";




//                rows.add(Arrays.asList(price, phoneNumbers, rooms, series, place, floor,createDate,updateDate, headText));

                if(!(price.equals("") & rooms.equals("") & series.equals("") & place.equals("") & floor.equals("") & createDate.equals("") & updateDate.equals("") & headText.equals("")))
                flats.add(new Flat(price, phoneNumbers, rooms, series, place, floor,createDate,updateDate, views, headText));
                count = count + 1;
//                System.out.print("Строка: " + count);
                System.out.println("rows: " + flats.size());



//                System.out.println(price + "       " + rooms + "       " + series+ "       " + place+ "       " + floor + "           " + createDate+ "        "+ updateDate+ "                          " +phoneNumbers+ "        " + headText);
            }

        }


    }


    public String extractDigits(String source, String delete)  {
        int startNum = delete.length()+1;
        int endNum = source.length();

        source = source.substring(startNum, endNum);
        String data = source.replace('.', '-');
//        System.out.println("source: " + source);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        return data;
    }

}
