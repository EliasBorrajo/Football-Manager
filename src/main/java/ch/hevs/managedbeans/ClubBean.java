package ch.hevs.managedbeans;

import ch.hevs.businessobject.*;
import ch.hevs.services.Football;
import ch.hevs.utils.exception.FootballException;

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

/**
 * JSF Managed Bean
 * Controller for the different views of the application
 * Contains all the methods used by the views to interact with the model (Football)
 * Contains all the attributes used by the views to display the model (Football) data
 */
@ManagedBean
public class ClubBean {
    //  A T T R I B U T S
    // General
    private Football football;
    private boolean  serviceLayerResult;
    private String currentUser;

    // Player
    private List<Player> playersList;
    private Player       selectedPlayer;
    private List<String> playerNames;
    private String       selectedPlayerName;
    private List<Player> playersFromClubList;
    private List<String> playerFromClubNames;
    private String       selectedPlayerFromClubName;
    private Player newPlayer;

    // Club
    private List<Club>   clubs;
    private Club         selectedClub;
    private List<String> clubNames;
    private String       selectedClubName;
    private List<Club> allClubs;
    private Long clubNumber;
    private Club newClub;

    //Fan
    private List<Fan> fansList;
    private Fan       selectedFan;
    private List<String> fansNames;
    private String       selectedFanName;
    private Fan newFan;

    //League
    private List<League> leaguesList;
    private League       selectedLeague;
    private List<String> leaguesNames;
    private String       selectedLeagueName;
    private Long leagueNumber;
    private League newLeague;




    //  C O N S T R U C T O R S
    /**
     *  Initialize the Managed Bean with the variables used by the views (xhtml) to display the data of the model (Football)
     *  @PostConstruct : executed after the constructor and the injection of the dependencies (EJB) by the container
     *  @throws NamingException : if the lookup fails to find the EJB with the specified JNDI name in the container registry (wildfly)
     */
    @PostConstruct // exécutée QUE si l'interface graphique est utilisée
    public void initialize() throws NamingException {
        System.out.println("initialize ClubBean");
        // use JNDI to inject reference to bank EJB
        InitialContext ctx = new InitialContext();
        football = (Football) ctx.lookup("java:global/Jakarta-Football-Manager-0.0.2-SNAPSHOT/FootballBean!ch.hevs.services.Football");

        initializeDB(); // Initial DB population && Reset si on change de User
    }

    //  M E T H O D S
    /**
     * Reset DB and populate it again with the initial data set
     * Used when changing user (manager, player, fan)
     * Used initially to populate the DB with the initial data set (seedDB)
     */
    public void initializeDB() {
        // populate football database
        System.out.println("reset football database...");
        System.out.println("RESET : " + football.resetDatabase() );

        System.out.println("DB RESET, NOW POPULATE... " + football.seedDB());
        System.out.println("DB POPULATED");

        initAttributes();
    }

    /**
     * Initialize the variables used by the views (xhtml) to display the data
     */
    private void initAttributes() {
        System.out.println("I N I T    A T T R I B U T E S");

        // General
        this.currentUser = football.getCurrentUser();

        //get players
        this.playersList = football.getPlayers();
        this.playerNames = new ArrayList<String>();
        for (Player player : playersList) {
            this.playerNames.add(player.getLastname());
        }

        // get clubs
        this.clubs = football.getClubs();
        //this.selectedClub = clubs.get(0);
        this.clubNames = new ArrayList<String>();
        for (Club club : clubs ) {
            this.clubNames.add(club.getNameClub());
        }
        allClubs = football.getClubs();

        // get fans
        this.fansList = football.getFans();
        this.fansNames = new ArrayList<String>();
        for(Fan fan : fansList){
            this.fansNames.add(fan.getLastname());
        }

        //get leagues
        this.leaguesList = football.getLeagues();
        this.leaguesNames = new ArrayList<String>();
        for(League league : leaguesList){
            this.leaguesNames.add(league.getNameLeague());
        }

        //Init league for add method
        newLeague = new League();
        newLeague.setCountry(new Country());

        //Init fan for add method
        newFan = new Fan();
        newFan.setCountry(new Country());
        newFan.setFanOfClub(new Club());

        //Init player for add method
        newPlayer = new Player();
        newPlayer.setCountry(new Country());
        newPlayer.setPlaysForClub(new Club());

        //Init club for add method
        newClub = new Club();
        newClub.setCountry(new Country());
        newClub.setLeague(new League());

    }

