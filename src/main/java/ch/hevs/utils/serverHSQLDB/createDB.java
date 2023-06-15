package ch.hevs.utils.serverHSQLDB;

import ch.hevs.services.Football;
import ch.hevs.services.FootballBean;
import ch.hevs.utils.exception.FootballException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

/**
 * Class to create the database (HSQLDB) before deploying the application on the server (wildfly)
 * This class is used to create the database only once, when the application is deployed for the first time
 * If the database already exists, it is reset (all data is deleted) and the HSQLDB server is restarted (Singleton).
 * If the database does not exist, it is created and the HSQLDB server is started (Singleton).
 *
 * @PersitenceContext : used to inject the EntityManager (JPA) into the class (used to execute queries on the database)
 */
public class createDB {

    @PersistenceContext(name = "dbFootballPU", type= PersistenceContextType.TRANSACTION)
    private static EntityManager em;

    /**
     * Main method to create the database
     * Program to run in the EDIT CONFIGURATION of IntelliJ IDEA BEFORE deploying the application on the server (wildfly)
     */
    public static void main(String[] args)
    {

        if (!isDatabaseInitialized())
        {
            System.out.println("Database is not initialized. Initializing...");
            initializeDatabase();
            System.out.println("Database initialized.");
        }
        else
        {
            System.out.println("Database is already initialized.");

            resetDatabase();

            if (HSQLDBServer.getInstance().isRunning())
            {
                System.out.println("Stopping HSQLDB server...");
                HSQLDBServer.getInstance().stop();
                System.out.println("HSQLDB server stopped.");
                System.out.println("Starting HSQLDB server...");
                HSQLDBServer.getInstance().start();
                System.out.println("HSQLDB server started.");
            }
            else
            {
                System.out.println("HSQLDB server is not running.");
                HSQLDBServer.getInstance().start();
            }
        }
    }

    /**
     * Check if database is initialized by counting the number of rows in each table of the DB
     * @return : true if database is initialized, false if not
     */
    private static boolean isDatabaseInitialized()
    {
        System.out.println("Checking if database is initialized...");
        try {
            Query queryClub     = em.createQuery("SELECT COUNT(*) FROM Club");
            queryClub.getSingleResult();

            Query queryPlayer   = em.createQuery("SELECT COUNT(*) FROM Player");
            queryPlayer.getSingleResult();

            Query queryFan      = em.createQuery("SELECT COUNT(*) FROM Fan");
            queryFan.getSingleResult();

            System.out.println("Database is initialized.");
            return true;
        } catch (Exception e) // If no result found in database, an exception is thrown
        {
            System.err.println("Database is not initialized." + e.getMessage());
            return false;
        }
    }

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

    /**
     * Reset database by deleting all data from all tables of the DB
     *
     */
    private static void resetDatabase() {
        System.out.println("Resetting the database...");

        System.out.println("Deleting all data from the database...");
        // Supprimer toutes les tables de la base de données
        em.getTransaction().begin();
        em.createNativeQuery("DROP TABLE IF EXISTS League CASCADE").executeUpdate();
        em.createNativeQuery("DROP TABLE IF EXISTS Club CASCADE").executeUpdate();
        em.createNativeQuery("DROP TABLE IF EXISTS Player CASCADE").executeUpdate();
        em.createNativeQuery("DROP TABLE IF EXISTS Fan CASCADE").executeUpdate();
        em.createNativeQuery("DROP TABLE IF EXISTS Person CASCADE").executeUpdate();
        em.createNativeQuery("DROP TABLE IF EXISTS Country CASCADE").executeUpdate();
        em.getTransaction().commit();

//        // Régénérer les tables avec la méthode populate
//        System.out.println("Regenerating the database...");
//        football.seedDB();

        System.out.println("Database reset complete.");
    }

}
