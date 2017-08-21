package com.ibm.swiftvending.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import Rest.Cloudant_Client;
import Rest.Hashing_Password;

/**
 * Servlet implementation class AddItem
 */
@WebServlet("/AddItem")
public class AddItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddItem() {
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
				JSONObject jsonResponse = (JSONObject) new JSONTokener(json).nextValue();
				String itemID = jsonResponse.getString("_id"); // manage with Nourhan 
				//Check itemID is valid ... Check Link 
				String url = "https://438b72b2-6a3a-438e-8805-69fe9c879004-bluemix.cloudant.com/svdb/"+ itemID;
				Cloudant_Client client = new Cloudant_Client();
				String content = client.Get_Function(url) ; 
                if (!content.equals("error")) {
                    out.print(0);   //itemID exists
                } else {            
                    String post_url = "https://438b72b2-6a3a-438e-8805-69fe9c879004-bluemix.cloudant.com/svdb";
                    ///item parameters 
                   // String itemNumber = jsonResponse.getString("number");
                    //String itemExpiry=jsonResponse.getString("expiry_date");
            		//String itemCalaroies=jsonResponse.getString("calories");
            		//String itemPrice=jsonResponse.getString("price"); 
            	    //String itemName=jsonRe sponse.getString("name");
  
                    jsonResponse.put("type", "item") ; 
//                    jsonResponse.put("number",jsonResponse.getString("number"));
//                    jsonResponse.put("expiry_date",jsonResponse.getString("expiry_date"));
//                    jsonResponse.put("calories",jsonResponse.getString("calories"));
//                    jsonResponse.put("price",jsonResponse.getString("price"));
//                    jsonResponse.put("name",jsonResponse.getString("name"));
//                   
                 
                    json = jsonResponse.toString();
                    client.Post_Function(post_url, json);
                    out.print(1);   //Item is now available 
                }
			} 
        catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			}
}
}
		
	
	


