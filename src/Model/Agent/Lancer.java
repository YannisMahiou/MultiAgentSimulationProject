package Model.Agent;

import Model.Terrain.AbstractTerrain;

import java.util.LinkedList;
import java.util.List;

public class Lancer extends MeleeAgent {


    public Lancer(int hp, int damageReduction, int speed, int strength, String color) {
        super(hp, damageReduction, speed, strength, 1, color);
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
        int damage = this.getStrength() - enemy.getDamageReduction();

        // If the enemy is a Knight fight with an advantage
        if (enemy instanceof Knight) {
            return (int) (damage * ADVANTAGE);
        }
        // If the enemy is an AxeMan fight with a disadvantage
        if (enemy instanceof AxeMan) {
            return (int) (damage * DISADVANTAGE);
        }

        // Else normal fight
        return damage;
    }

    @Override
    public String toString() {
        return this.getColor() + "I" + "\u001B[0m";
    }

    /**
     * Find the best target to attack
     * As a Lancer, the Agent wants to attack in priority Knight Agents because it has an advantage or BowMen that can't counter attack
     * If no Knight Agents can be targeted, look for other Lancer Agents
     * If no other targets found, return the initial target list
     *
     * @param targets targeted enemies
     * @param terrain the terrain
     * @return List<Agent> the best "best" fights the agent can do
     */
    protected List<Agent> findBestTargets(List<Agent> targets, AbstractTerrain terrain) {
        List<Agent> bestTargets = new LinkedList<>();

        // Find all enemies with advantage (BowMen can't counter attack Melee Agents)
        for (Agent target : targets) {
            if (target instanceof RangedAgent || target instanceof Knight) {
                bestTargets.add(target);
            }
        }

        // If no target found, find all enemies with no disadvantage
        if (bestTargets.size() == 0) {
            for (Agent target : targets) {
                if (target instanceof Lancer) {
                    bestTargets.add(target);
                }
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
