/*
ID: john.fa1
PROG: fence
LANG: C++11
 */
#include <iostream>
#include <algorithm>
#include <fstream>
#include <vector>
#include <unordered_set>

using namespace std;

const int MAX_V = 501;
int F, s, t;

vector<vector<int> > adj_list (MAX_V, vector<int>());
vector<int> circuit;

void find_circuit(int i) {
    while (adj_list[i].size() > 0) {
        int j = 501;
        for (int k : adj_list[i])
            j = min(j, k);

        auto pos = find(adj_list[i].begin(), adj_list[i].end(), j);
        adj_list[i].erase(pos);

        pos = find(adj_list[j].begin(), adj_list[j].end(), i);
        adj_list[j].erase(pos);

        find_circuit(j);
    }
    circuit.push_back(i);
}

int main() {
    ifstream fin("fence.in");
    ofstream fout("fence.out");

    bool has_odd = false;
    int start = 501;

    fin >> F;
    unordered_set<int> posts;
    for (int i = 0; i < F; i++) {
        fin >> s >> t;
        adj_list[s].push_back(t);
        adj_list[t].push_back(s);

        posts.insert(s);
        posts.insert(t);
    }

    for (int i = 0; i < MAX_V && !has_odd; i++)
        has_odd = has_odd || (adj_list[i].size() & 1);

    for (int post : posts)
        if (!has_odd || adj_list[post].size() & 1)
            start = min(start, post);

    find_circuit(start);

    for (int i = circuit.size() - 1; i >= 0; i--) {
        fout << circuit[i] << endl;
    }
}