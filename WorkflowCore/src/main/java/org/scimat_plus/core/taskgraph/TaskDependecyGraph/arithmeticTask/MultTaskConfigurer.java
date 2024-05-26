package org.scimat_plus.core.taskgraph.TaskDependecyGraph.arithmeticTask;

import org.scimat_plus.core.taskgraph.TaskDependecyGraph.arguments.Argument;
import org.scimat_plus.core.taskgraph.TaskDependecyGraph.arguments.ArgumentStore;
import org.scimat_plus.core.taskgraph.TaskDependecyGraph.mapper.ConfigureTask;

/**
 * @author mjcobo
 */
public class MultTaskConfigurer implements ConfigureTask<MultTask> {

    @Override
    public void setup(MultTask task, ArgumentStore arguments) {

        task.setA((double)arguments.getArgument("a").orElse(new Argument<Double>(0.0)).getValue());
        task.setB((double)arguments.getArgument("b").orElse(new Argument<Double>(0.0)).getValue());

    }
}