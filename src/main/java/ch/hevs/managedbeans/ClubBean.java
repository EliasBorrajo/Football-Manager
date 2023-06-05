package ch.hevs.managedbeans;


import ch.hevs.businessobject.Account;
import ch.hevs.businessobject.Club;
import ch.hevs.businessobject.League;
import ch.hevs.businessobject.Player;
import ch.hevs.services.Football;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.event.ValueChangeEvent;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.List;

@ManagedBean // TODO : Verifier le @ManagedBean
public class ClubBean {
    //  A T T R I B U T S
    private Football football;
    private boolean  serviceLayerResult;
    private List<String> playerNames;
    private List<String> clubNames;
    private List<String> leagueNames;
    private Player selectedPlayer;
    private String selectedPlayerName;
    private List<String> dropDownPlayersName;


    //  C O N S T R U C T O R S
    @PostConstruct // exécutée QUE si l'interface graphique est utilisée
    public void initialize() throws NamingException
    {
        // use JNDI to inject reference to bank EJB
        InitialContext ctx = new InitialContext();
        football = (Football) ctx.lookup("java:global/TP12-WEB-EJB-PC-EPC-E-0.0.1-SNAPSHOT/FootballBean!ch.hevs.services.Football");

        // populate football database
        loadData();

        //get players
        List<Player> playerList = football.getPlayers();
        this.playerNames = new ArrayList<String>();
        for (Player player : playerList) {
            this.playerNames.add(player.getLastname());
        }

        // get clubs
        List<Club> clubList = football.getClubs();
        this.clubNames = new ArrayList<String>();
        for(Club club : clubList) {
            this.clubNames.add(club.getNameClub());
        }

        // get leagues
        List<League> leagueList = football.getLeagues();
        this.leagueNames = new ArrayList<String>();
        for(League league : leagueList) {
            this.leagueNames.add(league.getNameLeague());
        }

        // get fans

        // initalize attributes for the view

        //get player

    }

    /**
     * Load data
     */
    private void loadData()
    {
        football.seedDB();
    }


    //  M E T H O D S
    public void populateDB()
    {
        // populate football database
        System.out.println("populate football database...");
        System.out.println("POPULATE : " + football.seedDB());
    }

    public boolean verifyDB()
    {


        return false;
    }

    //  G E T T E R S   &   S E T T E R S

    //Get all players names
    public List<String> getPlayerNames() {
        return playerNames;
    }

    //Get all clubs names
    public List<String> getClubNames() {

        return clubNames;
    }

    //Get all leagues names
    public List<String> getLeagueNames() {

        return leagueNames;
    }

    public Player getSelectedPlayer() {
        return selectedPlayer;
    }

    public void setSelectedPlayer(Player selectedPlayer) {
        this.selectedPlayer = selectedPlayer;
    }

    public void updatePlayers(ValueChangeEvent event) {
        this.selectedPlayerName = (String)event.getNewValue();

        List<Player> players = football.getPlayerInfo(this.selectedPlayerName);
        this.dropDownPlayersName = new ArrayList<String>();
        for (Player player : players) {
            this.dropDownPlayersName.add(player.getLastname());
        }
    }

    //Extend --> get players list from the previous club

}
