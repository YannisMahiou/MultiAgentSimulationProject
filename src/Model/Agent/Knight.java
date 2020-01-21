package Model.Agent;

public class Knight extends MeleeAgent{

    public Knight(int hp, int damageReduction, int speed, int strength) {
        super(hp, damageReduction, speed, strength, 1);
    }

    public void move() {   // pas besoin de redef (dans Model.Agent)

    }


    public void attack() {


    }

    @Override
    public String toString(){
        return "T";
    }

}
