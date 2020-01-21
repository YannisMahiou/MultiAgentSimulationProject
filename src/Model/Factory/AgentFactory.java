package Model.Factory;

import Model.Agent.*;

public class AgentFactory implements IFactory {

    public AgentFactory(){}

    public IAgent create(AgentType type) throws Exception{
        switch (type) {
            case BOWMAN:
                return new BowMan(25, 5, 10, 16);

            case AXEMAN:
                return new AxeMan(25, 5, 10, 16);

            case LANCER:
                return new Lancer(25, 5, 10, 16);

            case KNIGHT:
                return new Knight(25, 5, 10, 16);

            default: throw new Exception("Exception : Not an Model.Agent");
        }
    }

    public IAgent createAgent(AgentType type, int hp, int damageReduction, int speed, int strength) throws Exception{
        switch (type) {
            case BOWMAN:
                return new BowMan(hp, damageReduction, speed, strength);

            case AXEMAN:
                return new AxeMan(hp, damageReduction, speed, strength);

            case LANCER:
                return new Lancer(hp, damageReduction, speed, strength);

            case KNIGHT:
                return new Knight(hp, damageReduction, speed, strength);

            default: throw new Exception("Exception : Not an Model.Agent");
        }
    }
}
