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

/**
 * Servlet implementation class EditItem
 */
@WebServlet("/EditItem")
public class EditItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditItem() {
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
	
		PrintWriter out = response.getWriter();
		String json = "";
        BufferedReader br = new BufferedReader(new  InputStreamReader(request.getInputStream()));
        
        if(br != null) {
            json = br.readLine();
			try {
				
			// 1st get item ID and check if it exists in database   
				JSONObject jsonResponse = (JSONObject) new JSONTokener(json).nextValue();
				String item_id = jsonResponse.getString("_id");
				String item_url = "https://438b72b2-6a3a-438e-8805-69fe9c879004-bluemix.cloudant.com/svdb/"+item_id;
				Cloudant_Client get_client = new Cloudant_Client() ; 
				
				String content = get_client.Get_Function(item_url) ; 
				
                if (content.equals("error")) {
                    out.print(0);   //itemID deosn't exist
                  } else {            
                    
                	  JSONObject update_request = new JSONObject (content);  // Json of database 
                	  // json of sender 
                	  String itemNumber = jsonResponse.getString("number");
                      String itemExpiry=jsonResponse.getString("expiry_date");
              		  String itemCalories=jsonResponse.getString("calories");
              		  String itemPrice=jsonResponse.getString("price"); 
              	      String itemName=jsonResponse.getString("name");
              	      
              	      // put json of sender in json of database
              	      
              	    update_request.remove("number");
                    update_request.remove("price");
                    update_request.remove("expiry_date");
                    update_request.remove("name");
                    update_request.remove("calories");
                    
                    
                    update_request.put("number",itemNumber.toString());
                    update_request.put("price",itemPrice.toString());
                    update_request.put("expiry_date",itemExpiry.toString());
                    update_request.put("name",itemName.toString());
                    update_request.put("calories",itemCalories.toString());
                    update_request.put("type", "item") ; 
                    
                    String updateContent = update_request.toString();
                    String Put_url = "https://438b72b2-6a3a-438e-8805-69fe9c879004-bluemix.cloudant.com/svdb/"+ item_id;
                    boolean result =get_client.Put_Function(Put_url, updateContent);
                    
                    if (result==false) 
                    	out.print(0);
                    else
                    	out.print(1);      
                
		       }
			}
        
			catch (JSONException e) {
				e.printStackTrace();
				}
	        } //  if(br != null)
        
	}
        
       
			

}
