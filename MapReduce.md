
- [Introduction](#introduction)
- [MapReduce Programming model](#mapreduce-programming-model)
- [Hadoop](#hadoop)
  - [HDFS](#hdfs)
- [Pig](#pig)
- [Hive](#hive)
- [Spark](#spark)
  - [Characterstics](#characterstics)
  - [Supported Functionality](#supported-functionality)
  - [Advatanges](#advatanges)
  - [Disadvantages](#disadvantages)
  
## Introduction
This page is dedicated to all study notes concerning Batch Data Processing
## MapReduce Programming model
1. __Map__: each worker node applies the map function to the local data, and writes the output to a temporary storage. A master node ensures that only one copy of the redundant input data is processed.
2. __Shuffle__: worker nodes redistribute data based on the output keys (produced by the map function), such that all data belonging to one key is located on the same worker node.
3. __Reduce__: worker nodes now process each group of output data, per key, in parallel.

## Hadoop

### [HDFS](https://hadoop.apache.org/docs/r1.2.1/hdfs_design.html)
* __Composition__ :Master/Slave model
  * __NameNode__: master node, a single one in a cluster. 
    * Manages the file system namespace and regulates access to files by clients.
    * Store the block size and replication factor (how many times a file should be replicated) per file.
  * __DataNodes__: files are splited into blocks and stored on datanodes. 
  
* __Data Replication__: 
  * __Design__: Files are split into sequence of equal size blocks (except the last block). Files are write once and has a single writer. Namenode periodically receives heartbeat and a Blockreport from each of the DataNodes in the cluster. 
    * __Blockreport__: contains a list of all blocks on a DataNode.
    * __Heartbeat__: reception implies that the DataNode is functioning properly. 
  * __Replication Placement__: temporary for now. When the replication factor is three, HDFS’s placement policy is to put one replica on one node in the local rack, another on a node in a different (remote) rack, and the last on a different node in the same remote rack
  * __Replication selection for read__: choose the closest replica to the reader
  * __Safemode__: enter at the beginning of application. Each block needs a specified minimum number of replicas to be considered safely replicated. After receiving hearbeat and Blockreport, NameNode checks if the blocks are safely replicated. If a configurable percentage of data blocks are safely replicated(plus an additional 30 seconds), the NameNode exits the Safemode state. Then it replicates the not-safely-replicated blocks to Datanodes. 


## Pig

## Hive

## Spark
### Characterstics
* Has no built-in file system, can be used in conjunct with HDFS.
* Use RDD(Resilient Distributed Dataset)/Dataframes to represent data extracted from the file system. 
* Builds a DAG for operations nd their relations for optimization
* Compute in memory, don't write results to disk automatically
### Supported Functionality
1. Support stream processing, can process real-time data 
2. Support interactive analysis with Spark shell
3. Support batch processing like Hadoop
4. Support Machine learning 
### Advatanges
1. Very fast, computation completely in memory.Data can be in memory or from disk
2. Easy to use user interface
### Disadvantages
1. Not very secure: security is by default turned off
2. Costly, require lots of RAM 


