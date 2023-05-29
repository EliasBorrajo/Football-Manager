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
    @ManyToOne ( cascade = CascadeType.ALL)//( mappedBy = "fans" ) //  // TODO : ça bug "cannot find symbol" --> signifie que j'ai pas de players dans Club
    @JoinColumn( nullable = false ) // a fan must have a club he is fan of
    private Club fanOfClub;

    // FAN |-0..1------1..1-> PLAYER
    @OneToOne // Uni-directional, Fan knows his favorite player but not the opposite
    @JoinColumn( nullable = true ) // a fan must have a favorite player
    private Player favoritePlayer; // Par défaut peut avoir du null

    // C O N S T R U C T O R
    public Fan() {
        super();
    }
    public Fan(String firstname, String lastname,
               Date birthdate, Country country,
               Date subscriptionDate)
    {
        super(firstname, lastname, birthdate, country);
        this.subscriptionDate = subscriptionDate;
    }


    // G E T T E R S   &   S E T T E R S
    // subscriptionDate
    public Date getSubscriptionDate()
    {
        return subscriptionDate;
    }
    public void setSubscriptionDate(Date subscriptionDate)
    {
        this.subscriptionDate = subscriptionDate;
    }
    // fanOfClub
    public Club getFanOfClub()
    {
        return fanOfClub;
    }
    public void setFanOfClub(Club fanOfClub)
    {
        this.fanOfClub = fanOfClub;
    }
    // favoritePlayer
    public Player getFavoritePlayer()
    {
        return favoritePlayer;
    }
    public void setFavoritePlayer(Player favoritePlayer)
    {
        this.favoritePlayer = favoritePlayer;
    }
}
