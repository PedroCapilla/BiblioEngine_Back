package org.scimat_plus.core.taskgraph.TaskDependecyGraph.nodeTask;

import org.scimat_plus.core.taskgraph.TaskDependecyGraph.arguments.Argument;

/**
 * @author mjcobo
 */
public interface ResultObserver {

    void addArgument(Argument<?> argument, String name);
}
