package tor;

/**
 * Created by erkanmdr on 18.12.2015.
 */


import com.subgraph.orchid.TorClient;
import com.subgraph.orchid.TorInitializationListener;

public class OrchidDemo {

    private static TorClient client;

    public static void main(String[] args) {
        startOrchid();

    }

    private static void startOrchid() {

        //listen on 127.0.0.1:9150 (default)
        client = new TorClient();
        client.addInitializationListener(createInitalizationListner());
        client.start();
        client.enableSocksListener(2825);//or client.enableSocksListener(yourPortNum);

    }

    private static void stopOrchid() {
        client.stop();
    }

    public static TorInitializationListener createInitalizationListner() {
        return new TorInitializationListener() {
            @Override
            public void initializationProgress(String message, int percent) {
                System.out.println(">>> [ " + percent + "% ]: " + message);
            }

            @Override
            public void initializationCompleted() {
                System.out.println("Tor is ready to go!");
            }
        };
    }

}
