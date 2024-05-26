package org.scimat_plus.core.taskgraph.TaskDependecyGraph.nodeTask;

import org.scimat_plus.core.taskgraph.TaskDependecyGraph.task.BasicTask;
import org.scimat_plus.core.taskgraph.TaskDependecyGraph.arguments.Argument;
import org.scimat_plus.core.taskgraph.TaskDependecyGraph.arguments.ArgumentStore;
import org.scimat_plus.core.taskgraph.TaskDependecyGraph.mapper.ConfigureTask;
import org.scimat_plus.core.taskgraph.TaskDependecyGraph.task.ResultTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent a Generic task that can be added to a workflow, which internally run a {@code BasicTask}  or
 * {@code ResultTask} task. The nodes could receive arguments and its results could be observed by other node task that
 * implement {@code ResultObserver} interface.
 *
 * <ul>
 *     <li>
 *         Root: root nodes are the beginning of the workflow. A Root node dont receive arguments, so it is ready after
 *         the creation of a new instance. That is, it does not have to notify any {@code NodeTaskReadyObserver}. Also,
 *         it should have a set of {@code ResultObserver} that will be notified after the inner task is run.
 *     </li>
 *     <li>
 *         Branch: branch nodes are in the middle of the workflow. They can have as predecessor a ROOT node, or other
 *         BRANCH node. On the other hand, they can have as successor a BRANCH node or a LEAF node. Therefore, it
 *         receives a set of {@code Argument} stored internally in a {@code ArgumentStore}. Each argument will have a
 *         given name. Arguments are received from predecessor task, after they finish their execution. So, the node
 *         task will not be ready until all the predecessor node task has finished and send the argument to it.
 *         Since a branch task also produces a final result, its results are observable by those classes implementing
 *         {@code ResultObserver}. Thus, after the execution of the inner class it will notify to all
 *         {@code ResultObserver} registered using the {@code addResultObserver} method.
 *     </li>
 *     <li>
 *         LEAF: leafs nodes are at the end of the workflow, they could be understood as exit nodes. A leaf node receives
 *         arguments since it implements the {@code ResultObserver} interface. So, it should notify to the
 *         {@code NodeTaskReadyObserver} when it is ready. On the other hand, it does not generate any results, so it
 *         doesnt have to notify any result observable
 *     </li>
 * </ul>
 *
 * It is important to note that only LEAF node needs a {@code BasicTask}, meanwhile BRANCH and ROOT need
 * {@code ResultTask}.
 *
 * The class should be instantiated using the {@code builder()} method, since the class implement the Builder Pattern.
 *
 * @param <T> The type of the inner task
 * @author mjcobo
 */
public class GenericNodeTask<T extends BasicTask> implements ResultObserver, Runnable{


    // The nodeTask can be ROOT, BRANCH or LEAF
    private final NodeTaskType nodeType;
    // The inner task of type T
    private final T innerTask;
    // The argument store.
    private final ArgumentStore arguments;
    // A utility class to configure the inner class using the {@code ArgumentStore}
    private final ConfigureTask<T> configurer;
    // If the type is BRANCH or LEAF, when the node task will be ready (i.e. it has received all the arguments needed),
    // it will notify the {@code NodeTaskReadyObserver}
    private final NodeTaskReadyObserver readyObserver;
    // List of the {@code ResultObserver} assigned to this class
    private final List<ResultObserver> resultObservers;
    // Each observable will receive the argument associated with a name. This attribute contains the list of names.
    private final List<String> names;

    private GenericNodeTask(Builder<T> builder) {

        this.nodeType = builder.nodeType;
        this.innerTask = builder.innerTask;
        this.arguments = new ArgumentStore(builder.argumentsNames);
        this.configurer = builder.configurer;
        this.readyObserver = builder.readyObserver;

        this.resultObservers = new ArrayList<>();
        this.names = new ArrayList<>();
    }

    /*public final ArgumentStore getArguments() {
        return this.arguments;
    }*/

    public T getInnerTask() {
        return this.innerTask;
    }

    public void run(){

        // If the task has just receive the arguments needed, the inner task must be  run
        if (isReady()) {

            if (this.nodeType == NodeTaskType.BRANCH || this.nodeType == NodeTaskType.LEAF) {
                this.configurer.setup(this.innerTask, arguments);
            }

            this.innerTask.run();

            // After the execution of the task, we should notify to the result observers on branch or root nodes
            if (this.nodeType == NodeTaskType.BRANCH || this.nodeType == NodeTaskType.ROOT) {

                notifyResultObservers(new Argument<>(((ResultTask<?>)this.getInnerTask()).getValue()));
            }

        } else {

            throw new IllegalStateException("The task is not ready to be executed");
        }
    }

    public boolean isReady() {

        return ! this.arguments.isArgumentRemaining();
    }

