
factorial(0,1).
factorial(N,F) :- N>0, N1 is N-1, factorial(N1,F1), F is N * F1.

append([],Ys,Ys).
append([X|Xs],Ys,[X|Zs]):-append(Xs,Ys,Zs).

take([H|T],H,T).
take([H|T],R,[H|S]):-take(T,R,S).

delete_all(X,[],[]).
delete_all(X, [X|Xs], Y) :- delete_all(X, Xs, Y).
delete_all(X, [T|Xs], Y) :- delete_all(X, Xs, Y2), append([T], Y2, Y).


replace_all(_,[],_,[]).
replace_all(Element,[Element|T],Newelement,[Newelement|T2]) :- replace_all(Element, T, Newelement, T2).
replace_all(Element, [H|T],Newelement,[H|T2]) :- replace_all(Element, T, Newelement, T2).

the_last(X,[X]).
the_last(X,[_|Z]) :- the_last(X,Z).