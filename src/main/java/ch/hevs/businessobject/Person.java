package ch.hevs.businessobject;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

public abstract class Person
{
    // A T T R I B U T S
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Long   id;
    private String firstname;
    private String lastname;
    private Date   birthdate;


}
