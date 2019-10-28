import java.lang.Thread;
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class ChatClient{
	private String name;
	private boolean isRunning;
	private String hostname;
	private Socket socket;
	private int port;

	public ChatClient(String name, String hostname, int port){
		this.name = name;
		this.isRunning = true;
		this.port = port;
		this.hostname = hostname;
		try{
			this.socket = new Socket(hostname, port);
		}catch(Exception e){}
	}

	public String getName(){
		return this.name;
	}

	public Socket getSocket(){
		return this.socket;
	}

	public boolean isRunning(){
		return this.isRunning;
	}

	public void closeSocket(){
		try{
			this.socket.close();
		}catch(IOException e){}
	}

	public void setIsRunning(boolean value){
		this.isRunning = value;
	}

	public static class MessageReceiver extends Thread{
		private ChatClient client;
		private DataInputStream in;

		public MessageReceiver(ChatClient client){
			this.client = client;
			try{
				this.in = new DataInputStream(client.getSocket().getInputStream());
			}catch(IOException e){}
		}

		public void run(){
			while(client.isRunning()){
				String messageFromServer = "";
				try{
					messageFromServer = in.readUTF();
					String[] tokens = messageFromServer.split(":", 2);
					if(! tokens[0].equals(client.getName()) && client.isRunning())
						System.out.println(messageFromServer);
				}catch(IOException e){}	
			}
		}
	}

	public static class MessageSender extends Thread{
		private ChatClient client;
		private DataOutputStream out;

		public MessageSender(ChatClient client){
			this.client = client;
			try{
				this.out = new DataOutputStream(client.getSocket().getOutputStream());
				out.writeUTF("(" + client.getName() + " entrou)");
			}catch(IOException e){}
		}

		public void run(){
			Scanner scan = new Scanner(System.in);
			while(client.isRunning()){
				String messageToServer = scan.nextLine();
				try{
					if(messageToServer.equals("exit()")){
						out.writeUTF("(" + client.getName() + " saiu)");
						out.writeUTF("exit()");
						client.setIsRunning(false);
						client.closeSocket();
					}else{
						out.writeUTF(client.getName() + ": " + messageToServer);
					}
				}catch(IOException e){}
			}
		}		
	}

	public static void main(String[] args){
		if(args.length != 3){
			System.out.println("Erro: falta informar NOME DE USUÁRIO e/ou LOCALHOST e/ou NÚMERO DA PORTA (nesta ordem) via linha de comando");
		}else{
			String name = args[0];
			String hostname = args[1];
			int port = Integer.valueOf(args[2]);
			ChatClient client = new ChatClient(name, hostname, port);
			MessageSender sender = new MessageSender(client);
			MessageReceiver receiver = new MessageReceiver(client);
			sender.start();
			receiver.start();
		}
	}
}