package ch.hevs.managedbeans;


import ch.hevs.businessobject.Player;
import ch.hevs.services.Football;

import javax.annotation.PostConstruct;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.List;

public class ClubBean {
    //  A T T R I B U T S
    // General
    private Football football;
    private boolean  serviceLayerResult;
    private List<String> messages;
    private String       currentURL;
    public static final String NOT_AUTHORIZED_MESSAGE = "You are not authorized to perform this operation";

    // Player
    private List<Player> players;
    private List<String> playerLNames;
    private Player       selectedPlayer;
    private Player       playertoUpdate;




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
        System.out.println("populate football database...");
        System.out.println("POPULATE : " + football.populateDB());



        //get players
        players = football.getPlayers();                // Get all players from DB
        this.playerLNames = new ArrayList<String>();     // Initialize list of player names
        for (Player player : players) {
            this.playerLNames.add(player.getLastname());
        }

        // get clubs


        // get leagues


        // get fans

        // initalize attributes for the view



    }


    //  M E T H O D S




    //  G E T T E R S   &   S E T T E R S
    public List<Player> getPlayers() {
        return players;
    }
    public void setPlayers(List<Player> players) {
        this.players = players;
    }
    public List<String> getPlayerLNames() {
        return playerLNames;
    }
    public void setPlayerLNames(List<String> playerLNames) {
        this.playerLNames = playerLNames;
    }
    public Player getSelectedPlayer() {
        return selectedPlayer;
    }
    public void setSelectedPlayer(Player selectedPlayer) {
        this.selectedPlayer = selectedPlayer;
    }
    public Player getPlayertoUpdate() {
        return playertoUpdate;
    }
    public void setPlayertoUpdate(Player playertoUpdate) {
        this.playertoUpdate = playertoUpdate;
    }
}
