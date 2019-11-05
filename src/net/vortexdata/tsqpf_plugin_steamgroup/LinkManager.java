package net.vortexdata.tsqpf_plugin_steamgroup;

import com.github.theholywaffle.teamspeak3.*;
import net.vortexdata.tsqpf.plugins.*;
import net.vortexdata.tsqpf_plugin_steamgroup.exceptions.*;

import java.io.*;

public class LinkManager {

    String pluginPath = "plugins//SteamGroup//";
    Thread linkRemoverTask;
    private String groupUrl = "";
    private WebCrawler webCrawler;
    private PluginConfig config;

    public LinkManager(String groupUrl, PluginConfig config) {
        linkRemoverTask = new Thread(new TempLinkRemoverThread(pluginPath));
        linkRemoverTask.start();
        this.groupUrl = groupUrl;
        this.webCrawler = new WebCrawler();
        this.config = config;
    }

    public boolean storeLink(String url, int pin) {

        FileWriter fw = null;
        try {
            fw = new FileWriter(pluginPath + "templinks.txt", true);
            fw.write(url + ";" + pin + ";" + System.currentTimeMillis());
            fw.flush();
            fw.close();
            return true;
        } catch (IOException e) {
            // Handle
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    public String getPin(String url) throws TempLinkNotFoundException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(pluginPath + "templinks.txt"));
            while (br.ready()) {
                String cLine = br.readLine();
                if (cLine.split(";")[0].equalsIgnoreCase(url))
                    return cLine.split(";")[2];
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new TempLinkNotFoundException();
    }

    public boolean verifyLink(String url) {

        try {
            if (!webCrawler.getPageElement(PageElement.SUMMARY, url).toString().contains(getPin(url)))
                return false;
        } catch (TempLinkNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        if (webCrawler.getPageElement(PageElement.GROUPS, url + "/groups/").toString().contains(config.readValue("steamGroupUrl")))
            return true;

        return false;
    }

}
