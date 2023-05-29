package ch.hevs.utils.consoleTesting;

/**
 * consolePresentation - Console presentation of the project
 * This is to test the 3 layers of the project
 * - Peristence
 * - Services
 * - Presentation
 *
 * This is a console presentation of the project, so we can test the services and the persistence layers before
 * implementing the presentation layer (GUI) with web pages.
 */
public class consolePresentation
{
    public static void main(String[] args)
    {
        System.out.println("Console presentation of the project");
        System.out.println("Testing persistence...");
        if (testPersistence())
            System.out.println("Persistence tested successfully");
        else
        {
            System.out.println("Persistence testing failed");
            System.exit(1);
        }

        System.out.println("Testing services...");
        if (testServices())
            System.out.println("Services tested successfully");
        else
        {
            System.out.println("Services testing failed");
            System.exit(1); // Exit with error code 1 (error)
        }
    }

    private static boolean testPersistence()
    {
        // Seed DB with data
        return false;
    }

    private static boolean testServices()
    {
        // Test services
        return false;
    }

}
