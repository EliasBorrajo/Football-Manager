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
    private Player favoritePlayer; // Par défaut peut avoir du null

    // C O N S T R U C T O R
    public Fan() {
        super();
    }
    public Fan(String firstname, String lastname,
               Date birthdate, Country country,
               Date subscriptionDate, Club fanOfClub,
               Player favoritePlayer)
    {
        super(firstname, lastname, birthdate, country);
        this.subscriptionDate = subscriptionDate;
//        this.fanOfClub = fanOfClub;           // TODO METTRE ? JE PENSE PAS CAR MIS AVEC METHODES DEPUIS GRAPHIQUE
//        this.favoritePlayer = favoritePlayer;
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
