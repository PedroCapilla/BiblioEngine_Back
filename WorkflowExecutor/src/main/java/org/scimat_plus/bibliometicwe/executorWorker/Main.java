package org.scimat_plus.bibliometicwe.executorWorker;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.scimat_plus.core.Executor.Executor;

public class Main {

    public static JSONObject convertStringToJSON(String document){
        JSONParser parser = new JSONParser();
        try{
            return (JSONObject) parser.parse(document);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {

        String sDirectorioTrabajo = System.getProperty("user.dir");
        System.out.println("El directorio de trabajo es " + sDirectorioTrabajo);

        MongoDBConnection connection = new MongoDBConnection();
        MongoDatabase database = connection.getDatabase();

        MongoCollection<Document> workflowCollection = database.getCollection("workflow");
        MongoCollection<Document> resultsCollection = database.getCollection("results");

        while(true){
            Document workflow = workflowCollection.find(Filters.eq("execute", true)).first();
            if(workflow != null) {
                String workflowString = (String) workflow.get("workflow");
                JSONObject workflowJson = convertStringToJSON(workflowString);

                JSONObject root = (JSONObject) workflowJson.get("ROOT");
                JSONObject branch = (JSONObject) workflowJson.get("BRANCH");
                JSONObject leaf = (JSONObject) workflowJson.get("LEAF");

                ObjectId idWorkflow = workflow.getObjectId("_id");

                Executor executor = new Executor(root, branch, leaf, "Workflow", "results", idWorkflow);
                executor.setUp();


                workflowCollection.updateOne(Filters.eq("_id", idWorkflow), new Document("$set", new Document("execute", false)));
                System.out.println("Proceso finalizado");
            }

            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }


    }
}