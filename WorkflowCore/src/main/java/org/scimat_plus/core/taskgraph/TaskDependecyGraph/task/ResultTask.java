package org.scimat_plus.core.taskgraph.TaskDependecyGraph.task;


/**
 * @author mjcobo
 */
public interface ResultTask<T> extends BasicTask{

    T getValue();
}
