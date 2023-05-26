package ch.hevs.businessobject;

import javax.persistence.*;
import java.util.List;

@Entity
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String location;
    private String stadName;

    //Relations
    @Embedded
    private Country country;

    @ManyToOne
    private League league;

    @OneToMany
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
