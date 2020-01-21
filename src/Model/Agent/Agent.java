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
        this.hp = hp;
        this.damageReduction = damageReduction;
        this.speed = speed;
        this.strength = strength;
        this.range = range;
    }

    // Methods from the interface IAgent
    public abstract void move();
    public abstract void attack();
    public abstract String toString();

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getDamageReduction() {
        return damageReduction;
    }

    public void setDamageReduction(int damageReduction) {
        this.damageReduction = damageReduction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

}
