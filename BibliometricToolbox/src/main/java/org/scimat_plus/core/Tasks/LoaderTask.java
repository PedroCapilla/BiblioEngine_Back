package org.scimat_plus.core.Tasks;

import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.scimat_plus.core.taskgraph.TaskDependecyGraph.task.ResultTask;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;


public class LoaderTask implements ResultTask<Dataset<Row>>{

    private Dataset<Row> csv;
    private String collectionName;

    private Dataset<Row> stringToCsv(String csvUrl){

        SparkSession spark = SparkSession.builder().
                master("local[*]")
                .appName("BibliometricToolBox")
                .config("spark.mongodb.output.uri", "mongodb://127.0.0.1/Workflow.results")
                .getOrCreate();

        spark.sparkContext().setLogLevel("OFF");


        Dataset<Row> csv = spark.read().format("csv")
                .option("sep", ";")
                .option("inferSchema", "true")
                .option("header", "true")
                .load(csvUrl);

        //Convert Citations and Year from String to Integer in order to sort them
        csv = csv.withColumn("Citations", csv.col("Citations").cast(DataTypes.IntegerType));
        csv = csv.withColumn("Year", csv.col("Year").cast(DataTypes.IntegerType));

        return csv;
    }

    public LoaderTask(String csvUrl){
        this.csv = stringToCsv(csvUrl);
    }
    @Override
    public Dataset<Row> getValue(){return this.csv;}

    public void setCsv(Dataset<Row> csv){
        this.csv = csv;
    }

    @Override
    public void run(){
        System.out.println("Principal dataset size --> " + this.csv.count());
//        this.csv.printSchema();
    }
}
