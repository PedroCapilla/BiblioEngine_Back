package org.scimat_plus.core;


import org.bson.types.ObjectId;
import org.scimat_plus.core.Configurer.*;
import org.scimat_plus.core.Tasks.*;
import org.scimat_plus.core.taskgraph.TaskDependecyGraph.executor.WorkflowExecutorService;
import org.scimat_plus.core.taskgraph.TaskDependecyGraph.nodeTask.GenericNodeTask;
import org.scimat_plus.core.taskgraph.TaskDependecyGraph.nodeTask.NodeTaskType;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Pedro Capilla
 */
public class Main {

    static void SetDependencies(JSONObject root, JSONObject branch, JSONObject leaf, WorkflowExecutorService workflow) {

        Map<String, GenericNodeTask> rootGnd = obtainRootTasks(root, workflow);
        Map<String, GenericNodeTask> branchGnd = obtainBranchTasks(branch, workflow);
        Map<String, GenericNodeTask> leafGnd = obtainLeafTasks(leaf, workflow);


        connectingBranchWithRootAndBranchTasks(branch, rootGnd, branchGnd);
        connectingLeafWithBranchTasks(leaf, branchGnd, leafGnd);

        addNodeTaskToWorkflow(workflow, rootGnd, branchGnd, leafGnd);

    }

    private static void addNodeTaskToWorkflow(WorkflowExecutorService workflow, Map<String, GenericNodeTask> rootGnd,
                                              Map<String, GenericNodeTask> branchGnd, Map<String, GenericNodeTask> leafGnd) {
        for (Object rootKey : rootGnd.keySet()) {
            workflow.addNodeTask(rootGnd.get((String) rootKey));
        }
        for (Object branchKey : branchGnd.keySet()) {
            workflow.addNodeTask(branchGnd.get((String) branchKey));
        }
        for (Object leafKey : leafGnd.keySet()) {
            workflow.addNodeTask(leafGnd.get((String) leafKey));
        }
    }

    private static void connectingLeafWithBranchTasks(JSONObject leaf, Map<String, GenericNodeTask> branchGnd, Map<String, GenericNodeTask> leafGnd) {
        char letter;

        //Connection between branch and leaf
        for (Object keyLeaf : leaf.keySet()) {
            letter = 'a';
            JSONObject auxLeaf = new JSONObject((Map) leaf.get((String) keyLeaf));
            JSONObject leafArgs = new JSONObject((Map) auxLeaf.get("args"));
            for (int i = 0; i < leafArgs.size(); i++) {
                String s = String.valueOf(letter);
                if (branchGnd.containsKey(leafArgs.get(s))) {
                    branchGnd.get(leafArgs.get(s)).addResultObserver(s, leafGnd.get((String) keyLeaf));
                }
                System.out.println("Connecting " + leafArgs.get(s) + " con " + (String) keyLeaf);
                letter++;
            }
        }
    }

    private static void connectingBranchWithRootAndBranchTasks(JSONObject branch, Map<String, GenericNodeTask> rootGnd, Map<String, GenericNodeTask> branchGnd) {
        char letter;
        for (Object keyBranch : branch.keySet()) {
            letter = 'a';
            JSONObject auxBranch = new JSONObject((Map) branch.get((String) keyBranch));
            JSONObject branchArgs = new JSONObject((Map) auxBranch.get("args"));

            for (int i = 0; i < branchArgs.size(); i++) {
                String s = String.valueOf(letter);
                //If exists a Root GND in branch's arguments
                //Connection between root and branch
                if (rootGnd.containsKey(branchArgs.get(s))) {
                    rootGnd.get(branchArgs.get(s)).addResultObserver(s, branchGnd.get((String) keyBranch));
                    System.out.println("Connecting " + branchArgs.get(s) + " con " + keyBranch);
                }
                //If exists a Branch GND in branch's arguments
                //Connection between branch and branch
                if (branchGnd.containsKey(branchArgs.get(s))) {
                    branchGnd.get(branchArgs.get(s)).addResultObserver(s, branchGnd.get((String) keyBranch));
                    System.out.println("Connecting " + branchArgs.get(s) + " con " + keyBranch);
                }
                letter++;
            }
        }
    }

