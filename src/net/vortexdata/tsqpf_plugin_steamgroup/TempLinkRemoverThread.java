package net.vortexdata.tsqpf_plugin_steamgroup;

import java.io.*;
import java.util.ArrayList;

public class TempLinkRemoverThread implements Runnable {

    String path;

    public TempLinkRemoverThread(String path) {
        this.path = path;
    }

    @Override
    public void run() {

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
                if (Integer.parseInt(link[2]) < System.currentTimeMillis() - 600000) {
                    // Delete link
                } else {
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

        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {

        }

    }

}
