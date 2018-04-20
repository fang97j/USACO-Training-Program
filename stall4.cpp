/*
ID: john.fa1
PROG: stall4
LANG: C++11
*/
#include <iostream>
#include <fstream>
#include <queue>
#include <climits>
#include <cstring>

// maximum matching problem

std::ifstream fin("stall4.in");
std::ofstream fout("stall4.out");

// [0, 200) for cows, [200, 400) for stalls, 400 for source, 401 for sink
const int MAX_N = 402;
int res_graph[MAX_N][MAX_N];
const int s = 400;
const int t = 401;

int prev[MAX_N];

int N, M;
int num, stall;

int augment() {
  std::memset(prev, -1, sizeof(prev[0]) * MAX_N);

  bool has_path = false;
  std::queue<int> q;
  q.push(s);
  prev[s] = -2;

  while (!q.empty()) {
    int x = q.front();
    q.pop();

    if (x == t) {
      has_path = true;
      break;
    }

    for (int y = 0; y < MAX_N; y++) {
      if (res_graph[x][y] > 0  and prev[y] == -1) {
        q.push(y);
        prev[y] = x;
      }
    }
  }
  if (has_path) {
    int flow = INT_MAX;
    int cur = t;
    while (prev[cur] != -2) {
      flow = std::min(flow, res_graph[prev[cur]][cur]);
      cur = prev[cur];
    }
    
    cur = t;
    while (prev[cur] != -2) {
      res_graph[prev[cur]][cur] -= flow;
      res_graph[cur][prev[cur]] += flow;
      cur = prev[cur];
    }
    return flow;
  }
  else {
    return 0;
  }
}

int main() {
  std::memset(res_graph, 0, sizeof(res_graph[0][0]) * MAX_N * MAX_N);

  for (int i = 0; i < 200; i++) {
    res_graph[s][i] = 1;
    res_graph[i + 200][t] = 1;
  }

  fin >> N >> M;

  for (int n = 0; n < N; n++) {
    fin >> num;
    for (int i = 0; i < num; i++) {
      fin >> stall;
      res_graph[n][stall - 1 + 200] = 1;
    }
  }

  int max_flow = 0;
  int flow = augment();
  while (flow) {
    max_flow += flow;
    flow = augment();
  } 

  fout << max_flow << std::endl;
}
