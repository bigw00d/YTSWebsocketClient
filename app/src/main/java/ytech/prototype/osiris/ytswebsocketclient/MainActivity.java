package ytech.prototype.osiris.ytswebsocketclient;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHandler = new Handler();

        try {
            URI uri = new URI("ws", null, "192.168.1.9", 81, "/", null, null);
            WebSocketClient c = new WebSocketClient(uri, new Draft_17()) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    Log.e("onOpen", handshakedata.toString());
                }
                @Override
                public void onMessage(final String message) {
                    Log.e("onMessage", message);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    });                }
                @Override
                public void onError(Exception ex) {
                    Log.e("onError", ex.toString());
                }
                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.e("onClose", reason.toString());
                }
            };
            Log.d("connect", "connect");
            c.connect();
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
        }


    }
}
