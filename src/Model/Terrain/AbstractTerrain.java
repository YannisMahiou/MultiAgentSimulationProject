package Model.Terrain;

import Model.Agent.Agent;

import java.util.List;
import java.util.Random;

public abstract class AbstractTerrain {

    private static int NBAGENTS = 20;
    private static int SIZEY = 5;
    private int sizeX;
    private int sizeY;

    public Agent[][] agents = new Agent[NBAGENTS][NBAGENTS];

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
    AbstractTerrain(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    /**
     * Create an empty terrain for the simulation
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
    public void placeAgents(List<Agent> team, int side) {

        // Initialisation of variables
        Random generator = new Random();
        int r1;
        int r2;

        // For all the agents of the team
        for(int i = 0; i < NBAGENTS; ++i){

            //Generates 2 random numbers
            r1 = generator.nextInt(SIZEY);
            r2 = generator.nextInt(NBAGENTS);

            if(side == 1){
                r1 =  NBAGENTS - generator.nextInt(SIZEY) - side;
            }

            // If the place on the Terrain is free
            if(isFree(r1, r2)){

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
            for (int y = 0; y < NBAGENTS; ++y) {
                if (isFree(x, y))
                    System.out.print("*");
                else System.out.print(agents[x][y].toString());
            }
            System.out.print("\n");
        }
    }

    /**
     * Takes an agent and updates his coordinates
     */
    public void updateAgentCoordinates(Agent agent, int posX, int posY)
    {
        agents[agent.getPosX()][agent.getPosY()] = null;
        agents[posX][posY] = agent;
    }

    /**
     * Checks if the posX and posY given aren't out of bounds from the Terrain
     * @param posX
     * @param posY
     * @return true if [posX
     */
    public boolean isOutOfBounds(int posX, int posY)
    {
        boolean isOut = false;
        if(posX < 0 || posY < 0 || posX > NBAGENTS || posY > NBAGENTS)
            isOut = true;
        return isOut;
    }

    /**
     * Checks if the agent wants to move to the same [X][Y] coordinates
     * @param agent
     * @param posX
     * @param posY
     * @return
     */
    public boolean moveToSamePlace(Agent agent, int posX, int posY)
    {
        if(agent.getPosX() == posX || agent.getPosY()  == posY) {
            return true;
        }
        return false;
    }
}
