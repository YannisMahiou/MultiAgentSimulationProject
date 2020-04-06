package Model.Factory;

import Model.Agent.AgentType;
import Model.Agent.IAgent;

public interface IFactory {
    public IAgent createAgent(AgentType type, int hp, int damageReduction, int speed, int strength, String color) throws Exception;
    public IAgent create(AgentType type, String color) throws Exception;
}
