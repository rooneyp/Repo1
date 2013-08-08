package com.rooney.Mess;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.junit.Test;

public class StringParse {

	@Test
	public void testURIQueryString() throws MalformedURLException, URISyntaxException {
		String uri1 = "socket://localhost:11300?field1=value1&field2=value2&field3=value3";
		URI aURI = new URI(uri1);
		
		Map<String, String> queryMap = new HashMap<String, String>();
		for (NameValuePair param : URLEncodedUtils.parse(aURI, "UTF-8")) {
            queryMap.put(param.getName(), param.getValue());
        }
		
//		URL.setURLStreamHandlerFactory(fac)
		

//		System.out.println("protocol = " + aURL.getProtocol());
		System.out.println("scheme = " + aURI.getScheme());
//		System.out.println("authority = " + aURL.getAuthority());
		System.out.println("host = " + aURI.getHost());
		System.out.println("port = " + aURI.getPort());
		System.out.println("path = " + aURI.getPath());
		System.out.println("query = " + aURI.getQuery());
		
		System.out.println(queryMap);
//		System.out.println("filename = " + aURL.getFile());
//		System.out.println("ref = " + aURL.getRef());
	}
	
	
    void testStringArray() {
        String[] foo = { getA(), getA() };
    }

    private String getA() {
        return "A";
    }

    public static void testSplit() {
        String input = "A,B,C,D";

        String[] expectedOrder = { "A", "B", "C", "D" };

        String[] splitString = input.split(",");

        assertEquals(expectedOrder.length, splitString.length);

        for (int i = 0; i < expectedOrder.length; i++) {
            assertEquals(expectedOrder[i], splitString[i]);
        }
    }

}
