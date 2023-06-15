package ch.hevs.services;

import ch.hevs.businessobject.Club;
import ch.hevs.businessobject.Fan;
import ch.hevs.businessobject.League;
import ch.hevs.businessobject.Player;

import javax.ejb.Local;
import java.util.List;

/**
 * Interface of the Football service (EJB) that implements the business logic.
 * This interface is used by the JSF controllers.
 * The @Local annotation defines the EJB as a local EJB wich means that the EJB is used locally (in the same application).
 */
@Local
public interface Football
{
    // General
    String getCurrentUser();

    // Player
    List<Player> getPlayers();
    void updatePlayer(Player player);
    List<Player> getPlayersFromClubForFan(Long clubId);
    void addPlayer(Player player);

    // Club
    List<Club> getClubs();
    void updateClub(Club club);
    boolean deleteClub(Club club);
    List<Club> getClubsFromLeague(Long leagueId);
    Club getClub(long clubId);
    void addClub(Club club);

    // League
    List<League> getLeagues();
    void addLeague(League league);
    League getLeague(long leagueId);

    // Fan
    List<Fan> getFans();
    void addFan(Fan fan);

    // Roles verifications
    boolean verifyManagerRole();
    boolean verifyPlayerRole();
    boolean verifyFanRole();

    // Database
    boolean resetDatabase();
    boolean seedDB();

}
