# Lesson to be learned

## what to ask when clarification is needed
1. The range of any number appeared in the question, does it require long/int?
2. Can array be empty?
3. If given a range, can the lower bound be larger than the upper bound? 

## General
1. When initializing min/max values, what value should be picked to be the default? Integer.MAX_VALUE/MIN_VALUE or use 0? 
   * [LintCode 402 continuous subarray sum](https://www.lintcode.com/problem/continuous-subarray-sum/description)
2. When doing division, pay atttention to the divisor sign. Can it be positive, 0, negative? 
   * [Leetcode 523 continuoue subarray sum](https://leetcode.com/problems/continuous-subarray-sum)
3. When using while loop to loop tan array, always check if there's an index overflow in the while loop condition. 
4. Pay attention not to divide by 0 when divisor is a variable || length of range is 0
5. Using variable as array index, think about whether it can be negative. 

## Arrays
1. When using sliding window, pay attention to the condition of moving the left or right pointer
    *  Does 0 on both side affect the while loop condition? 
    *  can be used to find the sum within the window. 
    * Pay attention to the stop condition of the left and right pointer. Does it go over the range? How to get the subarray fulfilling the requirement? 
    * one pointer only moves once per loop, the other moves freely. This simplifies the code.  
2. Subarray sum: use subsum array
   * remember to initialize 0 at index 0 , the length of the subsum array should be original array.length + 1
   * the subarray represented by sum[a], sum[b] is in the range (a+1, b), pay attention if the final result needs to be left side inclusive. 
3. Scanning line algorithm: mix the start and end of ranges and sort them. Can be used when we need to know the maximum number of overlapping ranges. 
4. [Water Pool problem](https://www.lintcode.com/problem/trapping-rain-water): For each element, waterline is the minimum of maximum pool heights from all directions. Progressing from both directions of the array and have a poitner pointing to current max, the waterline must >= both of them for all elements in between. 
   * When need to know the extreme value from both directions, consider traverse the array from both directions and point to current extreme value.
5. When want to group elements by ranges of value, think of using `bucket`; when want elements within certain radius in the array, think of using sliding `window`. 

## Hashing
1. When storing the value directly cannot reduce the time complexity, think about storing other related values (mod value, frequency etc. )
2. Hashing is the best choice when O(1) lookup is required. Can add all elements to the set first, then start doing lookup. Delete while lookup can guarantee O(n) time complexity. 
   * [Lintcode 124 Longest consecutive sequence](https://www.lintcode.com/problem/longest-consecutive-sequence)
3. When looking up the frequency of characters in string, can use an int[] with the length of all possible characters. 
   * Need to ask the interviewer the type of the character: ASCII? 256 chars. Unicode: need to consider the size & character frequency of the source array. 

4. When using sidling window with hashing, remember to remove the leftmost character when moving the left pointer. 
## Dynamic Programming
   ### General Questions
   1. Pay attention to the border condition. Deal with them as special cases at the beginning of the function.
   2. Pay attention to what is the smaller problem to be solved. Sometimes it is not necessarily what has been executed, but can be what has not been executed.  
   
   ### Gaming/ 0 sum game
   1. Assume each step, both the opponent and the player will take the optimum strategy. 
   2. Think of what the remaining situation will be like after taking current step. The smaller problem to be solved is the remaining situation, what has not happened. 
      * Both players are equivalent, who has the upper hand at each step during the DP does not matter.
      * [Lintcode 395 Coins in a line II](https://www.lintcode.com/problem/coins-in-a-line-ii)

## Binary Search
Can be used to reduce linear time to O(logN).
1. When changing left/ right index, cannot set it directly to mid, need to be mid + 1/ mid - 1 for the loop to make progress. 
2. Can be used for search beyond finding the exact value. Essentially, everytime doing binary search is dividing a bigger problem into two smaller problems. Thought process is similar to recursion. 
   * [Lintcode 75 Finding peak element](https://www.lintcode.com/problem/find-peak-element/description)
3. Knowing the range of result, can also do binary search on the range to find the exact result. 

## Stack
1. Monotounously increasing stack: for each element in the stack, the first element less than current element is the one stored before current one in the stack. When  pop elements out of the stack to insert new element in, the first element less than popped element after its index is the one to be inserted. Verse versa for monotonously decreasing stack. 
   * Insert a -1 into mono-increasing stack to pop out all remaining elements. 
   * Can use Deque to get both the bottom and head of the stack. 
2. When popping/ peeking, always check size == 0
3. Implement iterator with stack, need to maintain a next variable for the iterator. 
4. Stack can be used to keep track of the current status as far as the current element. 
   

## Matrix
1. Different type of abstraction: 
   1. Transform 2D problem for the whole matrix into running 1D problem for each line. 
   2. Group related elements together as a unit and use union find
   3. Each rectangle is represented as one large rectangle minus 3 smaller rectangles. Can use prefix sum. 
   4. Rectangles are made up of single column rectangles, thus abstract each column into a single number and do 1D problem on the row. 
      * [Lintcode 510 maximal rectangle](https://www.lintcode.com/problem/maximal-rectangle)
      * [Lintcode 405 submatrix sum](https://www.lintcode.com/problem/submatrix-sum)
2. If change the matrix while looping might cause problem, can record the col/row that needs to be changed in sets. wait until the first round of looping ends, then start changing the matrix. 
   * can improve memory furthur by checking the first column and row, record their status, then use them as the record placer for the remainder of the matrix. 
   * [Lintcode 162 Set Matrix Zeros](https://www.lintcode.com/problem/set-matrix-zeroes/)
3. For row && col sorted matrix, do not limit thoughts to using binary search on each row/col. Sometimes not using binary search & simply moving in the matrix has better performance. 
   * To reach runtime O(m+n), with m is the length and n is the height of the matrix, every loop need to eliminate one row /one col. 
   * Less than, equal to, larger than current element means moving to 3 different diretions in the matrix. Key is to find the beginning position where there are 3 paths that satisfy the three different situations. `the corner of the matrix`.
   * [Lintcode 38 search 2D matrix II](https://www.lintcode.com/problem/search-a-2d-matrix-ii)

## Recursion
1. To avoid using recursion, consider using stack to mimize the recursion process. 
2. Think about what the smaller problem is like. Similar thought process as using DP. 

  