package org.scimat_plus.core.taskgraph.TaskDependecyGraph.arguments;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author mjcobo
 */
public class ArgumentStore {

    private int argumentsRemaining;
    private final Map<String, Argument<?>> arguments = new HashMap<>();

    /**
     * Construct a new argument store with a predefined set of arguments names. Therefore, it could not store an
     * argument not assigned to other name different from that.
     *
     * @param argumentNames the list of argument's names
     */
    public ArgumentStore(List<String> argumentNames) {

        for (String name: argumentNames) {

            // Initialize each argument with a null value
            this.arguments.put(name, null);
        }

        this.argumentsRemaining = argumentNames.size();
    }

    /**
     * Add a new argument associated with this name. If the name is not within the list of arguments names provided
     * in the constructor, or the there is already a value with this name, the method will do anything.
     *
     * @param arg
     * @param name
     */
    public void addArgument(Argument<?> arg, String name) {

        // If the name exists in the map, and it is null, the argument is assigned, and therefore, the arguments
        // remaining is decreased in one unit.
        if (this.arguments.containsKey(name) && this.arguments.get(name) == null) {

            this.arguments.put(name, arg);

            this.argumentsRemaining--;

        } else {

            // Otherwise, TODO think in to launch exception or some error
        }
    }

    /**
     * Return the value associated with the argument's name provided. If the argument has not been set, it will return
     * an optional with null value.
     *
     * @param name the name of the argument
     * @return The value associated with the argument's name
     */
    public Optional<Argument<?>> getArgument(String name) {

        // TODO think in to launch exception or some error
        return Optional.of(this.arguments.get(name));
    }

    /**
     * Retrieve the number of arguments left to assign.
     * @return the number of arguments left to assign.
     */
    public int getArgumentsRemaining() {
        return argumentsRemaining;
    }

    /**
     *
     * @return true if there are arguments left to assign
     */
    public boolean isArgumentRemaining() {

        return getArgumentsRemaining() > 0;
    }
}