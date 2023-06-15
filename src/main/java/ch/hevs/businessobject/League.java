package ch.hevs.businessobject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Business object League, used to persist data in the database.
 */
@Entity
public class League
{
    // A T T R I B U T S
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nameLeague;
    private String division;


    // R E L A T I O N S
    // League <-1..1------1..*-> Club
    @OneToMany ( mappedBy = "league" , cascade = CascadeType.REMOVE) // Remove : Supprime les clubs de la ligue si on supprime la ligue
    private List<Club> clubs;

    // League <>---------------| Country
    @Embedded
    private Country country;

    // C O N S T R U C T O R S
    /**
     * Default constructor, required by JPA
     */
    public League() {
        this.clubs = new ArrayList<Club>();
    }
    /**
     * Complete constructor to create a new league with all attributes
     * @param nameLeague : name of the league (ex: Super League)
     * @param division : division of the league (ex: 1)
     * @param country : country of the league (ex: Switzerland)
     */
    public League( String nameLeague, String division, Country country) {
        this.clubs = new ArrayList<Club>();
        this.nameLeague = nameLeague;
        this.division = division;
        this.country = country;
    }

    // M E T H O D S  &  O T H E R S
    public void addClub(Club club)
    {
        this.clubs.add(club);   // Add club to list here
        club.setLeague(this);   // Set league to club there
    }
    public void removeClub(Club club)
    {
        this.clubs.remove(club);
        club.setLeague(null);
    }



    // G E T T E R S   &   S E T T E R S
    //id
    public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id = id;
    }
    // name
    public String getNameLeague()
    {
        return nameLeague;
    }
    public void setNameLeague(String nameLeague)
    {
        this.nameLeague = nameLeague;
    }
    // division
    public String getDivision()
    {
        return division;
    }
    public void setDivision(String division)
    {
        this.division = division;
    }
    // clubs
    public List<Club> getClubs()
    {
        return clubs;
    }
    public void setClubs(List<Club> clubs)
    {
        this.clubs = clubs;
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

    @Override
    public String toString() {
        return "League{" +
                "id=" + id +
                ", nameLeague='" + nameLeague + '\'' +
                ", division='" + division + '\'' +
                ", clubs=" + clubs +
                ", country=" + country +
                '}';
    }
}
