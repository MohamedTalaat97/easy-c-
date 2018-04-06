package com.example.android.easyc.Connections;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.provider.SyncStateContract;
import android.text.format.Formatter;

import java.lang.ref.WeakReference;
import java.net.InetAddress;

/**
 * Created by KhALeD SaBrY on 05-Apr-18.
 */

public class NetworkWifiConnection extends AsyncTask<Void, Void, Void> {

    private static final String TAG = SyncStateContract.Constants.DATA + "nstask";
    private ConnectionDb connectionDb = ConnectionDb.getInstance();

    private WeakReference<Context> mContextRef;

    public NetworkWifiConnection(Context context) {
        mContextRef = new WeakReference<Context>(context);
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            Context context = mContextRef.get();

            if (context != null) {

                ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

                WifiInfo connectionInfo = wm.getConnectionInfo();
                int ipAddress = connectionInfo.getIpAddress();
                String ipString = Formatter.formatIpAddress(ipAddress);


                String prefix = ipString.substring(0, ipString.lastIndexOf(".") + 1);

                for (int i = 0; i < 255; i++) {
                    if (connectionDb.checkConnection())
                        return null;
                    String testIp = prefix + String.valueOf(i);

                    InetAddress address = InetAddress.getByName(testIp);
                    boolean reachable = address.isReachable(50);
                    String hostName = address.getCanonicalHostName();

                    if (reachable) {
                        connectionDb.setHost(testIp);
                        connectionDb.serverConnect();
                    }
                    /*
                    if (i == 254)
                        if (!connectionDb.checkConnection())
                            i = 0;
                            */
                }
            }
        } catch (Exception e) {

        } catch (Throwable t) {
        }

        return null;
    }
}