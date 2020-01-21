package Model.Terrain;

import Model.Agent.IAgent;
import Model.Factory.AgentFactory;

import java.util.LinkedList;
import java.util.Random;

public abstract class AbstractTerrain {

    private static int NBAGENTS = 20;
    private static int SIZEY = 5;
    private int sizeX;
    private int sizeY;

    public IAgent[][] agents = new IAgent[NBAGENTS][NBAGENTS];


    public int getSizeX() {
        return sizeX;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public int getIzeY() {
        return sizeY;
    }

    public void setIzeY(int sizeY) {
        this.sizeY = sizeY;
    }

    /**
     * AbstractTerrain Constructor
     *
     * @param sizeX the x size of the AbstractTerrain
     * @param sizeY the y size of the AbstractTerrain
     */
    public AbstractTerrain(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    /**
     * Create the terrain for the simulation
     */
    public void createTerrain() {
        for (int j = 0; j < NBAGENTS; ++j) {
            for(int i = 0; i < NBAGENTS; ++i){
                System.out.print("*");
            }
            System.out.print("\n");
        }
    }

    /**
     * Tells if a place is free in the terrain
     *
     * @param posX posX to check
     * @param posY posY to check
     * @return true if free, false else
     */
    public boolean isFree(int posX, int posY){
        boolean res = false;
        if(agents[posX][posY] == null)
            res =  true;
        return res;
    }

    /**
     * Create the agents on the terrain for the incoming simulation
     *
     * @param team Agents team created
     */
    public void placeAgents(LinkedList<IAgent> team) {

        // Initialisation of variables
        Random generator = new Random();
        int r1 = generator.nextInt() % NBAGENTS;
        int r2 = generator.nextInt() % SIZEY;
        int nbAgents = 20;

        // For all the agents of the team
        for(int i = 0; i < nbAgents; ++i){

            // If the place on the Terrain is free
            if(this.isFree(r1, r2)){

                // Place an Agent
                agents[r1][r2] = team.get(i);

            }
        }
    }

    /**
     * Function called to show the terrain with the agents
     */
    public void showTerrain(){
        for (int x = 0; x < NBAGENTS; ++x) {
            for(int y = 0; y < NBAGENTS; ++y){
                //if(isFree(x, y))
                  //  System.out.println("M");
                    //System.out.print(agents[x][y].toString());
                System.out.print("*");
            }
            System.out.print("\n");
        }
    }
}
