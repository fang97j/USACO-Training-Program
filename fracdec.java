/*
ID: john.fa1
LANG: JAVA
TASK: fracdec
*/
import java.io.*;
import java.util.*;

class fracdec {
  public static void main (String[] args) throws IOException {
		fracdec program = new fracdec();
		program.solve();

	}

	Map<List<Integer>, Integer> dp = new HashMap<List<Integer>, Integer>();

	public void solve() throws IOException {
		final String file = new Throwable().getStackTrace()[0].getClassName();
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file + ".out")));

		Scanner sc = new Scanner(new File(file + ".in"));
		int N = sc.nextInt();
		int D = sc.nextInt();
		StringBuilder sb = new StringBuilder();

		sb.append(N / D);
		sb.append(".");
		
		N = N % D;
		
		int ctr = sb.length();
		while (N != 0) {
			List<Integer> fraction = new ArrayList<Integer>(Arrays.asList(N, D));
			if (dp.containsKey(fraction)) {
				sb.insert(dp.get(fraction), "(");
				sb.append(")");
				break;
			}
			else {
				dp.put(fraction, ctr);
			}
			
			int tenth = findTenth(N, D);
			sb.append(tenth);

			// subtract
			// (N/D) - (x/10) = (10*N - x*D)/(10*D)
			N = 10 * N - tenth * D;
			D = 10 * D;
			
			// shift decimal place over
			N *= 10;
			
			int[] reduced = reduce(N, D);
			N = reduced[0];
			D = reduced[1];
			
			
			ctr++;
		}
		if (sb.charAt(sb.length() - 1) == '.') {
			sb.append(0);
		}
		for (int i = 0; i < sb.length(); i += 76) {
			out.println(sb.substring(i, Math.min(i + 76, sb.length())));
		}
		out.close();
	}

	int findTenth(int N, int D) {
		for (int i = 9; i >= 0; i--) {
			if (i * D <= N * 10) {
				return i;
			}
		}
		return -1;
	}

	int[] reduce(int N, int D) {
		int a = Math.max(N, D);
		int b = Math.min(N, D);
		
		while (b != 0) {
			int temp = b;
			b = a % b;
			a = temp;
		}
		return new int[] { N / a, D / a};
	}
}