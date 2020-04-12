package Model.Game;

import java.util.Random;

/**
 * Singleton design pattern used to get an unique instance of the RNG
 */
public class RandomSingleton {

    private static RandomSingleton instance;
    private Random generator;
    private static final long SEED = 78000L;

    /**
     * Constructor of the generator
     */
    private RandomSingleton(){
        generator = new Random();
    }

    /**
     * Returns the singleton instance
     *
     * @return the random variates
     */
    public static RandomSingleton getInstance()
    {
        if(instance == null)
        {
            instance = new RandomSingleton();
            if(SEED > 0){
                instance.generator.setSeed(SEED);
            }
        }
        return instance;
    }

    /**
     * Gives the random value generated (int value)
     *
     * @param value bound of the generator
     * @return the value generated
     */
    public int nextInt(int value)
    {
        return generator.nextInt(value);
    }
}
