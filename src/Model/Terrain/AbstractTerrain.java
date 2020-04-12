package Model.Terrain;

import Model.Agent.Agent;
import Model.Game.RandomSingleton;

import java.util.List;

public abstract class AbstractTerrain {

    private static int NB_AGENTS = 20;
    private static int SIZEY = 5;
    public Agent[][] agents = new Agent[NB_AGENTS][NB_AGENTS];
    private int sizeX;
    private int sizeY;

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
     * Create an empty terrain for the simulation
     */
    public void createTerrain() {
        for (int j = 0; j < NB_AGENTS; ++j) {
            for (int i = 0; i < NB_AGENTS; ++i) {
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
    public boolean isFree(int posX, int posY) {
        boolean res = false;
        if (agents[posX][posY] == null)
            res = true;
        return res;
    }

    /**
     * Create the agents on the terrain for the incoming simulation
     *
     * @param team Agents team created
     */
    public void placeAgents(List<Agent> team) {

        // Initialisation of variables
        int randX;
        int randY;

        // For all the agents of the team
        for (Agent agent : team) {

            // Do While the place on the Terrain is not free
            do {

                //Generates 2 random numbers
                randX = RandomSingleton.getInstance().nextInt(NB_AGENTS);
                randY = RandomSingleton.getInstance().nextInt(NB_AGENTS);
            } while (!isFree(randX, randY));

            // Place an Agent
            agents[randX][randY] = agent;
            agent.setPosX(randX);
            agent.setPosY(randY);
        }
    }

    /**
     * Function called to show the terrain with the agents
     */
    public void showTerrain() {
        for (int x = 0; x < NB_AGENTS; x++) {
            for (int y = 0; y < NB_AGENTS; y++) {
                if (isFree(x, y))
                    System.out.print("* ");
                else System.out.print(agents[x][y].toString() + " ");
            }
            System.out.print("\n");
        }
    }

    /**
     * Takes an agent and updates his coordinates
     *
     * @param agent the agent
     * @param posX the X coordinate
     * @param posY the Y coordinate
     */
    public void updateAgentCoordinates(Agent agent, int posX, int posY) {
        if(isFree(agent.getPosX(), agent.getPosY())){
            throw new IllegalStateException();
        }

        agents[agent.getPosX()][agent.getPosY()] = null;
        agents[posX][posY] = agent;

        agent.setPosX(posX);
        agent.setPosY(posY);
    }

    /**
     * Checks if the posX and posY given aren't out of bounds from the Terrain
     *
     * @param posX the X coordinate
     * @param posY the Y coordinate
     * @return true if out of bounds
     */
    public boolean isOutOfBounds(int posX, int posY) {
        boolean isOut = false;
        if (posX < 0 || posY < 0 || posX >= NB_AGENTS || posY >= NB_AGENTS)
            isOut = true;
        return isOut;
    }

    /**
     * Checks if the agent wants to move to the same [X][Y] coordinates
     *
     * @param agent the agent
     * @param posX the X coordinate
     * @param posY the Y coordinate
     * @return true if the coordinates correspond, false else
     */
    public boolean moveToSamePlace(Agent agent, int posX, int posY) {
        return agent.getPosX() == posX && agent.getPosY() == posY;
    }

    public void removeAgent(Agent agent) {
        agents[agent.getPosX()][agent.getPosY()] = null;
    }
}
