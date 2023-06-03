package ch.hevs.services;

import ch.hevs.businessobject.Club;
import ch.hevs.businessobject.Fan;
import ch.hevs.businessobject.League;
import ch.hevs.businessobject.Player;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

@Stateless
public class FootballBean implements Football
{
    @PersistenceContext (name = "dbFootballPU", type= PersistenceContextType.TRANSACTION)
    private EntityManager em;

    @Override
    public boolean populateDB()
    {
        League league = new League("Super League", "Division Suisse");
        em.persist(league);
        return true;
    }


    // CLUB - Use Cases :
    // 1) MAJ infos du club
    //  1.1) ajout suppresion jueur
    // 2) Inscrire un club à une ligue
    // 3) Consulter les infos de la ligue
    //  3.1) obtenir liste des clubs
    @Override
    public void updateClubInfo(Club club)
    {
        // TODO : Check if club exists
        Club clubUpdating = em.merge(club);
        em.persist(clubUpdating);
    }

    @Override
    public void addPlayer(Club club, Player player)
    {
        // TODO : Check if player is already in a club
        Club clubAddingMember = em.merge(club);
        Player playerAdding   = em.merge(player);

        club.addPlayer(playerAdding);
        player.setPlaysForClub(clubAddingMember);
    }

    @Override
    public void removePlayer(Player player)
    {
        // TODO : UTILISER LE EM POUR FAIRE LES MERGE ?
        // Remove player from club, and remove club from player, and delete player from DB
        Player playerRemoving = em.merge(player); // ID DEPUIS LE FRONTEND
        Club club = playerRemoving.getPlaysForClub();
        club.removePlayer(playerRemoving);
        playerRemoving.setPlaysForClub(null);
        em.remove(playerRemoving);
    }

    @Override
    public void subscribeToLeague(League league, Club club)
    {
        League leagueSubscribing = em.merge(league);
        Club clubSubscribing     = em.merge(club);

        league.addClub(clubSubscribing);
        club.setLeague(leagueSubscribing);
    }


    // FAN - Use Cases :
    // 1) Consulter les infos de son joueur préféré
    @Override
    public Player getPlayerInfo(Player player)
    {
        Player playerInfo = em.merge(player);
        return playerInfo;
    }

    // PLAYER - Use Cases :
    // 1) MAJ infos du joueur
    // 2) Consulter les infos du club
    //  2.1) obtenir liste des joueurs
    @Override
    public void updatePlayerInfo(Player player)
    {
        Player playerUpdating = em.merge(player);
        em.persist(playerUpdating);
    }

    @Override
    public Club getClubInfo(Club club)
    {
        Club clubInfo = em.merge(club);
        return clubInfo;
    }


}
