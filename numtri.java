/*
ID: john.fa1
LANG: JAVA
TASK: numtri
*/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

class numtri {
  
  public static void main (String [] args) throws IOException {
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("numtri.out")));
    
    /*Scanner sc = null;
    try {
      sc = new Scanner(new File("C:/Users/John/workspace/USACO/src/input.txt"));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }*/
    BufferedReader bf = new BufferedReader(new FileReader("numtri.in"));
    int R = Integer.parseInt(bf.readLine());
    
    int[][] values = new int[R][R];

    for (int row = 0; row < R; row++) {
      StringTokenizer st = new StringTokenizer(bf.readLine());
      for (int col = 0; col <= row; col++) {
        values[row][col] = Integer.parseInt(st.nextToken());
      }
    }

    for (int row = R - 2; row >= 0; row--) {
      for (int col = 0; col <= row; col++) {
        values[row][col] += Math.max(values[row + 1][col], values[row + 1][col + 1]);
      }
    }

    out.println(values[0][0]);
    out.close();
  }
}