package Model.Factory;

import Model.Agent.*;

/**
 * Factory focused on creating Agents
 */
public class AgentFactory implements IFactory {

    public AgentFactory() {
    }

    /**
     * Creates an Agent with default stats values
     *
     * @param type type of the agent
     * @param color blue or red
     * @param bonus bonus given by the user
     *
     * @return the created Agent
     * @throws Exception if any creation problem occurs
     */
    public Agent create(AgentType type, String color, int bonus) throws Exception {
        switch (type) {
            case BOWMAN:
                return new BowMan(25 + bonus, 5 + bonus, 10 + bonus, 16 + bonus, color);

            case AXEMAN:
                return new AxeMan(25 + bonus, 5 + bonus, 10 + bonus, 16 + bonus, color);

            case LANCER:
                return new Lancer(25 + bonus, 5 + bonus, 10 + bonus, 16 + bonus, color);

            case KNIGHT:
                return new Knight(25 + bonus, 5 + bonus, 10 + bonus, 16 + bonus, color);

            default:
                throw new Exception("Exception : Not an Agent");
        }
    }

    /**
     * Creates an Agent with stats
     *
     * @param type type of the agent
     * @param hp health points
     * @param damageReduction armor
     * @param speed speed
     * @param strength resistance
     * @param color blue or red
     *
     * @return the created Agent
     * @throws Exception if any creation problem occurs
     */
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
