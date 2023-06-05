package ch.hevs.utils.serverHSQLDB;

public class testDBCreation {
    public static void main(String[] args)
    {
        try {
            System.out.println("Starting HSQLDB server...");
            HSQLDBServer.getInstance().start();
            System.out.println("HSQLDB server started");
        } catch (Exception e) {
            System.out.println("Error while starting HSQLDB server");
            e.printStackTrace();
        }

    }
}
