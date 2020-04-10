package Model.Game;

import Model.Agent.Agent;
import Model.Agent.AgentType;
import Model.Factory.AgentFactory;
import Model.Terrain.AbstractTerrain;
import Model.Terrain.Terrain;
import sun.nio.cs.Surrogate;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class Game {

    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final int TEAM_SIZE = 20;

    long seed = 0;


    /**
     * Main function called when launching the MultiAgentSimulation
     *
     * @param args arguments used when calling main function
     */
    public static void main(String[] args) {

        // Try Loop used to catch Exception coming from lower levels
        try {

            // Initialisation of variables
            AbstractTerrain terrain = new Terrain(20, 20);
            LinkedList<Agent> agentsTeam1 = new LinkedList<>();
            LinkedList<Agent> agentsTeam2 = new LinkedList<>();

            int teamSize1 = TEAM_SIZE, teamSize2 = TEAM_SIZE;
            Agent currentAgentTeam1, currentAgentTeam2;

            // Random variates
            int type = 0;

            // Creation of the Agent Factory
            AgentFactory factory = new AgentFactory();

            // Agent Creation loop
            for (int i = 0; i < TEAM_SIZE; ++i) {
            type = RandomSingleton.getInstance().nextInt(AgentType.values().length);

                // Switch the type of Agent
                switch (type) {
                    case 0:
                        agentsTeam1.add(factory.create(AgentType.AXEMAN, ANSI_RED));
                        break;
                    case 1:
                        agentsTeam1.add(factory.create(AgentType.BOWMAN, ANSI_RED));
                        break;
                    case 2:
                        agentsTeam1.add(factory.create(AgentType.KNIGHT, ANSI_RED));
                        break;
                    case 3:
                        agentsTeam1.add(factory.create(AgentType.LANCER, ANSI_RED));
                        break;
                }
            }

            // Agent Creation loop
            for (int i = 0; i < TEAM_SIZE; ++i) {
                type = RandomSingleton.getInstance().nextInt(AgentType.values().length);

                // Switch the type of Agent
                switch (type) {
                    case 0:
                        agentsTeam2.add(factory.create(AgentType.AXEMAN, ANSI_BLUE));
                        break;
                    case 1:
                        agentsTeam2.add(factory.create(AgentType.BOWMAN, ANSI_BLUE));
                        break;
                    case 2:
                        agentsTeam2.add(factory.create(AgentType.KNIGHT, ANSI_BLUE));
                        break;
                    case 3:
                        agentsTeam2.add(factory.create(AgentType.LANCER, ANSI_BLUE));
                        break;
                }
            }

            // We place the teams on the board
            terrain.placeAgents(agentsTeam1);
            terrain.placeAgents(agentsTeam2);
            terrain.showTerrain();
            System.out.println("\n");

            Iterator<Agent> itTeam1 = agentsTeam1.iterator();
            Iterator<Agent> itTeam2 = agentsTeam2.iterator();

            for(int i = 0; i < 100 && (teamSize1 > 0 || teamSize2 > 0); ++i)
            {
                System.out.println(" \n Iteration" + i);
                while(itTeam1.hasNext() && itTeam2.hasNext())
                {
                    currentAgentTeam1 = itTeam1.next();
                    if(teamSize2 > 0 && currentAgentTeam1.isAlive()){
                        switch (currentAgentTeam1.actionTurn(terrain, agentsTeam2, agentsTeam1)){
                            case LOST:
                                teamSize1-=1;
                                break;
                            case WIN:
                                teamSize2-=1;
                                break;
                        }
                    }

                    currentAgentTeam2 = itTeam2.next();
                    if(teamSize1 > 0 && currentAgentTeam2.isAlive())
                    {
                        switch (currentAgentTeam2.actionTurn(terrain, agentsTeam1, agentsTeam2)){
                            case LOST:
                                teamSize2-=1;
                                break;
                            case WIN:
                                teamSize1-=1;
                                break;
                        }
                    }
                    else {
                        System.out.println();
                    }
                    //System.out.println("\n");
                }
                terrain.showTerrain();
                System.out.println();
                itTeam1 = agentsTeam1.iterator();
                itTeam2 = agentsTeam2.iterator();

            }

            if(teamSize1 == 0){
                System.out.println("Victoire de l'équipe 1");
            }
            else{
                System.out.println("Victoire de l'équipe 2");
            }
        } catch (Exception e) {
            System.err.println("[Exception]");
            e.printStackTrace();
            System.err.println("End of Main method");
        }
    }

}
