package ch.hevs.services;

import ch.hevs.businessobject.*;

import javax.ejb.Local;
import java.util.List;

@Local
public interface Football
{
    // Player
    List<Player> getPlayers();
    void updatePlayer(Player player);
    List<Player> getPlayersFromClubForFan(Long clubId);

    // Club
    List<Club> getClubs();
    void updateClub(Club club);
    Club getClubById(Long clubId);
    boolean deleteClub(Club club);

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

    // CLUB - Use Cases :
    // 1) MAJ infos du club
    //  1.1) ajout suppresion jueur
    // 2) Inscrire un club à une ligue
    // 3) Consulter les infos de la ligue
    //  3.1) obtenir liste des clubs
//    @TransactionAttribute(value = TransactionAttributeType.REQUIRED) // TODO : Changer

//    public void addPlayer(Club club ,Player player);
//    public void removePlayer(Player player);
//    public void subscribeToLeague(League league, Club club);


    // FAN - Use Cases :
    // 1) Consulter les infos de son joueur préféré
//    public Player getPlayerInfo(Player player);


    // PLAYER - Use Cases :
    // 1) MAJ infos du joueur
    // 2) Consulter les infos du club
    //  2.1) obtenir liste des joueurs
//    public void updatePlayerInfo(Player player);
//    public Club getClubInfo(Club club);




}
