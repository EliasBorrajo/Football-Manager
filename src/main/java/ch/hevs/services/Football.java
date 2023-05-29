package ch.hevs.services;

import ch.hevs.businessobject.Club;
import ch.hevs.businessobject.League;
import ch.hevs.businessobject.Player;

public class Football implements FootballBean
{
    // CLUB - Use Cases :
    // 1) MAJ infos du club
    //  1.1) ajout suppresion jueur
    // 2) Inscrire un club à une ligue
    // 3) Consulter les infos de la ligue
    //  3.1) obtenir liste des clubs

    @Override
    public void updateClubInfo(Club club)
    {

    }

    @Override
    public void addPlayer(Player player)
    {

    }

    @Override
    public void removePlayer(Player player)
    {

    }

    @Override
    public void subscribeToLeague(League league)
    {

    }


    // FAN - Use Cases :
    // 1) Consulter les infos de son joueur préféré

    @Override
    public Player getPlayerInfo(Player player)
    {
        return null;
    }

    // PLAYER - Use Cases :
    // 1) MAJ infos du joueur
    // 2) Consulter les infos du club
    //  2.1) obtenir liste des joueurs


    @Override
    public void updatePlayerInfo(Player player)
    {

    }

    @Override
    public Club getClubInfo()
    {
        return null;
    }
}
