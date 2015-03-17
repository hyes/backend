package proactor;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ServerInitializer {
	
	private static int PORT = 5010;
	private static int threadPoolSize = 30;
	private static int initialSize = 20;
	private static int backlog = 50;

	public void startServer() {
		
		System.out.println("PROACTOR_SERVER START! PORT: "+ PORT);
		
		NioHandleMap handleMap = new NioHandleMap();
		
		NioEventHandler sayHelloHandler = new NioSayHelloEventHandler();
		NioEventHandler sayUpdateProfileHandler = new NioUpdateProfileEventHandler();
		NioEventHandler fileWriteEventHandler = new NioFileWriteEventHandler();
		
		handleMap.put(sayHelloHandler.getHeader(), sayHelloHandler);
		handleMap.put(sayUpdateProfileHandler.getHeader(), sayUpdateProfileHandler);
		handleMap.put(fileWriteEventHandler.getHeader(), fileWriteEventHandler);
		
		ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);
		
		try{
			AsynchronousChannelGroup group = AsynchronousChannelGroup.withCachedThreadPool(executor, initialSize);
			
			AsynchronousServerSocketChannel listener = AsynchronousServerSocketChannel.open(group);
			listener.bind(new InetSocketAddress(PORT), backlog);
			
			listener.accept(listener, new Dispatcher(handleMap));
		
		}catch(IOException e){
			e.printStackTrace();
		}
				

	}

}
