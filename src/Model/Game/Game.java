package Model.Game;

import Model.Agent.AgentType;
import Model.Agent.IAgent;
import Model.Factory.AgentFactory;
import Model.Terrain.AbstractTerrain;
import Model.Terrain.Terrain;
import java.util.LinkedList;
import java.util.Random;

public class Game {

    /**
     * Main function called when launching the MultiAgentSimulation
     * @param args arguments used when calling main function
     */
    public static void main(String[] args) {

        // Try Loop used to catch Exception coming from lower levels
        try {
            // Initialisation of variables
            AbstractTerrain terrain = new Terrain(20, 20);
            LinkedList<IAgent> agentsTeam1 = new LinkedList<>();
            LinkedList<IAgent> agentsTeam2 = new LinkedList<>();

            // Random variates
            Random generator = new Random();
            int type = 0;

            // Creation of the Agent Factory
            AgentFactory factory = new AgentFactory();

            // Agent Creation loop
            for(int i = 0; i < 20; ++i){
                type = generator.nextInt(AgentType.values().length);

                // Switch the type of Agent
                switch(type) {
                    case 0 :
                        agentsTeam1.add(factory.create(AgentType.AXEMAN));
                        break;
                    case 1 :
                        agentsTeam1.add(factory.create(AgentType.BOWMAN));
                        break;
                    case 2 :
                        agentsTeam1.add(factory.create(AgentType.KNIGHT));
                        break;
                    case 3 :
                        agentsTeam1.add(factory.create(AgentType.LANCER));
                        break;
                    default :
                        agentsTeam1.add(factory.create(AgentType.AXEMAN));
                        break;
                }
            }
            terrain.placeAgents(agentsTeam1, 0);
            terrain.showTerrain();
        }
        catch(Exception e){
            System.err.println("[Exception]") ;
            e.printStackTrace();
            System.err.println("End of Main method");
        }
    }

}
