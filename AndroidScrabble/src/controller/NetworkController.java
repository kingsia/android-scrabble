package controller;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;

import android.util.Log;

import util.ResponseObject;
import util.SendObject;

import network.ClientOutput;

/**
 * Class that represents a "Server".
 * The server holds a socket which the phones connect to.
 * 
 * The server starts a new Thread (ServerController) which listens to the sockets,
 * and take care of all info. The ServerController also makes sure that the server
 * listens to the models that it uses.
 * 
 */
public class NetworkController extends Observable{

	private static NetworkController controller = null;
	private ClientOutput co = null;
	private Socket s = null;
	private int port = 7896;
	
	private NetworkController(String serverip){
		try {
			s = new Socket(serverip, port);
		}
		catch(UnknownHostException e){
			Log.e("socket e", e.getMessage());
		}
		catch(IOException e){
			Log.e("socket e", e.getMessage());
		}
		co = new ClientOutput(s);
	}

	public static NetworkController getInstance(String ip){
		if(controller == null){
			controller = new NetworkController(ip);
		}
		return controller;
	}
	
	public void redirect(ResponseObject so){
		setChanged();
		notifyObservers(so);
	}
	
	public Socket getSocket(){
		return s;
	}
	
	public void send(SendObject o){
		Log.d("send", "try");
		co.send(o);
	}
}