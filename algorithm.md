# Lesson to be learned

## what to ask when clarification is needed
1. The range of any number appeared in the question, does it require long/int?
2. Can array be empty?
3. If given a range, can the lower bound be larger than the upper bound? 
4. Ask whether the input will always be not null && valid? 
5. Involving the product/sum of many int, ask whether the result will overflow?
6. Involving product, whether the inputs can be negative? (Thus two negaative int will get positive result and change the algo)
7. When converting Math.abs(Integer.MIN_VALUE), will result in overflow. 
8. Asking whether values in a set can have duplicates. 

## General
1. When initializing min/max values, what value should be picked to be the default? Integer.MAX_VALUE/MIN_VALUE or use 0? 
   * [LintCode 402 continuous subarray sum](https://www.lintcode.com/problem/continuous-subarray-sum/description)
2. When doing division, pay atttention to the divisor sign. Can it be positive, 0, negative? 
   * [Leetcode 523 continuoue subarray sum](https://leetcode.com/problems/continuous-subarray-sum)
3. When using while loop to loop tan array, always check if there's an index overflow in the while loop condition. 
4. Pay attention not to divide by 0 when divisor is a variable || length of range is 0
5. Using variable as array index, think about whether it can be negative. 
6. Find the medium of numbers is essentially the same as finding the top K of the numbers. 
   * [Lintcode 65 Find the median of two sorted arrays](https://www.lintcode.com/problem/median-of-two-sorted-arrays)
7. Think about how to transform questions into solution-known, easier questions.
   * [Insert interval](https://www.lintcode.com/problem/insert-interval) is the same as [Merge Interval]() if you insert the interval without considering the merge.  
8. What does a well-formed string generated from parenthesis mean? For each substring [0, i), i <= length, number of left parenthesis >= right parenthesis. 
   * [Generate Parenthesis](https://www.lintcode.com/problem/generate-parentheses)
9. Pay attention to the description language used. kth Largest element in an array is different from kth element in an array. 

## Data Structure
### Arrays
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
   * For Matrix version, after one cell is filled with water, it becomes a new border element with the height of the water level. [Matrix version](https://www.lintcode.com/problem/trapping-rain-water-ii/description)
5. When want to group elements by ranges of value, think of using **bucket**; when want elements within certain radius in the array, think of using sliding **window**. 
6. Circular array, loop the non-circular array twice will be able to cover all elements.
   * [Leetcode 503](https://leetcode.com/problems/next-greater-element-ii/)
7. When need to amerge two arrays in place && knowing the size of the final array, considering starting from the end of the final array 

### Hashing
1. When storing the value directly cannot reduce the time complexity, think about storing other related values (mod value, frequency etc. )
2. Hashing is the best choice when O(1) lookup is required. Can add all elements to the set first, then start doing lookup. Delete while lookup can guarantee O(n) time complexity. 
   * [Lintcode 124 Longest consecutive sequence](https://www.lintcode.com/problem/longest-consecutive-sequence)
3. When looking up the frequency of characters in string, can use an int[] with the length of all possible characters. 
   * Need to ask the interviewer the type of the character: ASCII? 256 chars. Unicode: need to consider the size & character frequency of the source array. 

4. When using sidling window with hashing, remember to remove the leftmost character when moving the left pointer. 
### Stack
1. Monotonicly increasing stack: for each element in the stack, the first element less than current element is the one stored before current one in the stack. When  pop elements out of the stack to insert new element in, the first element less than popped element after its index is the one to be inserted. Verse versa for monotonously decreasing stack. 
   * Insert a -1 into mono-increasing stack to pop out all remaining elements. 
   * Can use Deque to get both the bottom and head of the stack. 
2. When popping/ peeking, always check size == 0
3. Implement iterator with stack, need to maintain a next variable for the iterator. 
4. Stack can be used to keep track of the current status as far as the insertion of current element. When deleting, pop from the status stack.
5. When need to find the **next larger/smaller element**, the first thing to consider is to use monotonic stack. 
6. 嵌套类问题，alway push each element to the stack, then keeps popping from the stack. 

### Heap
1. When using two heaps to keep track of the median of numbers, pay attention to the balance condition. If want to peek from the bottom heap when retrieving the median, set the balance condition to bottom.size() >= top.size(), aka the condition in while loop should be (bottom.size > top.size() + 1) && (top.size() > bottom.size())

### Tree
1. Tree is essentially a linked list tree. Can change the right pointer to point to the next node for O(1) retrieval of the next node. Can be used to implement O(1) iterator. 
2. Morris Traversal: transform a binary tree to a linkedlist, then traverse the linkedlist for the inorder traversal of the tree. O(1) space traversal. 
3. Deque can be used to traverse binary tree iteratively. 
  - BFS traversal: Need to remember the size of current level nodes, and add their children to the deque.
  - DFS traversal: use stack
4. Preorder vs Inorder vs Postorder
   - first element of preorder traversal is the root of the tree
   - last element of postoder traversal is the root of the tree. 
   - root split inorder list into left side sublist and right side sublist

### Trie
1. A single node does not represent any character. Instead, its position in its parent's chidren array represent the character. If a character exists, then the corresponding position in current root's chidren array is not null. 

### LinkedList
1. When deleting from the list, always think about whether the head might be deleted and need to return the next node of deleted node.
2. When accessing node.next, always check whether node is null. 
3. When the head of a list might change due to delete, can use recursion to deal with the chain in segments and return the head of each segment. Can also add a dummy node to the beginning of the list if applicable to the questions. 


## Algorithms
### Dynamic Programming
   #### General Questions
   1. Pay attention to the border condition. Deal with them as special cases at the beginning of the function.
   2. Pay attention to what is the smaller problem to be solved. Sometimes it is not necessarily what has been executed, but can be what has not been executed.  
   
   #### Gaming/ 0 sum game
   1. Assume each step, both the opponent and the player will take the optimum strategy. 
   2. Think of what the remaining situation will be like after taking current step. The smaller problem to be solved is the remaining situation, what has not happened. 
      * Both players are equivalent, who has the upper hand at each step during the DP does not matter.
      * [Lintcode 395 Coins in a line II](https://www.lintcode.com/problem/coins-in-a-line-ii)
  
   #### Backpack problems
   In this type of question, our result is limited to a upper/lower boundary. DP on the size of the backpack and the length of what needs to be put inside the backpack. 
   1. remember to set the dp matrix size to backpack size + 1, so that the situation of size == 0 can be represented in the dp matrix. If dp matrix indices are used to traverse input array only, no need to set its size to length + 1.
   
   ### Interval dependent
   In this type of question, the result of larger interval depends on the results of smaller intervals. DP on all possible sub intervals.
   * Pay attention about the boundarys & whether to split into one empty interval and a full-length interval 
   * Pay attention to in which direction the DP should move
   * (Lintcode stone game)[https://www.lintcode.com/problem/stone-game/]
  
   ### Decode questions
   1. When decoding from the need to consider all the situations. What are the illegal cases, when two digits can be converted to one letter? When only one digit can be converted to letter? 
   *  (Lintcode Decode ways)[https://www.lintcode.com/problem/decode-ways]
### Binary Search
Can be used to reduce linear time to O(logN).
1. When changing left/ right index, cannot set it directly to mid, need to be mid + 1/ mid - 1 for the loop to make progress. 
2. Can be used for search beyond finding the exact value. Essentially, everytime doing binary search is dividing a bigger problem into two smaller problems. Thought process is similar to recursion. 
   * [Lintcode 75 Finding peak element](https://www.lintcode.com/problem/find-peak-element/description)
3. Knowing the range of result, can also do binary search on the range to find the exact result. 
   * [Lintcode 437 copy books](https://www.lintcode.com/problem/copy-books)
4. Find Kth can do binary search on the range of all possible values. In every round, check if the mid of the range fulfills the return requirement, then move left & right correspondingly 
5. Binary search algo remains unchanged when there're repeats in the array. 
   * [Leetcode find kth smallest pair distance](https://leetcode.com/problems/find-k-th-smallest-pair-distance/discuss/109075/Java-solution-Binary-Search)
6. If the element is not present in the array, binary search always merges to the largest element that is smaller than the target value 
* General Binary seach template(both when the exact value exists or not) 
  ```
   while (left < right){
      mid = (left + right) / 2;
      if(mid == target){
         return mid;
      }
      if(mid > target){
         right = mid - 1;
      }
      else{
         left = mid + 1;
      }
      return Math.min(left, right); //depends on what you want to return when the exact value is not found, can return min or max of (left,right)
  }
  ```


### Matrix
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
   * Less than, equal to, larger than current element means moving to 3 different diretions in the matrix. Key is to find the beginning position where there are 3 paths that satisfy the three different situations. **the corner of the matrix**.
   * [Lintcode 38 search 2D matrix II](https://www.lintcode.com/problem/search-a-2d-matrix-ii)
4. To traverse the four neighbors of an element, can use displacement vector dx= {-1,1,0,0}, dy={0,0,1,-1}
5. The size of the max square with a point as its bottom-right corner can be calculcated from the upper, left, upper-left point.
   * [Lintcode 436](https://www.lintcode.com/problem/maximal-square)

### Recursion
1. To avoid using recursion, consider using stack or deque to mimize the recursion process. 
2. Think about what the smaller problem is like. Similar thought process as using DP.
3. When need to pass on both int and boolean in recursion, think about use -1 or any special value to represent false

### DFS/BFS
   #### When to use DFS/ BFS? 
   1. When need to find the min/max depth, use BFS.
   2. When need to find all the pathes to the deepest level, use DFS.
   3. If need to find the pathes with min/max depth, use BFS to find the depth & remember the possible parents/children first, then use DFS to find the exact path. 
   * If need to find the min/max path from a specific leaf to the root, can record the parent of all nodes during BFS, then use DFS from the leaf to find the path. 
   #### DFS
   1. When using stack to do DFS to avoid recursion, pay attention not to add the right children multiple times. Use HashSet to check if a node has been added, or design the traversal to avoid multiple addition. 
      * [Kth smallest element in BST](https://www.lintcode.com/problem/kth-smallest-element-in-a-bst/)
   #### BFS
   1. Use Deque for BFS, HashMap to remember the path. 

### Sorting
1. Patience sorting: essentially divide the array into multiple sorted piles, then merge all piles. 
   1. Initially, there are no piles. The first card dealt forms a new pile consisting of the single card.
   2. Each subsequent card is placed on the leftmost existing pile whose top card has a value greater than or equal to the new card's value, or to the right of all of the existing piles, thus forming a new pile.
   3. When there are no more cards remaining to deal, recover the total sorted array by repeatedly picking off the minimum visible card.
   * Top of the piles are alyaws sorted from left to right. 
   * Famously used to reduce the runtime of finding the longest increasing subsequence. 
   * [Leetcode 300. Longest Increasing Subsequence](https://leetcode.com/problems/longest-increasing-subsequence/)
     * In this question, eahc pile represents all the cards that are equivalent to using the top of the pile while forming the LIS. Thus each pile should be decreasing from top to bottom. Also therefore the number of piles is the length of LIS. 
2. Quicksort template
   ```
      int start = l;
      int end = r; 
      int p_i = getPivot(nums, l , r); 
      swap(p_i, r, nums);
      int pivot = nums[r];
      while(l < r){
         while(l < r && nums[l] <= pivot){
               l++;
         }
         nums[r] = nums[l]; 
         while(l < r && nums[r] > pivot){
               r--;
         }
         nums[l] = nums[r]; 
      }
      nums[l] = pivot;
      recSort(start, l - 1);
      recSort(l + 1, end);
      
   ```
   Quicksort's average runtime to find the kth element is O(n). 
   3. bucket sort: pay attention to avoid 0 bucket size, avoid max == min situation, how to decide bucket count(remember to + 1)

## Special mathemtical property related
1. Permumation: the smallest permutation is increasing array, largest is decreasing array. 
   * (Lintcode find next permutation)[https://www.lintcode.com/problem/next-permutation/]
   *  Find all permutations without recursion: use DP thinking method. find the permutation of array [0, i), insert the ith element to all the previous permutations. 
2. Combination: To find unique combination of a set, set the max/min value unchanged, reduce its value from the target, then recursively find the remaining target without using the max/min element. 
 

  