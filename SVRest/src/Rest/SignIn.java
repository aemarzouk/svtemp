package Rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


/**
 * Servlet implementation class SignIn
 */
@WebServlet("/SignIn")
public class SignIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignIn() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		String json = "";
        BufferedReader br = new BufferedReader(new  InputStreamReader(request.getInputStream()));
        if(br != null) {
            json = br.readLine();
			try {
				Cloudant_Client client = new Cloudant_Client() ; 
				JSONObject jsonResponse = (JSONObject) new JSONTokener(json).nextValue();
				String username = jsonResponse.getString("_id");
				String password = jsonResponse.getString("password");
				//Check username and password is valid
				String url ="https://438b72b2-6a3a-438e-8805-69fe9c879004-bluemix.cloudant.com/svdb/"+ username;
				String content=client.Get_Function(url) ; 
				if(content.equals("error")){
					out.println(2) ; //username doesnot exist  
					return ;
				}
                JSONObject password_request = (JSONObject) new JSONTokener(content).nextValue();
                String  output = password_request.getString("password");
                Hashing_Password Hash = new Hashing_Password();
                //password.equals(output)
                if(Hash.check(password, output)){
                	 
                	 	out.print(1);   //password correct
                 }
                 else{
                	 	out.print(0);   //password incorrect
                 }
                     
                } 
             
             //end of Check username and password is valid
			
			catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
	}


}

