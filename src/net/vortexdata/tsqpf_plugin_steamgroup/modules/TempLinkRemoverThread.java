package net.vortexdata.tsqpf_plugin_steamgroup.modules;

import java.io.*;
import java.util.ArrayList;

public class TempLinkRemoverThread implements Runnable {

    private String path;
    private int sleep;
    private LinkManager linkManager;

    public TempLinkRemoverThread(String path, LinkManager linkManager, int interval) {
        this.path = path;
        this.sleep = interval;
        this.linkManager = linkManager;
    }

    public TempLinkRemoverThread(String path, LinkManager linkManager) {
        this.path = path;
        this.sleep = 600;
        this.linkManager = linkManager;
    }

    @Override
    public void run() {

        try {
            Thread.sleep(sleep * 1000);
        } catch (InterruptedException e) {

        }

        BufferedReader br = null;
        BufferedWriter bw = null;
        ArrayList<String> validLinks = new ArrayList<>();

        try {
            br = new BufferedReader(new FileReader(path + "templinks.txt"));
            bw = new BufferedWriter(new FileWriter(path + "templinks.txt", true));

            String currentLine = "default";
            while (currentLine != null && !currentLine.isEmpty()) {
                currentLine = br.readLine();
                if (currentLine == null || currentLine.isEmpty())
                    break;

                String[] link = currentLine.split(";");
                if (Long.parseLong(link[2]) > System.currentTimeMillis() - sleep) {
                    validLinks.add(currentLine);
                }
            }

            BufferedWriter bwr = new BufferedWriter(new FileWriter(path + "templinks.txt"));
            bwr.write("");
            for (String line : validLinks) {
                bw.write(line);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
