package ch.hevs.services;

import ch.hevs.businessobject.*;
import ch.hevs.utils.exception.FootballException;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

/**
 * Implementation of the Football service (EJB) that implements the business logic.
 * This class is a stateless EJB (session bean) that is used by the JSF controllers via the Football interface.
 * @Stateless: stateless EJB (session bean) means that the EJB is stateless, i.e. it does not keep any state between two calls.
 * @RolesAllowed: defines the roles that are allowed to access the methods of the EJB. Here every role is allowed to access every method.
 *                But in the JSF we will make checks calls on roles to restrict the access to some buttons or pages
 * @PersistenceContext: defines the persistence context (unit) that is used by the EJB. The unit name is defined in the persistence.xml file.
 *                      Unit name is dbFootballPU wich is the name of the persistence unit defined in the persistence.xml file.
 * @Resource: defines the SessionContext that is used to get access to security information (user name, role, ...).
 */
@Stateless
@RolesAllowed(value = {"Manager", "Fan", "Player"})
public class FootballBean implements Football
{
    @PersistenceContext (name = "dbFootballPU", type= PersistenceContextType.TRANSACTION)
    private EntityManager em;

    @Resource
    private SessionContext ctx; // To get access to security information (user name, role, ...)

    // G E N E R A L
    /**
     * Get the current user name connected to te registry of wildfly
     * @return : the current user name
     */
    @Override
    public String getCurrentUser()
    {
        return  ctx.getCallerPrincipal().getName();
    }

    // P L A Y E R
    /**
     * Get all the players from the database
     * @return : a list of all the players
     */
    @Override
    public List<Player> getPlayers() {
        return em.createQuery("FROM Player").getResultList();
    }

    /**
     * Update a player in the database with the new information given in parameter
     * @param player : the player to update
     */
    @Override
    public void updatePlayer(Player player) {
        em.merge(player);
    }

    /**
     * Get all the players from a club for a fan (to display in the view)
     * @param clubId : the id of the club to get the players from
     * @return : a list of all the players from the club given in parameter
     */
    @Override
    public List<Player> getPlayersFromClubForFan(Long clubId){

        return em.createQuery("From Player p WHERE p.playsForClub.id = :clubId")
                .setParameter("clubId",clubId)
                .getResultList();

    }

    /**
     * Add a player into the database
     * @param player : the player to persist
     */
    @Override
    public void addPlayer(Player player){
        em.persist(player);
    }

    // C L U B
    /**
     * Get all the clubs from the database
     * @return : a list of all the clubs
     */
    @Override
    public List<Club> getClubs() {
        return em.createQuery("FROM Club").getResultList();
    }

    /**
     * Get a club from the database with the id given in parameter
     * @param club : the club to update to the database
     */
    @Override
    public void updateClub(Club club){
        em.merge(club);
    }

