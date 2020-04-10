package Model.Strategy;

import Model.Agent.Agent;
import Model.Terrain.Terrain;

import java.util.List;

public interface IAgentCreationStrategy {

    public abstract List<Agent> createTeam(List<Agent> team, int size, String color,int bonus) throws Exception;
}
