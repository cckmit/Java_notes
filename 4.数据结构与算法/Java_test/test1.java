public class test1{
    public static void main(String[] args){
        int[] nums = {-2,0,3,-5,2,-1};
        // 存放前缀和：0 3 8
        int[] preSums = new int[nums.length];

        for(int i = 1; i<nums.length; i++){
            preSums[0] = 0;
            preSums[i] = preSums[i-1] + nums[i-1];
            System.out.println(preSums[i]);
        }
    }
}