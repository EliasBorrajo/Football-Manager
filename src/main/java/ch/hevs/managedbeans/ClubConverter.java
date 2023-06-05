package ch.hevs.managedbeans;

import ch.hevs.businessobject.Club;
import ch.hevs.services.Football;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Convertisseur JSF pour l'objet Club (pour les menus déroulants)
 * Permet de convertir un objet Club en une chaîne et vice-versa
 * afin que l'interface graphique puisse afficher une liste déroulante de Clubs
 *
 * https://stackoverflow.com/questions/4734580/conversion-error-setting-value-for-null-converter-why-do-i-need-a-converter
 */
//@FacesConverter("clubConverter")
@FacesConverter(forClass = Club.class)
public class ClubConverter  implements Converter
{

    private Football football;

    @PostConstruct
    private void initFootball() throws NamingException {
        if (football == null) {
            InitialContext ctx = new InitialContext();
            football = (Football) ctx.lookup("java:global/TP12-WEB-EJB-PC-EPC-E-0.0.1-SNAPSHOT/FootballBean!ch.hevs.services.Football");
        }
    }

    /**
     * Convertit une chaîne en objet Club
     *
     * You need to implement getAsObject() in such way that EXACTLY that String representation
     * as returned by getAsString() can be converted back to EXACTLY the same Java object specified as modelValue in getAsString().
     *
     * @param context   {@link FacesContext} for the request being processed
     * @param component {@link UIComponent} with which this model object value is associated
     * @param value     String value to be converted (may be <code>null</code>)
     * @return          {@link Club} object (may be <code>null</code>)
     */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value)
    {
        if (value == null || value.isEmpty()) {
            return null;
        }

        try {
            // Remplacez cette ligne par votre logique de recherche du Club correspondant à partir de la valeur de la chaîne
            // Par exemple, recherchez le Club dans la base de données ou utilisez une autre méthode appropriée
            // pour obtenir l'objet Club à partir de la valeur de la chaîne
            Long clubId = Long.valueOf(value);
            Club club = football.getClubById(clubId);
            return club;
        } catch (NumberFormatException e) {
            throw new ConverterException("La valeur fournie n'est pas un ID de Club valide", e);
        }
    }


    /**
     * Convertit un objet Club en chaîne (ID du Club) pour l'affichage dans une liste déroulante
     *
     * You need to implement getAsString() method in such way that the desired Java object
     * is been represented in an UNIQUE String representation which can be used as HTTP request parameter.
     * Normally, the technical ID (the database primary key) is used here.
     *
     * @param context   {@link FacesContext} for the request being processed
     * @param component {@link UIComponent} with which this model object
     *                  value is associated
     * @param value     Model object value to be converted
     *                  (may be <code>null</code>)
     * @return          a zero-length String if value is <code>null</code>, otherwise the result of the conversion
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";  // Never return null here!
        }

        if (value instanceof Club) {
            // Remplacez cette ligne par votre logique de conversion de l'objet Club en une chaîne
            // Par exemple, renvoyez l'ID unique du Club ou une autre représentation appropriée en chaîne
            Club club = (Club) value;
            return String.valueOf(club.getId());
        } else {
            throw new ConverterException("La valeur fournie n'est pas un objet Club valide");
        }
    }
}

