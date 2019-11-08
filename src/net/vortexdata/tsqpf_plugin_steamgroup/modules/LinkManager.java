package net.vortexdata.tsqpf_plugin_steamgroup.modules;

import net.vortexdata.tsqpf_plugin_steamgroup.exceptions.*;
import net.vortexdata.tsqpf_plugin_steamgroup.webutils.*;

import java.io.*;
import java.util.*;

public class LinkManager {

    String pluginPath = "plugins//SteamGroup//";
    Thread linkRemoverTask;
    private String groupUrl = "";
    private WebCrawler webCrawler;
    private String steamGroupUrl;

    public LinkManager(String groupUrl) {
        linkRemoverTask = new Thread(new TempLinkRemoverThread(pluginPath, this));
        linkRemoverTask.start();
        this.groupUrl = groupUrl;
        this.webCrawler = new WebCrawler();

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(pluginPath + "templinks.txt", false));
            bw.write("");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LinkManager(String groupUrl, int customTempLinkRemoverInterval) {
        linkRemoverTask = new Thread(new TempLinkRemoverThread(pluginPath, this, customTempLinkRemoverInterval));
        linkRemoverTask.start();
        this.groupUrl = groupUrl;
        this.webCrawler = new WebCrawler();

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(pluginPath + "templinks.txt", false));
            bw.write("");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean removeLink(String url) {
        BufferedReader br = null;
        BufferedWriter bw = null;
        ArrayList<String> validLinks = new ArrayList<>();

        try {
            br = new BufferedReader(new FileReader(pluginPath + "templinks.txt"));
            bw = new BufferedWriter(new FileWriter(pluginPath + "templinks.txt", true));

            String currentLine = "default";
            while (currentLine != null && !currentLine.isEmpty()) {
                currentLine = br.readLine();
                if (currentLine == null || currentLine.isEmpty())
                    break;

                String[] link = currentLine.split(";");
                if (link[0].equalsIgnoreCase(url)) {
                    // Skip line
                } else {
                    validLinks.add(currentLine);
                }
            }

            BufferedWriter bwr = new BufferedWriter(new FileWriter(pluginPath + "templinks.txt"));
            bwr.write("");
            for (String line : validLinks) {
                System.out.println("Writing line");
                bw.write(line);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean storeLink(String url, String pin) {

        try {
            getPin(url);
            return false;
        } catch (TempLinkNotFoundException e) {
            FileWriter fw = null;
            try {
                fw = new FileWriter(pluginPath + "templinks.txt", true);
                fw.write(url + ";" + pin + ";" + System.currentTimeMillis()+ "\n");
                fw.flush();
                fw.close();
                System.out.println("Stored");
                return true;
            } catch (IOException g) {
                // Handle
            } finally {
                if (fw != null) {
                    try {
                        fw.close();
                    } catch (IOException g) {
                        g.printStackTrace();
                    }
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
                    return cLine.split(";")[1];
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

        if (webCrawler.getPageElement(PageElement.GROUPS, url + "/groups/").toString().contains(groupUrl))
            return true;

        return false;
    }

}
