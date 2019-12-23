package helpers;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class API {
    public static ArrayList<String> getFromAPI(String isbn) {
        String url = String.format("https://isbnsearch.org/isbn/%s", isbn);
        ArrayList<String> data = new ArrayList<>();
        Document doc;
        try {
            doc = Jsoup.connect(url)
                    .header("Content-type", "text/html")
                    .get();
        } catch (HttpStatusException err) {
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        Element content = doc.getElementById("book");
        Elements ps = content.select("p");
        data.add(content.getElementsByTag("img").attr("src"));
        data.add(content.select("h1").text());
        for (Element p : ps) {
            String tmp = p.text();
            data.add(tmp);
        }
        return data;
    }
}
