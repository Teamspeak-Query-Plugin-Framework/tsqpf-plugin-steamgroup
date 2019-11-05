package net.vortexdata.tsqpf_plugin_steamgroup;

import java.io.*;
import java.util.ArrayList;

public class TempLinkRemoverThread implements Runnable {

    String path;
    private int sleep;

    public TempLinkRemoverThread(String path, int interval) {
        this.path = path;
        this.sleep = interval;
    }

    public TempLinkRemoverThread(String path) {
        this.path = path;
        this.sleep = 60000;
    }

    @Override
    public void run() {

        try {
            Thread.sleep(sleep);
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
                if (Long.parseLong(link[2]) < System.currentTimeMillis() - 5000) {
                    System.out.println("Skipped line");
                } else {
                    validLinks.add(currentLine);
                    System.out.println("Saved line");
                }
            }

            BufferedWriter bwr = new BufferedWriter(new FileWriter(path + "templinks.txt"));
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

    }

    public void setSleep(int milliseconds) {
        this.sleep = milliseconds;
    }

}