    /**
     * Populate the DB with the initial data set (seedDB)
     * @Warning : If called twice, the data set will be duplicated in the DB
     */
    public void populateDB() {
        // populate football database
        System.out.println("populate football database...");
        System.out.println("POPULATE : " + football.seedDB());
    }


    /**
     * Button to get the inputs from the view (xhtml), update the model in the DB and refresh the view (xhtml) to display the changes
     */
    public void updateClubInfos() {
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

        // Effectuer la mise à jour des informations du club dans la base de données
        football.updateClub(selectedClub);

        // Afficher un message de succès
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Club mis à jour avec succès", null));

        initAttributes();
    }


    /**
     * Button to get the inputs from the view (xhtml), update the model in the DB and refresh the view (xhtml) to display the changes
     */
    public void updatePlayerInfos() {
        // Récupérer les informations du champ InutText et les mettre dans le club sélectionné (selectedClub)
        UIComponent component = FacesContext.getCurrentInstance().getViewRoot().findComponent("formId:positionPlayerId");
        String positionPlayer = ((UIInput) component).getValue().toString();
        selectedPlayer.setPositionPlayer(positionPlayer);

        component = FacesContext.getCurrentInstance().getViewRoot().findComponent("formId:numberId");
        String numberValue = ((UIInput) component).getValue().toString();
        int number = Integer.parseInt(numberValue);
        selectedPlayer.setNumber(number);

        component = FacesContext.getCurrentInstance().getViewRoot().findComponent("formId:rightFootedId");
        boolean rightFooted = Boolean.parseBoolean(((UIInput) component).getValue().toString());
        selectedPlayer.setRightFooted(rightFooted);

        component = FacesContext.getCurrentInstance().getViewRoot().findComponent("formId:heightId");
        double height = Double.parseDouble(((UIInput) component).getValue().toString());
        selectedPlayer.setHeight(height);

        component = FacesContext.getCurrentInstance().getViewRoot().findComponent("formId:weightId");
        double weight = Double.parseDouble(((UIInput) component).getValue().toString());
        selectedPlayer.setWeight(weight);


        // Effectuer la mise à jour des informations du club dans la base de données
        football.updatePlayer(selectedPlayer);

        // Afficher un message de succès
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Player mis à jour avec succès", null));

        initAttributes();
    }

    /**
     * Button to delete the selected club
     * Verify that the user has the adequate role, and then delete the club
     * @Warning : If the club has players & fans, all of them will be deletet too
     */
    public void deleteClub() {
        if (football.verifyManagerRole())
        {
            if( selectedClub != null)
            {
                System.out.println("Selected Club tu delete : " + selectedClub.getNameClub());
                boolean isSuccess = football.deleteClub(selectedClub);

                if(isSuccess)
                    FacesContext.getCurrentInstance()
                            .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Club deleted successfully", null));
                else
                    FacesContext.getCurrentInstance()
                            .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Club deletion could not happen", null));

                initAttributes();

            }
            else
            {
                FacesContext.getCurrentInstance()
                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No club selected", null));
            }
        }
        else {
            // L'utilisateur n'a pas le rôle requis, redirection vers la page d'accès refusé
            navigationRedirection("/accessDenied.xhtml");
        }
    }

    // DROP DOWN LIST UPDATERS
    /**
     * Update the selected club when the user selects a club from the drop down list
     * @param event : the event that triggered the method (selecting a club from the drop down list)
     */
    public void updateClubSelected(ValueChangeEvent event) {
        selectedClubName = (String) event.getNewValue();
        selectedClub = null; // Réinitialiser selectedClub

        // Rechercher le club sélectionné dans la liste des clubs
        for (Club club : clubs) {
            if (club.getNameClub().equals(selectedClubName)) {
                selectedClub = club;
                break;
            }
        }
    }

