package ch.hevs.businessobject;

import javax.annotation.PostConstruct;
import javax.ejb.PostActivate;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Business object Club, used to persist data in the database.
 */
@Entity
public class Club {

    // A T T R I B U T S
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nameClub;
    private String location;
    private String stadName;

    // R E L A T I O N S
    // Club <>---------------| Country
    @Embedded
    private Country country;

    // Club <-1..*------1..1-> League
    @ManyToOne
    private League league;

    // Club <-1..1------1..*-> Player
    @OneToMany(mappedBy = "playsForClub", cascade = CascadeType.REMOVE)
    private List<Player> players;

    // C O N S T R U C T O R S

    /**
     * Default constructor, required by JPA
     */
    public Club(){
        this.players = new ArrayList<Player>();
    }

    /**
     * Complete constructor to create a new club with all attributes
     * @param nameClub : name of the club (ex: FC Sion)
     * @param location : location of the club (ex: Sion)
     * @param stadName : name of the stadium (ex: Tourbillon)
     * @param country : country of the club (ex: Switzerland)
     * @param league : league of the club (ex: Super League)
     */
    public Club(String nameClub, String location, String stadName, Country country, League league) {
        this.players = new ArrayList<Player>();
        this.nameClub = nameClub;
        this.location = location;
        this.stadName = stadName;
        this.country = country;
        this.league = league;
    }

    // M E T H O D S  &  O T H E R S
    public void addPlayer(Player player)
    {
        this.players.add(player);       // Add player to list here
        player.setPlaysForClub(this);   // Set club to player there
    }
    public void removePlayer(Player player)
    {
        this.players.remove(player);
        player.setPlaysForClub(null);
    }


    // G E T T E R S   &   S E T T E R S
    // id
    public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id = id;
    }
    //name
    public String getNameClub() {return nameClub;}
    public void setNameClub(String name) {this.nameClub = name;}
    //location
    public String getLocation() {return location;}
    public void setLocation(String location) {this.location = location;}
    //stadName
    public String getStadName() {return stadName;}
    public void setStadName(String stadName) {this.stadName = stadName;}
    // players
    public List<Player> getPlayers()
    {
        return players;
    }
    public void setPlayers(List<Player> players)
    {
        this.players = players;
    }
    // country
    public Country getCountry()
    {
        return country;
    }
    public void setCountry(Country country)
    {
        this.country = country;
    }
    // league
    public League getLeague()
    {
        return league;
    }
    public void setLeague(League league)
    {
        this.league = league;
    }

    // T O   S T R I N G
    /**
     * Converter for the Club entity uses the toString() method of the entity to convert the entity to a String.
     *
     * Make sure that the toString() of your Club entity returns an unique representation of the entity.
     * You could for instance directly return the ID:
     */
    @Override
    public String toString() {
        return "Club{id=" + id + "}";
        //return String.valueOf(id);// works too
    }

    /**
     * Alternative for the toString() method of the entity to convert the entity to a String.
     * @return : String with all the attributes of the entity
     */
    public String show() {
        return "Club{" +
                "id=" + id +
                ", nameClub='" + nameClub + '\'' +
                ", location='" + location + '\'' +
                ", stadName='" + stadName + '\'' +
                ", country=" + country +
                ", league=" + league +
                ", players=" + players +
                '}';
    }

    // L I S T E N E R S  @POST...
    @PostPersist
    public void acknowledgePersist()
    {
        System.out.println("Club " + this.nameClub + " persisted");
    }

    @PostRemove
    public void acknowledgeRemove()
    {
        System.out.println("Club " + this.nameClub + " removed");
    }

    @PostUpdate
    public void acknowledgeUpdate()
    {
        System.out.println("Club " + this.nameClub + " updated");
    }

    @PostLoad
    public void acknowledgeLoad()
    {
        System.out.println("Club " + this.nameClub + " loaded");
    }

    @PostConstruct
    public void acknowledgeConstruct()
    {
        System.out.println("Club " + this.nameClub + " constructed");
    }

    @PostActivate
    public void acknowledgeActivate()
    {
        System.out.println("Club " + this.nameClub + " activated");
    }
}
