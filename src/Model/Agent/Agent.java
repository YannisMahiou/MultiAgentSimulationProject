package Model.Agent;

public abstract class Agent implements IAgent{

    private int hp;
    private int damageReduction;
    private int speed;
    private int strength;
    private int range;
    private int posX;
    private int posY;

    public Agent(int hp, int damageReduction, int speed, int strength, int range) {
        setHp(hp);
        setDamageReduction(damageReduction);
        setSpeed(speed);
        setStrength(strength);
        setRange(range);
    }

    // Methods from the interface IAgent
    public abstract void move();
    public abstract void attack();
    public abstract String toString();

    private int getHp() {
        return hp;
    }

    private void setHp(int hp) {
        this.hp = hp;
    }

    public final int getDamageReduction() {
        return damageReduction;
    }
    public final void setDamageReduction(int damageReduction) {
        this.damageReduction = damageReduction;
    }

    public final int getSpeed() {
        return speed;
    }

    public final void setSpeed(int speed) {
        this.speed = speed;
    }

    public final  int getStrength() {
        return strength;
    }

    public final  void setStrength(int strength) {
        this.strength = strength;
    }

    public final int getRange() {
        return range;
    }

    public final void setRange(int range) {
        this.range = range;
    }

}
