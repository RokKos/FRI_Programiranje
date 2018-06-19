reverse([],[]).
reverse([H|T], L) :-
    reverse(T, ReversedT),
    append(ReversedT, [H], L).

insert(X, L1, L2) :- L2 = [X | L1].
insert(X, L1, L2) :- [H | T] = L1, insert(X, T, L3), L2 = [H | L3].

is_sorted([]).
is_sorted([H]).
is_sorted([H1, H2|T]) :-
    H1 =< H2,
    is_sorted([H2|T]).

permute([], []).
permute([H|T], L) :-
    permute(T, PermutedT),
    insert(H, PermutedT, L).

bogosort(L, Sorted) :-
    permute(L, Sorted),
    is_sorted(Sorted).