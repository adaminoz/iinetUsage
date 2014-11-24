package www.theclaimapp.com.iinetusage;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import www.theclaimapp.com.iinetusage.Model.UserCreds;
import www.theclaimapp.com.iinetusage.Parser.JSONParseUserCreds;


public class MainActivity extends ActionBarActivity {


    //Button login;
    String url = "https://toolbox.iinet.net.au/cgi-bin/api.cgi?";
    public static String username;
    String password;
    CheckBox cbSaveDetails;


    EditText etUsername;
    EditText etPassword;


    //testing data
    TextView rawJson;
    TextView sToken;


    //list views to grab data
    List<BgTask> tasks;
    List<UserCreds> userCredsList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        cbSaveDetails = (CheckBox) findViewById(R.id.cbRememberLogin);

        //testing data
        rawJson = (TextView) findViewById(R.id.tvRawJSON);
        sToken = (TextView) findViewById(R.id.tvStoken);


        tasks = new ArrayList<>();

        loadSavedPrefs();

    }

private void grabData(String uri) {
    BgTask task = new BgTask();
    task.execute(uri);
}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onLoginClick(View view) {


            getuserdetails();

            String loginurl = (url + "_USERNAME=" + username + "&_PASSWORD=" + password);

            grabData(loginurl);

            updateDisplay();

   //     Intent loginIntent = new Intent(this, UsageData.class);
  //      startActivity(loginIntent);
    }

protected void  updateDisplay() {


    //   rawJson.setText(token);

    if (userCredsList != null) {
        for (UserCreds userCreds : userCredsList) {
   //         rawJson.append(userCreds.token);
            rawJson.setText(userCreds.token);
            sToken.setText(userCreds.sToken);


        }

    }
}

protected void getuserdetails() {



    //if(cbSaveDetails.isChecked()) {

    //Save shared prefs
    if(cbSaveDetails.isChecked()) {
        SharedPreferences userDetails = getSharedPreferences("MYPREFS", 0);

        //set username to etUsername and password to etPassword
        SharedPreferences.Editor editor = userDetails.edit();
        editor.putString("username", etUsername.getText().toString());
        editor.putString("password", etPassword.getText().toString());
        editor.apply();

    }
        username = etUsername.getText().toString();

        password = etPassword.getText().toString();
}


    protected void loadSavedPrefs() {

        SharedPreferences userDetails = getSharedPreferences("MYPREFS", 0);

        etUsername.setText(userDetails.getString("username", ""));
        etPassword.setText(userDetails.getString("password", ""));

        }


    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    private class BgTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
          //  updateDisplay("Starting task");
            if (tasks.size() == 0) {
    //            pb.setVisibility(View.VISIBLE);
            }
            tasks.add(this);
     }

        @Override
        protected String doInBackground(String... params) {

            String content = HttpManager.getData(params[0]);
            return content;

        }

        @Override
        protected void onPostExecute(String result) {

            userCredsList = JSONParseUserCreds.parseFeed(result);
            updateDisplay();

            tasks.remove(this);
            if (tasks.size() == 0) {
                //              pb.setVisibility(View.INVISIBLE);
            }
        }
        }
}


