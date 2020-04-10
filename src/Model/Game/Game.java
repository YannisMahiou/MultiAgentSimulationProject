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
            LinkedList<Agent> blueTeam = new LinkedList<>();
            LinkedList<Agent> redTeam = new LinkedList<>();

            int blueTeamSize = TEAM_SIZE, redTeamSize = TEAM_SIZE;
            Agent currentBlueTeamAgent, currentRedTeamAgent;

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
                        blueTeam.add(factory.create(AgentType.AXEMAN, ANSI_RED));
                        break;
                    case 1:
                        blueTeam.add(factory.create(AgentType.BOWMAN, ANSI_RED));
                        break;
                    case 2:
                        blueTeam.add(factory.create(AgentType.KNIGHT, ANSI_RED));
                        break;
                    case 3:
                        blueTeam.add(factory.create(AgentType.LANCER, ANSI_RED));
                        break;
                }
            }

            // Agent Creation loop
            for (int i = 0; i < TEAM_SIZE; ++i) {
                type = RandomSingleton.getInstance().nextInt(AgentType.values().length);

                // Switch the type of Agent
                switch (type) {
                    case 0:
                        redTeam.add(factory.create(AgentType.AXEMAN, ANSI_BLUE));
                        break;
                    case 1:
                        redTeam.add(factory.create(AgentType.BOWMAN, ANSI_BLUE));
                        break;
                    case 2:
                        redTeam.add(factory.create(AgentType.KNIGHT, ANSI_BLUE));
                        break;
                    case 3:
                        redTeam.add(factory.create(AgentType.LANCER, ANSI_BLUE));
                        break;
                }
            }

            // We place the teams on the board
            terrain.placeAgents(blueTeam);
            terrain.placeAgents(redTeam);
            terrain.showTerrain();
            System.out.println("\n");

            Iterator<Agent> blueTeamIterator = blueTeam.iterator();
            Iterator<Agent> redTeamIterator = redTeam.iterator();

            for(int i = 0; i < 100 && (blueTeamSize > 0 && redTeamSize > 0); ++i)
            {
                System.out.println(" \n Iteration" + i);
                while(blueTeamIterator.hasNext() && redTeamIterator.hasNext())
                {
                    currentBlueTeamAgent = blueTeamIterator.next();
                    if(redTeamSize > 0 && currentBlueTeamAgent.isAlive()){
                        switch (currentBlueTeamAgent.actionTurn(terrain, redTeam, blueTeam)){
                            case LOST:
                                blueTeamSize-=1;
                                break;
                            case WIN:
                                redTeamSize-=1;
                                break;
                        }
                    }

                    currentRedTeamAgent = redTeamIterator.next();
                    if(blueTeamSize > 0 && currentRedTeamAgent.isAlive())
                    {
                        switch (currentRedTeamAgent.actionTurn(terrain, blueTeam, redTeam)){
                            case LOST:
                                redTeamSize-=1;
                                break;
                            case WIN:
                                blueTeamSize-=1;
                                break;
                        }
                    }
                    //System.out.println("\n");
                }
                terrain.showTerrain();
                System.out.println();
                blueTeamIterator = blueTeam.iterator();
                redTeamIterator = redTeam.iterator();

            }

            if(blueTeamSize == 0){
                System.out.println("Victoire de l'équipe Bleu");
            }
            else{
                System.out.println("Victoire de l'équipe Rouge");
            }
        } catch (Exception e) {
            System.err.println("[Exception]");
            e.printStackTrace();
            System.err.println("End of Main method");
        }
    }

}
