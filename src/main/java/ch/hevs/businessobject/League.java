package ch.hevs.businessobject;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class League {

private String name;
private String division;

    //relations

    @OneToMany
    private Club club;

    @Embedded
    private Country country;

    //Name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    //division
    public String getDivision() {
        return division;
    }
    public void setDivision(String division) {
        this.division = division;
    }

    //Constructor
    public League(){
    }
    public League(String name, String division) {
        this.name = name;
        this.division = division;
    }


}
