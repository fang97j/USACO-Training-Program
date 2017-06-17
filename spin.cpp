/*
ID: john.fa1
PROG: spin
LANG: C++11
*/

#include <iostream>
#include <fstream>
#include <climits>
#include <algorithm>
#include <vector>
using namespace std;

struct wedge {
  int start, end, time;
  wedge(int s, int e, int t) : start(s), end(e), time(t) {}
};

int s, w, a, e, old_period, ans_period;
vector<wedge> ans;

int gcd(int a, int b) {
  while (b != 0) {
    int t = b;
    b = a % b;
    a = t;
  }
  return a;
}

int lcm(int a, int b) {
  return a / gcd(a, b) * b;
}

pair<int, int> intersect(int a_s, int a_e, int b_s, int b_e) {
  if (a_e < a_s) 
    a_e += 360;
  if (b_e < b_s)
    b_e += 360;

  for (int i = 0; i <= 360; i += 360) {
    for (int j = 0; j <= 360; j += 360) {
      if (a_s + i <= b_s + j && b_s + j <= a_e + i) {
        return pair<int, int> (b_s % 360, min(a_e  + i, b_e + j) % 360);
      }
      if (a_s + i <= b_e + j && b_e + j <= a_e + i) { 
        return pair<int, int> (max(a_s + i, b_s + j) % 360, b_e % 360);
      }
      if (b_s + j <= a_s + i && a_e + i <= b_e + j) {
        return pair<int, int> (a_s % 360, a_e % 360);
      }
    }
  }
  return pair<int, int>(-1, -1);
}

int main() {
  ifstream fin("spin.in");
  ofstream fout("spin.out");

  fin >> s >> w;
  ans_period = lcm(s, 360) / s;
  for (int j = 0; j < w; j++) {
    fin >> a >> e;
    for (int k = 0; k < ans_period; k++) {
      int start = (a + s * k) % 360;
      int end = (start + e) % 360;
      ans.push_back(wedge(start, end, k));
    }
  }

  for (int i = 0; i < 4; i++) {
    fin >> s >> w;
    old_period = ans_period;
    ans_period = lcm(ans_period, lcm(s, 360) / s);
    vector<wedge> new_ans;

    for (int j = 0; j < w; j++) {
      fin >> a >> e;

      for (int k = 0; k < ans_period; k += old_period) {
        for (wedge w : ans) {
          int time = w.time + k;
          int start = (a + s * time) % 360;
          int end = (start + e) % 360;

          auto x = intersect(w.start, w.end, start, end);
          if (x.first != -1) {
            new_ans.push_back(wedge(x.first, x.second, time));
          }
        }
      }
    }
    ans = new_ans;
  }
  int t = INT_MAX;
  for (wedge w : ans) 
    t = min(t, w.time);

  if (t == INT_MAX) 
    fout << "none" << endl;
  else 
    fout << t << endl;
}
