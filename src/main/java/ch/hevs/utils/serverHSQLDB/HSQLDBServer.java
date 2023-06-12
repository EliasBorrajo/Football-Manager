package ch.hevs.utils.serverHSQLDB;
import org.hsqldb.Server;

/**
 * Singleton class to start and stop the HSQLDB server used for the application
 * The server is started when the application is deployed, and stopped when the application is undeployed
 */
public class HSQLDBServer
{
    private static HSQLDBServer instanceSingleton;
    private Server server;
    private boolean isRunning;
    private static final String DB_NAME = "DB";
    private static final String DB_PATH = "file:../database/db/DB";


    private HSQLDBServer() {
        // Private constructor to prevent direct instantiation
        // Use getInstance() instead to instantiate the class
    }

    public static HSQLDBServer getInstance() {
        if (instanceSingleton == null) {
            instanceSingleton = new HSQLDBServer();
        }
        return instanceSingleton;
    }

    public void start()
    {
        // Start the server if it is not already running or if it has not been started yet
        if (server == null || !isRunning) {
            server = new Server();
            server.setDatabaseName(0, DB_NAME);
            server.setDatabasePath(0, DB_PATH);
            server.start();
            isRunning = true;
        }
    }

    public void stop() {
        if (server != null) {
            server.stop();
            server = null;
            isRunning = false;
        }
    }

    public boolean isRunning() {
        return isRunning;
    }
}
