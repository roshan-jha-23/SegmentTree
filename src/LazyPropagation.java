//import java.util.Arrays;
//import java.util.Random;
//import java.util.Scanner;
//
//public class LazyPropagation {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        Random random = new Random();
//
//        // Define the array length and generate random values for the array
//        int n = 3;
//        int[] arr = new int[n];
//        for (int i = 0; i < n; i++) {
//            arr[i] = random.nextInt(1000) + 1; // Values between 1 and 1000
//        }
//        System.out.println(Arrays.toString(arr));
//
//
//        // Initialize the segment tree
////        SGT st = new SGT(n);
////        st.build(0, 0, n - 1, arr);
////
////        // Number of queries
////        int numQueries = 100;
////
////        // Process random queries
////        for (int i = 0; i < numQueries; i++) {
////            int queryType = random.nextInt(2) +1; // 1 for sum query, 2 for update
////            int l = random.nextInt(n); // Starting index of the range
////            int r = random.nextInt(n); // Ending index of the range
////            if (l > r) {
////                int temp = l;
////                l = r;
////                r = temp;
////            }
////
////            if (queryType == 1) {
////                // Sum query
////                int sum = st.query(0, 0, n - 1, l, r);
////                System.out.println("Sum of range [" + l + ", " + r + "]: " + sum);
////            } else if (queryType == 2) {
////                // Update query
////                int val = random.nextInt(1000) + 1; // Update value between 1 and 1000
////                st.update(0, 0, n - 1, l, r, val);
////                System.out.println("Updated range [" + l + ", " + r + "] by " + val);
////            }
////        }
////
////        scanner.close();
////        SGT st = new SGT(n);
////        st.build(0, 0, n - 1, arr);
////
////        // Number of queries
////        int numQueries = 100;
////
////        // Process random queries
////        for (int i = 0; i < numQueries; i++) {
////            int queryType = random.nextInt(2) + 1; // 1 for min query, 2 for update
////            int l = random.nextInt(n); // Starting index of the range
////            int r = random.nextInt(n); // Ending index of the range
////            if (l > r) {
////                int temp = l;
////                l = r;
////                r = temp;
////            }
////
////            if (queryType == 1) {
////                // Min query
////                int minVal = st.query(0, 0, n - 1, l, r);
////                System.out.println("Minimum of range [" + l + ", " + r + "]: " + minVal);
////            } else if (queryType == 2) {
////                // Update query
////                int val = random.nextInt(1000) + 1; // Update value between 1 and 1000
////                st.update(0, 0, n - 1, l, r, val);
////                System.out.println("Updated range [" + l + ", " + r + "] by " + val);
////            }
////        }
////
////        scanner.close();
//    }
//}
//
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class LazyPropagation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // Define the array length and generate random values for the array
        int n = 4;
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt(2); // Values between 0 (tail) and 1 (head)
        }
        System.out.println(Arrays.toString(arr));

        // Initialize the segment tree
        SGT st = new SGT(n);
        st.build(0, 0, n - 1, arr);

        // Number of queries
        int numQueries = 100;

        // Process random queries
        for (int i = 0; i < numQueries; i++) {
            int queryType = random.nextInt(2) + 1; // 1 for count heads query, 2 for flip
            int l = random.nextInt(n); // Starting index of the range
            int r = random.nextInt(n); // Ending index of the range
            if (l > r) {
                int temp = l;
                l = r;
                r = temp;
            }

            if (queryType == 1) {
                // Count heads query
                int headCount = st.query(0, 0, n - 1, l, r);
                System.out.println("Number of heads in range [" + l + ", " + r + "]: " + headCount);
            } else if (queryType == 2) {
                // Flip heads to tails and tails to heads
                st.update(0, 0, n - 1, l, r);
                System.out.println("Flipped heads to tails and tails to heads in range [" + l + ", " + r + "]");
            }
        }

        scanner.close();
    }
}

class SGT {
    int[] seg;
    boolean[] lazy;

    SGT(int n) {
        seg = new int[4 * n];
        lazy = new boolean[4 * n];
    }

    public void build(int ind, int low, int high, int[] arr) {
        if (low == high) {
            seg[ind] = arr[low];
            return;
        }
        int mid = (low + high) >> 1;
        build(2 * ind + 1, low, mid, arr);
        build(2 * ind + 2, mid + 1, high, arr);
        seg[ind] = seg[2 * ind + 1] + seg[2 * ind + 2];
    }

    public void update(int ind, int low, int high, int l, int r) {
        if (lazy[ind]) {
            seg[ind] = (high - low + 1) - seg[ind];
            if (low != high) {
                lazy[2 * ind + 1] = !lazy[2 * ind + 1];
                lazy[2 * ind + 2] = !lazy[2 * ind + 2];
            }
            lazy[ind] = false;
        }

        if (low > r || high < l) return;

        if (low >= l && high <= r) {
            seg[ind] = (high - low + 1) - seg[ind];
            if (low != high) {
                lazy[2 * ind + 1] = !lazy[2 * ind + 1];
                lazy[2 * ind + 2] = !lazy[2 * ind + 2];
            }
            return;
        }

        int mid = (low + high) >> 1;
        update(2 * ind + 1, low, mid, l, r);
        update(2 * ind + 2, mid + 1, high, l, r);
        seg[ind] = seg[2 * ind + 1] + seg[2 * ind + 2];
    }

