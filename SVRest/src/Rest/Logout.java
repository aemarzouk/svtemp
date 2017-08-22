package Rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Servlet implementation class Logout
 */
@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */   
    public Logout() {
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
				JSONObject jsonResponse = new JSONObject(json);
				String username = jsonResponse.getString("user_id");
				String sessionID=request.getHeader("sessionID"); 
				//Check username and password is valid 
				String url ="https://438b72b2-6a3a-438e-8805-69fe9c879004-bluemix.cloudant.com/svdb/"+ username;
				String content=client.Get_Function(url) ; 
				if(content.equals("error")){
					out.println(2) ; //username doesnot exist  
					return ;
				}
                JSONObject user = new JSONObject(content);
                	 	
        		if(sessionID.equals(user.getString("sessionID"))) {
        			UUID uuid = UUID.randomUUID();
        			user.remove("sessionID");
        			user.put("sessionID", "null"); 
        	       if(client.Put_Function(url, user.toString())) {
        	    	   out.print("Logout successfully");
        	       }
        	       else
        	       {out.print(3);}  // Failure to connect 
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
