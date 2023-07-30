package lesson;

public class QuickFindDS implements DisjointSets {
    private int[] nums;

    public QuickFindDS(int N) {
        nums = new int[N];
        for (int i = 0; i < N; i++) {
            nums[i] = i;
        }
    }

    /***
     *     find nums[p] sets all positions
     *     then use nums[q] overwrite those positions
     */
    @Override
    public void connect(int p, int q) {
        int pid = nums[p];
        int qid = nums[q];
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == pid) {
                nums[i] = qid;
            }
        }
    }

    @Override
    public boolean isConnected(int p, int q) {
        return nums[p] == nums[q];
    }

    public static void main(String[] args) {
        QuickFindDS quickFindDS = new QuickFindDS(6);
        quickFindDS.connect(0, 1);
        quickFindDS.connect(0, 2);
        quickFindDS.connect(0, 3);
        System.out.println(quickFindDS.isConnected(2, 3));

    }
}
