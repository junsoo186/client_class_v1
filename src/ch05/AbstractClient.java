package ch05;
// 2단계 - 상속 활용 리팩토링  

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class AbstractClient {
	
	private Socket socket;
	private BufferedReader readerStream;
	private PrintWriter writerStream;
	private BufferedReader keyboardReader;
	
	//set 메서드
	
	protected void setSocket(Socket socket) {
		this.socket = socket; 
	}
	
	protected Socket getSocket() {
		return this.socket;
	}
	
	public final void run() {
		try {
			setupClient();
			setupStream();
			startService();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			cleanup();
		}
		
	}
	
	
	protected abstract void setupClient() throws IOException;
	
//	protected abstract void connection() throws IOException;
	
	private void setupStream() throws IOException {
		readerStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		writerStream = new PrintWriter(socket.getOutputStream(), true);
		keyboardReader = new BufferedReader(new InputStreamReader(System.in));
		
	}
	
	private void startService() {
		Thread readThread = createReadThread();
		Thread writeThread = createWriteThread();
		
		readThread.start();
		writeThread.start();
		try {
			readThread.join();
			writeThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private Thread createReadThread() {
		return new Thread(() -> {
			try {
				String msg; 
				while(  (msg = readerStream.readLine()) != null ) {
					System.out.println("client 측 msg : " + msg);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		});
	}
	private Thread createWriteThread() {
		return new Thread(() -> {
			try {
				String msg;
				while( (msg = keyboardReader.readLine()) != null ) {
					writerStream.println(msg);
					writerStream.flush();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	private void cleanup() {
		try {
			if(socket != null) {
				socket.close();
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	

}
