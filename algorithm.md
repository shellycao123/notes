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
4. 

## Arrays
1. When using sliding window, pay attention to the condition of moving the left or right pointer
    *  Does 0 on both side affect the while loop condition? 
    *  can be used t ofind the sum within the window. 
2. Subarray sum: use subsum array
   * remember to initialize 0 at index 0 , the length of the subsum array should be original array.length + 1
   * the subarray represented by sum[a], sum[b] is in the range (a+1, b), pay attention if the final result needs to be left side inclusive. 
3. Scanning line algorithm: mix the start and end of ranges and sort them. Can be used when we need to know the maximum number of overlapping ranges. 
4. [Water Pool problem](https://www.lintcode.com/problem/trapping-rain-water): For each element, waterline is the minimum of maximum pool heights from all directions. Progressing from both directions of the array and have a poitner pointing to current max, the waterline must >= both of them for all elements in between. 
   * When need to know the extreme value from both directions, consider traverse the array from both directions and point to current extreme value.
## Hashing
1. When storing the value directly cannot reduce the time complexity, think about storing other related values (mod value, frequency etc. )
2. Hashing is the best choice when O(1) lookup is required. Can add all elements to the set first, then start doing lookup. Delete while lookup can guarantee O(n) time complexity. 
   * [Lintcode 124 Longest consecutive sequence](https://www.lintcode.com/problem/longest-consecutive-sequence)

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
  