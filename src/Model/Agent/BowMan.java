package Model.Agent;

public class BowMan extends RangedAgent{

    public BowMan(int hp, int damageReduction, int speed, int strength) {
        super(hp, damageReduction, speed, strength, 2);
    }

    public void move() {   // pas besoin de redef (dans Model.Agent)

    }

    public void attack() {


    }

    @Override
    public String toString(){
        return "D";
    }

}
