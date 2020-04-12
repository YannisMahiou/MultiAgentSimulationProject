package Model.Game;

import Model.Agent.Agent;
import Model.Serialization.DataManager;
import Model.Serialization.FileDataManager;
import Model.Strategy.ChaosAgentCreationStrategy;
import Model.Strategy.IAgentCreationStrategy;
import Model.Strategy.RandomCompAgentCreationStrategy;
import Model.Strategy.StandardAgentCreationStrategy;
import Model.Terrain.AbstractTerrain;
import Model.Terrain.Terrain;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Game loop used to run the SMA
 */
public class Game {

    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final int TEAM_SIZE = 20;
    private static final int NB_ITE = 100;
    private static final int NB_EXPERIENCES = 100;
    private static final int NO_BONUS = 0;
    private static final boolean DISPLAY = false;

    /**
     * Main function called when launching the MultiAgentSimulation
     *
     * @param args arguments used when calling main function
     */
    public static void main(String[] args) {

        Statistics statistics = new Statistics(NB_ITE, NB_EXPERIENCES);
        int choice = -1, bonus = 0;
        LinkedList<Agent> blueTeam = new LinkedList<>();
        LinkedList<Agent> redTeam = new LinkedList<>();
        AbstractTerrain terrain = null;

        // Serialization
        DataManager dataManager = new FileDataManager();

        choice = startup();
        bonus = fillBonus(choice);

        for (int nbExperiences = 0; nbExperiences < NB_EXPERIENCES; ++nbExperiences) {

            int nbVictoryRed = 0;

            // Agent Creation
            try {
                createTeams(blueTeam, redTeam, choice, bonus);

                terrain = new Terrain(20, 20);

                terrain.placeAgents(blueTeam);
                terrain.placeAgents(redTeam);

                // Serialize the teams
                dataManager.saveAgents("redTeam.ser", redTeam);
                dataManager.saveAgents("blueTeam.ser", blueTeam);

            } catch (Exception e) {
                e.printStackTrace();
            }

            for (int turn = 0; turn < NB_ITE; ++turn) {

                // Try Loop used to catch Exception coming from lower levels
                try {

                    // Initialisation of variables
                    int blueTeamSize = TEAM_SIZE, redTeamSize = TEAM_SIZE;
                    Agent currentBlueTeamAgent, currentRedTeamAgent;

                    //terrain.showTerrain();

                    Iterator<Agent> blueTeamIterator = blueTeam.iterator();
                    Iterator<Agent> redTeamIterator = redTeam.iterator();

                    while (blueTeamSize > 0 && redTeamSize > 0) {
                        // System.out.println(" \n Tour " + i);
                        while (blueTeamIterator.hasNext() && redTeamIterator.hasNext()) {

                            currentBlueTeamAgent = blueTeamIterator.next();
                            if (redTeamSize > 0 && currentBlueTeamAgent.isAlive()) {
                                switch (statistics.countFightStatuses(currentBlueTeamAgent.actionTurn(terrain, redTeam, blueTeam))) {
                                    case LOST:
                                        blueTeamSize -= 1;
                                        break;
                                    case WIN:
                                        redTeamSize -= 1;
                                        break;
                                }
                            }

                            currentRedTeamAgent = redTeamIterator.next();
                            if (blueTeamSize > 0 && currentRedTeamAgent.isAlive()) {
                                switch (statistics.countFightStatuses(currentRedTeamAgent.actionTurn(terrain, blueTeam, redTeam))) {
                                    case LOST:
                                        redTeamSize -= 1;
                                        break;
                                    case WIN:
                                        blueTeamSize -= 1;
                                        break;
                                }
                            }
                        }
                        //terrain.showTerrain();
                        //System.out.println();
                        statistics.computeTerrainStats(terrain);
                        blueTeamIterator = blueTeam.iterator();
                        redTeamIterator = redTeam.iterator();

                    }

                    if (blueTeamSize == 0) {
                        //System.out.println("Red team wins");
                        nbVictoryRed++;
                    } else {
                        if (redTeamSize == 0) {
                            //System.out.println("Blue team wins");
                        } else {
                            throw new Exception();
                        }
                    }

                    // Deserialize the teams
                    redTeam = dataManager.loadAgents("redTeam.ser");
                    blueTeam = dataManager.loadAgents("blueTeam.ser");

                    terrain = new Terrain(20, 20);

                    terrain.replaceAgents(blueTeam);
                    terrain.replaceAgents(redTeam);

                } catch (Exception e) {
                    System.err.println("[Exception]");
                    e.printStackTrace();
                    System.err.println("End of Main method");
                }
            }

            statistics.addToMeanArray(nbVictoryRed, nbExperiences);
            blueTeam = new LinkedList<>();
            redTeam = new LinkedList<>();
        }
        statistics.computeMean();
        statistics.displayTerrainStats();
        statistics.displayFightStatuses();
    }

