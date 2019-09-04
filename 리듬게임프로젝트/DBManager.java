
import java.sql.*;
import java.io.*;
import java.beans.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.sql.Connection;
import java.sql.PreparedStatement;


public class DBManager {
	static java.sql.Connection conn = null;
	Statement stmt;
    static String driverName = "com.mysql.jdbc.Driver";
    static String dbURL = "jdbc:mysql://localhost:3306/music_game?characterEncoding=utf8&characterSetResults=utf8&useSSL=false";
    PreparedStatement pstmt;
   public DBManager() {     
    	try {
            Class.forName(driverName);
            conn = DriverManager.getConnection(dbURL, "mysql", "tjdwldn01!");
            System.out.println("connect success");

        } catch (Exception e) {
        	System.out.println("Mysql Server Not Connection.");
            e.printStackTrace();
        }
//		mi.insert("1.wav","6명의 한국 남자 아이돌","2017년에 발매된 love yourself Her 앨범에 수록된 곡","가사:첫눈에 널 알아보게 됐어 서롤 불러왔던 것처럼");
//		mi.insert("2.wav"," "," "," ");
//		mi.insert("3.wav"," "," "," ");
//		mi.insert("4.wav","TV프로그램 곡","서바이벌 TV프로그램","가사:너를 보던 그 순간 시선 고정 너에게");
//		mi.insert("5.wav"," "," "," ");
//		mi.insert("6.wav"," "," "," ");
//		mi.insert("7.wav","한 남자 아이돌 그룹 멤버가 솔로로 낸 곡","2018년에 발매된 xx앨범의 수록된 힙합곡","가사:똑똑 그대 보고 싶소 넘볼 수 없고 가질 수 없어");
//		mi.insert("8.wav"," "," "," ");
//		mi.insert("9.wav"," "," "," ");
//		try {
//			conn.close();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    }
    
    
    public String select_hint1 (String name ) {
        StringBuilder sb = new StringBuilder();
       // Random random = new Random();
       // int ne = random.nextInt(4);
        String sql = sb.append("select * from music where")
                .append(" name = ")
                .append("\""+name+"\"")
                .append(";").toString();
        System.out.println(sql);
        try {
        	
        	pstmt =  conn.prepareStatement(sql);
        	ResultSet rs =  pstmt.executeQuery();
        	if(rs.next()) {
        		//System.out.println("name : "+rs.getString("hint1"));
                return rs.getString("hint1");
        	}
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return "error";
    }
    
    public String select_hint2 (String name ) {
        StringBuilder sb = new StringBuilder();
        String sql = sb.append("select * from music where")
                .append(" name = ")
                .append("\""+name+"\"")
                .append(";").toString();
        try {
        	pstmt =  conn.prepareStatement(sql);
        	ResultSet rs =  pstmt.executeQuery();
        	if(rs.next()) {
                return rs.getString("hint2");}
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		return "error";
    }
    
    public String select_hint3 (String name ) {
        StringBuilder sb = new StringBuilder();
        String sql = sb.append("select * from music where")
                .append(" name = ")
                .append("\""+name+"\"")
                .append(";").toString();
        try {
        	pstmt =  conn.prepareStatement(sql);
        	ResultSet rs =  pstmt.executeQuery();
        	if(rs.next()) {
                return rs.getString("hint3");}
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return "error";
    }
    
    private void insert(String name,String hint1, String hint2, String hint3) {

		String sql="insert into music values(?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, hint1);
			pstmt.setString(3, hint2);
			pstmt.setString(4, hint3);
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}