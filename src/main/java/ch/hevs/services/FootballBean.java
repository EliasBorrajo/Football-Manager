package ch.hevs.services;

import ch.hevs.businessobject.Club;
import ch.hevs.businessobject.Country;
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
        Country country1 = new Country("France");
        Country country2 = new Country("England");
        Country country3 = new Country("Switzerland");
        Country country4 = new Country("Spain");
        Country country5 = new Country("Germany");
        Country country6 = new Country("Portugal");
        Country country7 = new Country("Egypt");
        Country country8 = new Country("Italia");
        /**
        em.persist(country1);
        em.persist(country2);
        em.persist(country3);
        em.persist(country4);
        em.persist(country5);
        em.persist(country6);
        em.persist(country7);
        em.persist(country8);
         **/


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
