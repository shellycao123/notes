# Note from Processing Big Data Fall 20
- [Note from Processing Big Data Fall 20](#note-from-processing-big-data-fall-20)
  - [Lesson 4](#lesson-4)
  - [HDFS](#hdfs)
  - [Lesson 6](#lesson-6)
  - [Lesson 8](#lesson-8)
  - [Distributed File System](#distributed-file-system)
  - [Relational database(RDBMS)](#relational-databaserdbms)
  - [Hive](#hive)
  - [Impala](#impala)
  - [NoSQL database](#nosql-database)
  - [HBase:](#hbase)
  - [Sqoop](#sqoop)
  - [Oozie](#oozie)
  - [Flume](#flume)
  - [Spark](#spark)
  - [Scala](#scala)
  - [Spark SQL](#spark-sql)
  - [spark streaming](#spark-streaming)
  - [zookeeper](#zookeeper)
  - [MapReduce 1](#mapreduce-1)
  - [MapReduce 2](#mapreduce-2)
## Lesson 4
1. Kilobyte -> Megabyte -> Gigabyte -> Terabyte -> Petabyte ->Exabyte -> Zettabyte -> Yottabyte
2. Active archive: store all the raw data. Before Hadoop, need to send raw data to offsite archive for long term storage. Now all raw data are available on demand. 
3. Big data: the size of data becomes part of the problem. Traditional methods of working with data stops working. 
4. prefer collection of multiple hard drives for parallel reading. 

## HDFS
1. HDFS: block 128 mb, stored on datanodes. 
   - namenode is the master node. Can have standby namenodes for high availability. 
   - datanode: actual storage
   - can process any type of data( structures, semi structured, unstructured)
   - can't have multiple writers at the same time. 
   - no random seek/write

## Lesson 6
1. Speculative Excetution: resolve hanging tasks. 
   -  decides # of map task( default one task per input split), input split is one block by default(GFS chunk). 
   -  Hadoop make a guess on the number of reducers needed. 
   -  backup task: exact copy of the original task
   -  Run original & backup the same time, hope one of them will be finished fasted and can be resolved as the final answer of the task. 
   -  solve hanging of tasks.
   -  there can be tasks from different jobs running the same time on a node. 
   -  one job's mapper and reducer cannot run at the same time.


## Lesson 8
1. data-local: data is colocated with the program. 
2. rack-local: data is located on the same rack as the server. used when data-local is not available. data goes thru intra-rack transfer.  Need to configure the location of the racks to be utilized.
3. Least favorate is inter-rack transfer of data. Uses public network, can be crowded & slow. copy of the data goes to the memory of the JVM, it's not a permanent copy. 
4. distance between blocks: distance is 0 if in the same server, distance is 2 if on the same rack but on different server, 4 if in the same datacenter but different racks, 8 if in different data center. count the edges. 


## Distributed File System
1. NFS: one of the oldest systems. 
2. Pig: produced by Yahoo, provide simple language for queires and data manipulation with PIG LATIN, data flow programming language.  
   - suitable for batch processing data. describes a DAG. edges are data flows, nodes are operators for processing data. 
   - grunt: shell for for interactive running pig commands.
   - don't support random write/updates. 
   - no schema as in SQL DB 

## Relational database(RDBMS)
1. Why to use: 
   - enforce referential integrity
   - manage concurrent access to data
   - updating data
2. Downside: less flexibility because of the limitation of schema. not adaptable to changing 

## Hive
1. Data warehouseing framework built by facebook. used for query and analyzing large datasets stored in HDFS. 
2. Query language called HiveQL, similar to SQL. 
3. Not a relational database, cannot be used for online transaction processing, cannot perform real time updates or queries. Has large latency. 
4. enforce schema on read, data size is PT. easily scalable, not a database. can havedifferent schemas on different reads. 
5. metadata in RDBMS fortmat, compile query into MapReduce job.
6. create Hive partition: everytime issuing a query, Hive will read the whole dataset. can partition by date & datacenter and can reference the partition as if it is the field. 
7. multiple user can query simutaneously. 
8. compile into mapreduce jobs. 

## Impala
1. sits on HDFS, can read Hadoop file formant
2. run on the same node as Hadoop process. 
3. High performance, written in C++
4. own execution engine, no mapreduce, an open source masively parallel processing SQL engine. 
5. not fault tolerant, need to restart query after it fails. 
6. enable issuing low latency SQL queries. 
7. designed for high concurrency
8. doesn't support user defined functions, support simple query, doesn't support complext types. 

## NoSQL database
1. Data normalization: remove duplicated data with foreign keys. 
2. types: 
   - Key value
   - column based:
   - graph
   - document

## HBase: 
1. built on HDFS, similar to Google's bigtable. 
2. column oriented
3. suited for sparse data sets (many null values)
4.  can do random read & writes. writes are implemented with versions of cells. 
5. can define time-to-live for the row. 
6. __structure__: 
   - Master nodes: manage regionservers. one running at a time, manage operations but not part of the read/ write path. 
   - regionservers: sit on worker nodes. many can run at the same time. perform reads, buffer writes, region(subset of all rows) are assigned to regionservers. 
     - region: sharding of the database. define y start row, exclusive of ending row key. initial one region, sharding threshold is configurable. 
7. Zookeeper: distributed coordination service to maintain server state in the cluster. 
   - active Hmaster sends heartbeat to zookeeper. 
   - naming, conifgurtion, synchronization, group service
   - can be used for other distributed systems. 
8. Data is stored in HFile, a key-value pair stored in HDFS
   - when data is added, it is written to a log called write ahead log and then is stored in the memory, Memstore(key-value pair). both in region server. when data stored in memstore reaches a threshold, it will be flushed to the disk and new HFile is created. 
   - HFiles are immutable. 
9. Minor compaction: compact small HFiles into fewer larger files. 
10. major compaction: heavy weight, rewrite all files within a column family into a single new file, deletes any tombstoned enries and expired data. 
11. reads: asisted by block cache, LRU or eviction. 


## Sqoop
1. move data between HDFS and external database.
2. create mapreduce job, default 4 mappers. each mapper reads a portion of the table, writes to each own HDFS file, but all under the same directory. 

## Oozie
1. execute & monitor workflows in Hadoop
2. periodic scheduling of workflows
3. trigger execution by data availability
4. command line interface and web console. 
5. prevent steps from running until previous steps are completed. allow steps interleaving and run automatically as scripted. workflow captured in XML file. 
6. Type of jobs: 
   - workflow jobs : DAGS with specified sequence of action to be executed. 
   - coordinator jobs: workflow jobs triggered by time and data availability.
   - bundles: a package of multiple coordinator and workflow jobs. 


## Flume
1. originally for log files, now for streaming data. 
2. topology consist of agents. 
3. agents: 
   - source nodes: consume events received from external source and store them into one or more channels, tell the node where to receive the data from. 
   - sink nodes: remove events from channel, push an event into an external repo(HDFS), optionally forwards an event to the Flume Source of the next Flume agent in the flow, tells the node where to send the data to. 
   - channel that link source & sink nodes:: passive store that buffer events until it is consumed by a Flume Sink. can be in memory or durable. 
4. different levels of delivery reliability. Best effort/ end-to-end guarantee delivery even if multiple Flume nodes fail between the data source and HDFS. 

## Spark
1. alternative to MapReduce. 
2. store data in memory, written in Scala, fast processing engine. can run with or without YARN. 
3. support batch processing, streaming,ML
4. prefer Scala, can support Python, java
5. parallelizes programs but not in map reduce. 
6. Flexibility: can pass the results of a map step directl to the user-specified next step in the workflow, does not have to have reduce. can do iterations of operation. 
7. expressiveness: rich set of transformations, can create complext processing workflows. 
8. in-memory processing: can materialize data in memory at any point in the workflow, instead of write the result to the disk. 
9. executors: worker nodes processes running individual tasks in a given spark job. 
10. fault tolerance: auto dels with failed or slow machines by re-executing the tasks. 
11. runs on top of JVM, can use tools built for JVM stack. 
12. REPL: read/evaluate/print loop. every spark app needs a sparkcontext. provides a preconfigured context sc. 
13. RDD: resilient distributed dataset, 
    -resilient: if data in memory is lost, it can be recreated. distributed collection of memory with permission. if any partition is lost, cna be rgenerated by applying te same tranformation operation on the lost partition in lineage. 
    distributed: processed across the cluster
    datasetL pulled from a file or created programmatically. fundamental unit of data in spark,each HDFS bloc is a partition, each partition form an RDD. 
    - Spark will run only with a lot memory. 
    - in memory data processing kept in memory, any lost partition can be rolled back, data in an RDD is immutable. 
    - created from files, memory data, another RDD.
    - lazy evaluation: transformations are applied to the data in RDD, but the output is not generated, but hte applied transforations are logged. Processed when user request for that data. 
    - coarse-grained operations: use can apply trnasformation to all elements in data sets thru map, filter or group by operations. 
    - types of operations: 
      - transformations: filter, access, and modify the data in parent RDD to generate a successive RDD. new RDD returns a pointer to the parent RDD to ensure dependency. lazy execution limit and optimize processing. 
        - narrow transformations: input data is required from one RDD only. map, filter, flatmap, partition, mappartitions
        - wide transformations: multiple RDD are needed for the transformation. reduceby, union. 
      - action: return a value or save to external storage. eager execution. take, count, collect, saveAstextfile first. 
      - lineage: a list of transformations to get back to after memory is enough to run the spark job. happen after data is evited from the memory. toDebugString, show where the RDD is from.
      - transformations can be chained. 
      - data generated fro each RDD is not auto cached, invoking an action will cause the transformations to be reexecuted. 
      - can't chain transformations after actions. since actions output values, but transformations are done on RDD.
14. Spark can choose to perform transformations by row. don't need to process on every row, like mapreduce.
15. use parallelize to create RDD from memory. filebased use sc.textFile, maps each line to its own RDD element, only works with newline separated text files. sc.wholeTextFiles map entire content of all files in a directory to a single RDD element, only work for small files fit into memory, can be used fro json, xml etc. 
16. Spark SQL: structured data processing. 


## Scala
1. anonymous variable: use underscore. 


## Spark SQL
1. allow querying structured data as a distributed dataset.    
2. dataframe API: working with data as tables. A SQL engine and command line interface.
3. RDD: core spark objects, don't have a senses of columns or structure. data frame is strcutured, more auto optimization, but don't support complie time type saftey. 
4. dataset: similar to dataframes, can be wrapped in explicit type and structure, schema can be upfront at compile time. type related errors will be detected when you build your script instead of while running. type safe and object oriented. 
5. data frame queries must return new dataframe. 
6. Spark SQL is built on Spard, a general purposed processing engin, impala is a specialized SQL engin. 

## spark streaming
1. streaming input -> processing -> output
2. challenges: unbounded memory limit, horizontal scaling, lookbacks, optimization. 
3. real time processing, marshalling in real time, making decision where to direct the output based on real time analytics, threshold checked and alerts generated, leaderboard maintained in real time. prediction. 
4. using streaming context. new API based on data set and data frames, not micro-batch set RDD. Dstream, infact continuous RDDs
5. transformations & output operations. 


## zookeeper
1. coordinate between nodes. keep track of info that need to be in sync across the cluster. can be used to recover from partial failure. share info across different hadoop services. 
2. bundled with HBase and many other Hadoop tools. difficult to setup. 
3. auto elect a master node. can recover from client master, worker, network errors. 
4. persistent node: znode remains until the explcity deleted.
5. ephemeral node: go away if the client created it crashes or loses its connection to the zookeeper. 
6. loose coupling, participants dont need to know each other in a static way. 
7. active distributed data structure. 
8. znodes form hierarchical tree of nodes, similar to a file system. 
9. atomic data access

## MapReduce 1
1. Job tracker: resource manager + app master, single point of failure. be notified of task failure through tasktracker heartbeat message, reschedules the task
2. task tracker: == node manager
3. runtime failre: task failure(reschedule task), task tracker failure(if job not complete, all completed tasks are rerun. all in progress tasks are rescheduled), job tracker failure(resubmit the job)

## MapReduce 2
1. difference in YARN & application master
2. job tracker was a single point of failure & can't be scaled, now is broken down into resource manager and appliation master.New application master for each new job, run on worker node, launched by resource manager. 
3. job submission:
   - submit() creates new JobSubmitter, get new app ID from RM, compute input split,calls, submitJob() on RM
   - errors: no output dir, no input dir, output dir exists
4. In task assignment, AM can decide to uberize the tasks(run tasks serial in AM's JVM) since the tasks are too small and parallelization overhead is too big. requests for mapper containers, after 5% of mappers done, request reducer containers.  
5. failures:
   - task failure: AM resschedules task
   - app master failure: already run job don't need to be rerun. 
   - node manager failure: AM & tasks run on the manager is recovered. completed task on the node manager rerun( the datanode it manages ight also have failed), any in-progress reschedule
   - resource manager: HA mode of YARN.
6. YARN: resource management in hadoop 2, separate processing from resource management.
   1. can run workflows other than MapReduce
   2. resource manager: 
   3. application master: request resources from resource manager, then works with containers provided by node manager. not run as a trusted service. manages the application life cycle, faults, provided metrics to Resource Manager. send heartbeat to RM. 
   4. node manager: takes instructions from the application amanger, provide containers(JVM) to application manager. manages the containers, gives container leases to app. send heartbea to RM
   5.  can run resource manager in HA(high availability mode)
   