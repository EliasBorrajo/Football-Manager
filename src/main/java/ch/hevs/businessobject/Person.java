package ch.hevs.businessobject;

import javax.persistence.*;
import java.util.Date;

@Entity
@Inheritance ( strategy = InheritanceType.TABLE_PER_CLASS )
public abstract class Person  // TODO LAISSER ABSTRACT ? Ou ça casse tout ?
{
    // A T T R I B U T S
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long   id;
    private String firstname;
    private String lastname;
    private Date   birthdate;

    // R E L A T I O N S
    @Embedded
    private Country country;

    // C O N S T R U C T O R S
    public Person() {
    }
    public Person(String firstname, String lastname, Date birthdate, Country country) {
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
    public Date getBirthdate()
    {
        return birthdate;
    }
    public void setBirthdate(Date birthdate)
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
