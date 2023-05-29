package ch.hevs.businessobject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Club {

    // A T T R I B U T S
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "nameClub")
    private String name;
    private String location;
    private String stadName;

    // R E L A T I O N S
    // Club <>---------------| Country
    @Embedded
    private Country country;

    // Club <-1..*------1..1-> League
    @ManyToOne ( cascade = CascadeType.ALL)
    private League league;

    // Club <-1..1------1..*-> Player
    @OneToMany(mappedBy = "playsForClub", cascade = CascadeType.ALL)
    private List<Player> players;

    // C O N S T R U C T O R S
    public Club(){
        this.players = new ArrayList<Player>();
    }
    public Club(String name, String location, String stadName) {
        this.players = new ArrayList<Player>();
        this.name = name;
        this.location = location;
        this.stadName = stadName;
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
        player.setPlaysForClub(null); // TODO : Peut on laisser null ?  Voir RELATION
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
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
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
}
