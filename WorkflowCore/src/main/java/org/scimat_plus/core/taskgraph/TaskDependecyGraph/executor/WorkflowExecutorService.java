package org.scimat_plus.core.taskgraph.TaskDependecyGraph.executor;

import org.scimat_plus.core.taskgraph.TaskDependecyGraph.nodeTask.GenericNodeTask;
import org.scimat_plus.core.taskgraph.TaskDependecyGraph.nodeTask.NodeTaskReadyObserver;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * @author mjcobo
 */
public class WorkflowExecutorService implements NodeTaskReadyObserver {

    private final Queue<GenericNodeTask> readyTaskQueue;
    private final List<GenericNodeTask> nonReadyTaskList;

    public WorkflowExecutorService() {

        this.readyTaskQueue = new ArrayDeque<>();
        this.nonReadyTaskList = new ArrayList<>();
    }

    public void execute() {

        GenericNodeTask nodeTask;

        while (!this.readyTaskQueue.isEmpty()) {

            nodeTask = this.readyTaskQueue.poll();

            nodeTask.run();
        }
    }

    public void addNodeTask(GenericNodeTask nodeTask) {

        if (nodeTask.isReady()) {

            this.readyTaskQueue.add(nodeTask);

        } else {

            this.nonReadyTaskList.add(nodeTask);
        }
    }

    @Override
    public void notifyReady(GenericNodeTask nodeTask) {

        // Remove the node task from the waiting list and, push it in the execution queue

        this.nonReadyTaskList.remove(nodeTask);
        this.readyTaskQueue.add(nodeTask);
    }
}
