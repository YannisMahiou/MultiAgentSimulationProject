package Model.Game;

import java.util.Random;

public class RandomSingleton {

    private static RandomSingleton instance;
    private Random generator;
    private static final long SEED = 151515L;

    private RandomSingleton(){
        generator = new Random();
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
            instance.generator.setSeed(SEED);
        }
        return instance;
    }

    public int nextInt(int value)
    {
        return generator.nextInt(value);
    }
}
