package websites.stroka.doska;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import websites.stroka.Parser;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.ParseException;

public class DoskaKg {

    public DoskaKg() throws IOException, ParseException {


        try{
            Document document = (Document) Jsoup.connect("https://doska.kg/cat:117/&type=2").get();
            Element tableVerb = document.getElementsByAttributeValue("class", "doska_last_items_list").first();
            System.out.println("Started class.Doska");

            new ParseDoska(tableVerb);
        }catch(SocketTimeoutException ignored){

        }



    }


}
