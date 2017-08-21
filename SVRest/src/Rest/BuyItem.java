package Rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Timestamp;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Servlet implementation class BuyItem
 */
@WebServlet("/BuyItem")
public class BuyItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuyItem() {
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
		//doGet(request, response);
		PrintWriter out = response.getWriter();
		String json = "";
        BufferedReader br = new BufferedReader(new  InputStreamReader(request.getInputStream()));
        if(br != null) {
            json = br.readLine();
			try {
				// 
				JSONObject jsonResponse = (JSONObject) new JSONTokener(json).nextValue();
				String spring_id = jsonResponse.getString("spring_id");
				String user_id = jsonResponse.getString("user_id");
				String session_id= request.getHeader("sessionID");
				Cloudant_Client get_client = new Cloudant_Client() ;
				// check session and user matching 
				String user_url="https://438b72b2-6a3a-438e-8805-69fe9c879004-bluemix.cloudant.com/svdb/"+user_id; 
				JSONObject user_request = new JSONObject(get_client.Get_Function(user_url));// new JSONTokener(get_client.Get_Function(user_url)).nextValue();
				// get item to be bought from springs
				if(!session_id.equals(user_request.getString("sessionID"))) {
					out.println("Authentication Error!");
					out.println(session_id);
					out.println(user_request.getString("sessionID"));
					return;
				}
				String spring_url = "https://438b72b2-6a3a-438e-8805-69fe9c879004-bluemix.cloudant.com/svdb/springs"; 
				JSONObject springs_request = (JSONObject) new JSONTokener(get_client.Get_Function(spring_url)).nextValue();
				String item_id = springs_request.getString("spring_" + spring_id);
				// retrieve item info
				String url = "https://438b72b2-6a3a-438e-8805-69fe9c879004-bluemix.cloudant.com/svdb/"+ item_id;
				JSONObject update_request = (JSONObject) new JSONTokener(get_client.Get_Function(url)).nextValue();		           
		        String item_name = update_request.getString("name");
  //----------------------------------------------------------------------------------
		          // increment no of transactions of a certain user            
	
		String Url = "https://438b72b2-6a3a-438e-8805-69fe9c879004-bluemix.cloudant.com/svdb/trans_number";
        JSONObject Update_request = (JSONObject) new JSONTokener(get_client.Get_Function(Url)).nextValue();		           
        Integer trans_number = Update_request.getInt("number");
        trans_number++;
		//String item_place = "";
        Update_request.remove("number");
		Update_request.put("number",trans_number.toString());
        String trans_content = Update_request.toString();
//--------------------------------------------------------------------------------------------
        String put_url ="https://438b72b2-6a3a-438e-8805-69fe9c879004-bluemix.cloudant.com/svdb/trans_number";
        get_client.Put_Function(put_url, trans_content);
//---------------------------------------------------------------------------------       
				//WatsonServiceID(UpdateCloudant(item_id));
        	boolean result=UpdateCloudant(item_id);
        	if(!result)
             {
        		out.print(0);
             }
        	else{
        				
        		WatsonServiceID(spring_id);
				UpdateBlockChain(user_id , item_id , item_name , trans_number.toString());
				//out.print(UpdateCloudant(item_id));
				out.print(1);
        	}
			}
			    catch (JSONException e) {
				e.printStackTrace();
			}
        }
	}
	
	protected boolean UpdateCloudant(String item_id) throws IOException, JSONException
	
	{
		
		Cloudant_Client get_client = new Cloudant_Client() ; 
		BufferedReader br = null;
		//String item_place = "";
		String url ="https://438b72b2-6a3a-438e-8805-69fe9c879004-bluemix.cloudant.com/svdb/"+ item_id;
	    JSONObject update_request = (JSONObject) new JSONTokener(get_client.Get_Function(url)).nextValue(); // return a string which we make the JSON object with 
        //item_place = update_request.getString("item_place");
        Integer item_amount = update_request.getInt("number");  // to decrement no of items 
        if(item_amount==0){ return false; } 
        item_amount-- ; 
        // update number 
        update_request.remove("number");
        update_request.put("number",item_amount.toString());
        String content = update_request.toString();
        String Put_url = "https://438b72b2-6a3a-438e-8805-69fe9c879004-bluemix.cloudant.com/svdb/"+ item_id;
        get_client.Put_Function(Put_url, content);
	    //return item_place;
        return true; 

	}
	
	protected void UpdateBlockChain(String user_id, String item_id , String item_name , String trans_number) throws IOException
	{ 
		String url = "https://c0dc93abf3f1451db1699a6e953d42aa-vp1.us.blockchain.ibm.com:5004/chaincode";
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            DateTimeFormatter ttf = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalDate localDate = LocalDate.now();
            LocalTime localTime = LocalTime.now();
            String date = dtf.format(localDate);
            String time = ttf.format(localTime);
            String json = "{\"jsonrpc\": \"2.0\",\"method\": \"invoke\",\"params\": {\"type\": 1,\"chaincodeID\": { \"name\": \"b4188327a5b30d43457e7cc55ba0f6b64348746532588b791f68b3ad9760d996ceabd152846f4e78aafddf2de233b0e9ef77d62e319c902e66a984bb483da34a\"},\"ctorMsg\": {\"function\": \"Buy\",\"args\": [\""+user_id+"\" , \""+item_name+"\" , \""+item_id+"\" ,\""+trans_number+"\" ,\""+date+"\" ,\""+time+"\" ]},\"secureContext\": \"user_type1_1\"},\"id\": 1}"; 
            Watson_Blockchain_Client post_client = new Watson_Blockchain_Client() ; 
            post_client.Post_Function(url, json); 
      }

	protected void WatsonServiceID(String item_place) throws IOException
	{
        String url = "https://node-red-12345.eu-gb.mybluemix.net/start"+item_place; 
        Watson_Blockchain_Client get_client = new Watson_Blockchain_Client() ; 
		get_client.Get_Function(url) ; 
	}

}
