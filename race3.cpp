/*
ID: john.fa1
PROG: race3
LANG: C++11
*/
#include <iostream>
#include <fstream>
#include <cstring>
#include <algorithm>
#include <queue>
#include <unordered_set>

// To check if a vertex is unavoidable, just remove its incoming (or outgoing) edges and use
// flood fill to see if you can get from node 0 to node N

// To check if a vertex is splitting, first remove all outgoing edges and flood fill from 0
// to get one course.
// Restore those edges, then remove all incoming edges and flood fill from the vertex to get 
// the other course. The vertex is splitting of these courses cover all vertices once, except 
// the vertex in question, which would appear twice.

std::ifstream fin("race3.in");
std::ofstream fout("race3.out");

const int MAX_N = 50;
int N = 0;
int m;

bool adj_mat[MAX_N][MAX_N];
bool visited[MAX_N];
std::vector<int> unavoidable;
std::vector<int> splitting;
std::unordered_set<int> detached;
std::unordered_set<int> course1;
std::unordered_set<int> course2;

void flood_fill(int root) {
  std::memset(visited, false, sizeof(visited[0]) * MAX_N);

  std::queue<int> q;
  q.push(root);
  visited[root] = true;
  while (!q.empty()) {
    int x = q.front();
    q.pop();

    for (int y = 0; y < N + 1; y++) {
      if (adj_mat[x][y] and !visited[y]) {
        q.push(y);
        visited[y] = true;
      }
    }
  }
}

int main() {
  std::memset(adj_mat, false, sizeof(adj_mat[0][0]) * MAX_N * MAX_N);

  fin >> m;
  while (m != -1) {
    while (m != -2) {
      adj_mat[N][m] = true;
      fin >> m;
    }
    fin >> m;
    N++;
  }
  N--;

  for (int n = 1; n < N; n++) {
    detached.clear();
    course1.clear();
    course2.clear();
    for (int m = 0; m < N + 1; m++) {
      if (adj_mat[n][m]) {
        detached.insert(m);
        adj_mat[n][m] = false;
      }
    }
    flood_fill(0);
    
    // unavoidable
    if (!visited[N])
      unavoidable.push_back(n);

    // course 1
    for (int m = 0; m < N + 1; m++) 
      if (visited[m]) 
        course1.insert(m);

    for (int m : detached) 
      adj_mat[n][m] = true;

    detached.clear();
    for (int m = 0; m < N + 1; m++) {
      if (adj_mat[m][n]) {
        detached.insert(m);
        adj_mat[m][n] = false;
      }
    }
    flood_fill(n);
    
    // course 2
    for (int m = 0; m < N + 1; m++) 
      if (visited[m]) 
        course2.insert(m);

    for (int m : detached) 
      adj_mat[m][n] = true;

    if (course1.size() + course2.size() == N + 2) 
      splitting.push_back(n);
  }
  std::sort(unavoidable.begin(), unavoidable.end());
  fout << unavoidable.size();
  for (int x : unavoidable)
    fout << " " << x;
  fout << std::endl;

  std::sort(splitting.begin(), splitting.end());
  fout << splitting.size();
  for (int x : splitting) 
    fout << " " << x;
  fout << std::endl;

}

