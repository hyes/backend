import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Dispatcher {
	
	private final int HEADER_SIZE = 6;

	public void dispatch(ServerSocket serverSocket, HandleMap handleMap) {
		
		try{
			Socket socket = serverSocket.accept();
			demultiplex(socket, handleMap);
		}catch(IOException e){
			e.printStackTrace();
		}
	}


	public void demultiplex(Socket socket, HandleMap handleMap){
		try{
			InputStream is = socket.getInputStream();
			
			byte[] buffer = new byte[HEADER_SIZE];
			is.read(buffer);
			String header = new String(buffer);
			
			handleMap.get(header).handleEvent(is);
			
			socket.close();
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}

}
