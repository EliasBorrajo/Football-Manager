package ch.hevs.businessobject;

import javax.persistence.*;

/**
 * Business object Person, wich is an abstract class and embeds Country
 * Will not be in the DB, but will be in PERSISTENCE.XML file
 * Parent class of Player and Fan
 */
@Entity
@Inheritance ( strategy = InheritanceType.TABLE_PER_CLASS )
public abstract class Person
{
    // A T T R I B U T S
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long   id;
    private String firstname;
    private String lastname;
    private String birthdate;

    // R E L A T I O N S
    @Embedded
    private Country country;

    // C O N S T R U C T O R S
    /**
     * Default constructor, required by JPA
     */
    public Person() {
    }

    /**
     * Complete constructor to create a new person with all attributes
     * @param firstname : firstname of the person (ex: John)
     * @param lastname : lastname of the person (ex: Doe)
     * @param birthdate : birthdate of the person (ex: 01.01.1990)
     * @param country : country of the person (ex: Switzerland)
     */
    public Person(String firstname, String lastname, String birthdate, Country country) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.country = country;
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

    // firstname
    public String getFirstname()
    {
        return firstname;
    }
    public void setFirstname(String firstname)
    {
        this.firstname = firstname;
    }

    // lastname
    public String getLastname()
    {
        return lastname;
    }
    public void setLastname(String lastname)
    {
        this.lastname = lastname;
    }

    // birthdate
    public String getBirthdate()
    {
        return birthdate;
    }
    public void setBirthdate(String birthdate)
    {
        this.birthdate = birthdate;
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
}
