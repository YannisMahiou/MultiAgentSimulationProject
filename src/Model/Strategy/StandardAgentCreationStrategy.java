package Model.Strategy;

import Model.Agent.Agent;
import Model.Agent.AgentType;
import Model.Factory.AgentFactory;
import Model.Game.RandomSingleton;

import java.util.LinkedList;
import java.util.List;

/**
 * Creates team in a standard way
 */
public class StandardAgentCreationStrategy implements IAgentCreationStrategy {

    /**
     * Creates a Team of agent
     * @param team the team to fill
     * @param size the size of the team
     * @param color the color of the team
     * @param bonus the bonus given to the team
     * @return the created team
     * @throws Exception if any problems occurs in the creation
     */
    @Override
    public List<Agent> createTeam(List<Agent> team, int size, String color, int bonus) throws Exception {
        AgentFactory factory = new AgentFactory();
        int type;
        // Agent Creation loop
        for (int i = 0; i < size; ++i) {
            type =  i % AgentType.values().length;

            // Switch the type of Agent
            switch (type) {
                case 0:
                    team.add(factory.create(AgentType.AXEMAN, color, bonus));
                    break;
                case 1:
                    team.add(factory.create(AgentType.BOWMAN, color, bonus));
                    break;
                case 2:
                    team.add(factory.create(AgentType.KNIGHT, color, bonus));
                    break;
                case 3:
                    team.add(factory.create(AgentType.LANCER, color, bonus));
                    break;
            }
        }
        return team;
    }
}
