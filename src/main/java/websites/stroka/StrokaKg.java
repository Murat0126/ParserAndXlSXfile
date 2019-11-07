package websites.stroka;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.text.ParseException;

public class StrokaKg {



    public StrokaKg() throws IOException, ParseException {

        Document document = (Document) Jsoup.connect("http://stroka.kg").get();
        Element tableVerb = document.getElementsByAttributeValue("class", "topics-list").first();
        System.out.println("Started class.Stroka");

        new Parser(tableVerb);

    }



}
