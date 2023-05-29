package ch.hevs.businessobject;

import javax.persistence.Embeddable;

// NE SERA PAS DANS LA DB, mais sera dans PERSISTENCE.XML
@Embeddable
public class Country {

    // A T T R I B U T S
    private String name;

    // C O N S T R U C T O R S
    public Country(){    }
    public Country(String name) {
        this.name = name;
    }

    // G E T T E R S   &   S E T T E R S
    //Name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
