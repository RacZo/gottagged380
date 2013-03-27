package com.gottagged380;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;


public class WelcomeMenu extends Activity {

	private AccountManager accMgr = null;
	private WelcomeMenu wMenu;
    AccountManagerFuture<Bundle> futur;
	
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		wMenu = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome_menu);
		
		List<String> list = new ArrayList<String>();
	    Spinner emailSpinner;
		
		
		try{
			accMgr = AccountManager.get(this);
			Account [] accounts = accMgr.getAccountsByType("com.google");
			String accountsList = "Accounts: " + accounts.length + "\n";
			
			for(Account account : accounts)
				accountsList += account.toString() + "\n";
			
			for(int i =0; i < accounts.length; i++)
				list.add(accounts.toString());
			
				setMessage(accountsList);
			}
			catch(Exception e){
				setMessage(e.toString());
			}
			
		    emailSpinner = (Spinner) findViewById(R.id.spinner);
		    ArrayAdapter<String> emailAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
		   // emailAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		    emailSpinner.setAdapter(emailAdapter);
		
			Button loginButton = (Button) findViewById(R.id.login);
			loginButton.setOnClickListener(mLoginListener);
	}

    private OnClickListener mLoginListener = new OnClickListener() {
        
		public void onClick(View v) {
            try {
                Account [] accounts = accMgr.getAccountsByType("com.google");
                if (accounts.length == 0) {
                    setResult("No Accounts");
                    return;
                }
                
                Account account = accounts[0];  
                futur = accMgr.getAuthToken(account, "mail", null, wMenu, new GetAuthTokenCallback(), null);
                                
            } catch (Exception e) {
                setResult(e.toString());
            }
        }
    };
    
    private class GetAuthTokenCallback implements AccountManagerCallback<Bundle> {
        public void run(AccountManagerFuture<Bundle> result) {
                Bundle bundle;
                try {
                        bundle = result.getResult();
                        Intent intent = (Intent)bundle.get(AccountManager.KEY_INTENT);
                        Intent intent2 = new Intent("com.gottagged380.MAINMENU");
                        
                        if(intent != null) {
                            // User input required
                        } else {
                          //  _this.setResult("Token:  " + bundle.getString(AccountManager.KEY_AUTHTOKEN));
                            startActivity(intent2);
                            finish();
                        }
                } catch (Exception e) {
                    wMenu.setResult(e.toString());
                }
        }
    };
		
	public void setResult(String msg) {
    TextView tv = (TextView) this.findViewById(R.id.auth);
    tv.setText(msg);
	}

	public void setMessage(String msg) {
    TextView tv = (TextView) this.findViewById(R.id.auth);
    tv.setText(msg);
	}

}
