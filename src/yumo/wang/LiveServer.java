package yumo.wang;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qiniu.pili.Client;
import com.qiniu.pili.Meeting;

public class LiveServer extends HttpServlet {

    String accessKey;
    String secretKey;
    Client cli;
    Meeting meeting;
	/**
	 * Constructor of the object.
	 */
	public LiveServer() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String room = request.getParameter("room");
		String userid = request.getParameter("userid");
		String token = testRoomToken(room,userid);
		response.getWriter().write(token);
	}

	
	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	 public String testRoomToken(String room,String userId){
           String token = null;
		try {
			token = meeting.roomToken(room, userId, "user", new Date(1785600000000L));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
           System.out.println(token);
           return token;
   }
	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
		 accessKey = "FOaMO9Fj2UEreKaU15HfY7XOgJD4r1Z0IeISeT4D";
	        secretKey = "FhzGmFBu37sWcNxvb7VXLZbI9e0zZ0GswzqqiNDd";

	        cli = new Client(accessKey, secretKey);
	        meeting = cli.newMeeting();
	}

}
