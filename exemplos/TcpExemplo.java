import java.net.*;
import java.io.*;

public class TcpExemplo {
	public static void main(String[] args) throws Exception {

		if(args.length > 0){
			Socket cliente = new Socket(args[0], 80);
			System.out.println(cliente.getInetAddress());
			cliente.close();
		}
	}
}