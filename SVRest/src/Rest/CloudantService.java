package Rest;



import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Base64;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Path("/getItems")
public class CloudantService {

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getItems(){
            BufferedReader br = null;
            String error = "" ;  
            URL url;
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
                
                String selector = "{\"selector\":{\"type\": \"item\"}}";
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

                String content = sb.toString();
                return content;

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
            return null;
        }
        
}
