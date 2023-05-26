package ch.hevs.businessobject;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

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
    @ManyToOne ( mappedBy = "players" )
    private Club playsForClub;
}
