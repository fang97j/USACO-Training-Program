/*
ID: john.fa1
PROG: butter
LANG: C++11
*/
#include <iostream>
#include <fstream>
#include <climits>

using namespace std;

const int INF = 1000000;
const int MAX_P = 801;

int adj_matrix [MAX_P][MAX_P], dist [MAX_P][MAX_P];
int num_cows [MAX_P];
int N, P, C, x, s, t, l;

int main() {
    ifstream fin("butter.in");
    ofstream fout("butter.out");

    fin >> N >> P >> C;
    for (int i = 0; i < N; i++) {
        fin >> x;
        num_cows[x]++;
    }

    for (int i = 0; i < MAX_P; i++)
        for (int j = 0; j < MAX_P; j++)
            dist[i][j] = INF;

    for (int i = 1; i <= P; i++)
        dist[i][i] = 0;

    for (int i = 0; i < C; i++) {
        fin >> s >> t >> l;
        adj_matrix[s][t] = l;
        adj_matrix[t][s] = l;

        dist[s][t] = l;
        dist[t][s] = l;
    }

    // floyd-warshall
    for (int k = 1; k <= P; k++)
        for (int i = 1; i <= P; i++)
            for (int j = 1; j <= P; j++)
                dist[i][j] = min(dist[i][j], dist[i][k] + dist[k][j]);

    // try each pasture
    int min_cost = INT_MAX;
    for (int i = 1; i <= P; i++) {
        int cost = 0;
        for (int j = 1; j<= P; j++) {
            cost += dist[i][j] * num_cows[j];
        }
        min_cost = min(min_cost, cost);
    }
    fout << min_cost << endl;
}