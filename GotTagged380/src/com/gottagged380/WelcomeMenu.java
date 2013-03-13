package com.gottagged380;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;


public class WelcomeMenu extends Activity {

	private AccountManager accMgr = null;
	private WelcomeMenu _this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		_this = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome_menu);
		
		try{
			accMgr = AccountManager.get(this);
			Account [] accounts = accMgr.getAccounts();
			String accountsList = "Accounts: " + accounts.length + "\n";
			
			for(Account account : accounts){
				accountsList += account.toString() + "\n";
			}
				setMessage(accountsList);
			}
			catch(Exception e){
				setMessage(e.toString());
			}
			Button loginBtn = (Button) findViewById(R.id.login);
			loginBtn.setOnClickListener(mLoginListener);
			}

    private OnClickListener mLoginListener = new OnClickListener() {
        
        @SuppressWarnings("deprecation")
		public void onClick(View v) {
            try {
                Account [] accounts = accMgr.getAccounts();
                if (accounts.length == 0) {
                    setResult("No Accounts");
                    return;
                }
                
                Account account = accounts[0];
                accMgr.getAuthToken(account, "mail", false, new GetAuthTokenCallback(), null);
                
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
                        if(intent != null) {
                            // User input required
                            startActivity(intent);
                        } else {
                            _this.setResult("Token: " + bundle.getString(AccountManager.KEY_AUTHTOKEN));
                        }
                } catch (Exception e) {
                    _this.setResult(e.toString());
                }
        }
    };

	public void setResult(String msg) {
    TextView tv = (TextView) this.findViewById(R.id.result);
    tv.setText(msg);
	}

	public void setMessage(String msg) {
    TextView tv = (TextView) this.findViewById(R.id.message);
    tv.setText(msg);
	}

}
