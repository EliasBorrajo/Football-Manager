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
    private List<String> playerNames;
    private List<String> clubNames;
    private List<String> leagueNames;
    private String selectedPlayerName;
    private Player selectedPlayer;
    private List<Player> playersList;
    private String selectedLeagueName;
    private League selectedLeague;
    private List<League> leaguesList;

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
         this.playersList = football.getPlayers();
        this.playerNames = new ArrayList<String>();
        for (Player player : playersList) {
            this.playerNames.add(player.getLastname());
        }

        // get clubs
        List<Club> clubList = football.getClubs();
        this.clubNames = new ArrayList<String>();
        for(Club club : clubList) {
            this.clubNames.add(club.getNameClub());
        }

        // get leagues
        this.leaguesList = football.getLeagues();
        this.leagueNames = new ArrayList<String>();
        for(League league : leaguesList) {
            this.leagueNames.add(league.getNameLeague());
        }

        List<Player> players = new ArrayList<Player>();

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

    public void updateSelectedPlayer(ValueChangeEvent event) {
        selectedPlayerName = (String) event.getNewValue();
        selectedPlayer = null; // Réinitialiser selectedPlayer

        for (Player player : playersList) {
            if (player.getLastname().equals(selectedPlayerName)) {
                selectedPlayer = player;
                break;
            }
        }
    }

    public void updateSelectedLeague(ValueChangeEvent event) {
        selectedLeagueName = (String) event.getNewValue();
        selectedLeague = null; // Réinitialiser selectedPlayer

        for (League league : leaguesList) {
            if (league.getNameLeague().equals(selectedLeagueName)) {
                selectedLeague = league;
                break;
            }
        }
    }

    public void updatePlayer() {
        // Mettre à jour le joueur dans la base de données avec les nouvelles informations
        football.updatePlayer(selectedPlayer);
        // Réinitialiser la propriété selectedPlayer pour désélectionner le joueur
        selectedPlayer = null;
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

    public void setPlayerNames(List<String> playerNames) {
        this.playerNames = playerNames;
    }

    public void setClubNames(List<String> clubNames) {
        this.clubNames = clubNames;
    }

    public void setLeagueNames(List<String> leagueNames) {
        this.leagueNames = leagueNames;
    }

    public String getSelectedPlayerName() {
        System.out.println("Get name player: "+selectedPlayerName);
        return selectedPlayerName;
    }

    public void setSelectedPlayerName(String selectedPlayerName) {
        this.selectedPlayerName = selectedPlayerName;
    }

    public Player getSelectedPlayer() {
        return selectedPlayer;
    }

    public void setSelectedPlayer(Player selectedPlayer) {
        this.selectedPlayer = selectedPlayer;
    }

    public List<Player> getPlayersList() {
        return playersList;
    }

    public void setPlayersList(List<Player> playersList) {
        this.playersList = playersList;
    }

    public String getSelectedLeagueName() {
        return selectedLeagueName;
    }

    public void setSelectedLeagueName(String selectedLeagueName) {
        this.selectedLeagueName = selectedLeagueName;
    }

    public League getSelectedLeague() {
        return selectedLeague;
    }

    public void setSelectedLeague(League selectedLeague) {
        this.selectedLeague = selectedLeague;
    }

    public List<League> getLeaguesList() {
        return leaguesList;
    }

    public void setLeaguesList(List<League> leaguesList) {
        this.leaguesList = leaguesList;
    }
}
