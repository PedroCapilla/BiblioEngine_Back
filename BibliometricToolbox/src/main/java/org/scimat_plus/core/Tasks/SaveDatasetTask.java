package org.scimat_plus.core.Tasks;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.scimat_plus.core.taskgraph.TaskDependecyGraph.task.ResultTask;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SaveDatasetTask implements ResultTask<Dataset<Row>>, Serializable {

    private transient MongoClient mongoClient; // No es parte del estado serializado
    private String databaseName;
    private String collectionName;
    private Dataset<Row> result;
    private String label;
    private String projectId;

    public SaveDatasetTask(String databaseName, String collectionName, String label, ObjectId projectId) {
        this.databaseName = databaseName;
        this.collectionName = collectionName;
        this.projectId = projectId.toString();
        this.label = label;
    }

    public void setResult(Dataset<Row> result) {
        this.result = result;
    }

    @Override
    public Dataset<Row> getValue() {
        return this.result;
    }

    @Override
    public void run() {

        JavaRDD<Document> documents = this.result.javaRDD().map(row -> {
            Document doc = new Document();
            String[] columns = row.schema().fieldNames();
            for (String col : columns) {
                doc.append(col, row.getAs(col));
            }
            doc.append("taskLabel", this.label);
            doc.append("projectId", this.projectId);
            return doc;
        });
        documents.foreachPartition(new VoidFunction<Iterator<Document>>() {
            @Override
            public void call(Iterator<Document> iterator) throws Exception {
                // Utilizar el nuevo método para crear una instancia de cliente
                try (MongoClient mongoClient = MongoClients.create()) {
                    MongoDatabase database = mongoClient.getDatabase(databaseName);
                    MongoCollection<Document> collection = database.getCollection(collectionName);

                    List<Document> batch = new ArrayList<>();
                    iterator.forEachRemaining(batch::add);

                    if (!batch.isEmpty()) {
                        collection.insertMany(batch);
                    }
                } // El try-with-resources cerrará automáticamente el cliente
            }
        });
    }

}

