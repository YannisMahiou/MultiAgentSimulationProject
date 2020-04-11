package Model.Agent;

import Model.Game.RandomSingleton;
import Model.Terrain.AbstractTerrain;

import java.io.Serializable;
import java.util.*;

public abstract class Agent implements Serializable {

    protected static double ADVANTAGE = 1.2;
    protected static double DISADVANTAGE = 0.8;
    protected static int MIN_DAMAGE = 1;
    protected static int DOUBLE_ATTACK_SPEED = 5;
    private int hp;
    private int damageReduction;
    private int speed;
    private int strength;
    private int range;
    private int posX;
    private int posY;
    private String color;

    /**
     * Agent constructor
     *
     * @param hp              health points
     * @param damageReduction armor
     * @param speed           speed of movement
     * @param strength        strength
     * @param range
     * @param color           color of the team
     */
    public Agent(int hp, int damageReduction, int speed, int strength, int range, String color) {
        setHp(hp);
        setDamageReduction(damageReduction);
        setSpeed(speed);
        setStrength(strength);
        setRange(range);
        setColor(color);
    }

    // Methods from the interface IAgent

    /**
     * Turn of an agent : Find a place to move to attack an enemy
     * The agent can either be at range of one or more enemies at the start of his turn and will focus the one he has more chance to win against
     * Or he will try to find the 5 closest enemies and focus the fight he has more chance to win against
     *
     * @param terrain    the terrain
     * @param enemyTeam  the enemy team
     * @param alliedTeam Healers ?
     * @return the status of the Fight : WIN if he killed an enemy, LOST if he died in battle, DRAW if both units are alive after a fight or NO_FIGHT if he couldn't fight
     */
    public FightStatus actionTurn(AbstractTerrain terrain, LinkedList<Agent> enemyTeam, LinkedList<Agent> alliedTeam) {
        if(!this.isAlive()){
            throw new IllegalStateException("Agent is not alive");
        }
        // Initialise var
        List<Agent> closestEnemies = findEnemiesAtRange(terrain, enemyTeam);
        List<Agent> bestTargets;
        Agent focused;
        int newPosX = 0, newPosY = 0, rand;

        // If there is at least 1 enemy at range
        if (closestEnemies.size() > 0) {
            // Find the best targets
            bestTargets = findBestTargets(closestEnemies, terrain);

            // Chose randomly the targets to focus
            rand = RandomSingleton.getInstance().nextInt(bestTargets.size());
            focused = bestTargets.get(rand);
        }
        // Else if there no enemy directly at range
        else {
            // Find the closest enemies (up to 5)
            closestEnemies = findClosestEnemies(enemyTeam);

            // Find the best targets
            bestTargets = findBestTargets(closestEnemies, terrain);
            // Do - While (Guarantee a move inside of the terrain bounds)
            do {
                // Chose randomly the targets to focus
                rand = RandomSingleton.getInstance().nextInt(bestTargets.size());
                focused = bestTargets.get(rand);

                // Get the Direction to attack
                switch (getDirection(focused)) {
                    // Ranged and Melee units
                    case BOT:
                        newPosX = focused.getPosX() - range;
                        newPosY = focused.getPosY();
                        break;
                    case TOP:
                        newPosX = focused.getPosX() + range;
                        newPosY = focused.getPosY();
                        break;
                    case LEFT:
                        newPosX = focused.getPosX();
                        newPosY = focused.getPosY() - range;
                        break;
                    case RIGHT:
                        newPosX = focused.getPosX();
                        newPosY = focused.getPosY() + range;
                        break;
                    // Ranged units Only
                    case TOP_LEFT:
                        newPosX = focused.getPosX() + 1;
                        newPosY = focused.getPosY() - 1;
                        break;
                    case TOP_RIGHT:
                        newPosX = focused.getPosX() + 1;
                        newPosY = focused.getPosY() + 1;
                        break;
                    case BOTTOM_LEFT:
                        newPosX = focused.getPosX() - 1;
                        newPosY = focused.getPosY() - 1;
                        break;
                    case BOTTOM_RIGHT:
                        newPosX = focused.getPosX() - 1;
                        newPosY = focused.getPosY() + 1;
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + getDirection(focused));
                }
            } while (terrain.isOutOfBounds(newPosX, newPosY));
        }

        // If the agent could move (he can "move" to the same place)
        if (moveTo(terrain, newPosX, newPosY)) {
            // Attack the focused enemy !
            switch (attack(focused)) {
                case WIN:
                    // The agent won the fight (killed the enemy)
                    terrain.removeAgent(focused);
                    // System.out.println("win");
                    return FightStatus.WIN;
                case LOST:
                    // The agent lost the fight (killed by enemy)
                    terrain.removeAgent(this);
                    // System.out.println("lose");
                    return FightStatus.LOST;
                case DRAW:
                    // The 2 agents are alive
                    // System.out.println("draw");
                    return FightStatus.DRAW;
            }
        }

