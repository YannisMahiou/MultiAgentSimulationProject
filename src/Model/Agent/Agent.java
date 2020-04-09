package Model.Agent;

import Model.Terrain.AbstractTerrain;

import java.util.*;

public abstract class Agent {

    protected static double ADVANTAGE = 1.2;
    protected static double DISADVANTAGE = 0.8;
    protected static int MIN_DAMAGE = 1;
    private int hp;
    private int damageReduction;
    private int speed;
    private int strength;
    private int range;
    private int posX;
    private int posY;
    private String color;

    public Agent(int hp, int damageReduction, int speed, int strength, int range, String color) {
        setHp(hp);
        setDamageReduction(damageReduction);
        setSpeed(speed);
        setStrength(strength);
        setRange(range);
        setColor(color);
    }

    // Methods from the interface IAgent
    public void actionTurn(AbstractTerrain terrain, LinkedList<Agent> enemyTeam, LinkedList<Agent> alliedTeam) {
        List<Agent> closestEnemies = findEnemiesAtRange(terrain, enemyTeam);
        Agent focused;
        Random r = new Random();
        int newPosX = 0, newPosY = 0, rand;

        if (closestEnemies.size() > 0) {
            rand = r.nextInt(closestEnemies.size() - 1);
            focused = closestEnemies.get(rand);
        } else {
            closestEnemies = findClosestEnemies(enemyTeam);

            do {
                rand = r.nextInt(closestEnemies.size() - 1);
                focused = closestEnemies.get(rand);

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

        if (moveTo(terrain, newPosX, newPosY)) {
            switch (attack(focused)) {
                case WIN:
                    // The agent won the fight (killed the enemy)
                    terrain.removeAgent(focused);
                    enemyTeam.remove(focused);
                    break;
                case LOST:
                    // The agent lost the fight (killed by enemy)
                    terrain.removeAgent(this);
                    alliedTeam.remove(this);
                    break;
                case DRAW:
                    // The 2 agents are alive
                    break;
            }
        }
    }

    public abstract FightStatus attack(Agent enemy);

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

    protected List<Agent> findClosestEnemies(LinkedList<Agent> enemyTeam) {
        Hashtable<Agent, Double> closest = new Hashtable<>();
        ArrayList<Agent> list = new ArrayList<>();
        double distance;

        for (Agent enemy : enemyTeam) {
            distance = Math.sqrt((this.getPosX() - enemy.getPosX()) * (this.getPosX() - enemy.getPosX()) + (this.getPosY() - enemy.getPosY()) * (this.getPosY() - enemy.getPosY()));

            closest.put(enemy, distance);
        }

        List<Map.Entry<Agent, Double>> entries = new LinkedList<>(closest.entrySet());
        entries.sort(Map.Entry.comparingByValue());

        /*
        ArrayList<Map.Entry<Agent, Integer>> l = new ArrayList(closest.entrySet());
        l.sort(new Comparator<Map.Entry<Agent, Integer>>() {

            public int compare(Map.Entry<Agent, Integer> o1, Map.Entry<Agent, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        */

        entries.forEach(entry -> {
            if (list.size() < 5) {
                list.add(entry.getKey());
            }
        });

        return list;
    }

    protected boolean moveTo(AbstractTerrain terrain, int posX, int posY) {
        if (terrain.moveToSamePlace(this, posX, posY)) {
            return true;
        }

        if (terrain.isFree(posX, posY)) {
            terrain.updateAgentCoordinates(this, posX, posY);
            return true;
        }
        return false;
    }

    public void takeDamage(int damage) {
        hp -= Math.max(damage, MIN_DAMAGE);
    }

    public boolean isAlive() {
        return hp > 0;
    }

    protected abstract List<Agent> findEnemiesAtRange(AbstractTerrain terrain, List<Agent> enemyTeam);

    protected abstract Direction getDirection(Agent enemy);
}
