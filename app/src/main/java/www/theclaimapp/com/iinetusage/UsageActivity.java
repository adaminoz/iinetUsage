package www.theclaimapp.com.iinetusage;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import www.theclaimapp.com.iinetusage.Model.UsageData;
import www.theclaimapp.com.iinetusage.Parser.JSONParseUsageData;


public class UsageActivity extends Activity{


    TextView rawData;
    ProgressBar pb;
    String loginURL;
    TextView title;

    TextView tvNameValue;


    List<BgTask> tasks;
    List<UsageData> usageDataList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usagedata_activity);
       
        tvNameValue = (TextView) findViewById(R.id.tvNameValue);
        pb = (ProgressBar) findViewById(R.id.progressBar2);

        tasks = new ArrayList<>();

        loginURL = getIntent().getExtras().getString("loginURL");
        grabData(loginURL);
   }

    private void grabData(String uri) {
        BgTask task = new BgTask();
        task.execute(uri);
    }
    protected void  updateDisplay() {


        if ( usageDataList != null) {
            for (UsageData usageData : usageDataList) {

                tvNameValue.setText(usageData.getName());

 }

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

            usageDataList = JSONParseUsageData.parseFeed(result);
            updateDisplay();

           tasks.remove(this);
           if (tasks.size() == 0) {
               pb.setVisibility(View.INVISIBLE);
            }
        }
    }
}



