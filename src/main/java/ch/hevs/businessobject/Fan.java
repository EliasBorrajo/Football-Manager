package ch.hevs.businessobject;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

public class Fan extends Person
{
    // A T T R I B U T S
    private Date subscriptionDate;
    private Player favoritePlayer;

    // R E L A T I O N S
    // FAN |-0..1------1..1-> CLUB
    @ManyToOne( mappedBy = "fans" )
    @JoinColumn( nullable = false ) // a fan must have a club he is fan of
    private Club fanOfClub;
}
