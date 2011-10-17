package network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import util.SendObject;

import android.util.Log;

public class ClientOutput{

	Socket s = null;
	ObjectOutputStream out = null;
	
	public ClientOutput(Socket s) {
		this.s = s;
		try {
			out = new ObjectOutputStream(s.getOutputStream());
		} catch (IOException e) {
			Log.d("alpha", "IOE", e);
		}
	}
	
	public void send(SendObject so){
		try {
			out.writeUnshared(so);
			out.flush();
		} catch (IOException e) {
			Log.d("alpha", "IOE", e);
		}
	}
}