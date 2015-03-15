package reactor;
import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;


public class StreamUpdateProfileEventHandler implements EventHandler {

	private static final int DATA_SIZE = 1024;
	private static final int TOKEN_NUM = 5;
	
	@Override
	public String getHandler() {
		return "0x6001";
	}

	public void handleEvent(InputStream is){
		
		try{
		byte[] buffer = new byte[DATA_SIZE];
		is.read(buffer);
		String data = new String(buffer);
		
		String[] params = new String[TOKEN_NUM];
		StringTokenizer token = new StringTokenizer(data, "|");
		
		int i = 0;
		while(token.hasMoreTokens()){
			params[i] = token.nextToken();
			++i;
		}	
		updateProfile(params);
		
	}catch(IOException e){
		e.printStackTrace();	
	}
}
	
	private void updateProfile(String[] param){
		System.out.println("UpdateProfile ID: " + param[0] + ", password: " + param[1] +", name: " + param[2] + ", age: " + param[3] + ", gender: " + param[4]);
	}
}
