package org.me.LovesAsuna.util;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author LovesAsuna
 * @date 2020/4/19 14:08
 */

public class NetWorkUtil {
    public static Tuple<InputStream, Integer> fetch(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.163 Safari/537.36");

            conn.connect();
            InputStream inputStream = conn.getInputStream();
            int length = conn.getContentLength();
            return new Tuple<>(inputStream, length);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