    /**
     * Delete a club from the database with a query, and delete all the fans associated to this club
     * @param club : the club to delete
     * @return : true if the club and the fans are deleted, false if not
     */
    @Override
    public boolean deleteClub(Club club) {
        try {
            // Supprimer les fans associés au club
            Club clubtoRemove = em.merge(club);
            List<Fan> fans = em.createQuery("FROM Fan f WHERE f.fanOfClub = :club")
                    .setParameter("club", clubtoRemove)
                    .getResultList();

            for (Fan fan : fans) {
                em.remove(fan);
            }

            // Supprimer le club lui-même
            em.remove(clubtoRemove);

            System.out.println("Club and associated fans deleted successfully.");
            return true;
        } catch (Exception e) {
            System.out.println("Failed to delete club and associated fans: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get all the clubs from a league with the id of the league given in parameter
     * @param leagueId : the id of the league to get the clubs from
     * @return : a list of all the clubs from the league given in parameter
     */
    @Override
    public List<Club> getClubsFromLeague(Long leagueId) {
        return em.createQuery("SELECT c FROM Club c WHERE c.league.id = :leagueId")
                .setParameter("leagueId", leagueId)
                .getResultList();
    }

    /**
     * Get a club by id from the database
     * @param clubId : the id club
     * @return : a club
     */
    @Override
    public Club getClub(long clubId) {
        return (Club) em.createQuery("FROM Club c where c.id=:id").setParameter("id", clubId).getSingleResult();
    }

    /**
     * Add a club into the database
     * @param club : the club to persist
     */
    @Override
    public void addClub(Club club){
        em.persist(club);
    }

    // L E A G U E
    /**
     * Get all the leagues from the database
     * @return : a list of all the leagues
     */
    @Override
    public List<League> getLeagues() {
        return em.createQuery("FROM League").getResultList();
    }

    /**
     * Add a league into the database
     * @param league : the league to persist
     */
    @Override
    public void addLeague(League league){
        em.persist(league);
    }

    // F A N
    /**
     * Get all the fans from the database
     * @return : a list of all the fans
     */
    @Override
    public List<Fan> getFans() {
        return em.createQuery("FROM Fan").getResultList();
    }

    /**
     * Add a fan into the database
     * @param fan : the fan to persist
     */
    @Override
    public void addFan(Fan fan){
        em.persist(fan);
    }

    /**
     * Get a league by id from the database
     * @param leagueId : the id league
     * @return : the league
     */
    @Override
    public League getLeague(long leagueId){
        return (League) em.createQuery("FROM League l where l.id=:id").setParameter("id", leagueId).getSingleResult();
    }


    // R O L E S  V E R I F I C A T I O N
    /**
     * Verify if the current user has the role "Manager" to access the ressource (page or button)
     * @return : true if the user has the role "Manager", false if not
     */
    @Override
    public boolean verifyManagerRole(){
        String roleManager = "Manager";
        System.out.println("Verify allowed role");
        System.out.println( "Current user : " + ctx.getCallerPrincipal().getName() +" is looking to access the ressource");

        if (ctx.isCallerInRole(roleManager))
        {
            System.out.println("User +"+ ctx.getCallerPrincipal().getName() +"is a "+roleManager+" and is allowed to access the ressource");
            return true;
        }
        else
        {
            System.out.println("Current role is not allowed to access the ressource");
            return false;
        }
    }

    /**
     * Verify if the current user has the role "Player" to access the ressource (page or button)
     * @return : true if the user has the role "Player", false if not
     */
    @Override
    public boolean verifyPlayerRole(){
        String rolePlayer = "Player";
        System.out.println("Verify allowed role");
        System.out.println( "Current user : " + ctx.getCallerPrincipal().getName() +" is looking to access the ressource");

        if (ctx.isCallerInRole(rolePlayer))
        {
            System.out.println("User +"+ ctx.getCallerPrincipal().getName() +"is a "+rolePlayer+" and is allowed to access the ressource");
            return true;
        }
        else
        {
            System.out.println("Current role is not allowed to access the ressource");
            return false;
        }
    }

    /**
     * Verify if the current user has the role "Fan" to access the ressource (page or button)
     * @return : true if the user has the role "Fan", false if not
     */
    @Override
    public boolean verifyFanRole(){
        String roleFan = "Fan";
        System.out.println("Verify allowed role");
        System.out.println( "Current user : " + ctx.getCallerPrincipal().getName() +" is looking to access the ressource");

        if (ctx.isCallerInRole(roleFan))
        {
            System.out.println("User +"+ ctx.getCallerPrincipal().getName() +"is a "+roleFan+" and is allowed to access the ressource");
            return true;
        }
        else
        {
            System.out.println("Current role is not allowed to access the ressource");
            return false;
        }
    }

    // D A T A B A S E  M A N A G E M E N T
    /**
     * Reset the database by deleting all data from the database. This method is used for testing & demo purposes.
     * @return : true if the database has been reset successfully, false otherwise.
     */
    @Override
    public boolean resetDatabase() {
        System.out.println("Resetting the database...");

        System.out.println("Deleting all data from the database...");
        // Supprimer toutes les tables de la base de données
        try {
            em.createNativeQuery("DELETE FROM Fan").executeUpdate();
            em.createNativeQuery("DELETE FROM Player").executeUpdate();
            em.createNativeQuery("DELETE FROM Club").executeUpdate();
            em.createNativeQuery("DELETE FROM League").executeUpdate();
            System.out.println("Database reset complete.");

            return true;
        } catch (Exception e) {
            System.out.println("Database reset failed." + e.getMessage());
            return false;
        }
    }

    /**
     * Seed the database with some data. This method is used for testing & demo purposes.
     * @return : true if the database has been seeded successfully, false otherwise.
     */
    @Override
    public boolean seedDB(){
        try {
            Country country1 = new Country("France");
            Country country2 = new Country("England");
            Country country3 = new Country("Switzerland");
            Country country4 = new Country("Spain");
            Country country5 = new Country("Germany");
            Country country6 = new Country("Portugal");
            Country country7 = new Country("Egypt");
            Country country8 = new Country("Italia");


            League leagueCH1 = new League("Super League", "Division 1",country3);
            League leagueCH2 = new League("Challenge League", "Division 2",country3);
            League leagueGB1 = new League("Premier League", "Division 1",country2);
            League leagueGB2 = new League("EFL Championship", "Division 2",country2);
            League leagueIT1 = new League("Serie A", "Division 1",country8);
            League leagueIT2 = new League("Serie B", "Division 2",country8);
            em.persist(leagueCH1);
            em.persist(leagueCH2);
            em.persist(leagueGB1);
            em.persist(leagueGB2);
            em.persist(leagueIT1);
            em.persist(leagueIT2);

            Club chelsea = new Club("Chelsea FC","London","Stamford Bridge",country2,leagueGB1);
            Club arsenal = new Club("Arsenal","London","Emirates Stadium",country2,leagueGB1);
            Club manu = new Club("Manchester United", "Manchester", "Old Trafford",country2,leagueGB1);
            Club liverpool = new Club("Liverpool FC", "Liverpool", "Anfield",country2,leagueGB1);

            Club juventus = new Club("Juventus FC","Turin","Juventus Stadium",country8,leagueIT1);
            Club roma = new Club("AS Roma","Rome","Stade Olympique de Rome",country8,leagueIT1);
            Club acmilan = new Club("AC Milan", "Milan", "San Siro",country8,leagueIT1);
            Club intermilan = new Club("Inter Milan", "Milan", "San Siro",country8,leagueIT1);

            Club basel = new Club("FC Basel", "Basel", "St. Jakob-Park",country3,leagueCH1);
            Club yb = new Club("BSC Young Boys", "Bern", "Stade de Suisse",country3,leagueCH1);

            em.persist(basel);
            em.persist(yb);
            em.persist(manu);
            em.persist(liverpool);
            em.persist(acmilan);
            em.persist(intermilan);
            em.persist(chelsea);
            em.persist(arsenal);
            em.persist(juventus);
            em.persist(roma);


            Player player1 = new Player("Edouard","Mendy","01.03.1992",country1,"GK",1,true,194,85,chelsea);
            Player player2 = new Player("N'Golo", "Kanté", "29.03.1991", country1, "MF", 7, false, 168, 70,chelsea);
            Player player3 = new Player("Mason", "Mount", "10.01.1999", country2, "MF", 19, false, 185, 73,chelsea);
            Player player4 = new Player("Timo", "Werner", "06.03.1996", country5, "FW", 11, false, 180, 75,chelsea);

        // Pour les joueurs d'Arsenal
            Player player5 = new Player("Pierre-Emerick", "Aubameyang", "18.06.1989", country1, "FW", 14, false, 187, 80,arsenal);
            Player player6 = new Player("Bukayo", "Saka", "05.09.2001", country2, "MF", 7, false, 178, 68,arsenal);

        // Pour les joueurs de Manchester United
            Player player7 = new Player("Bruno", "Fernandes", "08.09.1994", country6, "MF", 18, false, 179, 69,manu);
            Player player8 = new Player("Harry", "Maguire", "05.03.1993", country2, "DF", 5, true, 194, 88,manu);

        // Pour les joueurs de Liverpool FC
            Player player9 = new Player("Mohamed", "Salah", "15.06.1992", country7, "FW", 11, false, 175, 71,liverpool);
            Player player10 = new Player("Virgil", "van Dijk", "08.07.1991", country2, "DF", 4, true, 193, 92, liverpool);

        // Pour les joueurs de Juventus FC
            Player player11 = new Player("Cristiano", "Ronaldo", "05.02.1985", country3, "FW", 7, false, 187, 83,juventus);
            Player player12 = new Player("Giorgio", "Chiellini", "14.08.1984", country8, "DF", 3, true, 187, 85,juventus);

        // Pour les joueurs de AS Roma
            Player player13 = new Player("Lorenzo", "Pellegrini", "19.06.1996", country8, "MF", 7, false, 185, 74,roma);
            Player player14 = new Player("Bryan", "Cristante", "03.03.1995", country8, "MF", 4, false, 188, 79, roma);

        // Pour les joueurs de AC Milan
            Player player15 = new Player("Zlatan", "Ibrahimović", "03.10.1981", country3, "FW", 11, false, 195, 95,acmilan);
            Player player16 = new Player("Gianluigi", "Donnarumma", "25.02.1999", country1, "GK", 99, true, 196, 90,acmilan);

        // Pour les joueurs de Inter Milan
            Player player17 = new Player("Romelu", "Lukaku", "13.05.1993", country2, "FW", 9, false, 190, 94,intermilan);
            Player player18 = new Player("Stefan", "de Vrij", "05.02.1992", country2, "DF", 6, true, 188, 78,intermilan);

        // Pour les joueurs de FC Basel
            Player player19 = new Player("Valentin", "Stocker", "12.04.1989", country1, "MF", 14, false, 180, 72,basel);
            Player player20 = new Player("Noah", "Okafor", "24.05.2001", country1, "FW", 9, false, 176, 68,basel);

        // Pour les joueurs de BSC Young Boys
            Player player21 = new Player("Jean-Pierre", "Nsame", "01.02.1993", country1, "FW", 9, false, 186, 79,yb);
            Player player22 = new Player("Nicolas", "Ngamaleu", "10.09.1991", country1, "MF", 7, false, 177, 71,yb);

            em.persist(player1);
            em.persist(player2);
            em.persist(player3);
            em.persist(player4);
            em.persist(player5);
            em.persist(player6);
            em.persist(player7);
            em.persist(player8);
            em.persist(player9);
            em.persist(player10);
            em.persist(player11);
            em.persist(player12);
            em.persist(player13);
            em.persist(player14);
            em.persist(player15);
            em.persist(player16);
            em.persist(player17);
            em.persist(player18);
            em.persist(player19);
            em.persist(player20);
            em.persist(player21);
            em.persist(player22);

            Fan fan1 = new Fan("Theo","Clerc","24.08.2002",country3,"12.06.2023",chelsea);
            Fan fan2 = new Fan("Elias","Borrajo","01.01.1998",country4,"12.06.2023",acmilan);

            em.persist(fan1);
            em.persist(fan2);

            em.flush(); // synchronise la base de données avec les objets persistants

        return true;
    }
        catch (Exception e)
        {
            throw new FootballException("Error while seeding database : " + e.getMessage());
        }


    }

}
