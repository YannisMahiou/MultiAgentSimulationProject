package Model.Factory;

import Model.Agent.Agent;
import Model.Agent.AgentType;

public interface IFactory {
    Agent createAgent(AgentType type, int hp, int damageReduction, int speed, int strength, String color) throws Exception;

    Agent create(AgentType type, String color, int bonus) throws Exception;
}
