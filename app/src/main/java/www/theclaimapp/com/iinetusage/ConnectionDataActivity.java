package www.theclaimapp.com.iinetusage;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import www.theclaimapp.com.iinetusage.Model.ConnectionData;
import www.theclaimapp.com.iinetusage.Model.UsageData;
import www.theclaimapp.com.iinetusage.Parser.JSONParseConnectionData;


public class ConnectionDataActivity extends Activity{


    TextView rawData;
    ProgressBar pb;
    String loginURL;
    TextView title;

    TextView anniversary;
    TextView days_remaining;
    TextView ip;
    TextView on_since;
    TextView peakUsage;
    TextView peakName;

    Double peakUsageMb;

    TextView usageList;


    List<BgTask> tasks;
    List<UsageData> usageDataList;
    List<ConnectionData> conDataList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usagedata_activity);

        pb = (ProgressBar) findViewById(R.id.progressBar2);

        anniversary = (TextView) findViewById(R.id.tvMonthStartData);
        days_remaining = (TextView) findViewById(R.id.tvDaysRemaingData);
        ip = (TextView) findViewById(R.id.tvIPData);
        on_since = (TextView) findViewById(R.id.tvOnSinceData);
        peakUsage = (TextView) findViewById(R.id.tvNameUsageData);
        peakName = (TextView) findViewById(R.id.tvNamePeakData);




        tasks = new ArrayList<>();

        loginURL = getIntent().getExtras().getString("loginURL");
        grabData(loginURL);
   }

    private void grabData(String uri) {
        BgTask task = new BgTask();
        task.execute(uri);
    }
  /*  protected void  updateDisplay() {


        if ( usageDataList != null) {
            for (UsageData usageData : usageDataList) {

                peakName.setText(usageData.getName());
                peakUsage.setText(usageData.getUsed());
 }
        }
    }
*/
    protected void  updateConnectionDetails() {


        if ( conDataList != null) {
            for (ConnectionData connectionData : conDataList) {

                ip.setText(connectionData.getIp());
                on_since.setText(connectionData.getOn_since());
                anniversary.setText(connectionData.getAnniversary());
                days_remaining.setText(connectionData.getDays_remaining());
                peakName.setText(connectionData.getName());
                peakUsage.setText(connectionData.getUsed());

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

            conDataList= JSONParseConnectionData.parseFeed(result);
            updateConnectionDetails();

           tasks.remove(this);
           if (tasks.size() == 0) {
               pb.setVisibility(View.INVISIBLE);
            }
        }
    }
}



