/*
ID: john.fa1
PROG: heritage
LANG: C++11
*/

#include <iostream>
#include <fstream>
#include <unordered_map>
#include <queue>

struct node {
    char c;
    node* l;
    node* r;

    node(char c) : c(c), l(nullptr), r(nullptr) {}
};

std::ifstream fin("heritage.in");
std::ofstream fout("heritage.out");

std::unordered_map<char, int> in_map;
std::string in, pre;
int len, pre_idx = 0;

node* construct(int in_l, int in_r) {
    if (pre_idx >= len)
        return nullptr;

    int in_idx = in_map[pre[pre_idx]];
    if (in_l > in_idx || in_idx > in_r)
        return nullptr;

    node* root = new node(pre[pre_idx++]);
    root->l = construct(in_l, in_idx - 1);
    root->r = construct(in_idx + 1, in_r);
    return root;
}

void print_post(node* root) {
    if (root == nullptr)
        return;

    print_post(root->l);
    print_post(root->r);
    fout << root->c;
}

int main() {
    fin >> in >> pre;
    len = in.length();

    for (int i = 0; i < len; i++)
        in_map[in[i]] = i;

    node* root = construct(0, len - 1);
    print_post(root);
    fout << std::endl;
}
