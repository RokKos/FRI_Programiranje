:constant A ;
:constant B ;

true  := \ x y . x ;
false := \ x y . y ;
if := \ p x y . p x y ;

and := \ a b . if a b false; 
and' := \ a b . a b false;

or := \ a b . if a true b; 
or' := \ a b . a true b;

not := \ a . if a false true;
not' := \ a . a false true;

impl := \ a b. if (not a) true b;
impl' := \ a b. (not a) true b;

//iff := \ a b . if (or (and a b) (not (and a b))) true false;


0 := ^ x f . x ;
1 := ^ x f . f 0 x ;
2 := ^ x f . f 1 (f 0 x) ;
3 := ^ x f . f 2 (f 1 (f 0 x)) ;
4 := ^ x f . f 3 (f 2 (f 1 (f 0 x))) ;
5 := ^ x f . f 4 (f 3 (f 2 (f 1 (f 0 x)))) ;

:constant S
:constant Z
show := ^ n . n Z (^ m r . S r) ;

succ := \ n . (\ x f . f n (n x f));
pred := \ n . n 0 (\ m r . m);
+ := \ a b . b a (\ m r . succ r);
* := \ a b . b 0 (\ m r . + r a);
- := \ a b . b a (\ m r . pred r);