package Model.Agent;

import Model.Terrain.AbstractTerrain;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public abstract class RangedAgent extends Agent{

    public RangedAgent(int hp, int damageReduction, int speed, int strength, int range, String color) {
        super(hp, damageReduction, speed, strength, range, color);
    }

    @Override
    public void move(AbstractTerrain terrain, LinkedList<Agent> enemyTeam){
        List<Agent> closestEnemies = findClosestEnemies(enemyTeam);
        boolean canAttack = false;
        Agent focused;
        Random r = new Random();
        int newPosX = 0, newPosY = 0, rand;

        do{
            rand = r.nextInt(closestEnemies.size() - 1);
            focused = closestEnemies.get(rand);

            switch (getDirection(focused)){
                case BOT:
                    newPosX =  focused.getPosX() - 1;
                    newPosY =  focused.getPosY();
                    break;
                case TOP:
                    newPosX =  focused.getPosX() + 1;
                    newPosY =  focused.getPosY();
                    break;
                case LEFT:
                    newPosX =  focused.getPosX();
                    newPosY =  focused.getPosY() - 1;
                    break;
                case RIGHT:
                    newPosX =  focused.getPosX();
                    newPosY =  focused.getPosY() + 1;
                    break;
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

    private Direction getDirection(Agent enemy){
        Random r = new Random();
        int rand = r.nextInt(2);
        if(this.getPosX() == enemy.getPosX()){
            if(this.getPosY() > enemy.getPosY()){
                switch (rand){
                    case 1:
                        return Direction.TOP_RIGHT;
                    case 2:
                        return Direction.TOP_LEFT;
                    default:
                        return Direction.RIGHT;
                }
            }
            else {
                switch (rand){
                    case 1:
                        return Direction.TOP_LEFT;
                    case 2:
                        return Direction.BOTTOM_LEFT;
                    default:
                        return Direction.LEFT;
                }
            }
        }
        if(this.getPosX() > enemy.getPosX()){
            switch (rand){
                case 1:
                    return Direction.TOP_LEFT;
                case 2:
                    return Direction.TOP_RIGHT;
                default :
                    return Direction.TOP;
            }
        }
        else {
            switch (rand){
                case 1:
                    return Direction.BOTTOM_RIGHT;
                case 2:
                    return Direction.BOTTOM_LEFT;
                default:
                    return Direction.BOT;
            }
        }
    }
}
