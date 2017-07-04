package yumo.wang;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qiniu.pili.Client;
import com.qiniu.pili.Hub;
import com.qiniu.pili.PiliException;
import com.qiniu.pili.Stream;

public class RoomServlet extends HttpServlet {

    String accessKey;
    String secretKey;
    String streamKeyPrefix;
    String hubName;
    Client cli;
    Hub hub;
    String keyA = "A";
	/**
	 * Constructor of the object.
	 */
	public RoomServlet() {
		super();
		  // local test environment
   	 	accessKey = "FOaMO9Fj2UEreKaU15HfY7XOgJD4r1Z0IeISeT4D";
        secretKey = "FhzGmFBu37sWcNxvb7VXLZbI9e0zZ0GswzqqiNDd";
       hubName = "wangzhibo123";
       

       cli = new Client(accessKey, secretKey);
       hub = cli.newHub(hubName);
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
		String method = request.getParameter("method");
		streamKeyPrefix = "javasdktestw" + String.valueOf(new Date().getTime());
		String streamKey = streamKeyPrefix + keyA;
		if(method.equals("insert")) {
			String name = request.getParameter("name");
			String desc = request.getParameter("desc");
			createRoom(name, desc);
			String url = createUrl(streamKey);
			response.getWriter().write(url);
		} else if(method.equals("getAll")) {
			RoomDao dao = new RoomDao();
			String result = dao.getAllRoom();
			response.getWriter().write(result);
		}
		
	}

	private void createRoom(String name,String desc){
		String streamKey = streamKeyPrefix + keyA;
        // create
    	 System.out.println("create");
        try {
            hub.create(streamKey);
        } catch (PiliException e) {
        	System.out.println("fail");
        }
        
        Stream stream = null;
        try {
            stream = hub.get(streamKey);
            System.out.println("hub" + stream.getHub());
            System.out.println("key" + stream.getKey());
        } catch (PiliException e) {
            System.out.println("error");
        }
        String playUrl = createPlayUrl(streamKey);
        String publishUrl = createUrl(streamKey);
        RoomDao dao = new RoomDao();
        dao.insertRoom(name, desc, publishUrl, playUrl, streamKey);
        
	}
	
	private String createUrl(String streamKey){
		  String url = cli.RTMPPublishURL("pili-publish.www.emoreedu.com", hubName, streamKey, 360000000);
	      System.out.println(url);
	      return url;
	}
	private String createPlayUrl(String streamKey){
		String url = cli.RTMPPlayURL("pili-live-rtmp.www.emoreedu.com", hubName, streamKey);
	        System.out.println(url);
	        return url;
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

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
