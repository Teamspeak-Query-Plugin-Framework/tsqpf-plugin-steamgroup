package net.vortexdata.tsqpf_plugin_steamgroup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;

public class WebCrawler {

    public WebCrawler() {

    }

    public Element getPageElement(PageElement pageElement, String url) {

        try {
            Document document = Jsoup.connect(url).get();

            if (pageElement == PageElement.SUMMARY) {
                return document.select("div.profile_summary").first();
            } else if (pageElement == PageElement.GROUPS) {
                return document.select("#search_results").first();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}