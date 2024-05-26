package org.scimat_plus.core.taskgraph.TaskDependecyGraph.mapper;

import org.scimat_plus.core.taskgraph.TaskDependecyGraph.task.BasicTask;
import org.scimat_plus.core.taskgraph.TaskDependecyGraph.arguments.ArgumentStore;

/**
 * @author mjcobo
 */
public interface ConfigureTask<T extends BasicTask> {

    void setup(T task, ArgumentStore arguments);


}
