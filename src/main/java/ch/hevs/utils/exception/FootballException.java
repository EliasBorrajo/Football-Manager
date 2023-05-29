package ch.hevs.utils.exception;

/**
 * Exception class for the Football application
 * Create here all the exceptions you need for the application, and use them in the code when needed
 * Example : if a player is not found, throw a PlayerNotFoundException
 */
public class FootballException extends RuntimeException
{
    /**
     * Default constructor
     */
    public FootballException()
    {
        super();
    }

    /**
     * Constructor with message parameter to display when the exception is thrown
     * @param arg0 : message to display
     */
    public FootballException(String arg0)
    {
        super(arg0);
    }

    /**
     * Constructor with message and throwable parameters to display when the exception is thrown
     * @param arg0 : message to display
     * @param arg1 : throwable to display
     */
    public FootballException(String arg0, Throwable arg1)
    {
        super(arg0, arg1);
    }

    /**
     * Constructor with throwable parameter to display when the exception is thrown
     * @param arg0  : throwable to display
     */
    public FootballException(Throwable arg0)
    {
        super(arg0);
    }
}
