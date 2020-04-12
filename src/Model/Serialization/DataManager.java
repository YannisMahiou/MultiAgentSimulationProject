package Model.Serialization;

import Model.Agent.Agent;

import java.util.LinkedList;

/**
 * Interface used to propose ways to serialize/deserialize data
 */
public interface DataManager {

    /**
     * Serialize the agents
     * @param fileName file name where to save
     * @param agents agents to serialize
     */
    void saveAgents(String fileName, LinkedList<Agent> agents);

    /**
     * Deserialize the agents
     * @param fileName file where to load
     * @return the agents deserialize
     */
    LinkedList<Agent> loadAgents(String fileName);

}
