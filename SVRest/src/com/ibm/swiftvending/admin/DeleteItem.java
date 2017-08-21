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
 * Servlet implementation class DeleteItem
 */
@WebServlet("/DeleteItem")
public class DeleteItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteItem() {
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
				Cloudant_Client client = new Cloudant_Client() ; 
				
				String content = client.Get_Function(item_url) ; 
				
                if (content.equals("error")) {
                    out.print(0);   //itemID deosn't exist
                    return; 
                  } 
                else {            
                	JSONObject delete_request = new JSONObject (content); 
                	String rev= delete_request.getString("_rev"); 
                	//build delete url
                    String delete_url="https://438b72b2-6a3a-438e-8805-69fe9c879004-bluemix.cloudant.com/svdb/"+item_id+"?rev="+rev; 
                    boolean result= client.Delete_Function(delete_url); 
                    
                   // out.print(result);
                   if(result==true)
                    {out.print(1);}
                    else 
                    {out.print(0);} 
                    
                }
			}
			catch (JSONException e) {
				e.printStackTrace();
				}
		
	}
	}
	
	
	
}

