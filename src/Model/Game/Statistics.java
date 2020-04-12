package Model.Game;

import Model.Agent.Agent;
import Model.Terrain.AbstractTerrain;

import java.util.LinkedList;

public class Statistics {

    private static int NB_AGENTS = 20;
    private int[][] statsAgent;
    private double cumulAgent;
    private float[] experiences;
    int nbIte;
    int nbExperiences;

    public Statistics(int nbIte, int nbExperiences){
        statsAgent = new int[NB_AGENTS][NB_AGENTS];
        cumulAgent = 0;
        this.nbIte = nbIte;
        this.nbExperiences = nbExperiences;
        experiences = new float[nbExperiences];
    }

    public void computeStats(LinkedList<Agent> redTeam, LinkedList<Agent> blueTeam) {
    }

    public void computeTerrainStats(AbstractTerrain terrain) {
        for (int x = 0; x < NB_AGENTS; x++) {
            for (int y = 0; y < NB_AGENTS; y++) {
                if (!terrain.isFree(x, y)){
                    cumulAgent++;
                    statsAgent[x][y]++;
                }
            }
        }
    }

    public void showTerrainStats(){
        System.out.println("Number of agents on said position at the end of each round (1 turn for each team)");
        for (int x = 0; x < NB_AGENTS; x++) {
            for (int y = 0; y < NB_AGENTS; y++) {
                if(statsAgent[x][y] >= 10000){
                    System.out.print(statsAgent[x][y] + " ");
                }
                else if (statsAgent[x][y] >= 1000){
                    System.out.print(statsAgent[x][y] + "  ");
                }
                else if (statsAgent[x][y] >= 100){
                    System.out.print(statsAgent[x][y] + "   ");
                }
                else if (statsAgent[x][y] >= 10){
                    System.out.print(statsAgent[x][y] + "    ");
                }
                else if (statsAgent[x][y] >= 0){
                    System.out.print(statsAgent[x][y] + "     ");
                }
            }
            System.out.println();
        }

        System.out.println();

        System.out.println("Percentage of agents on said position at the end of each round (1 turn for each team, 2 digits)");
        for (int x = 0; x < NB_AGENTS; x++) {
            for (int y = 0; y < NB_AGENTS; y++) {
                System.out.print( String.format("%.2f", statsAgent[x][y] / cumulAgent * 100.0f) + " ");
            }
            System.out.println();
        }

        System.out.println("Number of positions counted : " + cumulAgent);
    }

    public void computeMean() {
        float cumulate = 0;

        System.out.println("Statistics part");
        for (int i = 0; i < nbExperiences; ++i) {
            System.out.println("EXPERIENCE " + i + " : RED won " + experiences[i] * 100  + "% games and BLUE won " + (1 - experiences[i]) * 100 + "% games");
            cumulate += experiences[i];
        }

        System.out.println("\n MEAN of the Experiences : " + (cumulate / nbExperiences) * 100 + "% won by RED and " + (100 - cumulate / nbExperiences * 100) + "% won by BLUE");
    }

    public void addsToMeanArray(int nbVictoryRed, int it) {
        experiences[it] = (float) nbVictoryRed / nbIte;
    }
}
