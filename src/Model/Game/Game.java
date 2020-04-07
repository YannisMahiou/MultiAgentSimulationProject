package Model.Game;

import Model.Agent.Agent;
import Model.Agent.AgentType;
import Model.Factory.AgentFactory;
import Model.Terrain.AbstractTerrain;
import Model.Terrain.Terrain;
import java.util.LinkedList;
import java.util.Random;

public class Game {

    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BLUE = "\u001B[34m";

    /**
     * Main function called when launching the MultiAgentSimulation
     * @param args arguments used when calling main function
     */
    public static void main(String[] args) {

        // Try Loop used to catch Exception coming from lower levels
        try {

            // Initialisation of variables
            AbstractTerrain terrain = new Terrain(20, 20);
            LinkedList<Agent> agentsTeam1 = new LinkedList<>();
            LinkedList<Agent> agentsTeam2 = new LinkedList<>();

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
                        agentsTeam1.add(factory.create(AgentType.AXEMAN, ANSI_RED));
                        break;
                    case 1 :
                        agentsTeam1.add(factory.create(AgentType.BOWMAN, ANSI_RED));
                        break;
                    case 2 :
                        agentsTeam1.add(factory.create(AgentType.KNIGHT, ANSI_RED));
                        break;
                    case 3 :
                        agentsTeam1.add(factory.create(AgentType.LANCER, ANSI_RED));
                        break;
                }
            }

            // Agent Creation loop
            for(int i = 0; i < 20; ++i){
                type = generator.nextInt(AgentType.values().length);

                // Switch the type of Agent
                switch(type) {
                    case 0 :
                        agentsTeam2.add(factory.create(AgentType.AXEMAN, ANSI_BLUE));
                        break;
                    case 1 :
                        agentsTeam2.add(factory.create(AgentType.BOWMAN, ANSI_BLUE));
                        break;
                    case 2 :
                        agentsTeam2.add(factory.create(AgentType.KNIGHT, ANSI_BLUE));
                        break;
                    case 3 :
                        agentsTeam2.add(factory.create(AgentType.LANCER, ANSI_BLUE));
                        break;
                }
            }

            // We place the teams on the board
            terrain.placeAgents(agentsTeam1, 0);
            terrain.placeAgents(agentsTeam2, 1);
            terrain.showTerrain();
            System.out.println("\n");

            for(int j = 0; j < 20; ++j)
            {
                agentsTeam1.get(j).move(terrain, agentsTeam2);
                System.out.println(j);
                System.out.println("\n");
            }
            terrain.showTerrain();
        }
        catch(Exception e){
            System.err.println("[Exception]") ;
            e.printStackTrace();
            System.err.println("End of Main method");
        }
    }

}
