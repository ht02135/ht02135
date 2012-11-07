package com.hung.experiment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.hung.auction.jaxbdomain.JaxbSeller;

public class RestfulAuctionClient 
{
	private static Logger log = Logger.getLogger(RestfulAuctionClient.class);
	
    public static void main( String[] args )
    {
    	try {

        	ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-swing.xml");
        	RestTemplate restTemplate = (RestTemplate) applicationContext.getBean("restTemplate");
        	
        	JaxbSeller seller = new JaxbSeller("An Chih Tsai", "ht02135@yahoo.com");
        	seller = createSeller(restTemplate, seller);
        	
        	// notice that i created seller without id info, but hibernate populate the id field after persistence
        	JaxbSeller seller2 = getSeller(restTemplate, seller.getId());
        	log.info("get persisted seller2="+seller2);
        	
        	JaxbSeller jaxbSeller = getSellerEntityByJaxb(restTemplate, seller.getId());
        	log.info("get persisted jaxbSeller="+jaxbSeller);
        	
        	JaxbSeller jsonSeller = getSellerEntityByJson(restTemplate, seller.getId());
        	log.info("get persisted jsonSeller="+jsonSeller);
        	
        	// display seller in application/json format
        	// {"id":5,"name":"An Chih Tsai","email":"ht02135@yahoo.com"}
        	displaySeller("application/json", seller.getId());
        	
        	// display seller in json application/xml format
        	// <?xml version="1.0" encoding="UTF-8" standalone="yes"?><JaxbSeller id="5"><email>ht02135@yahoo.com</email><name>An Chih Tsai</name></JaxbSeller>
        	displaySeller("application/xml", seller.getId());
        	
    	} catch (RestClientException rce) {
    		rce.printStackTrace();
    	}
    }
    
    public static JaxbSeller getSeller(RestTemplate restTemplate, Integer id) {
    	return getSellerEntityByJaxb(restTemplate, id);
    }
    
    public static JaxbSeller getSellerEntityByJaxb(RestTemplate restTemplate, Integer id) {
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_XML);
    	HttpEntity<String> entity = new HttpEntity<String>(headers);
    	ResponseEntity<JaxbSeller> response = restTemplate.exchange(
    		"http://localhost:8081/simple-restfulwebapp-module/auction/sellers/{id}", 
    		HttpMethod.GET, entity, JaxbSeller.class, id);
    	return response.getBody();
    }
    
    public static JaxbSeller getSellerEntityByJson(RestTemplate restTemplate, Integer id) {
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	HttpEntity<String> entity = new HttpEntity<String>(headers);
    	ResponseEntity<JaxbSeller> response = restTemplate.exchange(
    		"http://localhost:8081/simple-restfulwebapp-module/auction/sellers/{id}", 
    		HttpMethod.GET, entity, JaxbSeller.class, id);
    	return response.getBody();
    }
    
    public static void displaySeller(String mediaType, Integer id) {
		try {
			URL url = new URL("http://localhost:8081/simple-restfulwebapp-module/auction/sellers/"+id);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", mediaType);
			if (conn.getResponseCode() == 200) {
				log.info("getSeller: Success : HTTP code :" + conn.getResponseCode());
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
				String line = null;
				while ((line = br.readLine()) != null) {
					log.info(line);
				}
				conn.disconnect();
			} else {
				log.info("getSeller: Failed : HTTP error code :" + conn.getResponseCode());
			}
		} catch (MalformedURLException mue) {
			log.info("getSeller: mue="+mue.toString());
		} catch (IOException ioe) {
			log.info("getSeller: ioe="+ioe.toString());
		}
    }
    
    public static JaxbSeller createSeller(RestTemplate restTemplate, JaxbSeller seller) {
    	return restTemplate.postForObject(
            "http://localhost:8081/simple-restfulwebapp-module/auction/sellers", seller,  JaxbSeller.class);
    }
}
