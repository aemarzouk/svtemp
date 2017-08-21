package Rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Base64;

import org.json.JSONObject;
import org.json.JSONTokener;

public class Cloudant_Client {

	public boolean Put_Function(String url , String json) throws IOException{
		 BufferedReader br = null ; 
		 URL put_url = new URL(url);
         HttpURLConnection put_connection = (HttpURLConnection) put_url.openConnection();	
         String userCredentials = "wheyetandedismingrappiet" + ":" + "365f0f4f35f04a184a5823f0f3ee5d236a746337";
 	     String basicAuth = "Basic " + Base64.getEncoder().encodeToString(userCredentials.getBytes());
         put_connection.setRequestProperty("Authorization", basicAuth);
         put_connection.setRequestProperty("Content-Type", "application/json");
         put_connection.setRequestProperty("Accept", "application/json");
         put_connection.setRequestMethod("PUT");
         put_connection.setDoOutput(true);
         OutputStreamWriter wr = new OutputStreamWriter(put_connection.getOutputStream());
         wr.write(json);
         wr.flush();
         wr.close();
         int put_httpCode = put_connection.getResponseCode();
         if (put_httpCode == 201) {
             br = new BufferedReader(new InputStreamReader(put_connection.getInputStream()));
             put_connection.disconnect();
             return true; 
         } else {
         	
             br = new BufferedReader(new InputStreamReader(put_connection.getErrorStream()));
             put_connection.disconnect();
             return false;
         }
        
	}
	
	
	public String Get_Function(String Get_url ) throws IOException{
        BufferedReader br = null ; 	
        String content = null ; 
		URL url = new URL(Get_url);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	    String userCredentials = "wheyetandedismingrappiet" + ":" + "365f0f4f35f04a184a5823f0f3ee5d236a746337";
	    String basicAuth = "Basic " + Base64.getEncoder().encodeToString(userCredentials.getBytes());
	    connection.setRequestProperty("Authorization", basicAuth);
	    int httpCode = connection.getResponseCode();
	    if (httpCode == 200) {
		    br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		    StringBuilder sb = new StringBuilder();
	        String line = null;
	        while ((line = br.readLine()) != null) {
	            sb.append(line);
	            sb.append(System.getProperty("line.separator"));
	        }
	         content = sb.toString() ;
	    }
	    else {
	    	br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
	    	content = "error";
	    }
	     
        connection.disconnect();
		return content ; 
	}
	public boolean Post_Function (String url , String json) throws IOException{
		BufferedReader  br =null ; 
		URL post_url = new URL(url) ; 
        HttpURLConnection post_connection = (HttpURLConnection) post_url.openConnection();	
        String userCredentials = "wheyetandedismingrappiet" + ":" + "365f0f4f35f04a184a5823f0f3ee5d236a746337";
	    String basicAuth = "Basic " + Base64.getEncoder().encodeToString(userCredentials.getBytes());
        post_connection.setRequestProperty("Authorization", basicAuth);
        post_connection.setRequestProperty("Content-Type", "application/json");
        post_connection.setRequestProperty("Accept", "application/json");
        post_connection.setRequestMethod("POST");
        post_connection.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter(post_connection.getOutputStream());
        wr.write(json);
        wr.flush();
        wr.close();
        int post_httpCode =post_connection.getResponseCode();
        if (post_httpCode == 201) {
            br = new BufferedReader(new InputStreamReader(post_connection.getInputStream()));
            return true; 
        } else {
            br = new BufferedReader(new InputStreamReader(post_connection.getErrorStream()));
            return false ; 
        }

	}
	
	public boolean Delete_Function(String delete_url) throws IOException{
	    BufferedReader br = null ; 	
        String content = null ; 
		URL url = new URL(delete_url);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	    String userCredentials = "wheyetandedismingrappiet" + ":" + "365f0f4f35f04a184a5823f0f3ee5d236a746337";
	    String basicAuth = "Basic " + Base64.getEncoder().encodeToString(userCredentials.getBytes());
	    connection.setRequestProperty("Authorization", basicAuth);
//	    connection.setRequestProperty("Content-Type", "application/json");
//        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestMethod("DELETE");
        connection.setDoOutput(true);
	    int httpCode = connection.getResponseCode();
	    if (httpCode == 200) // success
	    {
		    br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		    StringBuilder sb = new StringBuilder();
	        String line = null;
	        while ((line = br.readLine()) != null) {
	            sb.append(line);
	            sb.append(System.getProperty("line.separator"));
	        }
	         content = sb.toString() ;
	         connection.disconnect();
	     if (content.contains( "true"))
	        	return true; 
	         else 
	            return false; 
	      
	    }
	    else {
	    	br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
	    	connection.disconnect();
	    	return false; 
	    }
		
		
	}
}
