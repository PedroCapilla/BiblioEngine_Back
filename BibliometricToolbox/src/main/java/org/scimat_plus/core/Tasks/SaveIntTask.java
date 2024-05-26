package org.scimat_plus.core.Tasks;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.scimat_plus.core.taskgraph.TaskDependecyGraph.task.ResultTask;

import java.io.Serializable;

public class SaveIntTask implements ResultTask<Double>, Serializable {
    private Double result;
    private String databaseName;
    private String collectionName;
    private String label;
    private String projectId;

    public SaveIntTask(String databaseName, String collectionName, String label, ObjectId projectId){
        this.databaseName = databaseName;
        this.collectionName = collectionName;
        this.label = label;
        this.projectId = projectId.toString();
    }

    public void setResult(Double result){this.result = result;}
    @Override
    public Double getValue(){return this.result;}

    @Override
    public void run(){

        // Crear el documento con la estructura deseada
        Document data = new Document();
        data.append("projectId", this.projectId);
        data.append("taskLabel", this.label);
        data.append("taskType", "INTEGER");
        data.append("result", this.result);

        // Utilizar try-with-resources para manejar la conexión a MongoDB
        try (MongoClient mongoClient = MongoClients.create()) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection(collectionName);

            // Insertar el documento en MongoDB
            collection.insertOne(data);
        }

    }


}
