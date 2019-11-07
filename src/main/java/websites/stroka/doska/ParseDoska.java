package websites.stroka.doska;

import model.Flat;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class ParseDoska {

    private List<List<String>> rows = new ArrayList<List<String>>();

    private static List<Flat> flats =  new ArrayList<Flat>();
    int count;


    public ParseDoska(Element element) throws IOException {

        System.out.print("Started doska.class.Parser");
        parsing(element);
//        for(int i = 0; i < 1; i++){
//            if(i > 0){
//                Document document = (Document) Jsoup.connect("https://doska.kg/cat:117/").get();
//                Element element1 = document.getElementsByAttributeValue("class", "title_url").first();
//                parsing(element1);
//            }
//        }

    }

    private void parsing(Element element) throws IOException {

        System.out.println("Started method.parsing()");
        Elements elements = element.select("div");

//        System.out.println("h1Elements: " + elements);

        for (Element e: elements){

            Elements h1Elements = e.select("a");
            String itemUrl = h1Elements.attr("href");

            if(!itemUrl.equals("")){
                try {

                    Document doc = (Document) Jsoup.connect("https://doska.kg/" + itemUrl).get();
                    Elements mainTable = doc.getElementsByAttributeValue("itemtype","http://schema.org/Place");
                    Elements itemDates = doc.getElementsByClass("col-md-9");


                    Element tableVerb = doc.getElementsByAttributeValue("title", "По курсу НБКР").first();
                    String price;
//                    if(tableVerb!= null)
                        price = extractDigits(tableVerb.text());                                                //Стоимость
//                    else price = "не указано";


                    System.out.println("Offers: " + price);
                    Element textElement = doc.getElementsByAttributeValue("class", "text").first();
                    Elements fl = textElement.select("b");
                    String floor;
                    if(fl.text().equals("Этаж:")){
                        Element div = textElement.select("div").first();
                        floor = replace(div.text(), "Этаж:");                                             //Этаж
                        System.out.println( "div:" + floor);
                    }else System.out.println( "не указано");


                    Element address = doc.getElementsByAttributeValue("itemprop", "streetAddress").first();         //Адрес
                    System.out.println( "streetAddress:" + address.text());


                    Elements tableItems = mainTable.select("div");
                    for (Element element1: tableItems) {
//
                        Elements bodyofType = element1.select("div");
                        Elements myDiv = new Elements();
                        if(bodyofType.equals(tableItems));
                        else{ myDiv = element1.select("div");}
                        if(myDiv.select("b").text().equals("Серия:")){
                            String series = replace(myDiv.text(),"Серия:");                          //Серия
                            System.out.println("series: " + series);

                        }
                        if(myDiv.select("b").text().equals("Тип здания:")) {

                            String typeOfBuilding = replace(myDiv.text(), "Тип здания:");            //Тип здания
                            System.out.println("typeOfBuilding: " + typeOfBuilding);

                        }

                        if(myDiv.select("b").text().equals("Кол - во комнат:")) {
                            String rooms = myDiv.select("a").text();            //Кол-во комнат
                            System.out.println("rooms: " + rooms);
                        }

                        if(myDiv.select("b").text().equals("Общ . площадь:")) {

                            String place = replace(myDiv.text(), "Общ . площадь:");                   // Площадь
                            System.out.println("typeOfBuilding: " + place);

                        }

                        if(myDiv.select("b").text().equals("Регион:")) {

                            String region = replace(myDiv.text(), "Регион:");                           // Регион
                            System.out.println("region: " + region);

                        }

                        Elements data = doc.getElementsByAttributeValue("class", "col-md-6 text fs14");         // Добавлено дата
                        String createdDate = replace(data.get(1).text(), "Добавлено:");


                        Elements updated = doc.getElementsByAttributeValue("class", "col-md-6 text fs14").next(); // Поднято. Обновлено дата
                        String updatedDate = replace(updated.text(), "Поднято:");
                        String updateDate = "";
                        if(getFirstWord(updatedDate, " Сегодня").equals(" Сегодня")){
                            LocalDate date = LocalDate.now(ZoneId.of("Asia/Bishkek"));
                            updateDate = date.toString();
                        }else{
                            updateDate = updatedDate;
                        }


                        Elements elementViews = doc.getElementsByAttributeValue("class", "text fs14");
                        String views = replace(elementViews.get(1).text(), "Просмотров:");                   // Просмотры

                        Elements elementDiscription = doc.getElementsByAttributeValue("itemprop", "description");
                        String discription = elementDiscription.text();


                        System.out.println("createdDate            : " + createdDate );
                        System.out.println("updateDate            : " + updateDate );
                        LocalDate date = LocalDate.now(ZoneId.of("Asia/Bishkek"));
                        System.out.println("elementViews            : " + views );
                        System.out.println("elementDiscription            : " + discription );

                        System.out.println("");
                        System.out.println("");
                        System.out.println("");

                    }

                }catch (IOException | NullPointerException ignored){}


////                rows.add(Arrays.asList(price, phoneNumbers, rooms, series, place, floor,createDate,updateDate, headText));
//
//                flats.add(new Flat(price, phoneNumbers, rooms, series, place, floor,createDate,updateDate, headText));
//                count = count + 1;
//                System.out.print("Строка: " + count);
//                System.out.println("rows: " + flats.size());
//
//
//
//                System.out.println(price + "       " + rooms + "       " + series+ "       " + place+ "       " + floor + "           " + createDate+ "        "+ updateDate+ "                          " +phoneNumbers+ "        " + headText);
            }

        }

//        new XLSFile("stroka.kg", flats);

    }



    private String extractDigits(String source)  {
        source = source.replaceAll("\\D+","");
        return source;
    }

    private String replace(String source, String delete)  {
        int startNum = delete.length();
        int endNum = source.length();
        /*оставить только часть текста от startNum до endNum*/
        source = source.substring(startNum, endNum);
        return source;
    }

    private String getFirstWord(String source, String delete)  {
        int endNum = delete.length();
        int startNum =0;
        /*оставить только часть текста от startNum до endNum*/
        source = source.substring(startNum, endNum);
        return source;
    }



}
