package controller;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Observable;
import java.util.Observer;

import model.GameLogic;
import model.UserLogic;
import network.ServerThread;

import util.ErrorHandler;
import util.SendObject;


/**
 * Class that represents a "Server".
 * The server holds a socket which the phones connect to.
 * 
 * The server starts a new Thread (ServerController) which listens to the sockets,
 * and take care of all info. The ServerController also makes sure that the server
 * listens to the models that it uses.
 * 
 * @author Magnus
 */
public class ServerController implements Observer{

	private ServerThread thread = null;	//	The Thread that waits for output
	private UserLogic ul = null;
	private GameLogic gl = null;
	
	public ServerController(ServerSocket s){
		ul = new UserLogic();
		ul.addObserver(this);
		gl = new GameLogic();
		gl.addObserver(this);
	}

	public synchronized void redirect(SendObject so){
		switch(so.getAction()){
			case LOGIN:
				String s = (String)so.getObject();
				ul.login(s);
				break;
			case LOGOUT:
				String s2 = (String)so.getObject();
				ul.logout(s2);
				break;
			case SEARCH_PLAYER:
				break;
			case SIGN_UP:
				String s3 = (String)so.getObject();
				ul.signUp(s3);
				break;
			/*case GAME_DATA:
				//TODO: do gamelogic
				break;*/
			default:
				break;
		}
	}
	
	/**
	 * Send back data to phone here.
	 */
	public synchronized void update(Observable obs, Object obj) {
		System.out.println("Server retrieved "+obj.toString()+" from "+obs.toString());
		/*SendObject so = (SendObject)obj;
		try {
			out.send(so);
		}
		catch(IOException e){
			ErrorHandler.report("Server controller cant send object: "+e.getMessage());
		}*/
	}

	public void setThread(ServerThread st) {
		thread = st;
	}
}