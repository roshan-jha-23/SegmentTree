import java.util.Scanner;

public class SerejaBrackets {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        int q = sc.nextInt();
        Info[] seg = new Info[4 * s.length()];

        // Build segment tree
        build(0, 0, s.length() - 1, s, seg);

        StringBuilder sb = new StringBuilder();

        while (q-- > 0) {
            int l = sc.nextInt();
            int r = sc.nextInt();
            l--;
            r--;
            Info ans = query(0, 0, s.length() - 1, l, r, seg);
            int res = ans.fullB * 2;
            sb.append(res).append("\n");
        }

        sc.close();
        System.out.print(sb);
    }

    static Info query(int ind, int low, int high, int l, int r, Info[] seg) {
        if (l > high || low > r) return new Info(0, 0, 0);
        if (low >= l && high <= r) return seg[ind];
        int mid = (low + high) / 2;
        Info left = query(2 * ind + 1, low, mid, l, r, seg);
        Info right = query(2 * ind + 2, mid + 1, high, l, r, seg);
        return merge(left, right);
    }

    static void build(int ind, int low, int high, String s, Info[] seg) {
        if (low == high) {
            seg[ind] = new Info((s.charAt(low) == '(') ? 1 : 0, (s.charAt(low) == ')') ? 1 : 0, 0);
            return;
        }
        int mid = (low + high) / 2;
        build(2 * ind + 1, low, mid, s, seg);
        build(2 * ind + 2, mid + 1, high, s, seg);
        seg[ind] = merge(seg[2 * ind + 1], seg[2 * ind + 2]);
    }

    static Info merge(Info left, Info right) {
        Info ans = new Info(0, 0, 0);
        int match = Math.min(left.openB, right.closeB);
        ans.fullB = left.fullB + right.fullB + match;
        ans.openB = left.openB + right.openB - match;
        ans.closeB = left.closeB + right.closeB - match;
        return ans;
    }
}

class Info {
    public int openB;
    public int closeB;
    public int fullB;

    Info(int openB, int closeB, int fullB) {
        this.openB = openB;
        this.closeB = closeB;
        this.fullB = fullB;
    }
}