        // He could not attack
        return FightStatus.NO_FIGHT;
    }

    /**
     * Attack an enemy at Range
     *
     * @param enemy the focused enemy
     * @return the status of the Fight : WIN if he killed an enemy, LOST if he died in battle, DRAW if both units are alive after a fight
     */
    public FightStatus attack(Agent enemy) {
        if(!enemy.isAlive()){
            throw new IllegalStateException("Enemy is already dead");
        }

        // Compute damage and enemy counter damage
        int damage = this.calculateDamage(enemy);
        int enemyCounterDamage = enemy.calculateDamage(this);

        // Inflict damage to the enemy
        enemy.takeDamage(damage);

        // If the enemy is still alive
        if (enemy.isAlive()) {
            // If the enemy can counter attack
            if (enemy.canCounterAttack(this)) {
                // Inflict damage to this Agent
                this.takeDamage(enemyCounterDamage);
            }
            // If the enemy killed this agent
            if (!this.isAlive()) {
                // Return the status of the fight : LOST
                return FightStatus.LOST;
            }
        }
        // If the enemy is dead
        else {
            // Return the status of the fight : WIN
            return FightStatus.WIN;
        }

        // If this agent can do a double attack
        if (this.getSpeed() - enemy.getSpeed() >= DOUBLE_ATTACK_SPEED) {
            // Inflict damage to the enemy
            enemy.takeDamage(damage);
            // If the enemy is not alive
            if (!enemy.isAlive()) {
                // Return the status of the fight : WIN
                return FightStatus.WIN;
            }
        }
        // Else, if the enemy can do a double attack
        else if (enemy.canCounterAttack(this) && enemy.getSpeed() - this.getSpeed() >= DOUBLE_ATTACK_SPEED) {
            // Inflict damage to this Agent
            this.takeDamage(enemyCounterDamage);
            // If this agent is not alive
            if (!this.isAlive()) {
                // Return the status of the fight : LOST
                return FightStatus.LOST;
            }
        }

        // The 2 Agents are still alive, return the status of the fight : DRAW
        return FightStatus.DRAW;
    }

    public abstract String toString();

    public final int getHp() {
        return hp;
    }

    private void setHp(int hp) {
        this.hp = hp;
    }

    public final int getDamageReduction() {
        return damageReduction;
    }

    public final void setDamageReduction(int damageReduction) {
        this.damageReduction = damageReduction;
    }

    public final int getSpeed() {
        return speed;
    }

    public final void setSpeed(int speed) {
        this.speed = speed;
    }

    public final int getStrength() {
        return strength;
    }

    public final void setStrength(int strength) {
        this.strength = strength;
    }

    public final int getRange() {
        return range;
    }

    public final void setRange(int range) {
        this.range = range;
    }

    public final String getColor() {
        return color;
    }

    public final void setColor(String color) {
        this.color = color;
    }

    public final int getPosX() {
        return posX;
    }

    public final void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    /**
     * Find up to 5 closest enemies using Euclidean distances
     *
     * @param enemyTeam the enemy team
     * @return List<Agent> The closest enemies
     */
    protected List<Agent> findClosestEnemies(LinkedList<Agent> enemyTeam) {
        Hashtable<Agent, Double> closest = new Hashtable<>();
        ArrayList<Agent> list = new ArrayList<>();
        double distance;

        // Get the distance between each alive enemy and this agent
        for (Agent enemy : enemyTeam) {
            if (enemy.isAlive()) {
                distance = Math.sqrt((this.getPosX() - enemy.getPosX()) * (this.getPosX() - enemy.getPosX()) + (this.getPosY() - enemy.getPosY()) * (this.getPosY() - enemy.getPosY()));

                closest.put(enemy, distance);
            }
        }

        // Sort the Hashtable by value
        List<Map.Entry<Agent, Double>> entries = new LinkedList<>(closest.entrySet());
        entries.sort(Map.Entry.comparingByValue());

        // Get up to the 5 closest enemies
        entries.forEach(entry -> {
            if (list.size() < 5) {
                list.add(entry.getKey());
            }
        });

        return list;
    }

    /**
     * Move an agent to the said coordinates on the terrain if the position is free of any agent
     *
     * @param terrain : the terrain
     * @param posX    : the X coordinate
     * @param posY    : the Y coordinate
     * @return true if the Agent moved or "moved" to the same place, false if he couldn't move
     */
    protected boolean moveTo(AbstractTerrain terrain, int posX, int posY) {
        // Check if the agent is moving to the same place
        if (terrain.moveToSamePlace(this, posX, posY)) {
            return true;
        }

        // If the agent is going to move to a free positon on the terrain
        if (terrain.isFree(posX, posY)) {
            // Update his coordinates on the terrain and set his new position
            terrain.updateAgentCoordinates(this, posX, posY);
            return true;
        }

        // The agent did not move
        return false;
    }

    /**
     * Removes hp to the agent (each agent takes a minimum of 1 damage)
     * An agent dies if his hp reaches 0
     *
     * @param damage the damage to be inflicted
     */
    public void takeDamage(int damage) {
        hp -= Math.max(damage, MIN_DAMAGE);
    }

    /**
     * Check if the agent is alive
     *
     * @return true if the agent hp is > 0, false else
     */
    public boolean isAlive() {
        return hp > 0;
    }

    protected abstract List<Agent> findEnemiesAtRange(AbstractTerrain terrain, List<Agent> enemyTeam);

    protected abstract Direction getDirection(Agent enemy);

    protected abstract List<Agent> findBestTargets(List<Agent> targets, AbstractTerrain terrain);

    protected abstract int calculateDamage(Agent enemy);

    protected abstract boolean canCounterAttack(Agent enemy);
}
