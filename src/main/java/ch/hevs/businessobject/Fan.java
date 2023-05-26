package ch.hevs.businessobject;
import javax.persistence.ManyToOne;
import java.util.Date;

public class Fan extends Person
{
    // A T T R I B U T S
    private Date subscriptionDate;
    private Player favoritePlayer;

    // R E L A T I O N S
    @ManyToOne( mappedBy = "fans" )
    private Club fanOfClub;
}
