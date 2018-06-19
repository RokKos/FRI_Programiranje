all_equal([]).
all_equal([H]).
all_equal([H1, H2 | T]) :-
    H1 = H2,
    all_equal([H2|T]).