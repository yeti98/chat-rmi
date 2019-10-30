package server;

public class ServerSingleton {
    private static Server server = null;

    public static synchronized Server getServer() {
        if (server == null) {
            server = new Server();
        }
        return server;
    }
}
