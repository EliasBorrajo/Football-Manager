package ch.hevs.businessobject;

import javax.persistence.Embeddable;

@Embeddable
public class Country {

    private String name;

    //Name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    //Constructor
    public Country(){
    }
    public Country(String name) {
        this.name = name;
    }
}
