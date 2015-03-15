package reactor;
import java.io.File;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class ServerInitializer {

	public void startServer(){
		
		int port = 5010;
		System.out.println("Server on : " + port);
		System.out.println("Reactor의 serverInitializer");
		
		
		Reactor reactor = new Reactor(port);
//		System.out.println("qw");
		
		try{
			Serializer serializer = new Persister();
			File source = new File("HandlerList.xml");
			ServerListData serverList = serializer.read(ServerListData.class,  source);
			
			for(HandlerListData handlerListData : serverList.getServer()){
				if("server1".equals(handlerListData.getName())){
					List<HandlerData> handlerList = handlerListData.getHandler();
					for(HandlerData handler : handlerList){
						try{
							reactor.registerHandler(handler.getHeader(), (EventHandler) Class.forName(handler.getHandler()).newInstance());
						}catch(InstantiationException e){
							e.printStackTrace();
						}catch(IllegalAccessException e){
							e.printStackTrace();
						}catch(ClassNotFoundException e){
							e.printStackTrace();
						}
					}
					break;
			}
	
		}
		
		
		
//		reactor.registerHandler(new StreamSayHelloEventHandler());
//		reactor.registerHandler(new StreamUpdateProfileEventHandler());
		
		reactor.startServer();
		
	}catch(Exception e){
		e.printStackTrace();
	}
}
}	
//		try{
//		ServerSocket serverSocket = new ServerSocket(port);
//		Dispatcher dispatcher = new Dispatcher();
//		
//			while(true){
//				dispatcher.dispatch(serverSocket);
//			}
////		connection = serverSocket.accept();
////		InputStreamReader isReader = new InputStreamReader(connection.getInputStream());
////		BufferedReader bufferedReader = new BufferedReader(isReader);
////		String line = bufferedReader.readLine();
////		
////		System.out.println(line);
//		
//		}catch (IOException e){
//			e.printStackTrace();
//		}
