package ch.hevs.businessobject;

// TODO : DELETE ?
public enum Countrys
{
    FRANCE,
    SWITZERLAND,
    ITALY,
    GERMANY,
    SPAIN,
    ENGLAND,
    PORTUGAL,
    BELGIUM,
    NETHERLANDS,
    RUSSIA,
    UKRAINE,
    TURKEY,
    GREECE,
    SCOTLAND;

    public String getName()
    {
        switch (this)
        {
            case FRANCE:
                return "France";
            case SWITZERLAND:
                return "Switzerland";
            case ITALY:
                return "Italy";
            case GERMANY:
                return "Germany";
            case SPAIN:
                return "Spain";
            case ENGLAND:
                return "England";
            case PORTUGAL:
                return "Portugal";
            case BELGIUM:
                return "Belgium";
            case NETHERLANDS:
                return "Netherlands";
            case RUSSIA:
                return "Russia";
            case UKRAINE:
                return "Ukraine";
            case TURKEY:
                return "Turkey";
            case GREECE:
                return "Greece";
            case SCOTLAND:
                return "Scotland";
            default:
                return "Unknown country";
        }
    }
}