    /**
     * Function used to create the two teams of agent for the simulations
     *
     * @param blueTeam the Blue team (that engages first and gets the bonus if there's one)
     * @param redTeam the Red team
     * @param choice the user simulation mode choice
     * @param bonus value of the bonus
     * @throws Exception if any errors in creation occurs
     */
    private static void createTeams(List<Agent> blueTeam, List<Agent> redTeam, int choice, int bonus) throws Exception {
        IAgentCreationStrategy agentCreationStrategy;

        switch (choice) {
            case 1:
                agentCreationStrategy = new ChaosAgentCreationStrategy();

                agentCreationStrategy.createTeam(blueTeam, TEAM_SIZE, ANSI_BLUE, bonus);
                agentCreationStrategy.createTeam(redTeam, TEAM_SIZE, ANSI_RED, bonus);
                break;
            case 2:
                agentCreationStrategy = new StandardAgentCreationStrategy();

                agentCreationStrategy.createTeam(blueTeam, TEAM_SIZE, ANSI_BLUE, bonus);
                agentCreationStrategy.createTeam(redTeam, TEAM_SIZE, ANSI_RED, NO_BONUS);
                break;
            case 3:
                agentCreationStrategy = new RandomCompAgentCreationStrategy();

                agentCreationStrategy.createTeam(blueTeam, TEAM_SIZE, ANSI_BLUE, NO_BONUS);
                agentCreationStrategy.createTeam(redTeam, TEAM_SIZE, ANSI_RED, NO_BONUS);
                break;
            default:
                agentCreationStrategy = new StandardAgentCreationStrategy();

                agentCreationStrategy.createTeam(blueTeam, TEAM_SIZE, ANSI_BLUE, NO_BONUS);
                agentCreationStrategy.createTeam(redTeam, TEAM_SIZE, ANSI_RED, NO_BONUS);
                break;
        }
    }

    /**
     * Startup of the app, shows the different simulations available and ask the user to choose the one he wants
     *
     * @return int : The user choice
     */
    private static int startup() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the colosseum simulation ! Please choose the wanted type of simulation in the list below :");
        System.out.println("Simulation type 1 : Chaos mode, randomized agent stats (penalty or bonus up to 5 points for each stat) and randomized team composition");
        System.out.println("Simulation type 2 : Advantage mode, you can give a penalty or a bonus to the blue team's agent statistics");
        System.out.println("Simulation type 3 : Randomized team composition mode");
        System.out.println("Simulation type Standard (press any other key than 1, 2 or 3) : Every units have the same stats");
        System.out.print("Your choice : ");

        return scanner.nextInt();
    }

    /**
     * Ask the user to fill the bonus or penalty wanted for the blue team if he chose the the Advantage mode)
     *
     * @param choice int : The user choice
     * @return int : The bonus or penalty
     */
    private static int fillBonus(int choice) {

        if (choice == 2) {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Fill the value of the penalty / bonus : ");

            return scanner.nextInt();
        }
        return NO_BONUS;
    }
}
