package Model.Agent;

import Model.Terrain.AbstractTerrain;

import java.util.List;

public interface IAgent {

    void move(AbstractTerrain terrain, List<Agent> enemyTeam);
    FightStatus attack(Agent enemy);
    String toString();
}
