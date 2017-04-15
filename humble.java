/*
ID: john.fa1
LANG: JAVA
TASK: humble
*/
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.PriorityQueue;
import java.util.Scanner;

public class humble {
	public static void main(String[] args) throws IOException {
		humble h = new humble();
		h.solve();
	}

	class pair implements Comparable<pair> {
		long val;
		int idx;
		
		public pair(long val, int idx) {
			this.val = val;
			this.idx = idx;
		}

		@Override
		public int compareTo(pair o) {
			return Long.compare(val, o.val);
		}
	}
	
	void solve() throws IOException {
	    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("humble.out")));
	    Scanner sc = new Scanner(new File("humble.in"));

		// Scanner sc = new Scanner(System.in);
		
		int K = sc.nextInt();
		int N = sc.nextInt();

		int ctr = 0;
		int added = 0;
		long max = Long.MIN_VALUE;
		long cap = Long.MAX_VALUE;
		
		long[] factors = new long[K];
		PriorityQueue<pair> heap = new PriorityQueue<pair>();

		for (int k = 0; k < K; k++) {
			factors[k] = sc.nextLong();
			heap.add(new pair(factors[k], k));
		}
		added += K;
		
		while (true) {
			pair p = heap.poll();
			if (ctr == N - 1) {
				out.println(p.val);
				break;
			}
			else if (p.val != 1) {
				ctr++;
			}
			
			for (int k = p.idx; k < K; k++) {
				long product = factors[k] * p.val;
				if (product < cap) {
					heap.add(new pair(product, k));
					added++;

					max = Math.max(max, product);
					if (added == N) {
						cap = max;
					}
				}
			}
		}
		out.close();
	}
}
