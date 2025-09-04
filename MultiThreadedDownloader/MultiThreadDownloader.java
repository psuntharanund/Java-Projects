import java.io.*;
import java.net.*;
import java.nio.file.*;

public class MultiThreadDownloader {

    public static void main(String[] args){
        String fileURL = "https://www.hq.nasa.gov/alsj/a17/A17_FlightPlan.pdf";
        Path savePath = Path.of("nasaPDF.pdf");
        Runnable runnable = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " is running. Download started. . .");
            try (InputStream in = new URL(fileURL).openStream()){
                Files.copy(in, savePath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Download complete: " + savePath.toAbsolutePath());
            } catch (IOException e){
                e.printStackTrace();
            }
        };
        Thread runThread = new Thread(runnable, "File Tester Thread");
        runThread.start();
    }
}

