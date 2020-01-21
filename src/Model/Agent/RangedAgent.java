package Model.Agent;

public abstract class RangedAgent extends Agent{

    public RangedAgent(int hp, int damageReduction, int speed, int strength, int range) {
        super(hp, damageReduction, speed, strength, range);
    }

    public abstract void move();
    public abstract void attack();

}
