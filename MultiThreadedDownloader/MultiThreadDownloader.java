import java.io.*;
import java.net.*;
import java.nio.file.*;

public class MultiThreadDownloader {

    public static void main(String[] args){
        String fileURL = "https://en.wikipedia.org/robots.txt";
        Path savePath = Path.of("sample.txt");
        Runnable runnable = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " is running. Download started. . .");
            try {
                URL url = new URL(fileURL);
                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
                c.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; OhmFileTester/1.0; +https://github.com/psuntharanund/Java-Projects)");
                c.setRequestProperty("Accept", "*/*");
            
                try (InputStream in = c.getInputStream()){
                    Files.copy(in, savePath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Download complete: " + savePath.toAbsolutePath());
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        };
        Thread runThread = new Thread(runnable, "File Tester Thread");
        runThread.start();
    }
}
