package in.premad.maisha_task.SocialLogins.LinkedIn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import in.premad.maisha_task.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISession;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class LinkedInProfileActivity extends AppCompatActivity {
    TextView user_detail;
    String firstName,lastName,userEmail;
    Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linked_in_profile);
        user_detail=(TextView)findViewById(R.id.userDetail);
        logout=(Button)findViewById(R.id.logout_button);

        fetchuserData();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LISessionManager.getInstance(getApplicationContext()).clearSession();
                Toast.makeText(getApplicationContext(),"Logout Successfully",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LinkedInProfileActivity.this, LinkedInLoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void fetchuserData() {
        String url = "https://www.linkedin.com/developers/apps/verification/098aedb0-a05c-4cd9-830c-b3c74468ea14)";

        APIHelper apiHelper = APIHelper.getInstance(getApplicationContext());
        apiHelper.getRequest(this, url, new ApiListener() {
            @Override
            public void onApiSuccess(ApiResponse apiResponse) {
                // Success!
                try {
                    JSONObject jsonObject = apiResponse.getResponseDataAsJson();
                    firstName = jsonObject.getString("firstName");
                    lastName = jsonObject.getString("lastName");
                    userEmail = jsonObject.getString("emailAddress");

                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("First Name " + firstName + "\n\n");
                    stringBuilder.append("Last Name " + lastName + "\n\n");
                    stringBuilder.append("Email " + userEmail);

                    user_detail.setText(stringBuilder);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onApiError(LIApiError liApiError) {
                // Error making GET request!
                Toast.makeText(getApplicationContext(),"API Error"+liApiError.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
}