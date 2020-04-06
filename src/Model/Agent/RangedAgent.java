package Model.Agent;

import Model.Terrain.AbstractTerrain;

import java.util.List;
import java.util.Random;

public abstract class RangedAgent extends Agent{

    public RangedAgent(int hp, int damageReduction, int speed, int strength, int range) {
        super(hp, damageReduction, speed, strength, range);
    }

    @Override
    public void move(AbstractTerrain terrain, List<Agent> enemyTeam){
        List<Agent> closestEnemies = findClosestEnemies(enemyTeam);

        Random r = new Random();
        int rand = r.nextInt(closestEnemies.size() - 1);

        Agent focused = closestEnemies.get(rand);
        boolean canAttack = false;

        switch (getDirection(focused)){
            case BOT:
                canAttack = moveTo(terrain, focused.getPosX(), focused.getPosY() - 1);
                break;
            case TOP:
                canAttack = moveTo(terrain, focused.getPosX(), focused.getPosY() + 1);
                break;
            case LEFT:
                canAttack = moveTo(terrain, focused.getPosX() - 1, focused.getPosY());
                break;
            case RIGHT:
                canAttack = moveTo(terrain, focused.getPosX() + 1, focused.getPosY());
                break;
            case TOP_LEFT:
                canAttack = moveTo(terrain, focused.getPosX() - 1, focused.getPosY() + 1);
                break;
            case TOP_RIGHT:
                canAttack = moveTo(terrain, focused.getPosX() + 1, focused.getPosY() + 1);
                break;
            case BOTTOM_LEFT:
                canAttack = moveTo(terrain, focused.getPosX() + 1, focused.getPosY() - 1);
                break;
            case BOTTOM_RIGHT:
                canAttack = moveTo(terrain, focused.getPosX() - 1, focused.getPosY() - 1);
                break;
        }

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
        if(this.getPosY() == enemy.getPosY()){
            if(this.getPosX() > enemy.getPosX()){
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
        if(this.getPosY() > enemy.getPosX()){
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
