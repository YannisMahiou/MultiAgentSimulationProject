package Model.Strategy;

import Model.Agent.Agent;
import Model.Terrain.Terrain;

import java.util.List;

/**
 * Interface used to handle user methods of agents creation
 */
public interface IAgentCreationStrategy {

    /**
     * Creates a Team of agent
     * @param team the team to fill
     * @param size the size of the team
     * @param color the color of the team
     * @param bonus the bonus given to the team
     * @return the created team
     * @throws Exception if any problems occurs in the creation
     */
    List<Agent> createTeam(List<Agent> team, int size, String color,int bonus) throws Exception;
}
