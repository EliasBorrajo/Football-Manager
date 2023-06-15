package ch.hevs.services;

import ch.hevs.businessobject.*;

import javax.ejb.Local;
import java.util.List;

@Local
public interface Football
{
    // General
    String getCurrentUser();

    // Player
    List<Player> getPlayers();
    void updatePlayer(Player player);
    List<Player> getPlayersFromClubForFan(Long clubId);

    // Club
    List<Club> getClubs();
    void updateClub(Club club);
    Club getClubById(Long clubId);
    boolean deleteClub(Club club);
    List<Club> getClubsFromLeague(Long leagueId);

    // League
    List<League> getLeagues();

    // Fan
    List<Fan> getFans();

    // Roles verifications
    boolean verifyManagerRole();
    boolean verifyPlayerRole();
    boolean verifyFanRole();

    // Database
    boolean resetDatabase();
    boolean seedDB();

}
