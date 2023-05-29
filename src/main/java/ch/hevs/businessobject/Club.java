package ch.hevs.businessobject;

import javax.persistence.*;
import java.util.List;

@Entity
public class Club {

    // A T T R I B U T S
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String location;
    private String stadName;

    // R E L A T I O N S
    @Embedded
    private Country country;

    @ManyToOne
    private League league;

    @OneToMany(mappedBy = "playsForClub")
    private List<Player> players;

    //name
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    //location
    public String getLocation() {return location;}
    public void setLocation(String location) {this.location = location;}

    //stadName
    public String getStadName() {return stadName;}
    public void setStadName(String stadName) {this.stadName = stadName;}

    //Contructor
    public Club(){
    }
    public Club(String name, String location, String stadName) {
        this.name = name;
        this.location = location;
        this.stadName = stadName;
    }
}
