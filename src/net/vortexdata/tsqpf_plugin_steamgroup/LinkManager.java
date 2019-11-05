package net.vortexdata.tsqpf_plugin_steamgroup;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LinkManager {

    String pluginPath = "plugins//SteamGroup//";
    Thread linkRemoverTask;

    public LinkManager() {
        linkRemoverTask = new Thread(new TempLinkRemoverThread());
        linkRemoverTask.start();
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

}
