package ch03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientFile {
	static Socket socket = null;
public static void main(String[] args) {
		
		Socket socket = null; 
		
		try {
			socket = new Socket("localhost", 5001);
			PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
			writer.println("i'm client Hi"); //줄바꿈 처리를 하자
			
			// 서버로부터 데이터를 받기 위한 입력 스트림이 필요하다.
			// 대상은 소켓이다.
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			String message = reader.readLine();
			System.out.println("sever answor " + message);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
