package org.scimat_plus.bibliometicwe.executorWorker;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection  {
    private MongoClient mongoClient;
    private MongoDatabase database;

    public MongoDBConnection(){

        //Configuración de URL
        String connectionString = "mongodb://localhost:27017";
        MongoClientURI uri = new MongoClientURI(connectionString);
        mongoClient = new MongoClient(uri);

        //Obtención de la base de datos
        String databaseName="Workflow";
        database = mongoClient.getDatabase(databaseName);
    }

    public MongoDatabase getDatabase(){
        return this.database;
    }

    public void closeConnection(){
        mongoClient.close();
    }
}
