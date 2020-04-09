package Model.Game;

import java.util.Random;

public class RandomSingleton {

    private static RandomSingleton instance;
    private Random generator;
    private static long seed = 0;

    private RandomSingleton(){
        generator = new Random(0);
    }

    /**
     * Returns the singleton instance
     * @return the random variates
     */
    public static RandomSingleton getInstance()
    {
        if(instance == null)
        {
            instance = new RandomSingleton();
        }
        return instance;
    }

    public int nextInt(int value)
    {
        return generator.nextInt(value);
    }
}