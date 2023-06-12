package ch.hevs.managedbeans;


import ch.hevs.businessobject.Club;
import ch.hevs.businessobject.League;
import ch.hevs.businessobject.Player;
import ch.hevs.services.Football;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
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
    private List<Player> playersList;
    private Player       selectedPlayer;
    private List<String> playerNames;          // Nécessaire pour le menu déroulant
    private String       selectedPlayerName;   // Nécessaire pour le menu déroulant
    private Player       playertoUpdate; // TODO Remove?
    private Player       playerAdd;



    // Club
    private List<Club>   clubs;
    private Club         selectedClub;
    private List<String> clubNames;         // Nécessaire pour le menu déroulant
    private String       selectedClubName;  // Nécessaire pour le menu déroulant


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
        //loadData();
        football.populateDB();


        //get players
        this.playersList = football.getPlayers();
        this.playerNames = new ArrayList<String>();
        for (Player player : playersList) {
            this.playerNames.add(player.getLastname());
        }
        playerAdd = new Player(); // Initialize new player for form in JSF page

        // get clubs
        this.clubs = football.getClubs();
        //this.selectedClub = clubs.get(0);
        this.clubNames = new ArrayList<String>();
        for (Club club : clubs ) {
            this.clubNames.add(club.getNameClub());
        }

        // get leagues
        List<League> leagueList = football.getLeagues();
        this.leagueNames = new ArrayList<String>();
        for(League league : leagueList) {
            this.leagueNames.add(league.getNameLeague());
        }

    }


    //  M E T H O D S
    public void populateDB()
    {
        // populate football database
        System.out.println("populate football database...");
        System.out.println("POPULATE : " + football.seedDB());
    }
    /**
     * Load data into the database if it is empty
     */
    private void loadData()
    {
        football.seedDB();
    }
    public boolean verifyDB()
    {
        return false;
    }

    public void saveClub()
    {
        System.out.println("Selected Club tu update : " + selectedClub.getNameClub());
        football.updateClub(selectedClub);
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Club updated successfully", null));
    }

    /**
     * BTN de passage de Club à Edit Club
     * Réalise une redirection vers la page d'édition du club sélectionné
     * Recurperer le nom du club sélectionné
     */
    public void editClub()
    {
        // Récupérer l'instance de ExternalContext
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

        // Obtenir le nom du club sélectionné
        String selectedClubName = selectedClub.getNameClub();

        try {
            // Redirection vers la page d'édition avec le nom du club en tant que paramètre
            externalContext.redirect("editClub.xhtml?clubName=" + selectedClubName);
        } catch (IOException e) {
            // Gérer les exceptions si la redirection échoue
            e.printStackTrace();
        }
    }

    /**
     * BTN de Edit Club à Club (retour) & envoyer les modifications à la DB (save)
     */
    public void updateClubInfos()
    {
        // TODO : Recuperer les informations du champ InutText et les mettre dans le club sélectionné (selectedClub)
        // Récupérer les informations du champ InutText et les mettre dans le club sélectionné (selectedClub)
        UIComponent component = FacesContext.getCurrentInstance().getViewRoot().findComponent("formId:clubNameId");
        String nameClub = ((UIInput) component).getValue().toString();
        selectedClub.setNameClub(nameClub);

        component = FacesContext.getCurrentInstance().getViewRoot().findComponent("formId:locationId");
        String location = ((UIInput) component).getValue().toString();
        selectedClub.setLocation(location);

        component = FacesContext.getCurrentInstance().getViewRoot().findComponent("formId:stadNameId");
        String stadName = ((UIInput) component).getValue().toString();
        selectedClub.setStadName(stadName);

        component = FacesContext.getCurrentInstance().getViewRoot().findComponent("formId:countryNameId");
        String countryName = ((UIInput) component).getValue().toString();
        selectedClub.getCountry().setNameCountry(countryName);


        // Effectuer la mise à jour des informations du club dans la base de données
        football.updateClub(selectedClub);

        // Afficher un message de succès
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Club mis à jour avec succès", null));
    }

    public void updateClubSelected(ValueChangeEvent event) {
        selectedClubName = (String) event.getNewValue();
        selectedClub = null; // Réinitialiser selectedClub
        //setSelectedClub(null);

        // Rechercher le club sélectionné dans la liste des clubs
        for (Club club : clubs) {
            if (club.getNameClub().equals(selectedClubName)) {
                //setSelectedClub(club);
                selectedClub = club;
                break;
            }
        }
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