    public void notifyReady() {

        if (isReady() && this.readyObserver != null) {

            this.readyObserver.notifyReady(this);
        }
    }

    @Override
    public void addArgument(Argument<?> argument, String name) {

        this.arguments.addArgument(argument, name);

        notifyReady();
    }

    public void addResultObserver(String name, ResultObserver observer) {

        this.names.add(name);
        this.resultObservers.add(observer);
    }

    public void notifyResultObservers(Argument<?> argument) {

        int i;

        for (i = 0; i < this.names.size(); i++) {
            this.resultObservers.get(i).addArgument(argument, this.names.get(i));
        }
    }

    /**
     * {@code GenericNodeTask} builder static inner class.
     */
    public static class Builder<R extends BasicTask> {

        private NodeTaskType nodeType = null;
        private R innerTask = null;
        private final List<String> argumentsNames = new ArrayList<>();
        private ConfigureTask<R> configurer = null;
        private NodeTaskReadyObserver readyObserver = null;

        public Builder(){}

        private void ValidateRootTask(){
            if(this.argumentsNames.size() != 0){
                throw new IllegalStateException("A ROOT task can't have arguments");
            }
            if (this.readyObserver == null){
                throw new IllegalStateException("A ROOT taks must have an observable");
            }
            if(this.innerTask == null){
                throw new IllegalStateException("The task must have an inner Task");
            }
            if(this.configurer != null){
                throw new IllegalStateException("A ROOT Task can't have a configurer");
            }
        }
        private void ValidateBranchTask(){
            if(this.innerTask == null){
                throw new IllegalStateException("The task must have an inner Task");
            }
            if(this.readyObserver == null){
                throw new IllegalStateException("A BRANCH task must have an observable");
            }
            if(this.argumentsNames.size() == 0){
                throw new IllegalStateException("A BRANCH task must have an argument at least");
            }
            if(this.configurer == null){
                throw new IllegalStateException("A BRANCH task must have a configurer");
            }
        }
        private void ValidateLeafTask(){
            if(this.innerTask == null){
                throw new IllegalStateException("The task must have an inner Task");
            }
//            if(this.readyObserver != null){
//                throw new IllegalStateException("A LEAF task can't have an observable");
//            }
            if(this.argumentsNames.size() == 0){
                throw new IllegalStateException("A LEAF task must have an argument at least");
            }
            if(this.configurer == null){
                throw new IllegalStateException("A LEAF task must have a configurer");
            }
        }

        private void Validate(){
            if (nodeType == null) {
                throw new IllegalStateException("The task must have a type");
            }else{
                if(nodeType == NodeTaskType.ROOT){
                    ValidateRootTask();
                }else if(nodeType == NodeTaskType.BRANCH){
                    ValidateBranchTask();
                } else if (nodeType == NodeTaskType.LEAF) {
                    ValidateLeafTask();
                }
            }
        }

        /**
         * Set the {@code task} and returns a reference to this Builder enabling method chaining.
         * @param task the {@code T} task to set.
         *
         * @return a reference to this Builder
         */
        public Builder<R> withTask(R task) {

            this.innerTask = task;
            return this;
        }

        /**
         * Sets the {@code nodeType} and returns a reference to this Builder enabling method chaining.
         *
         * @param nodeType the {@code NodeTaskType} to set.
         * @return a reference to this Builder
         */
        public Builder<R> ofType(NodeTaskType nodeType) {

            this.nodeType = nodeType;
            return this;
        }

        /**
         * Sets the {@code configurer} and returns a reference to this Builder enabling method chaining. The configurer
         * is called before the execution of the inner task in BRANCH and LEAF tasks.
         *
         * @param configurer the {@code ConfigureTask<T>} to set.
         * @return a reference to this Builder
         */
        public Builder<R> configuredBy(ConfigureTask<R> configurer) {

            this.configurer = configurer;
            return this;
        }

        /**
         * Add a new argument that should be received byh this task. The task will not be ready until all the arguments
         * set through this method arrive to the task. Also returns a reference to this Builder enabling method chaining.
         *
         * @param name the {@code argumentName} to set.
         * @return a reference to this Builder
         */
        public Builder<R> withArgument(String name) {

            this.argumentsNames.add(name);
            return this;
        }

        /**
         * Sets the {@code readyObserver} and returns a reference to this Builder enabling method chaining.
         *
         * @param readyObserver the {@code NodeTaskReadyObserver} to set.
         * @return a reference to this Builder
         */
        public Builder<R> readyObservedBy(NodeTaskReadyObserver readyObserver) {

            this.readyObserver = readyObserver;
            return this;
        }

        /**
         * Returns a {@code GenericNodeTask} built from the parameters previously set.
         *
         * @return a {@code GenericNodeTask} built with parameters of this {@code GenericNodeTask.Builder}
         */
        public GenericNodeTask<R> build() {
            Validate();
            return new GenericNodeTask<>(this);
        }
    }
}
