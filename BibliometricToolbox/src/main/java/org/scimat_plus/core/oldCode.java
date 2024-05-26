package org.scimat_plus.core;

public class oldCode {

    /*
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
//        JSONObject auxLeaf;
        char letter;
//        JSONObject leafArgs;
        //Connection between branch and leaf
        for (Object keyLeaf : leaf.keySet()) {
            letter = 'a';
//            auxLeaf = (JSONObject) leaf.get((String) keyLeaf);
            JSONObject auxLeaf = new JSONObject((Map) leaf.get((String) keyLeaf));
//            leafArgs = (JSONObject) auxLeaf.get("args");
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
//            auxBranch = (JSONObject) branch.get((String) keyBranch);
            JSONObject auxBranch = new JSONObject((Map) branch.get((String) keyBranch));
//            branchArgs = (JSONObject) auxBranch.get("args");
            JSONObject branchArgs = new JSONObject((Map) auxBranch.get("args"));
//                System.out.println(branchArgs.get("a"));
            for (int i = 0; i < branchArgs.size(); i++) {
                String s = String.valueOf(letter);
                //If exists a Root GND in branch's arguments
                if (rootGnd.containsKey(branchArgs.get(s))) {//Connection between root and branch
                    rootGnd.get(branchArgs.get(s)).addResultObserver(s, branchGnd.get((String) keyBranch));
                    System.out.println("Connecting " + branchArgs.get(s) + " con " + keyBranch);
                }
                //If exists a Branch GND in branch's arguments
                if (branchGnd.containsKey(branchArgs.get(s))) {//Connection between branch and branch
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

            if (aux.get("task").equals("const")) {
                GenericNodeTask<ConstTask> gnd = new GenericNodeTask.Builder<ConstTask>()
                        .ofType(NodeTaskType.ROOT)
                        .withTask(new ConstTask(Double.parseDouble((String) aux.get("value"))))
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
            if (aux.get("task").equals("sum")) {
                GenericNodeTask<SumTask> gnd = new GenericNodeTask.Builder<SumTask>()
                        .ofType(NodeTaskType.BRANCH)
                        .withTask(new SumTask())
                        .readyObservedBy(workflow)
                        .withArgument("a")
                        .withArgument("b")
                        .configuredBy(new SumTaskConfigurer())
                        .build();
                results.put((String) branchKey, gnd);
            } else if (aux.get("task").equals("mult")) {
                GenericNodeTask<MultTask> gnd = new GenericNodeTask.Builder<MultTask>()
                        .ofType(NodeTaskType.BRANCH)
                        .withTask(new MultTask())
                        .readyObservedBy(workflow)
                        .withArgument("a")
                        .withArgument("b")
                        .configuredBy(new MultTaskConfigurer())
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
            JSONObject aux = new JSONObject((Map) leaf.get((String) leafKey));
            if (aux.get("task").equals("print")) {
                GenericNodeTask<PrintNumberTask> gnd = new GenericNodeTask.Builder<PrintNumberTask>()
                        .ofType(NodeTaskType.LEAF)
                        .withTask(new PrintNumberTask())
                        .withArgument("a")
                        .configuredBy(new PrintNumberTaskConfigurer())
                        .build();
                results.put((String) leafKey, gnd);
            }
        }
        return results;
    }
     */
}
