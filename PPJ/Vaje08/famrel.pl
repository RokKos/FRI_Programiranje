parent(tina, william).
parent(thomas, william).
parent(thomas, sally).
parent(thomas, jeffrey).
parent(sally, andrew).
parent(sally, melanie).
parent(andrew, joanne).
parent(jill, joanne).
parent(joanne, steve).
parent(william, vanessa).
parent(william, patricia).
parent(vanessa, susan).
parent(patrick, susan).
parent(patricia, john).
parent(john, michael).
parent(john, michelle).

parent(frank, george).
parent(estelle, george).
parent(morty, jerry).
parent(helen, jerry).
parent(jerry, anna).
parent(elaine, anna).
parent(elaine, kramer).
parent(george, kramer).

parent(margaret, nevia).
parent(margaret, alessandro).
parent(ana, aleksander).
parent(aleksandr, aleksander).
parent(nevia, luana).
parent(aleksander, luana).
parent(nevia, daniela).
parent(aleksander, daniela).

male(william).
male(thomas).
male(jeffrey).
male(andrew).
male(steve).
male(patrick).
male(john).
male(michael).
male(frank).
male(george).
male(morty).
male(jerry).
male(kramer).
male(aleksandr).
male(alessandro).
male(aleksander).

female(tina).
female(sally).
female(melanie).
female(joanne).
female(jill).
female(vanessa).
female(patricia).
female(susan).
female(michelle).
female(estelle).
female(helen).
female(elaine).
female(anna).
female(margaret).
female(ana).
female(nevia).
female(luana).
female(daniela).


% komentar

% vejica (,) pomeni "in", podpicje (;) pomeni "ali"
mother(X, Y) :- female(X), parent(X, Y).

grandparent(X, Y) :- parent(X, Z), parent(Z, Y).

% TIP: negacije pisi cimbolj nakoncu
sister(X, Y) :- female(X), parent(Z, X), parent(Z, Y), not(X = Y). % ali pa takole da sta razlicna X \= Y

aunt(X, Y) :- sister(X,Z), parent(Z, Y).

ancestor(X, Y) :- parent(X, Y) ; parent(X,Z), ancestor(Z, Y).

% descendant(X, Y) :- parent(Y, X) ; parent(Z,X), descendant(Z, Y).  lahko bi napisal takole ampak mi je moja spodnja verzija bolj vsec
descendant(X, Y) :- ancestor(Y, X).

% stikanje skupaj seznamov append([1,2], [3,4], L)
% lahko pa tudi preverjamo ce dobimo tak seznam z appendom: append([1,2], [3,4], [1,2,3]) -> Vrne false

% append(A, B, [1,2,3]) vse moznosti za razdelitev arraya [1,2,3] na dva seznama
% append([A|C], B, [1,2,3]) to mu pove naj vzame saj en element v A arrayu

ancestor(X, Y, [X|[Y]]) :- parent(X, Y).
% vec moznih definicij
ancestor(X, Y, [X|L1]) :- parent(X,Z), ancestor(Z, Y, L1).

member(X, L) :- [H|_] = L, X = H.
member(X, L) :- [_|T] = L, member(X, T).

% krajsa verzija
% member(X, [X|_]) :- true.
% member(X, [_|T]) :- member(X, T).

insert(X, L1, L2) :- L2 = [X | L1].
insert(X, L1, L2) :- [H | T] = L1, insert(X, T, L3), L2 = [H | L3].

% krajsa verzija
% insert(X, L1, [X | L1]) :- . pika ubistvu pomeni true
% insert(X, [H | T], [H | L3]) :- insert(X, T, L3).


reverse([H|T], B) :- reverse(T, C), append(C, [H], B).
reverse([], []).  % celo tako deluje