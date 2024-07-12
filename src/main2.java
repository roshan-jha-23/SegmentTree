class Solution {
    class SegTree {
        int[] tree;
        public SegTree(int n) {
            int size = (int) (Math.ceil(Math.log(n) / Math.log(2)));
            size = (2 * (int) Math.pow(2, size)) - 1;
            tree = new int[size];
        }
        
        public int maxValue() {
            return tree[0];
        }

        public void update(int node, int left, int right, int idx, int val) {
            if (left == right) {
                tree[node] = Math.max(val, tree[node]);
            } else {
                int mid = (left + right) >> 1;
                if (idx <= mid) {
                    update(2 * node + 1, left, mid, idx, val);
                } else {
                    update(2 * node + 2, mid + 1, right, idx, val);
                }

                tree[node] = Math.max(tree[2 * node + 1], tree[2 * node + 2]);
            }
        }

        public int query(int node, int left, int right, int l, int r) {
            if (left > r || right < l) return 0;
            if (left >= l && right <= r) return tree[node];

            int mid = (left + right) >> 1;
            int leftRes = query(2 * node + 1, left, mid, l, r);
            int rightRes = query(2 * node + 2, mid + 1, right, l, r);

            return Math.max(leftRes, rightRes);
        }
    }

    public int lengthOfLIS(int[] nums, int k) {
        int n = (int)1e5 + 1;
        SegTree segTree = new SegTree(n);
        for (int x : nums) {
            int lower = Math.max(0, x - k);            
            int prevSubLongest = segTree.query(0, 0, n - 1, lower, x - 1);
            segTree.update(0, 0, n - 1, x, prevSubLongest + 1);
        }

        return segTree.maxValue();
    }
}
