package ch05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

// 1단계 - 함수로 분리 해서 리팩토링
public class MultiThreadcCient {

	public static void main(String[] args) {
		
		System.out.println(">>>손님1 입장<<<");
		
		try (Socket socket =	new Socket("",5000)){
		System.out.println("--서버와 연결 되었습니다.--");
		BufferedReader readerStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter writerStream = new PrintWriter(socket.getOutputStream(),true);
		
		BufferedReader keyboredReader = new BufferedReader(new InputStreamReader(System.in));
		
		startReadThread(readerStream);
		startWriterThread(writerStream, keyboredReader);
		
		} catch (Exception e) {
			e.printStackTrace();
		}

	} // end of main
////////////////////////////////////
// 서버로 부터 데이터를 읽는 스레드
 private static void startReadThread(BufferedReader bufferedReader) {
	 Thread readThread = new Thread(()->{ 
		try {
		
			String serverMessage;
			while((serverMessage = bufferedReader.readLine()) != null) {
				System.out.println("서버에서 온 MSG :" + serverMessage);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	 });
	 readThread.start();
 }

// 클라 측에서 --> 서버로 데이터를 보내는 기능

private static void startWriterThread(PrintWriter printWriter,BufferedReader keyboardReader) {
	Thread writerThread = new Thread(()->{
		try {
			String clientMessage;
			while((clientMessage = keyboardReader.readLine()) != null) {
				printWriter.println(clientMessage);
				printWriter.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	});
	writerThread.start();
}

private static void waitForThreadToEnd(Thread thread) {
	try {
		thread.join();
	}catch (Exception e) {
		e.printStackTrace();
	}
}

}
