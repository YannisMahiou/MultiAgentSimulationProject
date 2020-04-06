package Model.Agent;

import Model.Terrain.AbstractTerrain;

import java.util.*;

public abstract class Agent implements IAgent {

    private static int MIN_DAMAGE = 1;
    protected static double ADVANTAGE = 1.2;
    protected static double DISADVANTAGE = 0.8;

    private int hp;
    private int damageReduction;
    private int speed;
    private int strength;
    private int range;
    private int posX;
    private int posY;

    public Agent(int hp, int damageReduction, int speed, int strength, int range) {
        setHp(hp);
        setDamageReduction(damageReduction);
        setSpeed(speed);
        setStrength(strength);
        setRange(range);
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    // Methods from the interface IAgent
    public abstract void move(AbstractTerrain terrain, List<Agent> enemyTeam);

    public abstract FightStatus attack(Agent enemy);

    public abstract String toString();

    private int getHp() {
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

    protected List<Agent> findClosestEnemies(List<Agent> enemyTeam) {
        Hashtable<Agent, Integer> closest = new Hashtable<>();
        ArrayList<Agent> list = new ArrayList<>();
        int distance;

        for (Agent enemy : enemyTeam) {
            distance = Math.abs(this.posX - enemy.posX) + Math.abs(this.posY - enemy.posY);

            closest.put(enemy, distance);
        }

        List<Map.Entry<Agent, Integer>> entires = new ArrayList(closest.entrySet());
        entires.sort(Map.Entry.comparingByValue());

        /*
        ArrayList<Map.Entry<Agent, Integer>> l = new ArrayList(closest.entrySet());
        l.sort(new Comparator<Map.Entry<Agent, Integer>>() {

            public int compare(Map.Entry<Agent, Integer> o1, Map.Entry<Agent, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        */

        entires.forEach(entry -> {
            if (list.size() < 5) {
                list.add(entry.getKey());
            }
        });

        return list;
   }

    protected boolean moveTo(AbstractTerrain terrain, int posX, int posY){
        if(terrain.isFree(posX, posY)){

            return true;
        }
        return false;
    }

    public void takeDamage(int damage){
        hp -= Math.max(damage, MIN_DAMAGE);
    }

    public boolean isAlive(){
        return hp > 0;
    }
}
