package ch.hevs.services;

import ch.hevs.businessobject.*;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.List;

@Local
public interface Football{
    @TransactionAttribute(value = TransactionAttributeType.REQUIRED) // TODO : Changer
    List<Player> getPlayers();
    List<Club> getClubs();
    List<League> getLeagues();

    public boolean seedDB();

}
