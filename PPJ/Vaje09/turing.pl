% b -> je znak za blank
% R -> Array desno od glave in tudi tam kjer je glava
% L -> Array levo od glave (SEZNAM JE OBRNJEN, KER LAHKO DOSTOPAMO SAMO DO HEADA V LINKED LISTU)

% znak "-" da skupaj v eno spremenljivko

% kako bo izgledala akcija => action (Direction, InL - InR, OutL-OutR)

action(stay, InL - InR, InL - InR).

action(right, InL-[], [b|InL]-[]).
action(right, InL-[H|T], [H|InL]-T).

action(left, [] - InR, []-[b|InR]).
action(left, [H|T] - InR, [T]-[H|InR]).

head_rest([], b, []).
head_rest([H|T], H, T).

% opisuje nam izvajanje
program(plus1, q0, 1, q0, 1, right).
program(plus1, q0, b, final, 1, stay).

step(Name, InL-InR, InState, OutL-OutR, OutState) :-
    %dobimo simbol pod glavo
    head_rest(InR, Simbol, Ostalo),
    %uporabimo pravilo
    program(Name, InState, Simbol, OutState, NewSimbol, Action),
    %zgradimo nov trak
    NewR = [NewSimbol|Ostalo],
    %premaknemo glavo
    action(Action, InL-NewR, OutL-OutR).

run(Name, final, Tape, Tape).
run(Name, State, InTape, OutTape) :-
    step(Name, InTape, State, Tape1, State1),
    run(Name, State1, Tape1, OutTape).

