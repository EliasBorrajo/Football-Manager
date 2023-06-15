package ch.hevs.businessobject;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Business object Fan, wich enherit from Person
 */
@Entity
public class Fan extends Person
{
    // A T T R I B U T S
    private String subscriptionDate;

    // R E L A T I O N S
    // FAN |-0..1------1..1-> CLUB
    @ManyToOne /*( cascade = CascadeType.ALL)*/
    @JoinColumn( nullable = false ) // a fan must have a club he is fan of
    private Club fanOfClub;

    // C O N S T R U C T O R
    /**
     * Default constructor, required by JPA
     */
    public Fan() {
        super();
    }
    /**
     * Complete constructor to create a new fan with all attributes
     * @param firstname : firstname of the fan (ex: John)
     * @param lastname : lastname of the fan (ex: Doe)
     * @param birthdate : birthdate of the fan (ex: 01.01.1990)
     * @param country : country of the fan (ex: Switzerland)
     * @param subscriptionDate : subscription date of the fan (ex: 01.01.2018)
     * @param fanOfClub : club the fan is fan of (ex: FC Sion)
     */
    public Fan(String firstname, String lastname,
               String birthdate, Country country,
               String subscriptionDate, Club fanOfClub)
    {
        super(firstname, lastname, birthdate, country);
        this.subscriptionDate = subscriptionDate;
        this.fanOfClub = fanOfClub;
    }


    // G E T T E R S   &   S E T T E R S
    // subscriptionDate
    public String getSubscriptionDate()
    {
        return subscriptionDate;
    }
    public void setSubscriptionDate(String subscriptionDate)
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

    @Override
    public String toString() {
        return "Fan{" +
                "subscriptionDate='" + subscriptionDate + '\'' +
                ", fanOfClub=" + fanOfClub +
                '}';
    }
}