    static Map<String, GenericNodeTask> obtainRootTasks(JSONObject root, WorkflowExecutorService workflow) {
        Map<String, GenericNodeTask> results = new HashMap<>();
//        JSONObject aux;
        for (Object rootKey : root.keySet()) {
            JSONObject aux = new JSONObject((Map) root.get((String) rootKey));

            if (aux.get("task").equals("load")) {
                GenericNodeTask<LoaderTask> gnd = new GenericNodeTask.Builder<LoaderTask>()
                        .ofType(NodeTaskType.ROOT)
                        .withTask(new LoaderTask((String) aux.get("value")))
                        .readyObservedBy(workflow)
                        .build();
                results.put((String) rootKey, gnd);
            }

        }
        return results;
    }

    static Map<String, GenericNodeTask> obtainBranchTasks(JSONObject branch, WorkflowExecutorService workflow) {
        Map<String, GenericNodeTask> results = new HashMap<>();
//        JSONObject aux;
        for (Object branchKey : branch.keySet()) {
            JSONObject aux = new JSONObject((Map) branch.get((String) branchKey));
            if (aux.get("task").equals("filter")) {
                JSONObject paramsAux = new JSONObject((Map) aux.get("params"));
                GenericNodeTask<FilterWordTask> gnd = new GenericNodeTask.Builder<FilterWordTask>()
                        .ofType(NodeTaskType.BRANCH)
                        .withTask(new FilterWordTask((String) paramsAux.get("row"), (String) paramsAux.get("keyword")))
                        .readyObservedBy(workflow)
                        .configuredBy(new FilterWordTaskConfigurer())
                        .withArgument("a")
                        .build();

                results.put((String) branchKey, gnd);
            }
            else if (aux.get("task").equals("union")) {
                GenericNodeTask<UnionTask> gnd = new GenericNodeTask.Builder<UnionTask>()
                        .ofType(NodeTaskType.BRANCH)
                        .withTask(new UnionTask())
                        .readyObservedBy(workflow)
                        .configuredBy(new UnionTaskConfigurer())
                        .withArgument("a")
                        .withArgument("b")
                        .build();
                results.put((String) branchKey, gnd);
            }
            else if(aux.get("task").equals("betweenTask")){
                JSONObject paramsAux = new JSONObject((Map) aux.get("params"));
                GenericNodeTask<BetweenFilterTask> gnd = new GenericNodeTask.Builder<BetweenFilterTask>()
                        .ofType(NodeTaskType.BRANCH)
                        .withTask(new BetweenFilterTask((String) paramsAux.get("row"), (String) paramsAux.get("initial"), (String) paramsAux.get("final")))
                        .readyObservedBy(workflow)
                        .configuredBy(new BetweenFilterConfigurer())
                        .withArgument("a")
                        .build();
                results.put((String) branchKey, gnd);
            }
            else if(aux.get("task").equals("UnitByUnit")){
                JSONObject paramsAux = new JSONObject((Map) aux.get("params"));
                GenericNodeTask<CreateUnitByUnitDatasetTask> gnd = new GenericNodeTask.Builder<CreateUnitByUnitDatasetTask>()
                        .ofType(NodeTaskType.BRANCH)
                        .withTask(new CreateUnitByUnitDatasetTask((String) paramsAux.get("firstRow"),(String) paramsAux.get("secondRow")))
                        .readyObservedBy(workflow)
                        .configuredBy(new CreateUnitByUnitConfigurer())
                        .withArgument("a")
                        .build();
                results.put((String) branchKey, gnd);
            }
            else if(aux.get("task").equals("GreaterThan")) {
                JSONObject paramsAux = new JSONObject((Map) aux.get("params"));
                GenericNodeTask<GreaterThanTask> gnd = new GenericNodeTask.Builder<GreaterThanTask>()
                        .ofType(NodeTaskType.BRANCH)
                        .withTask(new GreaterThanTask((String) paramsAux.get("row"), (String) paramsAux.get("number")))
                        .readyObservedBy(workflow)
                        .configuredBy(new GreaterThanConfigurer())
                        .withArgument("a")
                        .build();
                results.put((String) branchKey, gnd);
            }
            else if(aux.get("task").equals("ArithmeticAgg")) {
                JSONObject paramsAux = new JSONObject((Map) aux.get("params"));
                GenericNodeTask<ArithmeticAggTask> gnd = new GenericNodeTask.Builder<ArithmeticAggTask>()
                        .ofType(NodeTaskType.BRANCH)
                        .withTask(new ArithmeticAggTask((String) paramsAux.get("type"), (String) paramsAux.get("row")))
                        .readyObservedBy(workflow)
                        .configuredBy(new ArithmeticAggConfigurer())
                        .withArgument("a")
                        .build();
                results.put((String) branchKey, gnd);
            }
            else if(aux.get("task").equals("PivotTable")) {
                JSONObject paramsAux = new JSONObject((Map) aux.get("params"));
                GenericNodeTask<PivotTableTask> gnd = new GenericNodeTask.Builder<PivotTableTask>()
                        .ofType(NodeTaskType.BRANCH)
                        .withTask(new PivotTableTask((String) paramsAux.get("row1"), (String) paramsAux.get("row2")))
                        .readyObservedBy(workflow)
                        .configuredBy(new PivotTableConfigurer())
                        .withArgument("a")
                        .build();
                results.put((String) branchKey, gnd);
            }
            else if(aux.get("task").equals("hIndex")) {
                GenericNodeTask<HIndexTask> gnd = new GenericNodeTask.Builder<HIndexTask>()
                        .ofType(NodeTaskType.BRANCH)
                        .withTask(new HIndexTask())
                        .readyObservedBy(workflow)
                        .configuredBy(new HIndexConfigurer())
                        .withArgument("a")
                        .build();
                results.put((String) branchKey, gnd);
            }
            else if(aux.get("task").equals("FilterPerFilter")) {
                JSONObject paramsAux = new JSONObject((Map) aux.get("params"));
                GenericNodeTask<FilterPerFilterTask> gnd = new GenericNodeTask.Builder<FilterPerFilterTask>()
                        .ofType(NodeTaskType.BRANCH)
                        .withTask(new FilterPerFilterTask((String) paramsAux.get("row1"), (String) paramsAux.get("row2")))
                        .readyObservedBy(workflow)
                        .configuredBy(new FilterPerFilterConfigurer())
                        .withArgument("a")
                        .build();
                results.put((String) branchKey, gnd);
            }
        }
        return results;
    }

