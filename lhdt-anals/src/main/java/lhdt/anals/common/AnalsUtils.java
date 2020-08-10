/**
 * 
 */
package lhdt.anals.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;

import dev.hyunlab.core.util.PpDateUtil;
import dev.hyunlab.core.util.PpUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 이것저것 유틸리티
 * @author gravity@daumsoft.com
 *
 */
@Slf4j
public class AnalsUtils extends PpUtil{
	
	/**
	 * unique한  long값 생성 & 리턴
	 * @return
	 */
	public static Long getUniqueLong() {
		return (new Date()).getTime();
	}
	
	/**
	 * result map 생성
	 * @return
	 */
	public static Map<String,Object> createResultMap(){
		return createResultMap(HttpStatus.OK, null, null);
	}
	
	/**
	 * result map 생성
	 * @param statusCode
	 * @param errorCode
	 * @param message
	 * @return
	 */
	public static Map<String,Object> createResultMap(HttpStatus statusCode, String errorCode, String message){
		Map<String,Object> map = new HashMap<>();
		
		//
		map.put(AnalsConst.STATUS_CODE, statusCode);
		map.put(AnalsConst.ERROR_CODE, errorCode);
		map.put(AnalsConst.MESSAGE, message);
		
		//
		return map;
	}
	
	
	
	/**
	 * @see lhdt.anals.common.AnalsUtils.doGet(String, Map<String, Object>, Map<String, Object>)
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String doGet(String url) throws Exception {
		return doGet(url, null, null);
	}
	
	/**
	 * @see lhdt.anals.common.AnalsUtils.doGet(String, Map<String, Object>, Map<String, Object>)
	 * @param url
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static String doGet(String url, Map<String,Object> param) throws Exception {
		return doGet(url, param, null);
	}
	

	/**
	 * http(s)를 이용해 get 방식으로 요청
	 * @param url
	 * @param param key/value
	 * @param requestProperty 헤더 key/value
	 * @return 리턴받은 값
	 * @throws Exception 
	 * @history
	 * 	1002 add requestProperty
	 */
	public static String doGet(String url, Map<String,Object> param, Map<String, Object> requestProperty) throws Exception {
		log.info(">>.doGet - {} {} {}", url, param, requestProperty);
		
		//
		if(url.toLowerCase().startsWith("https")) {
			return httpsGet(url, param, requestProperty);
		}
		
		//
		System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");

		try {
			String fullUrl = getFullUrl(url, param);			
			
			//
			URL url2 = new URL(fullUrl);
			HttpURLConnection conn = null;
			conn = (HttpURLConnection) url2.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(1000000);
			conn.setReadTimeout(1000000);
			
			//
			setRequestProperty(conn, requestProperty);
			
			//
			conn.connect();

			//
			return getResponseData(conn);

		} catch (Exception e) {
			throw e;
		}
	}
	

	/**
	 * https get방식으로 호출
	 * @param url
	 * @param param
	 * @param requestProperty
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @since	20191111
	 */
	private static String httpsGet(String url, Map<String,Object> param, Map<String,Object> requestProperty) throws IOException, NoSuchAlgorithmException, KeyManagementException {
		
		//
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			
			public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
				// TODO Auto-generated method stub
				
			}
			
			public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
				// TODO Auto-generated method stub
				
			}
			
			public X509Certificate[] getAcceptedIssuers() {
				// TODO Auto-generated method stub
				return null;
			}
		}};
		
		//
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		
		//
		String fullUrl = getFullUrl(url, param);
		
		//
		log.debug(".httpsGet - {}", fullUrl);
		

		//
		HttpsURLConnection conn = (HttpsURLConnection)new URL(fullUrl).openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(1000000);
		conn.setReadTimeout(1000000);
		
		//
		setRequestProperty(conn, requestProperty);		
		
		//
		conn.connect();
		conn.setInstanceFollowRedirects(true);
		
		//
		return getResponseData(conn);
	}
	
	
	/**
	 * url뒤에 파라미터 추가하여 full url생성
	 * @param url
	 * @param param
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static String getFullUrl(String url, Map<String,Object> param) throws UnsupportedEncodingException {
		//
		String fullUrl = url + "?_" + PpDateUtil.getYmdhms();
		
		//
		if(null != param && 0 < param.size()) {
			for(Map.Entry<String, Object> d : param.entrySet()) {
				fullUrl += "&" + d.getKey() + "=" + URLEncoder.encode(""+d.getValue(), "UTF-8");
			}
		}
		
		//
		return fullUrl;
	}
	
	
	
	/**
	 * response에서 데이터(문자열) 추출
	 * @param conn
	 * @return
	 * @throws IOException
	 */
	private static String getResponseData(HttpURLConnection conn) throws IOException {
		String data = IOUtils.toString(conn.getInputStream(), StandardCharsets.UTF_8);
		
		//
		if (200 == conn.getResponseCode()) {
			return data;
		} 

		//
		throw new RuntimeException(data);
	}
	
	
	/**
	 * conn에 request property 추가
	 * @param conn
	 * @param requestProperty
	 */
	private static void setRequestProperty(HttpURLConnection conn, Map<String,Object> requestProperty) {
		if(AnalsUtils.isEmpty(requestProperty)) {
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3851.0 Safari/537.36 Edg/77.0.223.0");
			return;
		}
		
		//
		for(Map.Entry<String,Object> d : requestProperty.entrySet()) {
			conn.setRequestProperty(d.getKey(), d.getValue().toString());
		}
		
	}
}
