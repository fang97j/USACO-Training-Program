/*
ID: john.fa1
PROG: inflate
LANG: C++
 */

#include<iostream>
#include <fstream>

using namespace std;

const int MAX_N = 10000;

int M;
int N;
int pts [MAX_N];
int times [MAX_N];

int maxPts [MAX_N + 1];

int main() {

    ofstream fout("inflate.out");
    ifstream fin("inflate.in");



    fin >> M >> N;

    for (int n = 0; n < N; n++) {
        fin >> pts[n] >> times[n];
    }


    for (int t = 1; t <= M; t++) {
        // for each problem type
        for (int n = 0; n < N; n++) {
            if (t - times[n] >= 0) {
                maxPts[t] = max(maxPts[t], maxPts[t - times[n]] + pts[n]);
            }
        }
        maxPts[t] = max(maxPts[t], maxPts[t - 1]);
    }
    fout << maxPts[M] << endl;
}
