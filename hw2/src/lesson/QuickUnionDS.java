package lesson;

public class QuickUnionDS implements DisjointSets {

    private int[] partent;

    public QuickUnionDS(int num) {
        partent = new int[num];
        for (int i = 0; i < num; i++) {
            partent[i] = -1;
        }
    }

    public int find(int p) {
        while (partent[p] >= 0) {
            p = partent[p];
        }
        return p;
    }

    @Override
    public void connect(int p, int q) {
        int i = find(p);
        int j = find(q);
        partent[i] = j;
    }

    @Override
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }

    public static void main(String[] args) {
        QuickUnionDS quickUnionDS = new QuickUnionDS(7);
        System.out.println(quickUnionDS.find(0));
        quickUnionDS.connect(0, 1);
        quickUnionDS.connect(0, 2);
        quickUnionDS.connect(0, 4);
        quickUnionDS.connect(3, 5);
        quickUnionDS.connect(5, 2);


    }
}
