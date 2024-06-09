public class Main {
    public static void main(String[] args) {
        // Example array and queries
        int[] arr = {2, 5, 1, 4, 9, 3};
        int[][] queries = {
                {0, 2},
                {1, 4},
                {3, 5}
        };

        Main main = new Main();

//        int[] result = main.minInRange(arr, queries);


//        for (int res : result) {
//            System.out.println(res);
//        }

    }

//    int[] minInRange(int[] arr, int[][] queries) {
//        int ans[] = new int[queries.length];
//        int ind = 0;
//        for (int[] row : queries) {
//            int x = row[0];
//            int y = row[1];
//            int mini = (int)(1e9);
//            for (int i = x; i <= y; i++) {
//                mini = Math.min(mini, arr[i]);
//            }
//            ans[ind++] = mini;
//        }
//        return ans;
//    }
}
//class SegmentTree{
//    int[] seg;
//    SegmentTree(int n){
//        seg=new int[4*n];
//    }
//    void build(int ind,int low,int high,int arr[]){
//        if(high==low){
//            seg[ind]=arr[low];
//            return;
//        }
//        int mid=(low+high)>>1;
//        build(2*ind+1,low,mid,arr);
//        build(2*ind+2,mid+1,high,arr);
//        seg[ind]=Math.min(seg[2*ind+1],seg[2*ind+2]);
//    }
//    int query(int ind,int low,int high,int l,int r){
//        if(r<low ||high<l)return Integer.MAX_VALUE;
//        if(low>=l && high<=r)return seg[ind];
//        int mid=(low+high)>>1;
//        int left=query(2*ind+1,low,mid,l,r);
//        int right=query(2*ind+2,mid+1,high,l,r);
//        return Math.min(left,right);
//    }
//    void update(int ind,int low,int high,int idx,int val){
//        if(low==high){
//            seg[ind]=val;
//            return;
//        }
//        int mid=(low+high)>>1;
//        if(mid>=idx){
//            update(2*ind+1,low,mid,idx,val);
//        }else{
//            update(2*ind+2,mid+1,high,idx,val);
//        }
//        seg[ind]=Math.min(seg[2*ind+1],seg[2*ind+2]);
//    }
//}

