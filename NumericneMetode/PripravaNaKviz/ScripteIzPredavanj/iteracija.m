function y=iteracija(f,x0,delta,maxsteps)

% y=ITERACIJA(f,x0,delta,maxsteps) izvaja iteracijo za funkcijo f z zacetnim 
% priblizkom x0. Funkcija f mora biti podana v obliki inline funkcije 
% (npr. f=inline('x^2-2')). Iteracija se konca, ko se zadnja priblizka relativno
% razlikujeta za manj kot delta ali ko prekoracimo maksimalno stevilo korakov maxsteps.
% Ce zadnjih dveh argumentov ne podamo, je privzeta vrednost za delta osnovna 
% zaokrozitvena napaka eps, za maxsteps pa 50. Funkcija f je lahko tudi
% vektorska, x0 pa mora biti potem vektor ustrezne dimenzije.
 
% Bor Plestenjak 2004
% zadnja sprememba 15.10.2006

% ce ne podamo tretjega argumenta z natancnostjo, privzamemo osnovno zaokrozitvno napako
if nargin<3, delta=eps; end
% ce ne podamo cetrtega argumenta z maksimalnim stevilom iteracij, vzamemo 50
if nargin<4, maxsteps=50; end

xn=x0;
x0=Inf*xn; 
korak=0;

while (norm(xn-x0,'inf')>delta*norm(xn,'inf')) & (korak<maxsteps)
   korak=korak+1;           						 
   x0=xn;
   xn=f(x0);   
   disp(sprintf('%3d:  %s %0.1e',korak,sprintf('%15.15f  ',xn),norm(xn-x0,'inf')/norm(x0,'inf')));  % nepomembno, izpisemo trenutni priblizek
end   

y=xn;