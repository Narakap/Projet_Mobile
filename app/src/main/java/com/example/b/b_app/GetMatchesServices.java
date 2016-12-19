package com.example.b.b_app;

import android.app.IntentService; 
import android.content.Intent;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class GetMatchesServices extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String  ACTION_GET_ALL_MATCHES = "com.example.b.b_app.action.FOO";
    private static final String ACTION_BAZ = "com.example.b.b_app.action.BAZ";


    public GetMatchesServices() {
        super("GetMatchesServices");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionMatch(Context context) {
        Intent intent = new Intent(context, GetMatchesServices.class);
        intent.setAction(ACTION_GET_ALL_MATCHES);

        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, GetMatchesServices.class);
        intent.setAction(ACTION_BAZ);

        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET_ALL_MATCHES.equals(action)) {
                handleActionMatches();

            } else if (ACTION_BAZ.equals(action)) {


            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionMatches() {
        // TODO: Handle action get_all_biers
        Log.i("tag", "log0");

        URL url = null;

        try {
            url = new URL("https://s3-us-west-1.amazonaws.com/riot-api/seed_data/matches1.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();

            if (HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
                copyInputStreamToFile(conn.getInputStream(), new File(getCacheDir(), "matches1.json"));
                Log.d("message", "Matches téléchargés");
            }
        } catch (MalformedURLException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(SecondeActivity.MATCHES_UPDATE));
    }



    private void copyInputStreamToFile(InputStream in, File file ) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz() {
        // TODO: Handle action Baz
        //throw new UnsupportedOperationException("Not yet implemented");
    }
}
