## Lesson 4
1. Kilobyte -> Megabyte -> Gigabyte -> Terabyte -> Petabyte ->Exabyte -> Zettabyte -> Yottabyte
2. Active archive: store all the raw data. Before Hadoop, need to send raw data to offsite archive for long term storage. Now all raw data are available on demand. 
3. Big data: the size of data becomes part of the problem. Traditional methods of working with data stops working. 
4. prefer collection of multiple hard drives for parallel reading. 

## Lesson 5 
1. HDFS: block 128 mb, stored on datanodes. 
   - namenode is the master node. Can have standby namenodes for high availability. 

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
