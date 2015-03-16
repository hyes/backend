package testClient;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.rmi.UnknownHostException;


public class TestClient {

	
	public static void main(String[] arg){
		
		System.out.println("Client ON");
	
		while(true){
		try{
		String message;
		
		Socket socket = new Socket("127.0.0.1", 5010);
		OutputStream out = socket.getOutputStream();
		message = "0x5001|홍길동|22";
		out.write(message.getBytes());
		socket.close();
		
		Socket socket2 = new Socket("127.0.0.1", 5010);
		OutputStream out2 = socket2.getOutputStream();
		message = "0x6001|hong|0000|홍길동|22|남";
		out2.write(message.getBytes());
		socket2.close();
		
		
		}catch(UnknownHostException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		}
		
	}
}
