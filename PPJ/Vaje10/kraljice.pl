:- use_module(library(clpfd)).

safe_pair(X1/Y1, X2/Y2) :-
    X1 #\= X2,
    Y1 #\= Y2,
    abs(X1 - X2)  #\= abs(Y1 - Y2).

safe_list(X1/Y1, []).
safe_list(X1/Y1, [X2/Y2|T]) :-
    safe_pair(X1/Y1, X2/Y2),
    safe_list(X1/Y1,T).

%to bi se bolj elegantno naredili takole
%safe_list(Q, L) :-
%    maplist(safe_pair(Q), L).   

safe_list([]).
safe_list([X1/Y1|T]) :-
    safe_list(X1/Y1,T),
    safe_list(T).

make_coord(X,Y, X/Y).

place_queens(N, L) :- 
    %L = [X1/Y1, X2/Y2, ...]
    length(Xs, N),
    length(Ys, N),
    numlist(1,N, Xs), % all_distinct(Xs),
    all_distinct(Ys),
    Xs ins 1..N,
    Ys ins 1..N,
    maplist(make_coord, Xs, Ys, L).

queens(N, L) :-
    place_queens(N,L),
    safe_list(L),
    term_variables(L, Vars),
    label(Vars).
    