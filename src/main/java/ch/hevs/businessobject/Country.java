package ch.hevs.businessobject;

import javax.persistence.Embeddable;

/**
 * Business object Country, wich is an embeddable object
 * Will not be in the DB, but will be in PERSISTENCE.XML file
 */
@Embeddable
public class Country {

    // A T T R I B U T S
    private String nameCountry;

    // C O N S T R U C T O R S
    /**
     * Default constructor, required by JPA
     */
    public Country(){    }
    /**
     * Complete constructor to create a new country with all attributes
     * @param nameCountry
     */
    public Country(String nameCountry) {
        this.nameCountry = nameCountry;
    }

    // G E T T E R S   &   S E T T E R S
    //Name
    public String getNameCountry() {
        return nameCountry;
    }
    public void setNameCountry(String name) {
        this.nameCountry = nameCountry;
    }

    @Override
    public String toString() {
        return "Country{" +
                "nameCountry='" + nameCountry + '\'' +
                '}';
    }
}
