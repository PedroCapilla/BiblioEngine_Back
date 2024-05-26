package org.scimat_plus.core.Configurer;

import org.scimat_plus.core.Tasks.PrintIntegerTask;
import org.scimat_plus.core.Tasks.SaveIntTask;
import org.scimat_plus.core.taskgraph.TaskDependecyGraph.arguments.Argument;
import org.scimat_plus.core.taskgraph.TaskDependecyGraph.arguments.ArgumentStore;
import org.scimat_plus.core.taskgraph.TaskDependecyGraph.mapper.ConfigureTask;

public class SaveIntTaskConfigurer implements ConfigureTask<SaveIntTask> {
    @Override
    public void setup(SaveIntTask printIntegerTask, ArgumentStore argumentStore) {
        printIntegerTask.setResult((Double) argumentStore.getArgument("a").orElse(new Argument<>("nullValue")).getValue());

    }
}
