package org.scimat_plus.core.taskgraph.TaskDependecyGraph.arguments;


/**
 * @author mjcobo
 */
public class Argument<T> {

    private final T value;

    public Argument(T value) {
        this.value = value;
    }

    public T getValue() {

        return value;
    }
}
