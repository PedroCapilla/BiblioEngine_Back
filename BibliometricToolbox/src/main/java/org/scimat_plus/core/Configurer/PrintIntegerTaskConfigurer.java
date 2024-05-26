package org.scimat_plus.core.Configurer;

import org.scimat_plus.core.Tasks.PrintIntegerTask;
import org.scimat_plus.core.taskgraph.TaskDependecyGraph.arguments.Argument;
import org.scimat_plus.core.taskgraph.TaskDependecyGraph.arguments.ArgumentStore;
import org.scimat_plus.core.taskgraph.TaskDependecyGraph.mapper.ConfigureTask;

public class PrintIntegerTaskConfigurer implements ConfigureTask<PrintIntegerTask> {
    @Override
    public void setup(PrintIntegerTask printIntegerTask, ArgumentStore argumentStore) {
        printIntegerTask.setResult((Double) argumentStore.getArgument("a").orElse(new Argument<>(0)).getValue());

    }
}
