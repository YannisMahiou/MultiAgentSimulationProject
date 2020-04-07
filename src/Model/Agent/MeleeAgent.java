package Model.Agent;

import Model.Terrain.AbstractTerrain;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by yannis on 14/01/20.
 */
public abstract class MeleeAgent extends Agent{

    public MeleeAgent(int hp, int damageReduction, int speed, int strength, int range, String color) {
        super(hp, damageReduction, speed, strength, range, color);
    }

    public void move(AbstractTerrain terrain, LinkedList<Agent> enemyTeam){
        List<Agent> closestEnemies = findClosestEnemies(enemyTeam);

        Random r = new Random();
        int rand = r.nextInt(closestEnemies.size() - 1);
        int newPosX = 0, newPosY = 0;

        Agent focused = closestEnemies.get(rand);
        boolean canAttack = false;

        do{
            switch (getDirection(focused)){
                case BOT:
                    newPosX =  focused.getPosX();
                    newPosY =  focused.getPosY() - 1;
                    break;
                case TOP:
                    newPosX =  focused.getPosX();
                    newPosY =  focused.getPosY() + 1;
                    break;
                case LEFT:
                    newPosX =  focused.getPosX() - 1;
                    newPosY =  focused.getPosY();
                    break;
                case RIGHT:
                    newPosX =  focused.getPosX() + 1;
                    newPosY =  focused.getPosY();
                    break;
            }
        }while(terrain.isOutOfBounds(newPosX, newPosY));

        canAttack = moveTo(terrain, newPosX, newPosY);

        if(canAttack){
            switch (attack(focused)){
                case WIN:
                    // Adversaire vaincu
                    break;
                case LOST:
                    // Agent mort au combat
                    break;
                case DRAW:
                    // Les deux combattants sont en vie
                    break;
            }
        }
    }

    public abstract FightStatus attack(Agent enemy);

    private Direction getDirection(Agent enemy) {
        if (this.getPosX() == enemy.getPosX()) {
            if (this.getPosY() > enemy.getPosY()) {
                return Direction.RIGHT;
            } else {
                return Direction.LEFT;
            }
        }
        if (this.getPosX() > enemy.getPosX()) {
            return Direction.TOP;
        } else {
            return Direction.BOT;
        }
    }
}