    /**
     * Update the selected player when the user selects a player from the drop down list
     * @param event : the event that triggered the method (selecting a player from the drop down list)
     */
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

    /**
     * Update the selected fan when the user selects a fan from the drop down list
     * @param event : the event that triggered the method (selecting a fan from the drop down list)
     */
    public void updateSelectedFan(ValueChangeEvent event) {
        selectedFanName = (String) event.getNewValue();
        selectedFan = null; // Réinitialiser selectedFan

        for (Fan fan : fansList) {
            if (fan.getLastname().equals(selectedFanName)) {
                selectedFan = fan;
                break;
            }
        }
    }

    /**
     * Update the selected league when the user selects a league from the drop down list
     * @param event : the event that triggered the method (selecting a league from the drop down list)
     */
    public void updateSelectedLeague(ValueChangeEvent event) {
        selectedLeagueName = (String) event.getNewValue();
        selectedLeague = null; // Réinitialiser selectedLeague

        for (League league : leaguesList) {
            if (league.getNameLeague().equals(selectedLeagueName)) {
                selectedLeague = league;
                break;
            }
        }
    }

    /**
     * Button to update the selected player with the new informations entered by the user in the form to the DB
     */
    public void updatePlayer() {
        // Mettre à jour le joueur dans la base de données avec les nouvelles informations
        football.updatePlayer(selectedPlayer);
        // Réinitialiser la propriété selectedPlayer pour désélectionner le joueur
        selectedPlayer = null;

        initAttributes();
    }

    /**
     * Adding a new league to the DB table
     */
    public void addLeague(){
            football.addLeague(newLeague);
            newLeague = new League();
            newLeague.setCountry(new Country());

            initAttributes();
    }

    /**
     * Adding a new fan to the DB table
     */
    public void addFan(){

            newFan.setFanOfClub(football.getClub(clubNumber));
            football.addFan(newFan);
            newFan = new Fan();
            newFan.setCountry(new Country());
            newFan.setFanOfClub(new Club());

            initAttributes();
    }

    /**
     * Adding a new player to the DB table
     */
    public void addPlayer(){

            newPlayer.setPlaysForClub(football.getClub(clubNumber));
            football.addPlayer(newPlayer);
            newPlayer = new Player();
            newPlayer.setCountry(new Country());
            newPlayer.setPlaysForClub(new Club());

            initAttributes();
    }

    /**
     * Adding a new club to the DB table
     */
    public void addClub(){

            newClub.setLeague(football.getLeague(leagueNumber));
            football.addClub(newClub);
            newClub = new Club();
            newClub.setCountry(new Country());

            initAttributes();
    }


    // NAVIGATION BUTTONS
    /*
    * Manager --> Acces To League (ALL) & Club (ALL)
    * Player  --> Acces To Player (ALL) & Club (readOnly)
    * Fan     --> Acces To Fan (ALL)    & Player (readOnly)
    */
    /**
     * Button to show the club page (if the user has the adequate role)
     */
    public void showClub() {
        if (football.verifyManagerRole() || football.verifyPlayerRole()) {
            // L'utilisateur a le rôle requis, redirection vers la page des clubs
            navigationRedirection("/Club/showClubInfos.xhtml" );
        }
        else {
            // L'utilisateur n'a pas le rôle requis, redirection vers la page d'accès refusé
            navigationRedirection("/accessDenied.xhtml");
        }
    }

    /**
     * Button to show the league page (if the user has the adequate role)
     */
    public void showLeague() {
        if (football.verifyManagerRole()) {
            // L'utilisateur a le rôle requis, redirection vers la page des clubs
            navigationRedirection("/League/leagueInfos.xhtml");
        }
        else {
            // L'utilisateur n'a pas le rôle requis, redirection vers la page d'accès refusé
            navigationRedirection("/accessDenied.xhtml");
        }
    }

    /**
     * Button to show the player page (if the user has the adequate role)
     */
    public void showPlayer() {
        if (football.verifyPlayerRole() || football.verifyFanRole()) {
            // L'utilisateur a le rôle requis, redirection vers la page des clubs
            navigationRedirection("/Player/playerInfos.xhtml");
        }
        else {
            // L'utilisateur n'a pas le rôle requis, redirection vers la page d'accès refusé
            navigationRedirection("/accessDenied.xhtml");
        }
    }

