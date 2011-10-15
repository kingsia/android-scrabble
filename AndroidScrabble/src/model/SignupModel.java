package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.content.Context;
import android.scrabble.R;
import android.util.Log;

import util.ResponseObject;
import util.SendObject;
import util.SendableAction;

public class SignupModel implements IModel{
	
	private Context context;
	private Socket socket = null;
	private ObjectInputStream is = null;
	private ObjectOutputStream out = null;
	
	public static final int SIGNUP_NOT_OK = 0;
	public static final int SIGNUP_OK = 1;
	
	public SignupModel(Context c){
		context = c;
		initSocket();
	}
	
	/*
	 * Initiates the socket and it's streams
	 */
	public void initSocket(){
		try {
			socket = new Socket(context.getString(R.string.serverip), 7896);
			
			is = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
		}
		catch(UnknownHostException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/*
	 * Sends a request to the server that "username" wants to signup
	 */
	public int sendLoginRequest(String username){
		ResponseObject retrieved = null;
		try{
			SendObject object = new SendObject(SendableAction.SIGN_UP, username);
			
			out.writeUnshared(object);
			out.flush();
			if(socket.getSoTimeout() == 0){
				socket.setSoTimeout(4000);	//	max time to wait for response, 4 secs
			}
			
			retrieved = getServerAnswer();
		}
		catch(IOException io){
			io.printStackTrace();
		}
		
		int res = evaluate(retrieved, username);
		return res;
	}

	/*
	 * Retrieves the answer from the server
	 */
	private ResponseObject getServerAnswer() {
		ResponseObject data = null;
		try {
			do{	//	wait until the data is read. must take less than socket.getSoTimeout() secs.
				data = (ResponseObject)is.readUnshared();
			}while(data == null);
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		catch(StreamCorruptedException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return data;
	}

	/*
	 * Evaluates the answer from the server and returns an int that is
	 * SignupModel#SIGNUP_NOT_OK, SignupModel#SIGNUP_OK or an error.
	 */
	private int evaluate(ResponseObject obj, String username) {
		String[] possibleOutcome = {
				"Sorry, the username "+username+" is already taken. Please choose another one.",
				"You are now signed up, welcome "+username
		};

		for(int i = 0; i<possibleOutcome.length; i++){
			if(obj.getObject().toString().equalsIgnoreCase(possibleOutcome[i])){
				return i;
			}
		}
		
		return (SIGNUP_OK+1);	//error
	}

	@Override
	public void dispose(){
		try {
			socket.close();
			Log.d("d�dats?", socket.isClosed()+" "+socket);
		}
		catch(IOException e){
			Log.e("Socket close", e.getMessage());
		}
		socket = null;
	}
}