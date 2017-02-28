// Allen Boynton

// JAV1 - 1703

// NetConnection.java

package edu.fullsail.aboynton.boyntonallen_ce09;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

class NetConnection {

    static String getData(String urlString) {
        String data = null;

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Starting connection
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            data = IOUtils.toString(inputStream);
            inputStream.close();

            // Stopping connection
            connection.disconnect();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }
}
