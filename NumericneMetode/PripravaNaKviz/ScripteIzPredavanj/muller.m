function y=muller(f,x0,x1,x2,delta,maxsteps)

% y=MULLER(f,x0,x1,x2,delta,maxsteps) dela Mullerjevo metodo za funkcijo f 
% z zacetnimi priblizki x0, x1 in x2. Funkcija f mora biti podana v obliki inline funkcije 
% (npr. f=inline('x^2-2')). Iteracija se konca, ko se zadnja priblizka relativno
% razlikujeta za manj kot delta ali ko prekoracimo maksimalno stevilo korakov maxsteps.
% Ce zadnjih dveh argumentov ne podamo, je privzeta vrednost za delta osnovna 
% zaokrozitvena napaka eps, za maxsteps pa 50.

% Bor Plestenjak 2006

if nargin<4, delta=eps; end
if nargin<5, maxsteps=50; end

xn=x2;
x2=x1;
x1=x0;
korak=0;                    						 
while (abs(xn-x2)>delta*abs(xn)) & (korak<maxsteps)
    x0=x1;
    x1=x2;
    x2=xn;
    ulomek = (x0-x2)*(x1-x2)*(x0-x1);
    a = ( (x1-x2)*(f(x0)-f(x2))-(x0-x2)*(f(x1)-f(x2)) )/ulomek;
    b = ( (x0-x2)^2*(f(x1)-f(x2))-(x1-x2)^2*(f(x0)-f(x2)) )/ulomek;
    c = f(x2);
    D = sqrt(b^2-4*a*c);
    if (b+D)>(b-D) 
        xn = x2 - 2*c/(b-sqrt(b^2-4*a*c));
    else
        xn = x2 - 2*c/(b+sqrt(b^2-4*a*c));
    end        
    korak=korak+1;
    if (imag(xn)~=0)
        disp(sprintf('%3d: %15.15f %+15.15fi',[korak,real(xn),imag(xn)]));  
    else
        disp(sprintf('%3d: %15.15f',[korak,xn]));  
    end
end   

y=xn;