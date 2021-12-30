//给你一个整数数组 nums 和一个整数 k ，请你统计并返回该数组中和为 k 的连续子数组的个数。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,1,1], k = 2
//输出：2
// 
//
// 示例 2： 
//
// 
//输入：nums = [1,2,3], k = 3
//输出：2
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 2 * 104 
// -1000 <= nums[i] <= 1000 
// -107 <= k <= 107 
// 
// Related Topics 数组 哈希表 前缀和 
// 👍 1233 👎 0


import java.util.HashMap;
import java.util.Map;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int subarraySum(int[] nums, int k) {
        int n = nums.length;

        Map<Integer, Integer> preSum = new HashMap<>();
        preSum.put(0,1);

        int count = 0,sum = 0;
        for(int i = 0; i < n; n++){
            sum += nums[i];
            int sum_j = sum - k;
            if (preSum.containsKey(sum_j)){
                count += preSum.get(sum_j);
            }
            preSum.put(sum, preSum.getOrDefault(sum,0) + 1);
        }
        return count;

    }
}
//leetcode submit region end(Prohibit modification and deletion)
