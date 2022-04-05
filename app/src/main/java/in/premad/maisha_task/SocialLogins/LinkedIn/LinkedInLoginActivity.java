package in.premad.maisha_task.SocialLogins.LinkedIn;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;

import in.premad.maisha_task.R;


public class LinkedInLoginActivity extends AppCompatActivity {
    Button loginBtn;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linked_in_login);
        loginBtn = (Button) findViewById(R.id.login_button);
        textView=(TextView)findViewById(R.id.textView);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginHandle();
            }
        });
    }
    public void loginHandle() {
        LISessionManager.getInstance(getApplicationContext()).init(LinkedInLoginActivity.this, buildScope(), new AuthListener() {
            @Override
            public void onAuthSuccess() {
                // Authentication was successful.  You can now do other calls with the SDK.

                Intent intent=new Intent(LinkedInLoginActivity.this,LinkedInProfileActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAuthError(LIAuthError error) {
                // Handle authentication errors
                Log.d("linkedin",error.toString());
                Toast.makeText(getApplicationContext(),"Login Error "+error.toString(),Toast.LENGTH_LONG).show();
            }
        }, true);
    }

    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.R_EMAILADDRESS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Add this line to your existing onActivityResult() method
        super.onActivityResult(requestCode, resultCode, data);
        LISessionManager.getInstance(getApplicationContext()).onActivityResult(this, requestCode, resultCode, data);
    }


}