/*
ID: john.fa1
LANG: JAVA
TASK: agrinet
*/
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.PriorityQueue;
import java.util.Scanner;

public class agrinet {
	static class Node implements Comparable<Node> {
		int number;
		int val;
		
		public Node(int number, int val) {
			this.number = number;
			this.val = val;
		}

		@Override
		public int compareTo(Node o) {
			return this.val - o.val;
		}
	}
	public static void main(String[] args) throws IOException {
		int MAX_N = 100;

		int N;
		int[][] adj_matrix = new int[MAX_N][MAX_N];
		int[] dists = new int[MAX_N];
		boolean[] inTree = new boolean[MAX_N];
		
		final String file = new Throwable().getStackTrace()[0].getClassName();
	    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file + ".out")));

	    Scanner sc = new Scanner(new File(file + ".in"));
		// Scanner sc = new Scanner(System.in);
		
		N = sc.nextInt();

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				adj_matrix[i][j] = sc.nextInt();
			}
	        dists[i] = Integer.MAX_VALUE;
		}
	    
	    PriorityQueue<Node> heap = new PriorityQueue<Node>();
	    dists[0] = 0;
	    heap.offer(new Node(0, 0));

	    int length = 0;
	    while(!heap.isEmpty()) {
	        int n = heap.poll().number;

	        if (inTree[n]) {
	            continue;
	        }
	        inTree[n] = true;
			length += dists[n];
	        
	        for (int m = 0; m < N; m++) {
	            if (adj_matrix[n][m] < dists[m] && !inTree[m]) {
	                dists[m] = adj_matrix[n][m];
	                heap.offer(new Node(m, dists[m]));
	            }
	        }
	    }
	    out.println(length);
	    out.close();
	}
}
