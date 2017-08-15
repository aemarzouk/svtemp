package Rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;

import javax.ws.rs.*;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

@Path("GetUsers")
public class Get_All_Users {

	@GET 
	@Produces(MediaType.APPLICATION_JSON)
	public String Get_Users() throws JSONException{
		  BufferedReader br = null;
          String error = "" ;  
          URL url;
          String content = "" ; 
          try {
              url = new URL("https://438b72b2-6a3a-438e-8805-69fe9c879004-bluemix.cloudant.com/svdb/_find");

              HttpURLConnection connection = (HttpURLConnection) url.openConnection();
              String userCredentials = "wheyetandedismingrappiet" + ":" + "365f0f4f35f04a184a5823f0f3ee5d236a746337";
              String basicAuth = "Basic " + Base64.getEncoder().encodeToString(userCredentials.getBytes());
              connection.setRequestProperty("Authorization", basicAuth);
              connection.setRequestProperty("Content-Type", "application/json");
              connection.setRequestProperty("Accept", "application/json");
              connection.setRequestMethod("POST");
				////////write here your parameters you want to select on///////
              
              String selector = "{\"selector\":{\"type\": \"user\"}}";
              connection.setDoOutput(true);
              OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
              wr.write(selector);
              wr.flush();
              wr.close();

              int httpCode = connection.getResponseCode();
              if (httpCode == 200 || httpCode == 201) {
                  br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
              } else {
                  br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
              }
              StringBuilder sb = new StringBuilder();
              String line = null;
              int x = 0;
              while ((line = br.readLine()) != null) {
                  if(x!=0) {
                      sb.append(line);
                      sb.append(System.getProperty("line.separator"));
                  }
                  x++;
              }
              
             content = '{' + sb.toString();
             //return content;
             JSONObject jsonResponse = (JSONObject) new JSONTokener(content).nextValue();
			 JSONArray user_trans = jsonResponse.getJSONArray("docs");
			 JSONArray all_users = new JSONArray()  ; 
			 
			 for(int i=0 ; i<user_trans.length() ; i++){
				 JSONObject j = new JSONObject() ;  
				j.put("name", user_trans.getJSONObject(i).getString("_id")) ; 
				all_users.put(j) ; 
			 }
			 
			 return all_users.toString() ; 

          } catch (MalformedURLException e) {
              error = e.getMessage();
              e.printStackTrace();
          } catch (IOException e) {
              error = e.getMessage();
              e.printStackTrace();
          } finally {
              try {
                  br.close();
              } catch (IOException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
              }
          }
          //return content;
          return "1" ; 
	}
	
}
