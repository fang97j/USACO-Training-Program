/*
ID: john.fa1
PROG: shopping
LANG: C++11
*/
#include <iostream>
#include <fstream>
#include <math.h>
#include <vector>
#include <queue>
#include <unordered_set>
#include <unordered_map>

using namespace std;
typedef int base6;

struct node {
    base6 left;
    int price;

    node(base6 left, int price) : left(left), price(price) {}
};

auto comp = [] (node a, node b) { return a.price > b.price; };
typedef priority_queue<node, vector<node>, decltype(comp)> heap;

int S, N, C, K, P, B;
base6 req;
unordered_map<int, int> to_idx;

unordered_map<base6, int> deals;
unordered_set<base6> seen;

base6 subtract (base6 a, base6 b) {
    base6 diff = 0;
    for (int i = 0; i < 5; i++) {
        int a_digit = a % 10;
        int b_digit = b % 10;

        if (a_digit - b_digit < 0)
            return -1;

        diff = diff + (a_digit - b_digit) * (int) pow(10, i);
        a /= 10;
        b /= 10;
    }
    return diff;
}

int main() {
    ifstream fin("shopping.in");
    ofstream fout("shopping.out");

    fin >> S;
    int code_ctr = 0;
    for (int i = 0; i < S; i++) {
        fin >> N;

        base6 d = 0;
        for (int j = 0; j < N; j++) {
            fin >> C >> K;

            if (to_idx.find(C) == to_idx.end())
                to_idx[C] = code_ctr++;

            d = d + K * (int) pow(10, to_idx[C]);
        }
        fin >> P;
        deals[d] = P;
    }
    fin >> B;
    for (int i = 0; i < B; i++) {
        fin >> C >> K >> P;

        if (to_idx.find(C) == to_idx.end())
            to_idx[C] = code_ctr++;

        req = req + K * (int) pow(10, to_idx[C]);
        base6 d = (int) pow(10, to_idx[C]);
        if (deals.find(d) == deals.end())
            deals[d] = P;
    }

    heap h(comp);
    h.push(node(req, 0));

    int price = -1;
    int count = 0;
    while (1) {
        node n = h.top();
        h.pop();


        if (seen.find(n.left) != seen.end())
            continue;
        seen.insert(n.left);

        if (n.left == 0) {
            price = n.price;
            break;
        }

        // otherwise, add children
        for (auto iter : deals) {
            base6 d = iter.first;
            int p = iter.second;

            base6 diff = subtract(n.left, d);
            if (diff != -1)
                h.push(node(diff, n.price + p));
        }
    }
    fout << price << endl;
}
