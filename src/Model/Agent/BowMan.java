package Model.Agent;

import Model.Terrain.AbstractTerrain;

import java.util.LinkedList;
import java.util.List;

/**
 * Ranged Agent with a bow
 */
public class BowMan extends RangedAgent {

    /**
     * Bowman constructor
     *
     * @param hp health points
     * @param damageReduction armor
     * @param speed speed
     * @param strength resistance
     * @param color blue or red
     */
    public BowMan(int hp, int damageReduction, int speed, int strength, String color) {
        super(hp, damageReduction, speed, strength, 2, color);
    }

    /**
     * Calculates the damage inflicted to the enemy in case of direct attack or counter attack
     *
     * @param enemy the enemy
     * @return int : the damage the attack or counter attack will deal
     */
    @Override
    protected int calculateDamage(Agent enemy) {
        // Compute damage / counter damage
        return this.getStrength() - enemy.getDamageReduction();
    }

    @Override
    public String toString() {
        return this.getColor() + "D" + "\u001B[0m";
    }

    /**
     * Find the best target to attack
     * As a BowMan, the Agent wants to attack in priority Melee Agents so he does not get counter attacked
     * If no melee agents can be targeted, returns the initial targets
     *
     * @param targets targeted enemies
     * @param terrain the terrain
     * @return List<Agent> the best "best" fights the agent can do
     */
    protected List<Agent> findBestTargets(List<Agent> targets, AbstractTerrain terrain) {
        List<Agent> bestTargets = new LinkedList<>();

        // Find all enemies with advantage (BowMen can't be counter attacked by Melee Agents)
        for (Agent target : targets) {
            if (target instanceof MeleeAgent) {
                bestTargets.add(target);
            }
        }
        // Return the targets with an advantage or no disadvantage if there's any
        if (bestTargets.size() > 0) {
            return bestTargets;
        }

        // Else return all targets
        return targets;
    }

}
