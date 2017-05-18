/*
ID: john.fa1
PROG: stamps
LANG: C++11
*/

#include <iostream>
#include <fstream>
#include <limits.h>
#include <algorithm>
#include <deque>
using namespace std;

const int MAX_N = 50;
const int MAX_VAL = 10000;

int N, K;
int stamp_vals[MAX_N];

int main() {
  ofstream fout("stamps.out");
  ifstream fin("stamps.in");

  fin >> K >> N;
  for (int n = 0; n < N; n++) {
    fin >> stamp_vals[n];
  }

  // fill in queue
  int M;
  deque<int> q(MAX_VAL, INT_MAX);
  for (int i = 0; i < N; i++) {
    q[stamp_vals[i] - 1] = 1;
  }

  for(M = 1; ; M++) {
    int num_stamps = q.front();
    if (num_stamps == INT_MAX) {
      break;
    }
    q.pop_front();
    q.push_back(INT_MAX);

    for (int i = 0; i < N; i++) {
      int idx = stamp_vals[i] - 1;
      if (num_stamps + 1 <= K) {
        q[idx] = min(num_stamps + 1, q[idx]);
      }
    }
  }
  fout << M - 1 << endl;
}
