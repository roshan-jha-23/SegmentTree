import java.util.Scanner;

public class XeninAndHerIssues {
    public static void main(String[] args) {
        solve();
    }

    static void solve() {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int q = sc.nextInt();
        int elm = 1 << n;
        int[] arr = new int[elm];
        for (int i = 0; i < elm; i++) {
            arr[i] = sc.nextInt();
        }
        int[] seg = new int[2 * elm - 1];
        build(0, 0, elm - 1, arr, seg, (n & 1) == 0 ? 0 : 1);

        StringBuilder output = new StringBuilder();
        while (q-- > 0) {
            int i = sc.nextInt() - 1;
            int val = sc.nextInt();
            update(0, 0, elm - 1, seg, (n & 1) == 0 ? 0 : 1, i, val);
            output.append(seg[0]).append("\n");
        }
        sc.close();
        System.out.print(output);
    }

    static void build(int ind, int low, int high, int[] arr, int[] seg, int or) {
        if (low == high) {
            seg[ind] = arr[low];
            return;
        }
        int mid = (low + high) >> 1;
        build(2 * ind + 1, low, mid, arr, seg, 1 - or);
        build(2 * ind + 2, mid + 1, high, arr, seg, 1 - or);
        if (or == 1) {
            seg[ind] = seg[2 * ind + 1] | seg[2 * ind + 2];
        } else {
            seg[ind] = seg[2 * ind + 1] ^ seg[2 * ind + 2];
        }
    }

    static void update(int ind, int low, int high, int[] seg, int or, int i, int val) {
        if (low == high) {
            seg[ind] = val;
            return;
        }
        int mid = (low + high) >> 1;
        if (i <= mid) {
            update(2 * ind + 1, low, mid, seg, 1 - or, i, val);
        } else {
            update(2 * ind + 2, mid + 1, high, seg, 1 - or, i, val);
        }
        if (or == 1) {
            seg[ind] = seg[2 * ind + 1] | seg[2 * ind + 2];
        } else {
            seg[ind] = seg[2 * ind + 1] ^ seg[2 * ind + 2];
        }
    }
}
