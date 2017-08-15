package Rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Servlet implementation class GetItems
 */
@WebServlet("/GetItems")
public class GetItems extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetItems() {
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
		String user_info = "";
		String user_id =""; 
        BufferedReader user_br = new BufferedReader(new  InputStreamReader(request.getInputStream()));
        if(user_br != null) {
            user_info = user_br.readLine();
			JSONObject jsonResponse;
			try {
				jsonResponse = (JSONObject) new JSONTokener(user_info).nextValue();
				 user_id = jsonResponse.getString("_id");
			}catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String url = "https://c0dc93abf3f1451db1699a6e953d42aa-vp1.us.blockchain.ibm.com:5004/chaincode";
            String json = "{\"jsonrpc\": \"2.0\",\"method\": \"query\",\"params\": {\"type\": 1,\"chaincodeID\": { \"name\": \"b4188327a5b30d43457e7cc55ba0f6b64348746532588b791f68b3ad9760d996ceabd152846f4e78aafddf2de233b0e9ef77d62e319c902e66a984bb483da34a\"},\"ctorMsg\": {\"function\": \"read\",\"args\": [\""+user_id+"\" ]},\"secureContext\": \"user_type1_1\"},\"id\": 1}"; 
            Watson_Blockchain_Client  post_client = new Watson_Blockchain_Client() ; 
                String content=post_client.Post_Function(url, json) ; 
                JSONObject Trans_array;
				try {
					Trans_array = new JSONObject(content);
					JSONObject j = new JSONObject(Trans_array.getJSONObject("result").getString("message"));	
					JSONArray user_trans = j.getJSONArray("Transactions");
					JSONArray User_all_trans = new JSONArray() ;  
					for (int i = 0; i < user_trans.length(); ++i) {
						Integer transaction = user_trans.getInt(i); 
						String get_trans_url ="https://c0dc93abf3f1451db1699a6e953d42aa-vp1.us.blockchain.ibm.com:5004/chaincode";
			            String get_trans_json = "{\"jsonrpc\": \"2.0\",\"method\": \"query\",\"params\": {\"type\": 1,\"chaincodeID\": { \"name\": \"b4188327a5b30d43457e7cc55ba0f6b64348746532588b791f68b3ad9760d996ceabd152846f4e78aafddf2de233b0e9ef77d62e319c902e66a984bb483da34a\"},\"ctorMsg\": {\"function\": \"read\",\"args\": [\""+transaction.toString()+"\" ]},\"secureContext\": \"user_type1_1\"},\"id\": 1}"; 
			                JSONObject trans_json_obj = new JSONObject(post_client.Post_Function(get_trans_url,get_trans_json));
			                JSONObject js = new JSONObject(trans_json_obj.getJSONObject("result").getString("message"));
			                JSONObject JsonObjectResponse = new JSONObject(); 
			                JsonObjectResponse.put("item_name", js.getString("ItemName"));
			                JsonObjectResponse.put("trans_date", js.getString("Date"));
			                JsonObjectResponse.put("trans_time", js.getString("Time"));
			                User_all_trans.put(JsonObjectResponse);
					}              
				JSONObject js = new JSONObject() ; 
				js.put("Transactions", User_all_trans) ;  
				out.print(js);
					} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
	} 
}
	
