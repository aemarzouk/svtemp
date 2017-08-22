package com.ibm.swiftvending.admin;

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

import Rest.Cloudant_Client;
import Rest.Watson_Blockchain_Client;

@Path("Transactions")
public class Transactions {

	@GET
	@Produces(MediaType.APPLICATION_JSON) 
	public String GetAllTransactions() throws JSONException, IOException {
		String url = "https://438b72b2-6a3a-438e-8805-69fe9c879004-bluemix.cloudant.com/svdb/trans_number";
		Cloudant_Client client = new Cloudant_Client();
		Watson_Blockchain_Client blockchainClient = new Watson_Blockchain_Client();
		JSONObject transactionsNumber = new JSONObject(client.Get_Function(url));
		//JSONArray user_trans = transactions.getJSONArray("Transactions");
		JSONArray User_all_trans = new JSONArray() ;
		//JSONArray Test = new JSONArray() ;
		for (int i = transactionsNumber.getInt("number"); i > 100; i--) {
			Integer transaction = i;//user_trans.getInt(i); 
			String get_trans_url ="https://c0dc93abf3f1451db1699a6e953d42aa-vp1.us.blockchain.ibm.com:5004/chaincode";
            String get_trans_json = "{\"jsonrpc\": \"2.0\",\"method\": \"query\",\"params\": {\"type\": 1,\"chaincodeID\": { \"name\": \"b4188327a5b30d43457e7cc55ba0f6b64348746532588b791f68b3ad9760d996ceabd152846f4e78aafddf2de233b0e9ef77d62e319c902e66a984bb483da34a\"},\"ctorMsg\": {\"function\": \"read\",\"args\": [\""+transaction.toString()+"\" ]},\"secureContext\": \"user_type1_1\"},\"id\": 1}"; 
                JSONObject trans_json_obj = new JSONObject(blockchainClient.Post_Function(get_trans_url,get_trans_json));
                if(!trans_json_obj.getJSONObject("result").isNull("message")) {
                JSONObject js = new JSONObject(trans_json_obj.getJSONObject("result").getString("message"));
                JSONObject JsonObjectResponse = new JSONObject(); 
                JsonObjectResponse.put("item_name", js.getString("ItemName"));
                JsonObjectResponse.put("trans_date", js.getString("Date"));
                JsonObjectResponse.put("trans_time", js.getString("Time"));
                JsonObjectResponse.put("username", js.getString("Username"));
                User_all_trans.put(JsonObjectResponse);
                }
                //Test.put(trans_json_obj);
                
		}
		JSONObject js = new JSONObject() ; 
		js.put("Transactions", User_all_trans) ;
		//js.put("Transactions", Test) ;
		return js.toString(); 
	}
}
