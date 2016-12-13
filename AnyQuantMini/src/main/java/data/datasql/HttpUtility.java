package data.datasql;

import org.apache.commons.codec.EncoderException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;


//创建http client
public class HttpUtility {
	private static final String ACCESS_TOKEN = "958040b380d30f50154c7b5ae423a964a9e786cbc9f16fdabbdfdfbc9ed4ea79";
	private static StockInfoDataImp stockInfoDataImp=null;
	private static CloseableHttpClient httpClient = createHttpsClient();
	public HttpUtility() {
		// TODO Auto-generated constructor stub
		stockInfoDataImp=new StockInfoDataImp();
	}
	public static void addAllStockInfo(){
		ArrayList<String> allIds=stockInfoDataImp.getAllStockId();
		for(String temp:allIds){
			try {
				addStockInfo(temp);
			} catch (IOException | EncoderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void addStockInfo(String id) throws IOException, EncoderException {
		// assert(id.length()==8):("The stock id length is wrong!");
		if (id.length() == 8 && id.charAt(0) == 's') {
			String urlId = id.substring(2);
			// 根据api store页面上实际的api url来发送get请求，获取数据
			String url = "https://api.wmcloud.com:443/data/v1/api/equity/getEqu.json?ticker=" + urlId + "&equTypeCD=A";
			// String
			// url="https://api.wmcloud.com:443/data/v1/api/equity/getEqu.json?ticker=000001&equTypeCD=A";

			HttpGet httpGet = new HttpGet(url);
			// 在header里加入 Bearer {token}，添加认证的token，并执行get请求获取json数据
			httpGet.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
			CloseableHttpResponse response = httpClient.execute(httpGet);

			HttpEntity entity = response.getEntity();
			String body = EntityUtils.toString(entity);
			System.out.println(body);
			handleString(body, id);
		}
	}

	public static void handleString(String str, String id) throws IOException {
		JSONObject jsonObject = new JSONObject(str);
		JSONArray childs = jsonObject.getJSONArray("data");
		JSONObject object = childs.getJSONObject(0);
		String secFullName = object.getString("secFullName");
		String officeAddr = object.getString("officeAddr");
		String primeOperation= object.getString("primeOperating");
		stockInfoDataImp.addStockInfo(id, secFullName, officeAddr, primeOperation);
		System.out.println("add "+id+" stockinfo success!");
//		System.out.println(
//				"secFullName:" + secFullName + "\nofficeAddr:" + officeAddr + "\nprimeOperation:" + primeOperating);
	}

	// 创建http client
	@SuppressWarnings("deprecation")
	public static CloseableHttpClient createHttpsClient() {
		X509TrustManager x509mgr = new X509TrustManager() {
			@Override
			public void checkClientTrusted(X509Certificate[] xcs, String string) {
			}

			@Override
			public void checkServerTrusted(X509Certificate[] xcs, String string) {
			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		// 因为java客户端要进行安全证书的认证，这里我们设置ALLOW_ALL_HOSTNAME_VERIFIER来跳过认证，否则将报错
		SSLConnectionSocketFactory sslsf = null;
		try {
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, new TrustManager[] { x509mgr }, null);
			sslsf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return HttpClients.custom().setSSLSocketFactory(sslsf).build();
	}
	
	
	public static void main(String[] args) {
		HttpUtility httpUtility=new HttpUtility();
		addAllStockInfo();
	}
}
