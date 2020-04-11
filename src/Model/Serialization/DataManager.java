package Model.Serialization;

import Model.Agent.Agent;

import java.util.LinkedList;

public interface DataManager {

    //Saves the agents
    public void saveAgents(String fileName, LinkedList<Agent> agents);

    //Saves the agents
    public LinkedList<Agent> loadAgents(String fileName);

}
