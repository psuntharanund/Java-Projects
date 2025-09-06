import java.io.*;
import java.util.*;
import java.net.*;

public interface HttpClientAdapter{

    public HttpURLConnection connectHTTP(URL url)throws IOException;

}
