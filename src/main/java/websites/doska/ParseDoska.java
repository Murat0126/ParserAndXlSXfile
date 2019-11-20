package websites.doska;

import converter.Date;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class ParseDoska {

    private List<List<String>> rows = new ArrayList<List<String>>();

    private static List<Flat> flats =  new ArrayList<>();
    int count;
    private LogicHelper logicHelper = new LogicHelper();



    public ParseDoska(MainFrame mainFrame) throws IOException {
        System.out.print("Started doska.class.Parser");
        JTextField pagesOfDoska = mainFrame.getPagesOfDoska();
        int pagesNumber = Integer.parseInt(pagesOfDoska.getText());
        for(int i = 1; i <=pagesNumber; i++){
                Document document = (Document) Jsoup.connect("https://doska.kg/cat:117/&type=2&region=1&page="+i).get();
                Element element = document.getElementsByAttributeValue("class", "doska_last_items_list").first();

                parsing(element);
        }

        JTextPane textPane = mainFrame.getTextPane();

        textPane.setText("Загрузка данных doska. Завершена");


        new XLSFile("doska.kg", flats, "doska", mainFrame);



    }


    private void parsing(Element element){

        System.out.println("Started method.parsing()");
        Elements elements = element.select("div");

//        System.out.println("h1Elements: " + elements);

        for (Element e: elements){

            Elements h1Elements = e.select("a");
            String itemUrl = h1Elements.attr("href");

            String headText= "", phoneNumbers= "",views= "", updateDate= "", createDate= "", region= "", place= "",
                    rooms= "", typeOfBuilding= "", series= "", address = "", floor = "", price = "";


            if(!itemUrl.equals("")){
                try {

                    Document doc = (Document) Jsoup.connect("https://doska.kg/" + itemUrl).get();
                    Elements mainTable = doc.getElementsByAttributeValue("itemtype","http://schema.org/Place");
                    Elements itemDates = doc.getElementsByClass("col-md-9");


                    Element tableVerb = doc.getElementsByAttributeValue("title", "По курсу НБКР").first();
                    price = logicHelper.extractDigits(tableVerb.text());                                                //Стоимость

                    System.out.println("Стоимость: " + price);
                    Element textElement = doc.getElementsByAttributeValue("class", "text").first();
                    Elements fl = textElement.select("b");

                    if(fl.text().equals("Этаж:")){
                        Element div = textElement.select("div").first();
                        floor = logicHelper.replace(div.text(), "Этаж:");                                             //Этаж
                        System.out.println( "Этаж:" + floor);
                    }else System.out.println( "не указано");

                    Element addressElement = doc.getElementsByAttributeValue("itemprop", "streetAddress").first();         //Адрес
                    System.out.println( "streetAddress:" + address);

                    Elements tableItems = mainTable.select("div");
                    for (Element element1: tableItems) {


                        Elements bodyofType = element1.select("div");
                        Elements myDiv = new Elements();
                        if(bodyofType.equals(tableItems));
                        else myDiv = element1.select("div");

                        if(myDiv.select("b").text().equals("Серия:")){
                            series = logicHelper.replace(myDiv.text(),"Серия:");                          //Серия
                            System.out.println("Серия: " + series);

                        }
                        if(myDiv.select("b").text().equals("Тип здания:")) {

                            typeOfBuilding = logicHelper.replace(myDiv.text(), "Тип здания:");            //Тип здания
                            System.out.println("Тип здания: " + typeOfBuilding);

                        }

                        if(myDiv.select("b").text().equals("Кол - во комнат:")) {
                            rooms = myDiv.select("a").text();            //Кол-во комнат
                            System.out.println("Кол-во комнат: " + rooms);
                        }

                        if(myDiv.select("b").text().equals("Общ . площадь:")) {

//                            place = replace(myDiv.text(), "Общ . площадь:");                   // Площадь
                            place = logicHelper.extractDigits(myDiv.text());                   // Площадь
                            System.out.println("Площадь: " + place);

                        }

                        if(myDiv.select("b").text().equals("Регион:")) {

                            region = logicHelper.replace(myDiv.text(), "Регион:");                           // Регион
                            System.out.println("region: " + region);

                        }

                        Elements data = doc.getElementsByAttributeValue("class", "col-md-6 text fs14");         // Добавлено дата
                        createDate = logicHelper.replace(data.get(1).text(), "Добавлено:");
                        if(logicHelper.getFirstWord(createDate, " Вчера").equals(" Вчера")){
                            createDate = new Date().getDate(-1);
                        } else if(logicHelper.getFirstWord(createDate, " Сегодня").equals(" Сегодня"))
                            createDate = new Date().getDate(0);


                        Elements updated = doc.getElementsByAttributeValue("class", "col-md-6 text fs14").next(); // Поднято. Обновлено дата
                        updateDate = logicHelper.replace(updated.text(), "Поднято:");
                        if(logicHelper.getFirstWord(updateDate, " Сегодня").equals(" Сегодня")){
                            updateDate = new Date().getDate(0);
                        }else if(logicHelper.getFirstWord(updateDate, " Вчера").equals(" Вчера"))
                            updateDate = new Date().getDate(-1);


                        Elements elementViews = doc.getElementsByAttributeValue("class", "text fs14");
                        views = logicHelper.replace(elementViews.get(1).text(), "Просмотров:");                   // Просмотры


                        Elements elementPhoneUrl = elementViews.select("img");
                        String phoneBase64 = elementPhoneUrl.attr("src");



//                        SaveImageFromUrl saveImageFromUrl = new SaveImageFromUrl();
//                        String destinationFile = "image.png";
//                        saveImageFromUrl.saveImage(phoneUrl, destinationFile);
//
//                        TextFromImage textFromImage = new TextFromImage();
//                        String phoneNumber = textFromImage.getImgText();

                        Elements elementDiscription = doc.getElementsByAttributeValue("itemprop", "description");
                        headText = elementDiscription.text();                         // Описание


                        System.out.println("createdDate            : " + createDate );
                        System.out.println("updateDate            : " + updateDate );
                        LocalDate date = LocalDate.now(ZoneId.of("Asia/Bishkek"));
                        System.out.println("elementViews            : " + views );
                        System.out.println("elementDiscription            : " + headText );
                        System.out.println("phoneUrl            : " + phoneBase64 );
                        System.out.println("");
//                        System.out.println("phoneNumber            : " + phoneNumber );

                        System.out.println("");
                        System.out.println("");
                        System.out.println("");


                    }

                }catch (IOException | NullPointerException ignored){}

                if(!(price.equals("") & rooms.equals("") & series.equals("") & place.equals("")
                        & floor.equals("") & createDate.equals("") & updateDate.equals("") & views.equals("") & headText.equals("")))
                    flats.add(new Flat(price, phoneNumbers, rooms, series, place, floor,createDate,updateDate,views, headText));

//                textPane.setText(textPane.getText() + "\n" + price + "       " + rooms + "       " + series+ "       " + place+
//                        "       " + floor + "           " + createDate+ "        "+ updateDate+
//                        "                          " +phoneNumbers+ "        " + headText+ "\n");


//                MainFrame mainFrame = new MainFrame();
//                JTextField textField = mainFrame.getTextField();
//                textField.setText(price + "       " + rooms + "       " + series+ "       " + place+ "       " + floor + "           " + createDate+ "        "+ updateDate+ "                          " +phoneNumbers+ "        " + headText);
//                System.out.println(price + "       " + rooms + "       " + series+ "       " + place+ "       " + floor + "           " + createDate+ "        "+ updateDate+ "                          " +phoneNumbers+ "        " + headText);
            }

        }



    }






}
