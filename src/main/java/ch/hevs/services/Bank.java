package ch.hevs.services;

import java.util.List;

import javax.ejb.Local;

import ch.hevs.businessobject.toRemove.Client;
import ch.hevs.businessobject.toRemove.Account;

@Local
public interface Bank {

	Account getAccount(String accountDescription, String lastnameOwner);
	
	public List<Account> getAccountListFromClientLastname(String lastname);

	void transfer(Account compteSrc, Account compteDest, int montant) throws Exception;

	List<Client> getClients();

	Client getClient(long clientid);
}
