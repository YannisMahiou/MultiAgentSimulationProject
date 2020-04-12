package Model.Factory;

import Model.Agent.Agent;
import Model.Agent.AgentType;

/**
 * Interface used to manipulate Creators
 */
public interface IFactory {

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
    Agent createAgent(AgentType type, int hp, int damageReduction, int speed, int strength, String color) throws Exception;

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
    Agent create(AgentType type, String color, int bonus) throws Exception;
}
