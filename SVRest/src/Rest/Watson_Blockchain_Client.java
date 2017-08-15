package Rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Watson_Blockchain_Client {

	public String Post_Function(String url , String json_object ) throws IOException{
			BufferedReader br = null ; 
			URL post_url = new URL(url);
            HttpURLConnection post_connection = (HttpURLConnection) post_url.openConnection();	                    
            post_connection.setRequestProperty("Content-Type", "application/json");
            post_connection.setRequestProperty("Accept", "application/json");
            post_connection.setRequestMethod("POST");
            post_connection.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(post_connection.getOutputStream());
            wr.write(json_object);
            wr.flush();
            wr.close();
            int post_httpCode = post_connection.getResponseCode();
            if (post_httpCode == 200) {
                br = new BufferedReader(new InputStreamReader(post_connection.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(post_connection.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.getProperty("line.separator"));
            }
            post_connection.disconnect();
            return sb.toString(); 
	}
	public void Get_Function(String get_url){
		BufferedReader br = null;
		String error ; 
        try {
            URL url = new URL(get_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            int httpCode = connection.getResponseCode();
            if (httpCode == 200|| httpCode == 201) {
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.getProperty("line.separator"));
            }
            connection.disconnect();
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
	}
}

