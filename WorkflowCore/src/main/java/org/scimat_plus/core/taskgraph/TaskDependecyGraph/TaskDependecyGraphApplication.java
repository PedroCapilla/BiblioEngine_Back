package org.scimat_plus.core.taskgraph.TaskDependecyGraph;
import org.scimat_plus.core.taskgraph.TaskDependecyGraph.arithmeticTask.*;
import org.scimat_plus.core.taskgraph.TaskDependecyGraph.executor.WorkflowExecutorService;
import org.scimat_plus.core.taskgraph.TaskDependecyGraph.nodeTask.GenericNodeTask;
import org.scimat_plus.core.taskgraph.TaskDependecyGraph.nodeTask.NodeTaskType;

public class TaskDependecyGraphApplication {

//	public static void main(String[] args) {
//		SpringApplication.run(TaskDependecyGraphApplication.class, args);
////		JSONParser parser = new JSONParser();
////		System.out.println("working directory " + System.getProperty("user.dir"));
////		try {
////			Object obj = parser.parse(new FileReader("src/main/java/org/scimat_plus/core/taskgraph/TaskDependecyGraph/FirstTask.json"));
//////            System.out.println("working directory " + System.getProperty("user.dir"));
////			JSONObject jsonObject = (JSONObject) obj;
////			JSONObject root = (JSONObject) jsonObject.get("ROOT");
////			JSONObject branch = (JSONObject) jsonObject.get("BRANCH");
////			JSONObject leaf = (JSONObject) jsonObject.get("LEAF");
////
////			// Create the executor services
////			WorkflowExecutorService workflow = new WorkflowExecutorService();
////
////			SetDependencies(root, branch, leaf, workflow);
////			workflow.execute();
////
////		} catch (IOException e) {
////			e.printStackTrace();
////		} catch (ParseException e) {
////			throw new RuntimeException(e);
////		}
//	}
public static void main(String[] args) {

	// Create the executor services
	WorkflowExecutorService workflow = new WorkflowExecutorService();

	// Create a const task
	GenericNodeTask<ConstTask> c1 = new GenericNodeTask.Builder<ConstTask>()
			.ofType(NodeTaskType.ROOT)
			.withTask(new ConstTask(10.0))
			.readyObservedBy(workflow)
			.build();

	// Create a second const task
	GenericNodeTask<ConstTask> c2 = new GenericNodeTask.Builder<ConstTask>()
			.ofType(NodeTaskType.ROOT)
			.withTask(new ConstTask(20.0))
			.readyObservedBy(workflow)
			.build();

	// Create a second const task
	GenericNodeTask<ConstTask> c3 = new GenericNodeTask.Builder<ConstTask>()
			.ofType(NodeTaskType.ROOT)
			.withTask(new ConstTask(30.0))
			.readyObservedBy(workflow)
			.build();

	// Create a second const task
	GenericNodeTask<ConstTask> c4 = new GenericNodeTask.Builder<ConstTask>()
			.ofType(NodeTaskType.ROOT)
			.withTask(new ConstTask(40.0))
			.readyObservedBy(workflow)
			.build();

	// Create a sum task that receive two arguments
	GenericNodeTask<SumTask> s2 = new GenericNodeTask.Builder<SumTask>()
			.ofType(NodeTaskType.BRANCH)
			.withTask(new SumTask())
			.readyObservedBy(workflow)
			.withArgument("a")
			.withArgument("b")
			.configuredBy(new SumTaskConfigurer())
			.build();

	// Create a sum task that receive two arguments
	GenericNodeTask<SumTask> s1 = new GenericNodeTask.Builder<SumTask>()
			.ofType(NodeTaskType.BRANCH)
			.withTask(new SumTask())
			.readyObservedBy(workflow)
			.withArgument("a")
			.withArgument("b")
			.configuredBy(new SumTaskConfigurer())
			.build();

	//Create a mult task that receive two arguments
	GenericNodeTask<MultTask> m1 = new GenericNodeTask.Builder<MultTask>()
			.ofType(NodeTaskType.BRANCH)
			.withTask(new MultTask())
			.readyObservedBy(workflow)
			.configuredBy(new MultTaskConfigurer())
			.withArgument("a")
			.withArgument("b")
			.build();

	// Create a print number task that receive one argument
	GenericNodeTask<PrintNumberTask> p1 = new GenericNodeTask.Builder<PrintNumberTask>()
			.ofType(NodeTaskType.LEAF)
			.withTask(new PrintNumberTask())
			.readyObservedBy(workflow)
			.withArgument("n")
			.configuredBy(new PrintNumberTaskConfigurer())
			.build();


	// Set the dependency between tasks
	c1.addResultObserver("a", s1); // s1 observes c1 through the argument "a"
	c2.addResultObserver("b", s1); // s2 observes c1 through the argument "b"
	c3.addResultObserver("a", s2);
	c4.addResultObserver("b", s2);

	s1.addResultObserver("a", m1);
	s2.addResultObserver("b", m1);

	m1.addResultObserver("n", p1); // p1 observes s1 through the argument "n"

	// Add the tasks to the workflow
	workflow.addNodeTask(c1);
	workflow.addNodeTask(c2);
	workflow.addNodeTask(c3);
	workflow.addNodeTask(c4);

	workflow.addNodeTask(s1);
	workflow.addNodeTask(s2);

	workflow.addNodeTask(p1);

	// Execute the workflow
	workflow.execute();

}
}
