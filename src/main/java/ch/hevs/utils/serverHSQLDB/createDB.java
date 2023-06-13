package ch.hevs.utils.serverHSQLDB;

import ch.hevs.services.Football;
import ch.hevs.services.FootballBean;
import ch.hevs.utils.exception.FootballException;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;


public class createDB {

    @PersistenceContext(name = "dbFootballPU", type= PersistenceContextType.TRANSACTION)
    private static EntityManager em;

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
