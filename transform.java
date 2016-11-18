/*
ID: john.fa1
LANG: JAVA
TASK: transform
*/
import java.io.*;
import java.util.*;

class transform {
  public static void main (String [] args) throws IOException {
    final String file = new Throwable().getStackTrace()[0].getClassName();
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file + ".out")));
    
    /*Scanner sc = null;
    try {
      sc = new Scanner(new File("C:/Users/John/workspace/USACO/src/input.txt"));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }*/
    Scanner sc = new Scanner(new File(file + ".in"));
    int n = sc.nextInt();
    char[][] original = new char[n][n];
    char[][] transform = new char[n][n];
    for (int i = 0; i < n; i++){
      String line = sc.next();
      for (int j = 0; j < n; j++) {
        original[i][j] = line.charAt(j);
      }
    }
    for (int i = 0; i < n; i++){
      String line = sc.next();
      for (int j = 0; j < n; j++) {
        transform[i][j] = line.charAt(j);
      }
    }
    out.println(findTransform(original, transform, n));
    out.close();
  }
  
  static char[][] rotate(char[][] original, int n) {
    char[][] rotated = new char[n][n];
    for (int y = 0; y < n; y++) {
      for (int x = 0; x < n; x++) {
        rotated[x][n - 1 - y] = original[y][x];
      }
    }
    return rotated;
  }
  
  static char[][] reflect(char[][] original, int n) {
    char[][] reflected = new char[n][n];
    for (int y = 0; y < n; y++) {
      for (int x = 0; x < n; x++) {
        reflected[y][n - 1 - x] = original[y][x];
      }
    }
    return reflected;
  }
  
  static int findTransform(char[][] original, char[][] transform, int n) {
    char[][] test = original;
    for (int i = 1; i <= 3; i++) {
      test = rotate(test, n);
      if (Arrays.deepEquals(test, transform)) return i;
    }
    test = rotate(test, n);
    test = reflect(test, n);
    if (Arrays.deepEquals(test, transform)) return 4;
    for (int i = 1; i <= 3; i++) {
      test = rotate(test, n);
      if (Arrays.deepEquals(test, transform)) return 5;
    }
    if (Arrays.deepEquals(original, transform)) return 6;
    return 7;
  }
}