    /**
     * Button to show the fan page (if the user has the adequate role)
     */
    public void showFan() {
        if (football.verifyFanRole()) {
            // L'utilisateur a le rôle requis, redirection vers la page des clubs
            navigationRedirection("/Fan/fanInfos.xhtml");
        }
        else {
            // L'utilisateur n'a pas le rôle requis, redirection vers la page d'accès refusé
            navigationRedirection("/accessDenied.xhtml");
        }
    }

    /**
     * Button to show the edit player page (if the user has the adequate role)
     */
    public void showEditPlayer() {
        if (football.verifyPlayerRole()) {
            // L'utilisateur a le rôle requis, redirection vers la page des clubs
            navigationRedirection("/Player/playerUpdate.xhtml");
        }
        else {
            // L'utilisateur n'a pas le rôle requis, redirection vers la page d'accès refusé
            navigationRedirection("/accessDenied.xhtml");
        }
    }

    /**
     * Button to show the edit Club page (if the user has the adequate role)
     * Redirect to the editClub page with the selected club name as parameter
     */
    public void showEditClub() {
        if (football.verifyManagerRole())
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
        else {
            // L'utilisateur n'a pas le rôle requis, redirection vers la page d'accès refusé
            navigationRedirection("/accessDenied.xhtml");
        }
    }

    /**
     * Button to show the add player page (if the user has the adequate role)
     */
    public void showAddPlayer() {

        if (football.verifyPlayerRole()) {
            // L'utilisateur a le rôle requis, redirection vers la page des clubs
            navigationRedirection("/Player/addPlayer.xhtml");
        }
        else {
            // L'utilisateur n'a pas le rôle requis, redirection vers la page d'accès refusé
            navigationRedirection("/accessDenied.xhtml");
        }
    }

    /**
     * Button to show the add fan page (if the user has the adequate role)
     */
    public void showAddFan() {

        if (football.verifyFanRole()) {
            // L'utilisateur a le rôle requis, redirection vers la page des clubs
            navigationRedirection("/Fan/addFan.xhtml");
        }
        else {
            // L'utilisateur n'a pas le rôle requis, redirection vers la page d'accès refusé
            navigationRedirection("/accessDenied.xhtml");
        }
    }

    /**
     * Button to show the add league page (if the user has the adequate role)
     */
    public void showAddLeague() {

        if (football.verifyManagerRole()) {
            // L'utilisateur a le rôle requis, redirection vers la page des clubs
            navigationRedirection("/League/addLeague.xhtml");
        }
        else {
            // L'utilisateur n'a pas le rôle requis, redirection vers la page d'accès refusé
            navigationRedirection("/accessDenied.xhtml");
        }
    }

    /**
     * Button to show the add club page (if the user has the adequate role)
     */
    public void showAddClub() {

        if (football.verifyManagerRole()) {
            // L'utilisateur a le rôle requis, redirection vers la page des clubs
            navigationRedirection("/Club/addClub.xhtml");
        }
        else {
            // L'utilisateur n'a pas le rôle requis, redirection vers la page d'accès refusé
            navigationRedirection("/accessDenied.xhtml");
        }
    }

