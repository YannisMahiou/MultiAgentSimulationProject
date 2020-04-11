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

public class Game {

    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final int TEAM_SIZE = 20;
    private static final int NB_ITE = 30;
    private static final int NB_EXPERIENCES = 30;
    private static final int NO_BONUS = 0;
    private static final boolean DISPLAY = false;

    /**
     * Main function called when launching the MultiAgentSimulation
     *
     * @param args arguments used when calling main function
     */
    public static void main(String[] args) {

        int nbVictoryRed = 0;
        int nbVictoryBlue = 0;
        int nbTurns = 0;
        float meanVictory;
        float cumulate = 0;
        float[] experiences = new float[NB_EXPERIENCES];
        int choice = -1, bonus = 0;
        LinkedList<Agent> blueTeam = new LinkedList<>();
        LinkedList<Agent> redTeam = new LinkedList<>();


        // Serialization
        DataManager dataManager = new FileDataManager();

        choice = startup();
        bonus = fillBonus(choice);

        for (int nbExperiences = 0; nbExperiences < NB_EXPERIENCES; ++nbExperiences) {

            // Agent Creation
            try {
                createTeams(blueTeam, redTeam, choice, bonus);

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
                    AbstractTerrain terrain = new Terrain(20, 20);

                    int blueTeamSize = TEAM_SIZE, redTeamSize = TEAM_SIZE;
                    Agent currentBlueTeamAgent, currentRedTeamAgent;

                    // We place the teams on the board
                    terrain.placeAgents(blueTeam);
                    terrain.placeAgents(redTeam);
                    //terrain.showTerrain();

                    Iterator<Agent> blueTeamIterator = blueTeam.iterator();
                    Iterator<Agent> redTeamIterator = redTeam.iterator();

                    for (int i = 0; i < 100 && (blueTeamSize > 0 && redTeamSize > 0); ++i) {
                        // System.out.println(" \n Tour " + i);
                        while (blueTeamIterator.hasNext() && redTeamIterator.hasNext()) {
                            currentBlueTeamAgent = blueTeamIterator.next();
                            if (redTeamSize > 0 && currentBlueTeamAgent.isAlive()) {
                                switch (currentBlueTeamAgent.actionTurn(terrain, redTeam, blueTeam)) {
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
                                switch (currentRedTeamAgent.actionTurn(terrain, blueTeam, redTeam)) {
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
                        blueTeamIterator = blueTeam.iterator();
                        redTeamIterator = redTeam.iterator();

                    }

                    if (blueTeamSize == 0) {
                        System.out.println("Victoire de l'équipe Rouge");
                        nbVictoryRed++;
                    } else {
                        System.out.println("Victoire de l'équipe Bleue");
                        nbVictoryBlue++;
                    }

                    nbTurns++;

                    // Deserialize the teams
                    redTeam = dataManager.loadAgents("redTeam.ser");
                    blueTeam = dataManager.loadAgents("blueTeam.ser");

                } catch (Exception e) {
                    System.err.println("[Exception]");
                    e.printStackTrace();
                    System.err.println("End of Main method");
                }
            }

            experiences[nbExperiences] = (float) nbVictoryRed / nbTurns;
            blueTeam = new LinkedList<>();
            redTeam = new LinkedList<>();
        }

        System.out.println("Statistics part");
        for (int i = 0; i < NB_EXPERIENCES; ++i) {
            System.out.println("EXPERIENCE " + i + " : RED won " + experiences[i] * 100 + "% games and BLUE won " + (100 - experiences[i]) + "% games");
            cumulate += experiences[i];
        }

        System.out.println("\n MEAN of the Experiences : " + cumulate / NB_EXPERIENCES * 100 + "% won by RED and " + (100 - cumulate / NB_EXPERIENCES * 100) + "% won by BLUE");
    }

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

    private static int startup() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenue dans la simulation colisée ! Veuillez choisir le type de simulation parmis la liste ci-dessous :");
        System.out.println("Simulation type 1 : Mode chaos, statistiques des unités randomisées (malus ou bonus allant jusqu'à 5) et composition totalement aléatoire");
        System.out.println("Simulation type 2 : Mode avantage, statistiques des unités de l'équipe bleue boostées");
        System.out.println("Simulation type 3 : Compositions aléatoires");
        System.out.println("Simulation standard : Mode équilibré");
        System.out.print("Votre choix : ");

        return scanner.nextInt();
    }

    private static int fillBonus(int choice) {

        if (choice == 2) {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Saisir la valeur du bonus : ");

            return scanner.nextInt();
        }
        return NO_BONUS;
    }
}
