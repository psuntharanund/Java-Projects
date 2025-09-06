import java.util.concurrent.*;
import java.nio.file.*;
import java.util.*;
import java.io.*;
import java.net.*;

public class Runner{
    
    private static final HttpClientAdapter hca = new Downloader();
    private static final Downloader dl = new Downloader();

    public static void main(String[] args) throws Exception{
        URL url = new URL("https://www.irs.gov/pub/irs-pdf/fw4.pdf");
        byte[] pdf = fetchPdfBytes(url);

        List<Path> outs = List.of(
            Path.of("w4-1.pdf"),
            Path.of("w4-2.pdf"),
            Path.of("w4-3.pdf"),
            Path.of("w4-4.pdf")
        );

        ExecutorService pool = Executors.newFixedThreadPool(4);
        for (Path out : outs){
            pool.submit(() -> Files.write(out, pdf));
        }
        pool.shutdown();
        pool.awaitTermination(30, TimeUnit.SECONDS);
        System.out.println("Saved as: " + outs);
    }

    private static byte[] fetchPdfBytes(URL url) throws IOException {
        var c = hca.connectHTTP(url);
            try{
                try(
                     var in = c.getInputStream();
                     var baos = new ByteArrayOutputStream(1 << 20)){
                    
                    byte[] head = in.readNBytes(5);
                    if (!(head.length == 5 && head[0]=='%' && head[1]=='P' && head[2]=='D' && head[3]=='F' && head[4]=='-')) {
                            String preview = new String(head) + new String(in.readNBytes(150));
                            throw new IOException("Not a PDF. Starts with: " + preview);
                    }
                    baos.write(head);
                    in.transferTo(baos);
                    return baos.toByteArray();
                }
            } finally{
                c.disconnect();
        }
    }
}
