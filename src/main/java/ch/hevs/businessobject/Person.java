package ch.hevs.businessobject;

import javax.persistence.*;
import java.util.Date;

@Entity     // TODO : CHANGER TYPE HERITAGE
@Inheritance ( strategy = InheritanceType.TABLE_PER_CLASS )
public abstract class Person
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

}
