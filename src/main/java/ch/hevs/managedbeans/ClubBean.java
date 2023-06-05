package ch.hevs.managedbeans;


import ch.hevs.businessobject.Account;
import ch.hevs.businessobject.Club;
import ch.hevs.businessobject.League;
import ch.hevs.businessobject.Player;
import ch.hevs.services.Football;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeEvent;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.List;

@ManagedBean // TODO : Verifier le @ManagedBean
public class ClubBean {
    //  A T T R I B U T S
    // General
    private Football football;
    private boolean  serviceLayerResult;
    private List<String> messages;
    private String       currentURL;
    public static final String NOT_AUTHORIZED_MESSAGE = "You are not authorized to perform this operation";


    // Player
    private List<String> playerNames;
    private String selectedPlayerName;
    private Player selectedPlayer;
    private List<Player> playersList;
    private Player       playertoUpdate;


    // Club
    private List<Club>  clubs;
    private Club        selectedClub;
    private String      selectedClubName;
    private List<String> clubNames;

    // League
    private List<String> leagueNames;

    //  C O N S T R U C T O R S
    @PostConstruct // exécutée QUE si l'interface graphique est utilisée
    public void initialize() throws NamingException
    {
        System.out.println("initialize ClubBean");
        // use JNDI to inject reference to bank EJB
        InitialContext ctx = new InitialContext();
        football = (Football) ctx.lookup("java:global/TP12-WEB-EJB-PC-EPC-E-0.0.1-SNAPSHOT/FootballBean!ch.hevs.services.Football");

        messages = new ArrayList<>();
        currentURL = "";


        // TODO : CHANGER
        // populate football database
        loadData();



        //get players
        this.playersList = football.getPlayers();
        this.playerNames = new ArrayList<String>();
        for (Player player : playersList) {
            this.playerNames.add(player.getLastname());
        }

        // get clubs
        this.clubs = football.getClubs();
        selectedClub = clubs.get(0);
//        this.clubNames = new ArrayList<String>();     // Initialize list of player names
//        for (Club club : clubs ) {
//            this.clubNames.add(club.getNameClub());
//        }

        // get leagues
        List<League> leagueList = football.getLeagues();
        this.leagueNames = new ArrayList<String>();
        for(League league : leagueList) {
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
    public void saveClub()
    {
        System.out.println("Selected Club tu update : " + selectedClub.getNameClub());
        football.updateClub(selectedClub);
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Club updated successfully", null));
    }

    public void updateClubSelected(ValueChangeEvent event) {
        // Mettez à jour les informations du club sélectionné en fonction de la nouvelle sélection
        this.selectedClub = (Club) event.getNewValue();
        // Faites les opérations nécessaires avec le club sélectionné

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

    //  G E T T E R S   &   S E T T E R S
    public Football getFootball() {
        return football;
    }
    public void setFootball(Football football) {
        this.football = football;
    }
    public boolean isServiceLayerResult() {
        return serviceLayerResult;
    }
    public void setServiceLayerResult(boolean serviceLayerResult) {
        this.serviceLayerResult = serviceLayerResult;
    }
    public List<String> getMessages() {
        return messages;
    }
    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
    public String getCurrentURL() {
        return currentURL;
    }
    public void setCurrentURL(String currentURL) {
        this.currentURL = currentURL;
    }
    public List<String> getPlayerNames() {
        return playerNames;
    }
    public void setPlayerNames(List<String> playerNames) {
        this.playerNames = playerNames;
    }
    public String getSelectedPlayerName() {
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
    public Player getPlayertoUpdate() {
        return playertoUpdate;
    }
    public void setPlayertoUpdate(Player playertoUpdate) {
        this.playertoUpdate = playertoUpdate;
    }
    public List<Club> getClubs() {
        return clubs;
    }
    public void setClubs(List<Club> clubs) {
        this.clubs = clubs;
    }
    public Club getSelectedClub() {
        return selectedClub;
    }
    public void setSelectedClub(Club selectedClub) {
        this.selectedClub = selectedClub;
    }
    public String getSelectedClubName() {
        return selectedClubName;
    }
    public void setSelectedClubName(String selectedClubName) {
        this.selectedClubName = selectedClubName;
    }
    public List<String> getClubNames() {
        return clubNames;
    }
    public void setClubNames(List<String> clubNames) {
        this.clubNames = clubNames;
    }
    public List<String> getLeagueNames() {
        return leagueNames;
    }
    public void setLeagueNames(List<String> leagueNames) {
        this.leagueNames = leagueNames;
    }
}
