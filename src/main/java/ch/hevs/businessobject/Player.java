package ch.hevs.businessobject;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Business object Player, wich inherit from Person
 */
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
    @ManyToOne
    private Club playsForClub;

    // C O N S T R U C T O R
    /**
     * Default constructor, required by JPA
     */
    public Player() {
        super();
    }
    /**
     * Complete constructor to create a new player with all attributes
     * @param firstname : firstname of the player (ex: John)
     * @param lastname : lastname of the player (ex: Doe)
     * @param birthdate : birthdate of the player (ex: 01.01.1990)
     * @param country : country of the player (ex: Switzerland)
     * @param positionPlayer : position of the player (ex: Goalkeeper)
     * @param number : number of the player (ex: 1)
     * @param isRightFooted : is the player right footed (ex: true)
     * @param height : height of the player (ex: 1.80)
     * @param weight : weight of the player (ex: 80.2)
     * @param playsForClub : club the player plays for (ex: FC Sion)
     */
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
        this.playsForClub = playsForClub;
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
