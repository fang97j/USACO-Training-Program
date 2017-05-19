/*
ID: john.fa1
PROG: fact4
LANG: C++11
*/
#include <iostream>
#include <fstream>
using namespace std;

int N;

int main() {
  ofstream fout("fact4.out");
  ifstream fin("fact4.in");

  fin >> N;
  int p = 1;
  for (int i = 2; i <= N; i++) {
    p *= i;
    while (p % 10 == 0)
      p /= 10;
    p %= 1000;
  }
  fout << p % 10 << endl;
}
