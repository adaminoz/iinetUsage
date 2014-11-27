package www.theclaimapp.com.iinetusage;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import www.theclaimapp.com.iinetusage.Model.UserCreds;
import www.theclaimapp.com.iinetusage.Parser.JSONParseUserCreds;


public class MainActivity extends ActionBarActivity{


    //Button login;
    String url = "https://toolbox.iinet.net.au/cgi-bin/api.cgi?";
    public static String username;
    String password;
    CheckBox cbSaveDetails;
    ProgressBar pb;

    EditText etUsername;
    EditText etPassword;

    String token;
    String sToken;
    String loginURL;

    //testing data
    TextView tvAuthToken;
    TextView tvSToken;
    TextView rawloginURL;

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
        pb = (ProgressBar) findViewById(R.id.progressBar);

        //testing data
        tvAuthToken = (TextView) findViewById(R.id.tvRawJSON);
        tvSToken = (TextView) findViewById(R.id.tvStoken);
        rawloginURL = (TextView) findViewById(R.id.loginURL);

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

        String credsurl = (url + "_USERNAME=" + username + "&_PASSWORD=" + password);

        grabData(credsurl);



    }
  //  public void onTestgo(View view) {




    protected void  updateDisplay() {


        if (userCredsList != null) {
            for (UserCreds userCreds : userCredsList) {

                tvAuthToken.setText(userCreds.getToken());

                tvSToken.setText(userCreds.getS_token());

      //          token = userCreds.getToken();
      //          sToken = userCreds.getS_token();

                loginURL = (url + "Usage&_TOKEN=" + userCreds.getToken() + "&_SERVICE=" + userCreds.getS_token());

                rawloginURL.setText(loginURL);

                Intent loginIntent = new Intent(this, UsageActivity.class);
                loginIntent.putExtra("loginURL", loginURL);

                startActivity(loginIntent);
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
                        pb.setVisibility(View.VISIBLE);
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
            pb.setVisibility(View.INVISIBLE);
            }
        }
        }
}


