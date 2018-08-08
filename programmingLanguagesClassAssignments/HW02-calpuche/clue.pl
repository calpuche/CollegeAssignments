%Carlos_Alpuche
%Cosc_3336
%dr_Sherbro
%the_Clue_Game


guest(mustard).
guest(plum).
guest(scarlett).
guest(green).

rich(boddy).
greedy(mustard).

affairWith(boddy,green).
affairWith(boddy,scarlett).

marriedTo(plum,green).

dead(boddy).

home(boddy).

affair(X,Y):- affairWith(X,Y).
affair(X,Y):- affairWith(Y,X).

married(X,Y):-marriedTo(X,Y).
married(X,Y):-marriedTo(Y,X).

greed(Suspect, Victim) :- greedy(Suspect),rich(Victim).

hate(Suspect, Victim) :-married(Suspect, Someone),affair(Someone, Victim).

murder(X, Victim):-hate(X, Victim).
murder(X, Victim):- greed(X, Victim).