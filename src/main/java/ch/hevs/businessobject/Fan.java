package ch.hevs.businessobject;
import javax.persistence.*;
import java.util.Date;

@Entity
public class Fan extends Person
{
    // A T T R I B U T S
    private Date subscriptionDate;


    // R E L A T I O N S
    // FAN |-0..1------1..1-> CLUB
    @ManyToOne( mappedBy = "fans" )
    @JoinColumn( nullable = false ) // a fan must have a club he is fan of
    private Club fanOfClub;

    @OneToOne // Uni-directional, Fan knows his favorite player but not the opposite
    private Player favoritePlayer; // Par défaut peut avoir du null
}