    public int query(int ind, int low, int high, int l, int r) {
        if (lazy[ind]) {
            seg[ind] = (high - low + 1) - seg[ind];
            if (low != high) {
                lazy[2 * ind + 1] = !lazy[2 * ind + 1];
                lazy[2 * ind + 2] = !lazy[2 * ind + 2];
            }
            lazy[ind] = false;
        }

        if (low > r || high < l) return 0;
        if (low >= l && high <= r) {
            return seg[ind];
        }

        int mid = (low + high) >> 1;
        int left = query(2 * ind + 1, low, mid, l, r);
        int right = query(2 * ind + 2, mid + 1, high, l, r);
        return left + right;
    }
}

//class SGT {
//    int[] seg;
//    int[] lazy;
//
//    SGT(int n) {
//        seg = new int[4 * n];
//        lazy = new int[4 * n];
//    }
//
//    public void build(int ind, int low, int high, int[] arr) {
//        if (low == high) {
//            seg[ind] = arr[low];
//            return;
//        }
//        int mid = (low + high) >> 1;
//        build(2 * ind + 1, low, mid, arr);
//        build(2 * ind + 2, mid + 1, high, arr);
//        seg[ind] = Math.min(seg[2 * ind + 1] ,seg[2 * ind + 2]);
//    }
//
//    public void update(int ind, int low, int high, int l, int r, int val) {
//        //first update the lazy one if remaining
//        if (lazy[ind] != 0) {
//            seg[ind] +=  lazy[ind];
//            //if not leaf then update
//            if (low != high) {
//                lazy[2 * ind + 1] += lazy[ind];
//                lazy[2 * ind + 2] += lazy[ind];
//            }
//            lazy[ind] = 0;
//        }
//
//        if (low > r || high < l) return;
//
//        if (low >= l && high <= r) {
//            seg[ind] +=  val;
//            if (low != high) {
//                lazy[2 * ind + 1] += val;
//                lazy[2 * ind + 2] += val;
//            }
//            return;
//        }
//
//        int mid = (low + high) >> 1;
//        update(2 * ind + 1, low, mid, l, r, val);
//        update(2 * ind + 2, mid + 1, high, l, r, val);
//        seg[ind] = Math.min(seg[2 * ind + 1] ,seg[2 * ind + 2]);
//    }
//
//    public int query(int ind, int low, int high, int l, int r) {
//        if (lazy[ind] != 0) {
//            seg[ind] +=  lazy[ind];
//            if (low != high) {
//                lazy[2 * ind + 1] += lazy[ind];
//                lazy[2 * ind + 2] += lazy[ind];
//            }
//            lazy[ind] = 0;
//        }
//
//        if (low > r || high < l) return Integer.MAX_VALUE;
//        if (low >= l && high <= r) {
//            return seg[ind];
//        }
//
//        int mid = (low + high) >> 1;
//        int left = query(2 * ind + 1, low, mid, l, r);
//        int right = query(2 * ind + 2, mid + 1, high, l, r);
//        return Math.min(left , right);
//    }
//}
//class SGT {
//    int[] seg;
//    int[] lazy;
//
//    SGT(int n) {
//        seg = new int[4 * n];
//        lazy = new int[4 * n];
//    }
//
//    public void build(int ind, int low, int high, int[] arr) {
//        if (low == high) {
//            seg[ind] = arr[low];
//            return;
//        }
//        int mid = (low + high) >> 1;
//        build(2 * ind + 1, low, mid, arr);
//        build(2 * ind + 2, mid + 1, high, arr);
//        seg[ind] = seg[2 * ind + 1] + seg[2 * ind + 2];
//    }
//
//    public void update(int ind, int low, int high, int l, int r, int val) {
//        //first update the lazy one if remaining
//        if (lazy[ind] != 0) {
//            seg[ind] += (high - low + 1) * lazy[ind];
//            //if not leaf then update
//            if (low != high) {
//                lazy[2 * ind + 1] += lazy[ind];
//                lazy[2 * ind + 2] += lazy[ind];
//            }
//            lazy[ind] = 0;
//        }
//
//        if (low > r || high < l) return;
//
//        if (low >= l && high <= r) {
//            seg[ind] += (high - low + 1) * val;
//            if (low != high) {
//                lazy[2 * ind + 1] += val;
//                lazy[2 * ind + 2] += val;
//            }
//            return;
//        }
//
//        int mid = (low + high) >> 1;
//        update(2 * ind + 1, low, mid, l, r, val);
//        update(2 * ind + 2, mid + 1, high, l, r, val);
//        seg[ind] = seg[2 * ind + 1] + seg[2 * ind + 2];
//    }
//
//    public int query(int ind, int low, int high, int l, int r) {
//        if (lazy[ind] != 0) {
//            seg[ind] += (high - low + 1) * lazy[ind];
//            if (low != high) {
//                lazy[2 * ind + 1] += lazy[ind];
//                lazy[2 * ind + 2] += lazy[ind];
//            }
//            lazy[ind] = 0;
//        }
//
//        if (low > r || high < l) return 0;
//        if (low >= l && high <= r) {
//            return seg[ind];
//        }
//
//        int mid = (low + high) >> 1;
//        int left = query(2 * ind + 1, low, mid, l, r);
//        int right = query(2 * ind + 2, mid + 1, high, l, r);
//        return left + right;
//    }
//}
