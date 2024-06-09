import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class InversionCount {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Set the size of the large test case
        int n = 100000;

        // Generate the large array of random integers
        int[] arr = new int[n];
        Random rand = new Random();
        int mx = -1;
        for (int i = 0; i < n; i++) {
            arr[i] = rand.nextInt(100000) + 1;
            mx = Math.max(arr[i], mx);
        }

        // Initialize the frequency array
        int[] freq = new int[mx + 1];
        for (int i = 0; i < n; i++) {
            freq[arr[i]]++;
        }

        // Build the segment tree
        SegmentTree st = new SegmentTree(mx + 1);
        st.build(0, 0, mx, freq);

        // Calculate inversion count
        long cnt = 0;
        for (int i = 0; i < n; i++) {
            freq[arr[i]]--;
            st.update(0, 0, mx, arr[i], -1);
            cnt += st.query(0, 0, mx, 1, arr[i] - 1);
        }
        System.out.println(Arrays.toString(arr));
        System.out.println("Total inversion count: " + cnt);
    }
}

class SegmentTree {
    int[] seg;

    SegmentTree(int n) {
        seg = new int[4 * n];
    }

    void build(int ind, int low, int high, int[] arr) {
        if (high == low) {
            seg[ind] += arr[low];
            return;
        }
        int mid = (low + high) >> 1;
        build(2 * ind + 1, low, mid, arr);
        build(2 * ind + 2, mid + 1, high, arr);
        seg[ind] = (seg[2 * ind + 1] + seg[2 * ind + 2]);
    }

    int query(int ind, int low, int high, int l, int r) {
        if (r < low || high < l) return 0;
        if (low >= l && high <= r) return seg[ind];
        int mid = (low + high) >> 1;
        int left = query(2 * ind + 1, low, mid, l, r);
        int right = query(2 * ind + 2, mid + 1, high, l, r);
        return (left + right);
    }

    void update(int ind, int low, int high, int idx, int val) {
        if (low == high) {
            seg[ind] += val;
            return;
        }
        int mid = (low + high) >> 1;
        if (mid >= idx) {
            update(2 * ind + 1, low, mid, idx, val);
        } else {
            update(2 * ind + 2, mid + 1, high, idx, val);
        }
        seg[ind] = (seg[2 * ind + 1] + seg[2 * ind + 2]);
    }
}
