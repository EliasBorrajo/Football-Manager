package ch.hevs.businessobject;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Player extends Person
{
    // A T T R I B U T S
    private String  position;
    private int     number;
    private boolean isRightFooted;
    private double  height;
    private double  weight;

    // R E L A T I O N S
    // PLAYER <-1..*------1..1-> CLUB
    @ManyToOne ( mappedBy = "players" )
    private Club playsForClub;

    // C O N S T R U C T O R
    public Player() {
    }
    public Player(String position, int number, boolean isRightFooted, double height, double weight /*, Club playsForClub*/)
    {
        this.position = position;
        this.number = number;
        this.isRightFooted = isRightFooted;
        this.height = height;
        this.weight = weight;
        //this.playsForClub = playsForClub; // TODO METTRE ?
    }
}
