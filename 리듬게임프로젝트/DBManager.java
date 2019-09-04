
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
//		mi.insert("1.wav","6���� �ѱ� ���� ���̵�","2017�⿡ �߸ŵ� love yourself Her �ٹ��� ���ϵ� ��","����:ù���� �� �˾ƺ��� �ƾ� ���� �ҷ��Դ� ��ó��");
//		mi.insert("2.wav"," "," "," ");
//		mi.insert("3.wav"," "," "," ");
//		mi.insert("4.wav","TV���α׷� ��","�����̹� TV���α׷�","����:�ʸ� ���� �� ���� �ü� ���� �ʿ���");
//		mi.insert("5.wav"," "," "," ");
//		mi.insert("6.wav"," "," "," ");
//		mi.insert("7.wav","�� ���� ���̵� �׷� ����� �ַη� �� ��","2018�⿡ �߸ŵ� xx�ٹ��� ���ϵ� ���հ�","����:�ȶ� �״� ���� �ͼ� �Ѻ� �� ���� ���� �� ����");
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