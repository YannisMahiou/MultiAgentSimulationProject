package Model.Factory;

import Model.Agent.*;

public class AgentFactory implements IFactory {

    public AgentFactory() {
    }

    public Agent create(AgentType type, String color) throws Exception {
        switch (type) {
            case BOWMAN:
                return new BowMan(25, 5, 10, 16, color);

            case AXEMAN:
                return new AxeMan(25, 5, 10, 16, color);

            case LANCER:
                return new Lancer(25, 5, 10, 16, color);

            case KNIGHT:
                return new Knight(25, 5, 10, 16, color);

            default:
                throw new Exception("Exception : Not an Model.Agent");
        }
    }

    public Agent createAgent(AgentType type, int hp, int damageReduction, int speed, int strength, String color) throws Exception {
        switch (type) {
            case BOWMAN:
                return new BowMan(hp, damageReduction, speed, strength, color);

            case AXEMAN:
                return new AxeMan(hp, damageReduction, speed, strength, color);

            case LANCER:
                return new Lancer(hp, damageReduction, speed, strength, color);

            case KNIGHT:
                return new Knight(hp, damageReduction, speed, strength, color);

            default:
                throw new Exception("Exception : Not an Model.Agent");
        }
    }
}
