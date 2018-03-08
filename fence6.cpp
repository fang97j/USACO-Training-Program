/*
ID: john.fa1
PROG: fence6
LANG: C++11
*/
#include <iostream>
#include <fstream>
#include <climits>
#include <vector>
#include <unordered_set>
#include <queue>

// basically, just need to build a graph representation of the fences.
// Then the answer is the shortest cycle, which can be found with Dijkstra's

using namespace std;

ifstream fin("fence6.in");
ofstream fout("fence6.out");

const int INF = 1000000;
const int MAX_N = 10;
const int A = 0;
const int B = 1;

int N, s, L_s, N1_s, N2_s;
int vertex_ctr = 0;
int x;

vector<vector<unordered_set<int>>> neighbors; // neighbors[x][A] = neighbors of x from end A
vector<vector<int>> vertex_id;                // vertex_id[x][A] = vertex # of x at end A
vector<int> L;                                // L[x] = length of edge x
vector<vector<int>> adj_mat;

int dijkstra(int s, int t) {
  vector<int> dists = vector<int>(vertex_ctr, INF);
  vector<bool> visited = vector<bool>(vertex_ctr, false);
  dists[s] = 0;

  auto cmp = [] (pair<int, int> left, pair<int, int> right) {
    return left.second > right.second;
  };
  priority_queue< pair<int, int>, vector<pair<int, int>>, decltype(cmp) > q(cmp);
  q.push({s, 0});
  
  while(q.size() != 0) {
    auto p = q.top(); q.pop();
    int x = p.first;
    int d = p.second;

    if(visited[x])
      continue;
    visited[x] = true;

    for(int y = 0; y < vertex_ctr; y++) {
      if((adj_mat[x][y] != INF) and (d + adj_mat[x][y] < dists[y])) {
        dists[y] = d + adj_mat[x][y];
        q.push({ y, d + adj_mat[x][y] });
      }
    }
  }
  return dists[t];
}

int main() {
  fin >> N;

  for(int i = 0; i < N; i++) {
    neighbors.push_back(vector<unordered_set<int>>(2, unordered_set<int>()));
    vertex_id.push_back(vector<int>(2, -1));
  }
  L = vector<int>(N, -1);
  
  // input
  for(int i = 0; i < N; i++) {
    fin >> s >> L_s >> N1_s >> N2_s;

    L[s - 1] = L_s;
    for(int j = 0; j < N1_s; j++) {
      fin >> x;
      neighbors[s - 1][A].insert(x - 1);
    }
    for(int j = 0; j < N2_s; j++) {
      fin >> x;
      neighbors[s - 1][B].insert(x - 1);
    }
  }

  // build vertices
  for(int edge = 0; edge < N; edge++) {
    for(int side = 0; side < 2; side++) {
      // get vertex id
      int vertex = vertex_id[edge][side];
      if(vertex == -1) {
        vertex = vertex_ctr++;
      } 
      vertex_id[edge][side] = vertex;

      // give id to neighbors
      for(int nbr : neighbors[edge][side]) {
        int nbr_side = (neighbors[nbr][A].find(edge) != neighbors[nbr][A].end()) ? A : B;
        vertex_id[nbr][nbr_side] = vertex;
      }
    }
  }

  // min weight cycle with Dijkstra's
  adj_mat = vector<vector<int>>(vertex_ctr, vector<int>(vertex_ctr, INF));
  for(int edge = 0; edge < N; edge++) {
    adj_mat[vertex_id[edge][A]][vertex_id[edge][B]] = L[edge];
    adj_mat[vertex_id[edge][B]][vertex_id[edge][A]] = L[edge];
  }

  int min_loop = INF;
  for(int edge = 0; edge < N; edge++) {
    int s = vertex_id[edge][A];
    int t = vertex_id[edge][B];

    adj_mat[s][t] = INF;
    adj_mat[t][s] = INF;
    min_loop = min(min_loop, L[edge] + dijkstra(s, t));
    adj_mat[s][t] = L[edge];
    adj_mat[t][s] = L[edge];
  }
  fout << min_loop << endl;
}
