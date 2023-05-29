package ch.hevs.utils.seed;

/**
 * SeedDB - Seed the database with some data for testing purposes
 */
public class SeedDB
{
    public static void main(String[] args)
    {
        System.out.println("Seeding database...");
        if (seedDB())
            System.out.println("Database seeded successfully");
        else
            System.out.println("Database seeding failed");
    }

    private static boolean seedDB()
    {
        if (    seedClub() && seedCountry() &&
                seedFan() && seedLeague() && seedPlayers() )
            return true;
        else
            return false;

    }

    // TODO : SEED DATABASE : POPULATE TABLES WITH DATA

    private static boolean seedPlayers()
    {
        return false;
    }

    private static boolean seedCountry()
    {
        return false;
    }

    private static boolean seedFan()
    {
        return false;
    }

    private static boolean seedClub()
    {
        return false;
    }

    private static boolean seedLeague()
    {
        return false;
    }
}
