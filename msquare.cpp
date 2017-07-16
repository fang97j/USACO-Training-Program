/*
ID: john.fa1
PROG: msquare
LANG: C++11
*/

#include <iostream>
#include <algorithm>
#include <fstream>
#include <queue>
#include <unordered_set>

using namespace std;

struct state {
    string perm;
    string step;
    state* parent;

    state(string perm, state* parent, string step) :
            perm(perm), parent(parent), step(step) {}
};

string A(string s) {
    string top = s.substr(0, 4);
    string bot = s.substr(4, 4);
    reverse(bot.begin(), bot.end());
    reverse(top.begin(), top.end());
    return bot + top;
}

string B(string s) {
    return s.substr(3, 1) + s.substr(0, 3) + s.substr(5, 3) + s.substr(4, 1);
}

string C(string s) {
    return s.substr(0, 1) + s.substr(6, 1) + s.substr(1, 1) + s.substr(3, 1) +
           s.substr(4, 1) + s.substr(2, 1) + s.substr(5, 1) + s.substr(7, 1);
}

int main() {
    ifstream fin("msquare.in");
    ofstream fout("msquare.out");

    queue<state *> q;
    unordered_set<string> seen;

    string c;
    string target = "";
    for (int i = 0; i < 8; i++) {
        fin >> c;
        target = target + c;
    }

    state* start = new state("12345678", nullptr, "");
    q.push(start);
    seen.insert(start->perm);

    state* ans = nullptr;

    while(1) {
        state *s = q.front();
        q.pop();

        if ((s->perm).compare(target) == 0) {
            ans = s;
            break;
        }

        string a = A(s->perm);
        if (seen.find(a) == seen.end()) {
            state *a_state = new state(a, s, "A");
            q.push(a_state);
            seen.insert(a);
        }

        string b = B(s->perm);
        if (seen.find(b) == seen.end()) {
            state *b_state = new state(b, s, "B");
            q.push(b_state);
            seen.insert(b);
        }

        string c = C(s->perm);
        if (seen.find(c) == seen.end()) {
            state *c_state = new state(c, s, "C");
            q.push(c_state);
            seen.insert(c);
        }
    }

    string path = "";
    while (ans != nullptr) {
        path = ans->step + path;
        ans = ans->parent;
    }
    fout << path.length() << endl;

    int ptr = 0;
    if (path.length() == 0) {
        fout << path << endl;
    }
    else {
        while (ptr < path.length()) {
            int size = min(60, (int) (path.length() - ptr));
            fout << path.substr(ptr, size) << endl;
            ptr += size;
        }
    }
}