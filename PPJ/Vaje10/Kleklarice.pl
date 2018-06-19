:- use_module(library(clpfd)).

meeting(X,Y) :- 
    X in 0..22,
    Y in 0..22,
    % lahko bi tako naredil [X,Y] ins 0..22
    X + Y #= 22,
    X * 2 + Y * 4 #= 72,
    label([X,Y]).