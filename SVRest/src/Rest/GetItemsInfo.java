package Rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

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
 * Servlet implementation class GetItemsInfo
 */
@WebServlet("/GetItemsInfo")
public class GetItemsInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetItemsInfo() { 
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
				String spring_id = jsonResponse.getString("item_id");
				String spring_url = "https://438b72b2-6a3a-438e-8805-69fe9c879004-bluemix.cloudant.com/svdb/springs"; 
				JSONObject springs_request = (JSONObject) new JSONTokener(client.Get_Function(spring_url)).nextValue();
				String item_id = springs_request.getString("spring_" + spring_id);
				//Check username and password is valid
				String url ="https://438b72b2-6a3a-438e-8805-69fe9c879004-bluemix.cloudant.com/svdb/"+item_id ;
				String content=client.Get_Function(url) ; 
				JSONObject item_content= (JSONObject) new JSONTokener(content).nextValue();
				item_content.remove("_rev") ;
				item_content.remove("_id") ;
				item_content.remove("number") ;
				item_content.remove("item_place") ;
				item_content.remove("type") ;
				out.print(item_content.toString()); 
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
