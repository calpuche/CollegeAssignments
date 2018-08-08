male(mark).
male(mel).
male(richard).
male(tom).
male(adam).

female(amy).
female(jane).
female(joan).
female(betty).
female(rosa).
female(fran).

parent(mel,joan).
parent(jane,betty).
parent(jane,tom).
parent(richard,adam).
parent(richard,rosa).
parent(joan,fran).
parent(mark,jane).
parent(amy,jane).
parent(amy,richard).
parent(amy,joan).

mother(Mom,Kid):- female(Mom),parent(Mom,Kid).
father(Dad,Kid):- parent(Dad,Kid),male(Dad).
child(Parent,Kid):- parent(Parent,Kid).
sibling(Kid,Kid2):- mother(Parent,Kid),mother(Parent,Kid2).
sister(X,Y):- sibling(X,Y),female(Y).
brother(X,Y):- sibling(X,Y),male(Y).
daughter(Parent,Kid):- parent(Parent,Kid),female(Kid).
son(Parent,Kid):- parent(Parent,Kid),male(Kid).
uncle(Adult,Kid):- parent(Parent,Kid),sibling(Adult,Parent),male(Adult).
aunt(Adult,Kid):- parent(Parent,Kid),sibling(Adult,Parent),female(Adult).
cousin(Kid,Kid2):- parent(Parent,Kid),parent(Parent2,Kid2),sibling(Parent,Parent2).