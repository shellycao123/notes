
- [1. Introduction](#1-introduction)
- [2. MapReduce Programming model](#2-mapreduce-programming-model)
- [3. Hadoop](#3-hadoop)
  - [3.1. MapReduce:](#31-mapreduce)
    - [3.1.1. Overview](#311-overview)
    - [Typical setup:](#typical-setup)
    - [Components:](#components)
  - [3.2. HDFS](#32-hdfs)
    - [3.2.1. Overview: Master/Slave model](#321-overview-masterslave-model)
    - [3.2.2. Data Organization & Replication:](#322-data-organization--replication)
    - [3.2.3. Metadata Storage:](#323-metadata-storage)
    - [3.2.4. Communication Protocols:](#324-communication-protocols)
    - [3.2.5. Failure Handling:](#325-failure-handling)
    - [3.2.6. Data Corruption handling:](#326-data-corruption-handling)
    - [3.2.7. Space release:](#327-space-release)
    - [3.2.8. Data rebalancing:](#328-data-rebalancing)
    - [3.2.9. File System Permission:](#329-file-system-permission)
    - [3.2.10. 3.2.10. Other related features:](#3210-3210-other-related-features)
  - [Hadoop YARN](#hadoop-yarn)
    - [Architecture](#architecture)
    - [Special functionalities:](#special-functionalities)
  - [Hadoop Commons:](#hadoop-commons)
  - [Hadoop Ozone:](#hadoop-ozone)
    - [Characteristics:](#characteristics)
- [4. Pig](#4-pig)
- [5. Hive](#5-hive)
- [6. Spark](#6-spark)
  - [6.1. Characterstics](#61-characterstics)
  - [6.2. Supported Functionality](#62-supported-functionality)
  - [6.3. Advatanges](#63-advatanges)
  - [6.4. Disadvantages](#64-disadvantages)
# 1. Introduction
This page is dedicated to all study notes concerning Batch Data Processing

# 2. MapReduce Programming model
  1. __Map__: each worker node applies the map function to the local data, and writes the output to a temporary storage. A master node ensures that only one copy of the redundant input data is processed.
  2. __Shuffle__: worker nodes redistribute data based on the output keys (produced by the map function), such that all data belonging to one key is located on the same worker node.
  3. __Reduce__: worker nodes now process each group of output data, per key, in parallel.

# 3. Hadoop

## 3.1. [MapReduce](https://hadoop.apache.org/docs/r1.2.1/mapred_tutorial.html): 
### 3.1.1. Overview
  * __Job Tracker__: master node, scheduling the jobs' component tasks on the slaves, monitoring them and re-executing the failed tasks
  * __Task Tracker__: slaves, execute the tasks as directed by the master in a separate JVM as a child process. 
### Typical setup: 
  1. Storage node and computing node are the same to increase aggregate bandwidth. aka Node runs HDFS and MapReduce. 
  2. Both in and output are stored in file system, not in memory. 
### Components: 
  * __Mapper__: maps input key/value pairs to a set of intermediate key/value pairs. 
  * __Reducer__: reduces a set of intermediate values which share a key to a smaller set of values. Can be __NONE__. Stages: 
     1. Shuffle:  the framework fetches the relevant output of all the mappers, via HTTP.  
     2. Sort: Framework groups Mapper outputs by keys. Occur simutaneously with Shuffle. 
     3. Secondary Sort (optional): Can be used to sort the output of Mappers by values. Can sort in reducer or create composite key from natural key + value and sort by framework. 
     4. Reduce: reduce the sorted input and write the result to FS.  
 * __Partitioner__: control the partitioning of mapper outputs. # of partitions is the same as number of reduce tasks. 
 * __Reporter__: report progress, set application-level status messages and update Counters. Can be implemented in Mapper and Reducer. 
 * __Output Collector__: collect the output of mapper and reducer. 
 * __Job Configration__
## 3.2. [HDFS](https://hadoop.apache.org/docs/r1.2.1/hdfs_design.html)
### 3.2.1. Overview: Master/Slave model
  * __NameNode__: master node, a single one in a cluster. 
    * Manages the file system namespace and regulates access to files by clients.
    * Store the block size and replication factor (how many times a file should be replicated) per file.
    * Editlog stores the change to the file system metadata, FsImagestores the OS namespace (mapping of file blocks and fs properties) in local OS file system. 
  * __DataNodes__: files are splited into blocks and stored on datanodes. 
    * Store each block of HDFS ata in a separate file in the local FS. When a DataNode starts up, it scans through its local file system, generates a list of all HDFS data blocks that correspond to each of these local files and sends this report to the NameNode: this is the Blockreport.
  
### 3.2.2. Data Organization & Replication: 
  * __Design__: Files are split into sequence of equal size blocks (typical 64mb, except the last block).Each block is stored on a different DataNode if possible.  Files are write once and has a single writer. Namenode periodically receives heartbeat and a Blockreport from each of the DataNodes in the cluster. 
    * __Blockreport__: contains a list of all blocks on a DataNode.
    * __Heartbeat__: reception implies that the DataNode is functioning properly. 
  * __File creation__: support streaming writing with a client-side buffer. When the buffer has enough data for a block, client contacts the NameNode, NameNode insert the file name into the fs and allocates a data block for it. Client gets DataNode identity and the destination block. Client then flushes the data to the DataNode. When the file is closed, client contacts the NameNode, NameNode commits the file into persistent store; if the file is not commited, it's lost.
    * __Replication creation__: Client receives a list of DataNode that'll host the block's replica. The first node receives the data in small portions, write the portion to local, and flush it to the next node in the list, so on and so forth for all rest nodes in the list, creating a pipeline. Support receiving from the previous node and concurrently write to the next node.  
  * __Replication Placement__: temporary for now. When the replication factor is three, HDFS’s placement policy is to put one replica on one node in the local rack, another on a node in a different (remote) rack, and the last on a different node in the same remote rack
  * __Replication selection for read__: choose the closest replica to the reader
  * __Safemode__: enter at the beginning of application. Each block needs a specified minimum number of replicas to be considered safely replicated. After receiving hearbeat and Blockreport, NameNode checks if the blocks are safely replicated. If a configurable percentage of data blocks are safely replicated(plus an additional 30 seconds), the NameNode exits the Safemode state. Then it replicates the not-safely-replicated blocks to Datanodes. 

### 3.2.3. Metadata Storage: 
  checkpoint occurs when starting up the NameNode. Node reads the Fsimage from disk and apply all Editlog items to the image. New Fsimage is flushed to disk and out-dated Editlog portion is truncated. 

### 3.2.4. Communication Protocols: 
  Based on TCP/IP. Cient use ClientProtocol with NameNode. DataNode communicate with NameNode by DataNode Protocol. Both Protocols are wrapped in a RPC abstraction. NameNode only responses, never initialize RPCs.

### 3.2.5. Failure Handling: 
  * __Data disk failure__: When a hearbeat is missing from a Datanode, NameNode stops forwarding message to it. 
    * __Re-replication__: might be caused by a DataNode becoming unavailable, a replica becoming corrupted, a hard disk on a DataNode may fail, or the replication factor of a file may be increased.
  * __Metadata disk failure__: store multiple copies of Fsimage and Editlog on the NameNode. When starting, select the latest consistent copy. When editing to these files, update all copies sychrounously. 
    * __Secondary NameNode__ :  separate machine from primary NameNode.
      1. Periodically merges fsimage with Editlog and send the image to primary to avoid overly large Editlog when restarting. 
      2. Checkpointing metadata from primary NameNode. 
      3. Primary NameNode can retrieve fsimage and editlog from secondary NameNode after failure. Secondary itself can't become the primary.
   
### 3.2.6. Data Corruption handling: 
  checksum checking for the content of HDFS files. When creating the file, each block's checksum is stores in a separate hidden file in the HDFS namespace. When retrieving, compare the checksum of each block. If corrputed, can opt to retrieve from a replica. 

### 3.2.7. Space release: 
  * __File Deletion__: when a file is deleted, it is first renamed to be in the /trash directory. After some time (6 hrs for now), the filename is deleted from the namespace. 
  * __Decrease Replication Factor__: NameNode selects the Replica to be deleted, send the msg in the next heartbeat. 
  
### 3.2.8. Data rebalancing: 
  e.g. when adding new nodes to the cluster. Multiple considerations
  1. Keep one of the replicas of a block on the same node as the node that is writing the block.
  2. Spread different replicas of a block across the racks to survive loss of whole rack.
  3. One of the replicas is usually placed on the same rack as the node writing to the file to reduce cross-rack network I/O.
  4. Spread HDFS data uniformly across the DataNodes in the cluster.
   
### 3.2.9. File System Permission:
  Similar to POSIX file system. 

### 3.2.10. 3.2.10. Other related features: 
  1. Support __Recovery Mode__ for metadata if no replication exists.
  2. support __Rollback__ cluster state back to before update. can store one copy of previous version. 
  3. Suppoer __Synthetic Load Generator__ to test NameNode behavior under different client loads. Can specify the load mix and intensity. 
  4. Adiministrator can create __Quotas__: stick with renamed files
   * __Name Quota__: limit on the number of file and directory names in the tree rooted at that directory.
   * __Space Quota__:  hard limit on the number of bytes used by files in the tree rooted at that directory. Replica counts
  5. Support __Offline Image Viewer__, dump the contents of hdfs fsimage files to human-readable formats for offline analysis.
  6. Support __HFTP__ for reading from a remote Hadoop cluster, use HTTPS.   
##  Hadoop YARN
  Separate resource management from scheduling & monitoring by using different daemons. 
### Architecture
  *  __Resource Manager__: global in the system, manage all resources in the system. 
     *  __Scheduler__:allocate resource to running applications. Perform no monitoring or status tracking. Partition policy is pluggable. 
     *  __Application Manager__: accepting job-submissions, negotiating the first container for executing the application specific Application Master and restartg the Application Master container on failure. 
  *  __Application Master__: per application, negotiating resources with scheduler and working with the NodeManager(s) to execute and monitor the tasks. Also track resource status and monitor for progress. 
  *  __Node Manager__: per machine, responsible for containers, monitoring their resource usage (cpu, memory, disk, network) and reporting to RM.
### Special functionalities: 
  1. __Resource reservation__: reserve resource for predictable execution. 
  2. __Federation__: transparently wire together multiple YARN clusters. 
   
## Hadoop Commons: 
  common utilites to support other modules
## Hadoop Ozone: 
  Dsitributed key-value store, support both large and small file management (HDFS is limited when files are small). Relatively new, more reading in the future if necessary. 
  ### Characteristics:
    1. Support strict serializability.
      * Serializability: Result of interleaving is equivalent to sequential execution of transactions. 
      * Strict Serializability: if transaction A terminates before the start of B, then A must appear before B in the serialized order. 
    2. Support layered architecture, it separates the namespace management from block and node management layer, allowing users to independently scale on both axes.
   


  


# 4. Pig

# 5. Hive

# 6. Spark
## 6.1. Characterstics
* Has no built-in file system, can be used in conjunct with HDFS.
* Use RDD(Resilient Distributed Dataset)/Dataframes to represent data extracted from the file system. 
* Builds a DAG for operations nd their relations for optimization
* Compute in memory, don't write results to disk automatically
## 6.2. Supported Functionality
1. Support stream processing, can process real-time data 
2. Support interactive analysis with Spark shell
3. Support batch processing like Hadoop
4. Support Machine learning 
## 6.3. Advatanges
1. Very fast, computation completely in memory.Data can be in memory or from disk
2. Easy to use user interface
## 6.4. Disadvantages
1. Not very secure: security is by default turned off
2. Costly, require lots of RAM 


