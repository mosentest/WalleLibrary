package mo.http;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request {
	String url;
	String method;
	List<String> headers;
//	byte[] bodyBytes;
	String bodyText;
//	File bodyFile;
	Map<String, String> bodyKeyValues;
	//Map<String, String> multiPartKeyValues;
//	Map<String, File> multiPartFiles;
	Map<String, File> files;
	Object tag;
	
	public Request() {
		this.method = "GET";
		this.tag = this;
	}
	
	public Request url(String url) {
        if (url == null) throw new NullPointerException("url == null");
        this.url = url;
        return this;
	}
	
	public Request post() {
    	this.method = "POST";
        return this;
    }
	
	public Request setHeader(String name, String value) {
		if(headers == null) headers = new ArrayList<String>();
		for(int i = 0; i < headers.size(); i +=2){
			if(name.equals(headers.get(i))){
				headers.remove(i + 1);
				headers.add(i + 1, value);
				return this;
			}
		}
		headers.add(name);
		headers.add(value);
        return this;
    }
	
	public Request addHeader(String name, String value) {
		if(headers == null) headers = new ArrayList<String>();
		headers.add(name);
		headers.add(value);
		return this;
	}
	
	public Request defaultHeader() {
		addHeader("Accept-Charset", "UTF-8");
        return this;
    }
	
	public Request addKeyValue(String name, String value) {
		if(bodyKeyValues == null) bodyKeyValues = new HashMap<String, String>();
		bodyKeyValues.put(name, value);
		return this;
	}
	
	public Request addJson(String json) {
		bodyText = json;
		return this;
	}
	
	public Request addString(String text) {
		bodyText = text;
		return this;
	}
	
//	public Request addBytes(byte[] bytes) {
//		bodyBytes = bytes;
//		return this;
//	}
	
	public Request addFile(File file) {
		if(files == null) files = new HashMap<String, File>();
		files.put("filename", file);
		return this;
	}
	
	public Request addFile(String name, File file) {
		if(files == null) files = new HashMap<String, File>();
		files.put(name, file);
		return this;
	}
	
	public String host(){
		URL u = null;
		try {
			u = new URL(url);
			return u.getHost();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public String getHeader(String field){
		if(headers == null) return null;
		for(int i = 0; i < headers.size(); i +=2){
			if(field.equals(headers.get(i))){
				return headers.get(i + 1);
			}
		}
		return null;
	}
	
	public byte[] bodyKeyValues2Bytes() throws UnsupportedEncodingException{
		if(bodyKeyValues == null || bodyKeyValues.size() == 0) {
			return null;
		}
		String text = "";
		for (Map.Entry<String, String> entry : bodyKeyValues.entrySet()) {  
			  
//			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
			if(text.length() >0 ){
				text += "&";
			}
			text += entry.getKey() + "=" + entry.getValue();
		}
		return text.getBytes("UTF-8");
	}
	
	public String bodyKeyValues() throws UnsupportedEncodingException{
		if(bodyKeyValues == null || bodyKeyValues.size() == 0) {
			return null;
		}
		String text = "";
		for (Map.Entry<String, String> entry : bodyKeyValues.entrySet()) {  
			  
//			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
			if(text.length() >0 ){
				text += "&";
			}
			text += entry.getKey() + "=" + entry.getValue();
		}
		return text;
	}
}
