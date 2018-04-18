/*
ID: john.fa1
PROG: ditch
LANG: C++11
*/
#include <iostream>
#include <fstream>
#include <queue>
#include <climits>

// just a max flow problem. Only catch is that you add edges if there are multiple per node pair

using namespace std;

ifstream fin("ditch.in");
ofstream fout("ditch.out");

const int MAX_M  = 200;
int N, M;       // N is edges, M is nodes 
int S, E, C;

int res_graph[MAX_M][MAX_M];
int parent[MAX_M];

bool bfs() {
  for (int i = 0; i < M; i++) {
    parent[i] = -1;
  }

  parent[0] = -2;
  queue<int> q;
  q.emplace(0);
  while (!q.empty()) {
    int x = q.front();
    q.pop();

    if (x == M - 1) {
      return true;
    }

    for (int y = 0; y < M; y++) {
      if (parent[y] == -1 and res_graph[x][y] > 0) {
        parent[y] = x;
        q.emplace(y);
      }
    }
  }
  return false;
}

int main() {
  for (int i = 0; i < MAX_M; i++) {
    for (int j = 0; j < MAX_M; j++) {
      res_graph[i][j] = -1;
    }
  }

  fin >> N >> M;
  for(int n = 0; n < N; n++) {
    fin >> S >> E >> C;
    if (res_graph[S - 1][E - 1] == -1)
      res_graph[S - 1][E - 1] = C;
    else
      res_graph[S - 1][E - 1] += C;
  }
  
  int total_flow = 0;
  while(bfs()) {
    int flow = INT_MAX;
    int cur = M - 1;
    while (parent[cur] != -2) {
      flow = min(flow, res_graph[parent[cur]][cur]);
      cur = parent[cur];
    }
    
    cur = M - 1;
    while (parent[cur] != -2) {
      res_graph[parent[cur]][cur] -= flow;
      res_graph[cur][parent[cur]] += flow;
      cur = parent[cur];
    }
    total_flow += flow;
  }
  fout << total_flow << endl;
}