    static Map<String, GenericNodeTask> obtainLeafTasks(JSONObject leaf, WorkflowExecutorService workflow) {
        Map<String, GenericNodeTask> results = new HashMap<>();
//        JSONObject aux;
        for (Object leafKey : leaf.keySet()) {
            JSONObject aux = new JSONObject((Map) leaf.get(leafKey));
            if (aux.get("task").equals("printCsv")) {
                JSONObject paramsAux = new JSONObject((Map) aux.get("params"));
                GenericNodeTask<PrintCsvTask> gnd = new GenericNodeTask.Builder<PrintCsvTask>()
                        .ofType(NodeTaskType.LEAF)
                        .withTask(new PrintCsvTask((String) paramsAux.get("size")))
                        .withArgument("a")
                        .configuredBy(new PrintCsvTaskConfigurer())
                        .readyObservedBy(workflow)
                        .build();
                results.put((String) leafKey, gnd);
            }
            else if(aux.get("task").equals("printInt")){
                JSONObject paramsAux = new JSONObject((Map) aux.get("params"));
                GenericNodeTask<PrintIntegerTask> gnd = new GenericNodeTask.Builder<PrintIntegerTask>()
                        .ofType(NodeTaskType.LEAF)
                        .withTask(new PrintIntegerTask((String) paramsAux.get("description")))
                        .withArgument("a")
                        .configuredBy(new PrintIntegerTaskConfigurer())
                        .readyObservedBy(workflow)
                        .build();
                results.put((String) leafKey, gnd);
            }else if(aux.get("task").equals("saveInt")) {
                JSONObject paramsAux = new JSONObject((Map) aux.get("params"));
                GenericNodeTask<SaveIntTask> gnd = new GenericNodeTask.Builder<SaveIntTask>()
                        .ofType(NodeTaskType.LEAF)
                        .withTask(new SaveIntTask("Workflow","results", (String) paramsAux.get("description"), new ObjectId()))
                        .withArgument("a")
                        .configuredBy(new SaveIntTaskConfigurer())
                        .readyObservedBy(workflow)
                        .build();
                results.put((String) leafKey, gnd);
            }
        }
        return results;
    }
    public static void main(String[] args) {
        System.out.println("working directory " + System.getProperty("user.dir"));
        JSONParser parser = new JSONParser();
        try{
            Object obj = parser.parse(new FileReader("src/main/java/org/scimat_plus/core/JSON/finalJson.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject root = (JSONObject) jsonObject.get("ROOT");
            JSONObject branch = (JSONObject) jsonObject.get("BRANCH");
            JSONObject leaf = (JSONObject) jsonObject.get("LEAF");

            // Create the executor services
            WorkflowExecutorService workflow = new WorkflowExecutorService();

            SetDependencies(root, branch, leaf, workflow);
            workflow.execute();

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }
}