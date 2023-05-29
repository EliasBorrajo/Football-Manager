package ch.hevs.businessobject;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Player extends Person
{
    // A T T R I B U T S
    private String position;
    private int    number;
    private boolean isRightFooted;
    private double height;
    private double weight;

    // R E L A T I O N S
    // PLAYER <-1..*------1..1-> CLUB
    @ManyToOne ( cascade = CascadeType.ALL )
    private Club playsForClub;
}
