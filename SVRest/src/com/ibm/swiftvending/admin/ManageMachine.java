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

/*
 *Servlet implementation class ManageMachine
 */
@WebServlet("/ManageMachine")
public class ManageMachine extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageMachine() {
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
			
				// get JSON Object sent from Front End 
				JSONObject jsonResponse = (JSONObject) new JSONTokener(json).nextValue();
				
				//  get rev of spring ID 
				Cloudant_Client client = new Cloudant_Client() ; 
				String spring_url = "https://438b72b2-6a3a-438e-8805-69fe9c879004-bluemix.cloudant.com/svdb/springs"; 
				JSONObject springs_request = (JSONObject) new JSONTokener(client.Get_Function(spring_url)).nextValue();
				String rev=springs_request.getString("_rev"); 
				String id=springs_request.getString("_id"); 
				
				
				// Add rev to this JSON 
				jsonResponse.put("_rev",rev); 
				jsonResponse.put("_id", id); 
				json =jsonResponse.toString(); 
				
				boolean result= client.Put_Function(spring_url, json);
	
			
                if (result==true) {
                    out.print(1);   //Success to post 
                  } else {            
                   
                	  out.print(0);  
                  }
                	  
			}
        
			catch (JSONException e) {
				e.printStackTrace();
				}
	        } //  if(br != null)
			}	
		
	}
