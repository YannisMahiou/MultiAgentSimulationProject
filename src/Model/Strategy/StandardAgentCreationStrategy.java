package Model.Strategy;

import Model.Agent.Agent;
import Model.Agent.AgentType;
import Model.Factory.AgentFactory;
import Model.Game.RandomSingleton;

import java.util.LinkedList;
import java.util.List;

public class StandardAgentCreationStrategy implements IAgentCreationStrategy {

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
