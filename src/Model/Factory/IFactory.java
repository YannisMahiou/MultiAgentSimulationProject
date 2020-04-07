package Model.Factory;

import Model.Agent.Agent;
import Model.Agent.AgentType;

public interface IFactory {
    public Agent createAgent(AgentType type, int hp, int damageReduction, int speed, int strength, String color) throws Exception;
    public Agent create(AgentType type, String color) throws Exception;
}
