#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct {
	int id_student;
	char* id_predmet;
	int ocena;
} student;


int main () {
	int n,m,p;
	scanf("%d %d %d", &n, &m, &p);

	student *tabela = (student*) malloc (sizeof(student) * 1000);
	int *id_vseh_stud = (int*) malloc (sizeof(int) * 1000);
	int unique_poskusov = 0;
	int unique_id = 0;
	for (int i = 0; i < n; ++i) {
		int id_s, ocena_t;
		char* id_p = (char*) malloc (sizeof(char) * 11);

		scanf("%d %s %d", &id_s, id_p, &ocena_t);
		// Dodam studenta v unique id ce se ni
		int flag_id = 1;
		for (int j = 0; j < unique_id; ++j) {
			if (id_s == id_vseh_stud[j]) {
				flag_id = 0;
			}
		}

		if (flag_id) {
			id_vseh_stud[unique_id] = id_s;
		}

		unique_id += flag_id;

		// Pregledam ali ze obstaja tak student z takih predmetom in samo updatam oceno
		// Ce ne obstaja dodam ta predmet v seznam
		int unique = 1;

		for (int j = 0; j < unique_poskusov; ++j) {
			if (id_s == tabela[j].id_student && strcmp(id_p, tabela[j].id_predmet) == 0) {  // Pazi pri primerjanju predmetov, primerjanje pointerjev namesto vrednosti
				//printf("here\n");
				unique = 0;
				tabela[j].ocena = ocena_t;
			}
		}
		if (unique) {
			tabela[unique_poskusov].id_student = id_s;
			tabela[unique_poskusov].id_predmet = id_p;
			tabela[unique_poskusov].ocena = ocena_t;
		}

		unique_poskusov += unique;

	}

	/*for (int i = 0; i < unique_poskusov; ++i) {
		printf("%d %s %d\n", tabela[i].id_student, tabela[i].id_predmet, tabela[i].ocena);
	}*/

	int rez	= 0;
	for (int i = 0; i < unique_id; ++i) {
		int c = 0;
		for (int j = 0; j < unique_poskusov; ++j) {
			if (tabela[j].id_student == id_vseh_stud[i] && tabela[j].ocena >= 6) {
				c++;
			}
		}
		if (c >= p) {
			rez++;
		}
	}

	printf("%d\n", rez);

	free (id_vseh_stud);
	free (tabela);

	return 0;
}
