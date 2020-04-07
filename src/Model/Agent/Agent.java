package Model.Agent;

import Model.Terrain.AbstractTerrain;

import java.util.*;

public abstract class Agent{

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
    public abstract void move(AbstractTerrain terrain, LinkedList<Agent> enemyTeam);
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

    public final  int getStrength() {
        return strength;
    }

    public final  void setStrength(int strength) {
        this.strength = strength;
    }

    public final int getRange() {
        return range;
    }

    public final void setRange(int range) {
        this.range = range;
    }

    public final String getColor() { return color; }

    public final void setColor(String color) { this.color = color; }

    public final int getPosX() { return posX; }

    public final void setPosX(int posX) { this.posX = posX; }

    public int getPosY() { return posY; }

    public void setPosY(int posY) { this.posY = posY; }

    protected List<Agent> findClosestEnemies(LinkedList<Agent> enemyTeam) {
        Hashtable<Agent, Double> closest = new Hashtable<>();
        ArrayList<Agent> list = new ArrayList<>();
        double distance;

        for (Agent enemy : enemyTeam) {
            distance = Math.sqrt((this.getPosX() - enemy.getPosX()) * (this.getPosX() - enemy.getPosX()) + (this.getPosY() - enemy.getPosY()) * (this.getPosY() - enemy.getPosY()));

            closest.put(enemy, distance);
        }

        List<Map.Entry<Agent, Double>> entries = new ArrayList(closest.entrySet());
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

    protected boolean moveTo(AbstractTerrain terrain, int posX, int posY){
        if(terrain.moveToSamePlace(this, posX, posY)) {
            return true;
        }

        if(terrain.isFree(posX, posY)){
            terrain.updateAgentCoordinates(this, posX, posY);
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
