package org.scimat_plus.core.taskgraph.TaskDependecyGraph.arithmeticTask;

import org.scimat_plus.core.taskgraph.TaskDependecyGraph.arguments.Argument;
import org.scimat_plus.core.taskgraph.TaskDependecyGraph.arguments.ArgumentStore;
import org.scimat_plus.core.taskgraph.TaskDependecyGraph.mapper.ConfigureTask;

/**
 * @author mjcobo
 */
public class PrintNumberTaskConfigurer implements ConfigureTask<PrintNumberTask> {

    @Override
    public void setup(PrintNumberTask task, ArgumentStore arguments) {
        task.setN((double)arguments.getArgument("n").orElse(new Argument<Double>(0.0)).getValue());
    }

}
