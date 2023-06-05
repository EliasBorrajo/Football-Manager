package ch.hevs.managedbeans;


import ch.hevs.services.Football;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@ManagedBean // TODO : Verifier le @ManagedBean
public class ClubBean {
    //  A T T R I B U T S
    private Football football;
    private boolean  serviceLayerResult;

    //  C O N S T R U C T O R S
    @PostConstruct // exécutée QUE si l'interface graphique est utilisée
    public void initialize() throws NamingException
    {
        // use JNDI to inject reference to bank EJB
        InitialContext ctx = new InitialContext();
        football = (Football) ctx.lookup("java:global/TP12-WEB-EJB-PC-EPC-E-0.0.1-SNAPSHOT/FootballBean!ch.hevs.services.Football");

        // populate football database
        loadData();

        // get players

        // get clubs

        // get leagues

        // get fans

        // initalize attributes for the view

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
}
