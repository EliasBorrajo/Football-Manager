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

    // Club
    private List<Club>   clubs;
    private Club         selectedClub;
    private List<String> clubNames;
    private String       selectedClubName;
    private List<Club> allClubs;

    //Fan
    private List<Fan> fansList;
    private Fan       selectedFan;
    private List<String> fansNames;
    private String       selectedFanName;

    //League
    private List<League> leaguesList;
    private League       selectedLeague;
    private List<String> leaguesNames;
    private String       selectedLeagueName;




    //  C O N S T R U C T O R S
    @PostConstruct // exécutée QUE si l'interface graphique est utilisée
    public void initialize() throws NamingException {
        System.out.println("initialize ClubBean");
        // use JNDI to inject reference to bank EJB
        InitialContext ctx = new InitialContext();
        football = (Football) ctx.lookup("java:global/Jakarta-Football-Manager-0.0.2-SNAPSHOT/FootballBean!ch.hevs.services.Football");

        initializeDB(); // Initial DB population && Reset si on change de User
    }

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
    }

    //  M E T H O D S
    public void populateDB() {
        // populate football database
        System.out.println("populate football database...");
        System.out.println("POPULATE : " + football.seedDB());
    }

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
     * BTN de Edit Club à Club (retour) & envoyer les modifications à la DB (save)
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

    public void updatePlayer() {
        // Mettre à jour le joueur dans la base de données avec les nouvelles informations
        football.updatePlayer(selectedPlayer);
        // Réinitialiser la propriété selectedPlayer pour désélectionner le joueur
        selectedPlayer = null;

        initAttributes();
    }

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

    // NAVIGATION BUTTONS
    /*
    * Manager --> Acces To League & Club
    * Player  --> Acces To Player & Club (readOnly)
    * Fan     --> Acces To Fan    & Player (readOnly)
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
     * BTN de passage de Club à Edit Club
     * Réalise une redirection vers la page d'édition du club sélectionné
     * Recurperer le nom du club sélectionné
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
     * Redirection vers la page xhtml passée en paramètre (avec le chemin relatif)
     * @param xhtmlPageName : nom de la page xhtml (avec le chemin relatif)
     *                        Exemple : "/Club/showClubInfos.xhtml"
     * @return true si la redirection a été effectuée, false sinon
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
}
