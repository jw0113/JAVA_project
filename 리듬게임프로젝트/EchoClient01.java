import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
 
 
public class EchoClient01 {
 
 /**
  * @param args
  */
 public static void main(String[] args) {
  Socket socket = null;
  BufferedWriter bw = null; // 데이터 쓰기
  BufferedReader br = null; // 데이터 읽기
  String ipNumber = "localhost"; // 연결할 IP
  int portNumber = 9000; // 연결할 Port 번호
  String msg = ""; // BufferedReader 를 사용하여 받은 메시지 저장
   
  try {
    
   ////////////////////////////////////////////////////
   //           1. IP 주소 입력 사항 확인              //
   ////////////////////////////////////////////////////
  // if(args.length != 2) {
  //  System.out.println("Server IP, Port 입력 바랍니다.");
   // System.out.println("잘못된 실행이여서 프로그램을 종료합니다.");
  //  System.exit(0);
 //  }
  /// else {
   // ipNumber = args[0];
   // portNumber = Integer.parseInt(args[1]);
  // }
   ////////////////////////////////////////////////////
    
 
   System.out.println(ipNumber + " Server 연결 중");
 
    
   ////////////////////////////////////////////////
   //  2. Server 에서 열어놓은 Port 로 접속   //
   ////////////////////////////////////////////////
   socket = new Socket(ipNumber, portNumber);
   ////////////////////////////////////////////
    
   System.out.println("Server 연결 성공");
    
    
   ////////////////////////////////////////////////////////////////////////////
   //                             3. Server 로 데이터 전송                           //
   ////////////////////////////////////////////////////////////////////////////
   bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    
    
   System.out.print("Client send message : ");
   br = new BufferedReader(new InputStreamReader(System.in));
   msg = br.readLine();
   bw.write(msg + "\n");
   bw.flush(); // 출력 버퍼를 비우는 메서드(반드시 사용해 줘야 함)
    
   ////////////////////////////////////////////////////////////////////////////
    
 
   ////////////////////////////////////////////////////////////////////////////
   //         4. Server 로 보낸 메시지 다시 받기(Server -> Client)         //
   ////////////////////////////////////////////////////////////////////////////
   br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    
   System.out.print("Server send message : " + br.readLine());
   ////////////////////////////////////////////////////////////////////////////
   System.out.println("");
    
    
    
  } catch (UnknownHostException e) {
   e.printStackTrace();
  } catch (IOException e) {
   e.printStackTrace();
  } finally {
   try {
    br.close();
    bw.close();
    socket.close();
   } catch (IOException e) {
    e.printStackTrace();
   }
  }
 
 }
 
}