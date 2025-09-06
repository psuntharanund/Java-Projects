import java.io.*;
import java.net.*;
import java.nio.*;

public class Downloader implements HttpClientAdapter {

    @Override
    public HttpURLConnection connectHTTP(URL url)throws IOException{
        for (int i = 0; i < 7; i++) {
            var c = (HttpURLConnection) url.openConnection();
            c.setInstanceFollowRedirects(false);
            c.setRequestMethod("GET");
            c.setRequestProperty("User-Agent",
                "Mozilla/5.0 (compatible; OhmFileTester/1.0; +https://github.com/psuntharanund/Java-Projects)");
            c.setRequestProperty("Accept", "*/*");
            c.setRequestProperty("Accept-Encoding", "identity");

            int code = c.getResponseCode();
            if (code == 301 || code == 302 || code == 303 || code == 307 || code == 308) {
                String preview = c.getHeaderField("Location");
                c.disconnect();                     // avoid leaking the previous connection
                url = new URL(url, preview);
                continue;
            }
            if (code / 100 != 2) {
                c.disconnect();
                throw new IOException("HTTP " + code);
            }
            return c;                               // return the open 2xx connection
        }
        throw new IOException("Too many redirects");
    }

    private static HttpClientAdapter hca = new Downloader();

    public static void startTask(){
        String fileURL = "https://www.irs.gov/pub/irs-pdf/fw4.pdf";
        try {
            URL url = new URL(fileURL);
            var out = java.nio.file.Path.of("sample.pdf");
            var c =  hca.connectHTTP(url); //this turns to HttpURLConnection
            
            try{ 
                try (var in = c.getInputStream()){ //this turns to InputStream 

                    byte[] head = in.readNBytes(5);
                    if (!(head.length == 5 && head[0]=='%' && head[1]=='P' && head[2]=='D' && head[3]=='F' && head[4]=='-')) {
                        String preview = new String(head) + new String(in.readNBytes(150));
                        throw new IOException("Not a PDF. Starts with: " + preview);
                      }
                      try (var os = java.nio.file.Files.newOutputStream(out,
                              java.nio.file.StandardOpenOption.CREATE,
                              java.nio.file.StandardOpenOption.TRUNCATE_EXISTING)) {
                        os.write(head);
                        in.transferTo(os);
                      }
                }
            }   finally{
                c.disconnect();
    }   System.out.println("Download complete. Saved at " 
                            + out.toAbsolutePath() + "(" 
                            + java.nio.file.Files.size(out) + "bytes)");
    }   catch (IOException e){
            e.printStackTrace();
        }
    }
}

