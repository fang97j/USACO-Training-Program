/*
ID: john.fa1
LANG: JAVA
TASK: frac1
*/
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

class frac1 {
  public static void main(String[] args) throws IOException {
    frac1 program = new frac1();
    program.solve();

  }

  int N;
  List<Fraction> simplified = new ArrayList<Fraction>();
  Set<Fraction> all = new HashSet<Fraction>();

  public void solve() throws IOException {
    final String file = new Throwable().getStackTrace()[0].getClassName();
    PrintWriter out = new PrintWriter(
        new BufferedWriter(new FileWriter(file + ".out")));

    /*Scanner sc = null;
    try {
      sc = new Scanner(new File("C:/Users/John/workspace/USACO/src/input.txt"));
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }*/

    Scanner sc = new Scanner(new File(file + ".in"));
    N = sc.nextInt();

    for (int denom = 1; denom <= N; denom++) {
      for (int numer = 0; numer <= denom; numer++) {
        Fraction f = new Fraction(numer, denom);

        // haven't encountered this before
        if (!all.contains(f)) {
          simplified.add(f);
          all.add(f);


          // generate all unsimplified fractions
          for (int i = 2; denom * i <= N; i++) {
            all.add(new Fraction(numer * i, denom * i));
          }
        }
      }
    }

    Collections.sort(simplified);

    for (Fraction f : simplified) {
      out.println(f);
    }

    out.close();
  }
}

class Fraction implements Comparable<Fraction> {
  int numer;
  int denom;

  Double value;

  public Fraction(int numer, int denom) {
    this.numer = numer;
    this.denom = denom;
    value = ((double) numer) / denom;
  }

  @Override
  public int compareTo(Fraction o) {
    return value.compareTo(o.value);
  }
  
  @Override
  public String toString() {
    return numer + "/" + denom;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + denom;
    result = prime * result + numer;
    result = prime * result + ((value == null) ? 0 : value.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Fraction other = (Fraction) obj;
    if (denom != other.denom)
      return false;
    if (numer != other.numer)
      return false;
    if (value == null) {
      if (other.value != null)
        return false;
    }
    else if (!value.equals(other.value))
      return false;
    return true;
  }
}