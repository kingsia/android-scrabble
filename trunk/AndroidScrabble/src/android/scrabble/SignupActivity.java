package android.scrabble;

import java.util.Observable;
import java.util.Observer;

import model.SignupModel;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignupActivity extends Activity implements Observer, OnClickListener{
	
	private SignupModel model = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        
        String username = getIntent().getStringExtra("USERNAME");
        setTakenName(username);
        
        model = new SignupModel(getBaseContext());
        model.addObserver(this);
    	
    	Button submit = (Button)(findViewById(R.id.submit));
    	submit.setOnClickListener(this);
    }

	@Override
	public void onClick(View v){
        EditText input = (EditText)(findViewById(R.id.username));
        
        model.sendLoginRequest(input.getText().toString());
	}
	
	public void setTakenName(String username){
        TextView nameTaken = (TextView)(findViewById(R.id.nameTaken));
    	nameTaken.setText("The username "+username+" does not exist!");
	}

	@Override
	public void update(Observable observable, Object data){
        int resCode = ((Integer)(data)).intValue();
		
        if(resCode == SignupModel.SIGNUP_NOT_OK){
            EditText input = (EditText)(findViewById(R.id.username));
            setTakenName(input.getText().toString());
        }
        else if(resCode == SignupModel.SIGNUP_OK){
        	showSignedUpDialog("You are now signed up! Welcome!");
        }
        else{
        	//TODO: Take care of error
        }
	}
	
	public void showSignedUpDialog(String message){
		AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
		builder.setMessage(message);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				finish();
			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
	}
}