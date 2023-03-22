package com.mw.fantasy;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.google.firebase.FirebaseApp;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.FirebaseUtils;

import org.acra.ACRA;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.engineio.client.transports.WebSocket;
import okhttp3.OkHttpClient;

public class AppController extends MultiDexApplication {

    // CHANGES
    private static Context context;
    private static Socket mSocket;
    private static final String SOCKET_URL = BuildConfig.SOCKET_URL;
//    private static final String SOCKET_URL = BuildConfig.SOCKET_URL;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        context = getApplicationContext();
        FirebaseApp.initializeApp(this);
        FirebaseUtils.initFirebase();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        try {
            ProviderInstaller.installIfNeeded(getApplicationContext());
            SSLContext sslContext;
            sslContext = SSLContext.getInstance("LL.14");
            sslContext.init(null, null, null);
            sslContext.createSSLEngine();
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException
                | NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
        ACRA.init(this);
    }

    public static Context getContext() {
        return context;
    }

    public static void expirySession(String message) {
        AppUtils.startLogoutService(getContext(), message, true);
    }


    public Socket getSocket() {
        if (mSocket == null /*&& AppSession.getInstance().getLoginSession()!=null*/) {
            try {

                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .hostnameVerifier(new HostnameVerifier() {

                            @Override
                            public boolean verify(String hostname, SSLSession session) {
                                return true;
                            }
                        })
                        .sslSocketFactory(getSSLSocketFactory(), getX509TrustManager())
                        .build();
                okHttpClient.retryOnConnectionFailure();
                // default settings for all sockets
                IO.setDefaultOkHttpWebSocketFactory(okHttpClient);
                IO.setDefaultOkHttpCallFactory(okHttpClient);


                IO.Options opts = new IO.Options();
                opts.forceNew = true;
                opts.reconnection = true;
                opts.transports = new String[]{WebSocket.NAME};
               /* opts.query = "uid=" +AppSession.getInstance().getLoginSession().getResponse().getMasterUserId()
                        +"&uname=" +AppSession.getInstance().getLoginSession().getResponse().getUserFirstName()+" "+AppSession.getInstance().getLoginSession().getResponse().getUserLastName();
               */
                opts.timeout = 16 * 1000;//15 seconds
                opts.reconnectionDelay = 1000;
                opts.reconnectionDelayMax = 5000;
                opts.reconnectionAttempts = 99999;
                opts.query = "transport=websocket";
                opts.secure = true;
                opts.callFactory = okHttpClient;
                opts.webSocketFactory = okHttpClient;
                mSocket = IO.socket(SOCKET_URL, opts);
            } catch (URISyntaxException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return mSocket;
    }

    public void closeSocket() {
        if (mSocket != null && mSocket.connected()) mSocket.disconnect();
        mSocket = null;
    }

    public static SSLSocketFactory getSSLSocketFactory() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            return sslSocketFactory;
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            return null;
        }

    }

    public static X509TrustManager getX509TrustManager() {
        return new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[]{};
            }

            public void checkClientTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }
        };
    }


}
