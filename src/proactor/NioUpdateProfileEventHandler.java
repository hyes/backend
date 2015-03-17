package proactor;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.StringTokenizer;


public class NioUpdateProfileEventHandler implements NioEventHandler{
	private static final int TOKEN_NUM = 5;
	
	private AsynchronousSocketChannel channel;
	private ByteBuffer buffer;

	@Override
	public String getHeader() {
		return "0x6001";
	}

	@Override
	public int getDataSize() {
		return 1024;
	}

	@Override
	public void initialize(AsynchronousSocketChannel channel, ByteBuffer buffer) {
		this.channel = channel;
		this.buffer = buffer;
		
	}
	
	public void completed(Integer result, ByteBuffer buffer) {
		if(result == -1){
			try{
				channel.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}else if(result > 0){
			buffer.flip();
			String msg = new String(buffer.array());
			String[] params = new String[TOKEN_NUM];
			StringTokenizer token = new StringTokenizer(msg, "|");
			int i = 0;
			while(token.hasMoreTokens()){
				params[i] = token.nextToken();
				i++;
			}
			updateProfile(params);
			
			try{
				buffer.clear();
				channel.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}

	}
	private void updateProfile(String[] param){
		System.out.println("UpdateProfile ID: " + param[0] + ", password: " + param[1] +", name: " + param[2] + ", age: " + param[3] + ", gender: " + param[4]);
	}

	@Override
	public void failed(Throwable exc, ByteBuffer attachment) {
		// TODO Auto-generated method stub
		
	}
}
