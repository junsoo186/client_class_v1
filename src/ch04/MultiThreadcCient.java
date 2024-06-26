package ch04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MultiThreadcCient {
	
		
		public static void main(String[] args) {
			
			System.out.println("### 클라이언 실행 ### ");
			
			try {
				
				Socket socket = new Socket("192.168.0.48", 5000);
				System.out.println("*** connected to the Server  ***");
				
				PrintWriter socketWriter = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader socketReader =
						new BufferedReader(new InputStreamReader(socket.getInputStream()));
				BufferedReader keyboardReader =
						new BufferedReader(new InputStreamReader(System.in));
				
				// 서버로 부터 데이터를 읽는 스레드 
				Thread readThread = new Thread(() -> {
					//while <---
					String serverMessage;
					try {
						while((serverMessage = socketReader.readLine()) != null) {
							System.out.println("서버에서 온 메세지 : " + serverMessage);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					
				});
				
				// 서버에게 데이터를 보내는 스레드 
				Thread writeThread = new Thread(() -> {
					
					try {
						String clientMessage;
						while((clientMessage = keyboardReader.readLine()) != null ) {
							//1. 키보드에서 데이터 응용프로그램 안으로 입력 받아서
							// 2. 서버측
							socketWriter.println(clientMessage);
							
							
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					
				});
				
				readThread.start();
				writeThread.start();
				
				readThread.join();
				writeThread.join();
				
				System.out.println(" 클라이언트 측 프로그램 종료 ");
				
			} catch (Exception e) {
			
				
				
			}
			
				
			}

		} 