    /**
     * Redirection to the page passed as parameter (with the relative path)
     * @param xhtmlPageName : name of the xhtml page (with the relative path)
     *                      Example : "/Club/showClubInfos.xhtml"
     * @return true if the redirection has been done, false otherwise
     */
    private boolean navigationRedirection(String xhtmlPageName ) {
        // L'utilisateur a le rôle requis, redirection vers la page des clubs
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            String redirectUrl = facesContext.getApplication().getViewHandler().getActionURL(facesContext, xhtmlPageName);
            facesContext.getExternalContext().redirect(redirectUrl);
            facesContext.responseComplete();
            return true;
        } catch (IOException e) {
            throw new FootballException("Erreur de redirection vers la page : "+ xhtmlPageName, e);
        }
    }

    /**
     * Data table to show all the clubs in the league selected by the user in the league list (leagueInfos.xhtml)
     * @return : list of all the clubs in the league selected by the user
     */
    public List<Club> getAllClubsInLeague() {
        List<Club> clubs = football.getClubsFromLeague(selectedLeague.getId());
        return clubs;
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
    public List<String> getPlayerNames() {
        return playerNames;
    }
    public void setPlayerNames(List<String> playerNames) {
        this.playerNames = playerNames;
    }
    public String getSelectedPlayerName() {
        System.out.println("Get selected player : "+selectedPlayerName);
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
    public List<Fan> getFansList() {
        return fansList;
    }
    public void setFansList(List<Fan> fansList) {
        this.fansList = fansList;
    }
    public Fan getSelectedFan() {
        if(selectedFan!=null){
            this.playersFromClubList = football.getPlayersFromClubForFan(this.selectedFan.getFanOfClub().getId());
            this.playerFromClubNames = new ArrayList<>();
            for (Player player : playersFromClubList) {
                this.playerFromClubNames.add(player.getLastname());
            }
        }else{
        }

        return selectedFan;

    }
    public void setSelectedFan(Fan selectedFan) {
        this.selectedFan = selectedFan;
    }
    public List<String> getFansNames() {
        return fansNames;
    }
    public void setFansNames(List<String> fansNames) {
        this.fansNames = fansNames;
    }
    public String getSelectedFanName() {
        System.out.println("Get Selected Fan name: "+selectedFanName);
        return selectedFanName;
    }
    public void setSelectedFanName(String selectedFanName) {
        this.selectedFanName = selectedFanName;
    }
    public List<Player> getPlayersFromClubList() {
        return playersFromClubList;
    }
    public void setPlayersFromClubList(List<Player> playersFromClubList) {
        this.playersFromClubList = playersFromClubList;
    }
    public List<String> getPlayerFromClubNames() {
        return playerFromClubNames;
    }
    public void setPlayerFromClubNames(List<String> playerFromClubNames) {
        this.playerFromClubNames = playerFromClubNames;
    }
    public String getSelectedPlayerFromClubName() {
        return selectedPlayerFromClubName;
    }
    public void setSelectedPlayerFromClubName(String selectedPlayerFromClubName) {
        this.selectedPlayerFromClubName = selectedPlayerFromClubName;
    }
    public List<League> getLeaguesList() {
        return leaguesList;
    }
    public void setLeaguesList(List<League> leaguesList) {
        this.leaguesList = leaguesList;
    }
    public League getSelectedLeague() {
        return selectedLeague;
    }
    public void setSelectedLeague(League selectedLeague) {
        this.selectedLeague = selectedLeague;
    }
    public List<String> getLeaguesNames() {
        return leaguesNames;
    }
    public void setLeaguesNames(List<String> leaguesNames) {
        this.leaguesNames = leaguesNames;
    }
    public String getSelectedLeagueName() {
        return selectedLeagueName;
    }
    public void setSelectedLeagueName(String selectedLeagueName) {
        this.selectedLeagueName = selectedLeagueName;
    }
    public String getCurrentUser() {
        return currentUser;
    }
    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }
    public List<Club> getAllClubs() {
        return allClubs;
    }
    public void setAllClubs(List<Club> allClubs) {
        this.allClubs = allClubs;
    }

    public League getNewLeague() {
        return newLeague;
    }

    public void setNewLeague(League newLeague) {
        this.newLeague = newLeague;
    }

    public Fan getNewFan() {
        return newFan;
    }

    public void setNewFan(Fan newFan) {
        this.newFan = newFan;
    }

    public Long getClubNumber() {
        return clubNumber;
    }

    public void setClubNumber(Long clubNumber) {
        this.clubNumber = clubNumber;
    }

    public Club getNewClub() {
        return newClub;
    }

    public void setNewClub(Club newClub) {
        this.newClub = newClub;
    }

    public Player getNewPlayer() {
        return newPlayer;
    }

    public void setNewPlayer(Player newPlayer) {
        this.newPlayer = newPlayer;
    }

    public Long getLeagueNumber() {
        return leagueNumber;
    }

    public void setLeagueNumber(Long leagueNumber) {
        this.leagueNumber = leagueNumber;
    }
}
