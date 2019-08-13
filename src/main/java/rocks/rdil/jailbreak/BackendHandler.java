package rocks.rdil.jailbreak;

import cc.hyperium.Hyperium;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BackendHandler {
    public static HttpClient httpclient = HttpClients.createDefault();

    public BackendHandler() {}

    public void apiRequest(String url) {
        try {
            httpclient.execute(generate("https://backend.rdil.rocks/" + url));
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public boolean apiUpdateCheck() {
        try {
            // Execute and get the response.
            return !Objects.equals(EntityUtils.toString(
                    httpclient.execute(generate("https://backend.rdil.rocks/checkUpdate")).getEntity(), "UTF-8"
            ), Hyperium.version);
        } catch (IOException e) {
            return false;
        }
    }

    public static HttpPost generate(String url) {
        HttpPost tmp = new HttpPost(url);
        tmp.setHeader("User-agent", "HyperiumJailbreak");
        List<NameValuePair> params = new ArrayList<NameValuePair>(0);
        try {
            tmp.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return tmp;
    }
}
