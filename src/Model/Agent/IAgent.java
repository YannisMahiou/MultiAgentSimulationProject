package Model.Agent;

import Model.Terrain.AbstractTerrain;

import java.util.List;

public interface IAgent {
    public abstract void move(AbstractTerrain terrain, List<Agent> enemyTeam);
    public abstract FightStatus attack(Agent enemy);
    String toString();
}
