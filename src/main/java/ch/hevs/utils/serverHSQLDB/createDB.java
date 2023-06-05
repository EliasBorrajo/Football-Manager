package ch.hevs.utils.serverHSQLDB;

import ch.hevs.utils.exception.FootballException;

import javax.persistence.Query;

public class createDB {
    public static void main(String[] args)
    {

//        if (!isDatabaseInitialized())
//        {
//            System.out.println("Database is not initialized. Initializing...");
//            initializeDatabase();
//            System.out.println("Database initialized.");
//        }
        try {
            System.out.println("Starting HSQLDB server...");
            HSQLDBServer.getInstance().start();
            System.out.println("HSQLDB server started");
        } catch (Exception e) {
            System.out.println("Error while starting HSQLDB server");
            e.printStackTrace();
        }

    }

//    /**
//     * Check if database is initialized by counting the number of rows in each table of the DB
//     * @return : true if database is initialized, false if not
//     */
//    private static boolean isDatabaseInitialized()
//    {
//        System.out.println("Checking if database is initialized...");
//        try {
//            Query queryClub     = em.createQuery("SELECT COUNT(*) FROM Club");
//            queryClub.getSingleResult();
//
//            Query queryPlayer   = em.createQuery("SELECT COUNT(*) FROM Player");
//            queryPlayer.getSingleResult();
//
//            Query queryFan      = em.createQuery("SELECT COUNT(*) FROM Fan");
//            queryFan.getSingleResult();
//
//            System.out.println("Database is initialized.");
//            return true;
//        } catch (Exception e) // If no result found in database, an exception is thrown
//        {
//            System.err.println("Database is not initialized." + e.getMessage());
//            return false;
//        }
//    }

    /**
     * Initialize database by starting HSQLDB server
     * no need to delete database before initializing it, as it is done automatically by HSQLDB server (Singleton)
     * @throws FootballException : if an error occurs while initializing database
     */
    private static void initializeDatabase()
    {
        try {
            System.out.println("Starting database...");
            HSQLDBServer.getInstance().start();
            System.out.println("Database started succesfully.");
        }catch (Exception e)
        {
            System.err.println("Error while initializing database : " + e.getMessage());
            throw new FootballException("Database is not initialized. run script \"startDB.bat\" to initialize it.");
        }

    }
}
