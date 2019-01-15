function y=bisekcija(f,a,b,delta)

% y=BISEKCIJA(f,a,b) izvaja bisekcijo za funkcijo f na intervalu [a,b],
% kjer je f(a)*f(b)<0. Funkcija f mora biti podana v obliki inline funkcije 
% (npr. f=inline('x^2-2')). Bisekcija se konca, ko se krajisci relativno
% razlikujeta za manj kot delta glede na sirino zacetnega intervala. 
% Ce zadnjega argumenta ne podamo, je privzeta vrednost za delta 
% osnovna zaokrozitvena napaka eps.

% Bor Plestenjak 2004
% zadnja sprememba 15.10.2006

% ce ne podamo cetrtega argumenta z natancnostjo, privzamemo osnovno zaokrozitvno napako
if nargin<4, delta=eps; end

fa=f(a);
fb=f(b);
korak=0;                    				 % nepomembno, stejemo korake za izpis na zaslon
if sign(fa)==sign(fb) 
   disp('Nepravilen interval')
   return
end
epsilon = delta*abs(b-a);
while abs(b-a)>epsilon;
   c=a+(b-a)/2;
   korak=korak+1;           				 % nepomembno, stejemo korake za izpis na zaslon
   disp(sprintf('%3d: %15.15f',[korak,c]));  % nepomembno, izpisemo trenutno sredinsko tocko
   fc=f(c);
   if sign(fa)==sign(fc) 
      a=c;
      fa=fc;
   else
      b=c;
      fb=fc;
   end
end

y=c; % koncni priblizek je sredina zadnjega intervala