import java.lang.Thread;
import java.util.ArrayList;
import java.net.*;
import java.io.*;

public class ChatServer extends Thread{
	private static ArrayList<DataInputStream> inputDataStreams = new ArrayList<DataInputStream>();
	private static ArrayList<DataOutputStream> outputDataStreams = new ArrayList<DataOutputStream>();
	private DataInputStream in;
	private DataOutputStream out;
	private Socket server;

	public ChatServer(DataInputStream in, DataOutputStream out, Socket server){
		this.in = in;
		this.out = out;
		addInputDataStream(in);
		addOutputDataStream(out);
		this.server = server;
		System.out.println("Servidor criou thread para cliente");
	}

	private static void addInputDataStream(DataInputStream in){
		inputDataStreams.add(in);
	}

	private static void addOutputDataStream(DataOutputStream out){
		outputDataStreams.add(out);
	}

	private void broadcastMessage(String messageToClients){
		for(DataOutputStream out : outputDataStreams){
			try{
				out.writeUTF(messageToClients);
			}catch(IOException e){}
		}
	}

	private void removeDataStreams(DataInputStream in, DataOutputStream out){
		inputDataStreams.remove(in);
		outputDataStreams.remove(out);
	}

	public void run(){
		while(true){
			String messageFromClient = "";
			try{
				messageFromClient = in.readUTF();
				System.out.println("MENSAGEM RECEBIDA: " + messageFromClient);
			}catch(IOException e){
				System.out.println(e.getMessage());
			}
			if(messageFromClient.equals("exit()")){
				removeDataStreams(in, out);
				try{
					server.close();
					System.out.println("Servidor removeu cliente");
				}catch(IOException e){
					System.out.println(e.getMessage());
				}
				return;
			}else{
				broadcastMessage(messageFromClient);
			}
		}

	}

	public static void main(String[] args){
		ServerSocket serverSocket = null;
		int port = 5000;
		Socket server = null;
		try{
			serverSocket = new ServerSocket(port);
		}catch(IOException e){}
		while(true){
			try{
				server = serverSocket.accept();
				DataInputStream in = new DataInputStream(server.getInputStream());
				DataOutputStream out = new DataOutputStream(server.getOutputStream());
				ChatServer chatServer = new ChatServer(in, out, server);
				chatServer.start();
			}catch(Exception e){}
		}
	}
}