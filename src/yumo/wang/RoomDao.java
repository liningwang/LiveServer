package yumo.wang;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class RoomDao {
	Connection con;
	public RoomDao(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = (Connection) DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/liveroom", "root", "root");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void insertRoom(String name,String describe,String publishUrl,
			String playUrl,String key){
		try {
			PreparedStatement pre = (PreparedStatement) con.prepareStatement("insert into room(name,describea,publishurl,playurl,keya) values(?,?,?,?,?)");
			pre.setString(1, name);
			pre.setString(2, describe);
			pre.setString(3, publishUrl);
			pre.setString(4, playUrl);
			pre.setString(5, key);
			pre.executeUpdate();
			
			pre.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getAllRoom(){
		PreparedStatement pre;
		List<Room> list = new ArrayList<Room>();
		try {
			pre = (PreparedStatement) con.prepareStatement("select * from room");
			ResultSet result = pre.executeQuery();
			while(result.next()) {
				Room room = new Room();
				room.setName(result.getString("name"));
				room.setDesc(result.getString("describea"));
				room.setPlayUrl(result.getString("playurl"));
				list.add(room);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject obj = new JSONObject();
		obj.put("result", list);
		return obj.toString();
	}

}
