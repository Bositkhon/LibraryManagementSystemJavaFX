package helpers;

import models.BookByISBN;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class API {
    public static BookByISBN getFromAPI(String url) throws IOException {
//        String url = "https://isbnsearch.org/isbn/"+isbn;
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
        Elements img = content.getElementsByTag("img");
        Elements ps = content.select("p");
        ArrayList<String> datas = new ArrayList<String>();
        for (Element p : ps){
            String tmp = p.text();
            int k = tmp.indexOf(":");
            tmp = tmp.substring(k+1);
            tmp = tmp.trim();
            datas.add(tmp);
        }

        BookByISBN book = new BookByISBN();
        book.setTitle(content.select("h1").text());
        book.setImgLink(content.getElementsByTag("img").attr("src"));
//        book.setISBN_13(datas.get(0));
//        book.setISBN_10(datas.get(1));
//        book.setAuthor(datas.get(2));
//        book.setEdition(datas.get(3));
//        book.setBinding(datas.get(4));
//        book.setPublisher(datas.get(5));
//        book.setPublished(datas.get(6));
//        book.setPrice(datas.get(7));

        return book;
    }
}
