package ch.hevs.businessobject;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Player extends Person {
    // A T T R I B U T S
    private String  positionPlayer;
    private int     number;
    private boolean isRightFooted;
    private double  height;
    private double  weight;

    // R E L A T I O N S
    // PLAYER <-1..*------1..1-> CLUB
    @ManyToOne /*(cascade = CascadeType.ALL)*/
    private Club playsForClub;

    // C O N S T R U C T O R
    public Player() {
        super();
    }
    public Player(String firstname, String lastname,
                  String birthdate, Country country,
                  String positionPlayer, int number,
                  boolean isRightFooted, double height,
                  double weight , Club playsForClub)
    {
        super(firstname, lastname, birthdate, country);
        this.positionPlayer = positionPlayer;
        this.number = number;
        this.isRightFooted = isRightFooted;
        this.height = height;
        this.weight = weight;
        this.playsForClub = playsForClub; // TODO METTRE ? JE PENSE PAS CAR MIS AVEC METHODES DEPUIS GRAPHIQUE
    }



    // G E T T E R S   &   S E T T E R S
    // position
    public String getPositionPlayer() {return positionPlayer;}
    public void setPositionPlayer(String positionPlayer) {this.positionPlayer = positionPlayer;}
    // number
    public int getNumber() {return number;}
    public void setNumber(int number) {this.number = number;}
    // isRightFooted
    public boolean isRightFooted() {return isRightFooted;}
    public void setRightFooted(boolean rightFooted) {isRightFooted = rightFooted;}
    // height
    public double getHeight() {return height;}
    public void setHeight(double height) {this.height = height;}
    // weight
    public double getWeight() {return weight;}
    public void setWeight(double weight) {this.weight = weight;}
    // playsForClub
    public Club getPlaysForClub() {return playsForClub;}
    public void setPlaysForClub(Club playsForClub) {this.playsForClub = playsForClub;}


    // C O N V E N I E N C E   M E T H O D S
    @Override
    public String toString() {
        return "Player{" +
                "position='" + positionPlayer + '\'' +
                ", number=" + number +
                ", isRightFooted=" + isRightFooted +
                ", height=" + height +
                ", weight=" + weight +
                ", playsForClub=" + playsForClub +
                '}';
    }



}
