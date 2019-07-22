package more.core.Operater;

import more.core.Abstract.Api;
import more.core.Interface.HttpRequest;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpApi extends Api implements HttpRequest{

    private Logger logger = LogManager.getLogger(this.getClass().getName());

    @Override
    public void properties() {

    }

    @Override
    public String test() {
        return null;
    }

    @Override
    public String doGet(String uri) {
        CloseableHttpClient httpCilent = HttpClients.createDefault();//Creates CloseableHttpClient instance with default configuration.
        HttpGet httpGet = new HttpGet(uri);
        try {
            HttpResponse response = httpCilent.execute(httpGet);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                httpCilent.close();//释放资源
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public String doPost(String uri, Map<String, Object> mapParam) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(uri);
        RequestConfig requestConfig = RequestConfig.custom().
                setConnectTimeout(180 * 1000).setConnectionRequestTimeout(180 * 1000)
                .setSocketTimeout(180 * 1000).setRedirectsEnabled(true).build();
        httpPost.setConfig(requestConfig);

        List<NameValuePair> nvps = new ArrayList();
        for (String key : mapParam.keySet()) {
            nvps.add(new BasicNameValuePair(key, String.valueOf(mapParam.get(key))));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            logger.info("httpPost ===**********===>>> " + EntityUtils.toString(httpPost.getEntity()));
            HttpResponse response = httpClient.execute(httpPost);
            String strResult = "";
            if (response.getStatusLine().getStatusCode() == 200) {
                strResult = EntityUtils.toString(response.getEntity());
                return strResult;
            } else {
                return "Error Response: " + response.getStatusLine().toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "post failure :caused by-->" + e.getMessage();
        }finally {
            if(null != httpClient){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String doPost(String uri, String jsonParam) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(uri);
        RequestConfig requestConfig = RequestConfig.custom().
                setConnectTimeout(180 * 1000).setConnectionRequestTimeout(180 * 1000)
                .setSocketTimeout(180 * 1000).setRedirectsEnabled(true).build();

        httpPost.setConfig(requestConfig);
        httpPost.setHeader("Content-Type","application/json");  //
        try {
            httpPost.setEntity(new StringEntity(jsonParam, ContentType.create("application/json", "utf-8")));
            System.out.println("request parameters" + EntityUtils.toString(httpPost.getEntity()));
            HttpResponse response = httpClient.execute(httpPost);
            System.out.println(" code:"+response.getStatusLine().getStatusCode());
            System.out.println("doPostForInfobipUnsub response"+response.getStatusLine().toString());
            return String.valueOf(response.getStatusLine().getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            return "post failure :caused by-->" + e.getMessage();
        }finally {
            if(null != httpClient){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
