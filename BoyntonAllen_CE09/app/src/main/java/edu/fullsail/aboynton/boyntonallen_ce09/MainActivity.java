// Allen Boynton

// JAV1 - 1703

// MainActivity.java

package edu.fullsail.aboynton.boyntonallen_ce09;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import edu.fullsail.aboynton.boyntonallen_ce09.model.Book;
import edu.fullsail.aboynton.boyntonallen_ce09.parser.BookJSONParser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity.TAG";

    private GridView bookGridView;
    private ProgressBar progressBar;
//    private ArrayList<GetBooksTask> tasks;
    private ArrayList<Book> bookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: ON");

        bookGridView = (GridView) findViewById(R.id.bookGridView);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        if (isOnline()) {
            requestData();
        }
        else {
            Toast.makeText(this, R.string.noNetworkToast, Toast.LENGTH_LONG).show();
        }

        bookList = new ArrayList<>();

        Log.d(TAG, "onCreate: OFF");
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        Log.i(TAG, " ----> onOptionsItemSelected()");
//
//        if (item.getItemId() == R.id.action_get_data) {
//            if (isOnline()) {
//                requestData();
//            }
//            else {
//                Toast.makeText(this, R.string.noNetworkToast, Toast.LENGTH_LONG).show();
//            }
//        }
//        Log.i(TAG, " <---- onOptionsItemSelected()");
//        return false;
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        Log.i(TAG, " ----> onCreateOptionsMenu()");
//
//        getMenuInflater().inflate(R.menu.main, menu);
//
//        Log.i(TAG, " <---- onCreateOptionsMenu()");
//        return true;
//    }

    private void requestData() {
        Log.i(TAG, " ----> requestData()");

        String apiUrl = "https://www.googleapis.com/books/v1/volumes?q=scorpions&orderBy=newest&key=AIzaSyDA1ZH96CVwdIshPOzfCUsWjKpibS6_JO8";

        GetBooksTask getBooksTask = new GetBooksTask();

        // Runs asynch so it operates quicker by running parallel not serially
        getBooksTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, apiUrl);

        Log.i(TAG, " <---- requestData()");
    }

    private void updateDisplay() {
        Log.i(TAG, " ----> updateDisplay()");

        //Use BookAdapter to display data
        bookGridView.setAdapter(new BookAdapter(this, bookList));

        Log.i(TAG, " <---- updateDisplay()");
    }

    private boolean isOnline() {
        Log.i(TAG, " <---- isOnline()");

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();

        Log.i(TAG, " <---- isOnline()");
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private class GetBooksTask extends AsyncTask<String, String, ArrayList<Book>> {

        private static final String TAG = "GetBooksTask.TAG";

        @Override
        protected void onPreExecute() {
            Log.i(TAG, " ----> onPreExecute()");

            progressBar.setVisibility(View.VISIBLE);

            Log.i(TAG, " <---- onPreExecute()");
        }

        @Override
        protected ArrayList<Book> doInBackground(String... params) {
            Log.i(TAG, " ----> doInBackground()");

            // Getting data and parsing Json asynchronously
            String content = NetConnection.getData(params[0]);
            bookList = BookJSONParser.parseFeed(content);

            Log.i(TAG, " <---- doInBackground()");
            return bookList;
        }

        @Override
        protected void onPostExecute(ArrayList<Book> result) {
            Log.i(TAG, " ----> onPostExecute()");

            progressBar.setVisibility(View.INVISIBLE);

            if (result == null) {
                Toast.makeText(MainActivity.this, R.string.noWebToast, Toast.LENGTH_LONG).show();
                return;
            }

            bookList = result;
            updateDisplay();
            Log.i(TAG, " <---- onPostExecute()");
        }
    }
}
