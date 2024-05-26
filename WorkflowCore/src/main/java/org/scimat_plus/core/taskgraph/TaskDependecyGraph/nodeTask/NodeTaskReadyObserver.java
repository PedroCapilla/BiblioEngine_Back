package org.scimat_plus.core.taskgraph.TaskDependecyGraph.nodeTask;


/**
 * @author mjcobo
 */
public interface NodeTaskReadyObserver {

    public void notifyReady(GenericNodeTask nodeTask);
}
