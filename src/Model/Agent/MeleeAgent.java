package Model.Agent;

/**
 * Created by yannis on 14/01/20.
 */
public abstract class MeleeAgent extends Agent{

    public MeleeAgent(int hp, int damageReduction, int speed, int strength, int range) {
        super(hp, damageReduction, speed, strength, range);
    }

    public abstract void move();
    public abstract void attack();

}
