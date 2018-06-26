#include <stdio.h>
#include <stdlib.h>

typedef struct _node {
	int value;
	struct _node* left;
	struct _node* right;
} node;

int* nivoji;
node* root;

void tree_print(node* root){
	if (root != NULL) {
		printf("%d", root->value);
		tree_print(root->left);
		tree_print(root->right);
	}

	return;

}

node* naredi (int i, int N) {
	/*if (i == 0) {
		root = (node*) malloc (sizeof(node));
		root.value = nivoji[0];
		root.left = naredi(2*0 + 1, N);
		root.right = naredi(2*0 + 3, N);
		return root;
	}*/
	if (i >= N) {
		return NULL;
	}
	//printf("%d %d\n", nivoji[i], i);
	node* novi = (node*) malloc (sizeof(node));
	novi->value = nivoji[i];
	novi->left = naredi(2*i + 1, N);
	novi->right = naredi(2*i + 2, N);
	return novi;

}

int potenca (int a, int n) {
	int sum = 1;
	for (int i = 0; i < n; ++i) {
		sum *= a;
	}
	return sum;
}

int main () {
	int k;
	scanf("%d", &k);
	int N = (potenca(2,k) - 1);
	nivoji = (int*) malloc (sizeof(int) * N);
	for (int i = 0; i < N; ++i) {
		scanf("%d", &nivoji[i]);
	}

	//2 * i + 1, 2 * i + 2
	root = naredi(0,N);
	//printf("%d %d %d\n", root->value, root->left->value, root->right->value);

	tree_print(root);

	return 0;
}