package helpers;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class API {
    public static ArrayList<String> getFromAPI(String isbn) throws IOException {
        String url = "https://isbnsearch.org/isbn/"+isbn;
        Document doc = null;
        try {
            doc = Jsoup.connect(url)
                    .header("Content-type","text/html")
                    .get();
        } catch (HttpStatusException err){
            System.out.println("404 Not Found");
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        Element content = doc.getElementById("book");
        Elements ps = content.select("p");
        ArrayList<String> datas = new ArrayList<String>();
        datas.add(content.getElementsByTag("img").attr("src"));
        datas.add(content.select("h1").text());
        for (Element p : ps){
            String tmp = p.text();
            datas.add(tmp);
        }

        return datas;
    }
}
