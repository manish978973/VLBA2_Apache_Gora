# MAP REDUCE USING APACHE GORA

The Apache Gora open source framework provides an in-memory data model and persistence for big data. Gora supports persisting to column stores, key value stores, document stores, distributed in-memory key/value stores, in-memory data grids, in-memory caches, distributed multi-model stores, and hybrid in-memory architectures.

Gora also enables analysis of data with extensive Apache Hadoop MapReduce.

## FEATURES OF GORA

* **Data Persistence** : Persisting objects to Column stores such as Apache HBase, Apache Cassandra, Hypertable; key-value stores such as Voldermort, Redis, etc; SQL databases, such as MySQL, HSQLDB, flat files in local file system of Hadoop HDFS;
* **Data Access** : An easy to use Java-friendly common API for accessing the data regardless of its location;
* **Indexing** : Persisting objects to Apache Lucene and Apache Solr indexes, accessing/querying the data with Gora API;
* **Analysis** : Accesing the data and making analysis through adapters for Apache Pig, Apache Hive and Cascading;
* **MapReduce support** : Out-of-the-box and extensive MapReduce (Apache Hadoop) support for data in the data store.

## BACKGROUND

ORM stands for Object Relation Mapping. It is a technology which abstacts the persistency layer (mostly Relational Databases) so that plain domain level objects can be used, without the cumbersome effort to save/load the data to and from the database.


## WHO IS GORA FOR ?

* Hands on Developers required to deal with data volumes which justify Big Data storage solutions classified under NoSQL platform.
* Developers who seek a Java friendly(REST- style) **API** for mapping Java objects to and from NoSQL technologies.
* Gora is simple since it ignores complex SQL mappings.
* Developers seeking extensive support for Map-Reducing data and mapping the data store.
* Indexing and Persisting support to Column/Key-Value store.

## APACHE GORA SUPPORT FOR PROJECTS

<Image src="Images/avro.png" class="center" style="width:50%"> <Image src="Images/cassandra.png" class="center" style="width:50%"><Image src="Images/hbase.png" class="center" style="width:50%"><Image src="Images/hive.png" class="center" style="width:50%"><Image src="Images/solr.jpg" class="center" style="width:50%">




## WHAT PLATFORM DOES GORA WORK ON ?

* Mac OSX 10.9.3
* Linux Mint
* Ubuntu

## SETTING UP THE JAVA and LINUX ENVIRONMENT 

This project was developed using IntelliJ IDEA integrated environment and in Ubuntu.Follow the [link ](https://www.jetbrains.com/help/idea/installation-guide.html) to set up the Java IDE.

## DOWNLOADING AND INSTALLING THE PROJECT

Download the appropriate Gora versions by clicking the following [link ](https://gora.apache.org/downloads.html).After downloading the source code of Gora, we are good to install it

`$ cd gora`

`$ mvn clean install`

## USING MAVEN TO MANAGE GORA

If your project however uses maven, then you can include Gora dependencies to your project by adding all the lines of given pom.xml file

## GORA MODULES AND INTEGRATION

A further idea on Gora modules and its integration can be studies from the following [article ](https://gora.apache.org/current/index.html).

## USECASES IMPLEMENTED ON APACHE GORA

#### <ins>USECASE 1</ins>

* We have parsed a log file into a No SQL database using Gora In-Memory framework.
* The log files are of (**now showdown**) server at [site ](http://www.buldinle.com).The file was obtained from Apache Gora's official portal.
* Example logs contain 10,000 lines between dates 2009/3/10-2009/03/15
* The first fields in order are User's IP,ignored,ignored,Date and time,HTTP method,URL,HTTP method, HTTP status code,Number of bytes returned and User Agent.


#### <ins>USECASE 2</ins>

* Perform a Data Analysis on the data in H-base.
* A map-reduce program to calculate the number of daily pageviews for each URL in the site from the log file is to be performed.
* After the map-reduce process,the output needs to be pushed back to No-SQL database in another table.

### ENTITY RELATION - DATA MODELLING


                     <Image src="Images/entityrelation.png" class="center" style="width:50%">


### PROCESS FLOW IN USE-CASE 1 - PROCESS MODELLING

### PROCESS FLOW IN USE-CASE 2 - PROCESS MODELLING 

## APACHE GORA SYSTEM DESIGN ARCHITECTURE

### DATA BEAN DEFINITION

* Data beans are the main way to hold data in memory and persist in Gora.
* Apache AVRO is used for defining beans as it gives us the possibility to keep tracking an objects persistent state and implement serializability(objects that can be written to streams or databases).
* We define a JSON structure of the object to be mapped to data store.
* Next, these JSON structure is compiled by Apache Gora compiler (extension of Avro) to to Java class.
* These generated classes extend persistence interface.
* Next using the mapping (Data store configuration) we declare how the fields of classes declared in Avro Schemas are serialized and persisted to data store.
* Next API can be called to perform the case parsing or MapReduce.


## USABILITY OF GORA

* Gora has out of the box first class MapReduce support for Apache Hadoop.
* Gora Data store mapping and APIs can be used as inputs and outputs of jobs.
* Gora’s Integrated APIs can be used for further Analysis of Big Data subjected to Map Reduce.
* Gora’s support and mapping to  NoSQL databases with its inbuilt data store classes. 









