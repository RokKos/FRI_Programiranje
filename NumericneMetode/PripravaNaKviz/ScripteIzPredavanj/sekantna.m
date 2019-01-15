function y=sekantna(f,x0,x1,delta,maxsteps)

% y=SEKANTNA(f,x0,x1,delta,maxsteps) dela sekantno metodo za funkcijo f 
% z zacetnima priblizkoma x0 in x1. Funkcija f mora biti podana v obliki inline funkcije 
% (npr. f=inline('x^2-2')). Iteracija se konca, ko se zadnja priblizka relativno
% razlikujeta za manj kot delta ali ko prekoracimo maksimalno stevilo korakov maxsteps.
% Ce zadnjih dveh argumentov ne podamo, je privzeta vrednost za delta osnovna 
% zaokrozitvena napaka eps, za maxsteps pa 50.

% Bor Plestenjak 2004

if nargin<4, delta=eps; end
if nargin<5, maxsteps=50; end

xn=x1;
x1=x0;
korak=0;                    						 
while (abs(xn-x1)>delta*abs(xn)) & (korak<maxsteps)
    x0=x1;
    x1=xn;
    xn=x1-(x1-x0)/(f(x1)-f(x0))*f(x1);
    korak=korak+1;
    if (imag(xn)~=0)
        disp(sprintf('%3d: %15.15f %+15.15fi',[korak,real(xn),imag(xn)]));  
    else
        disp(sprintf('%3d: %15.15f',[korak,xn]));  
    end
end   

y=xn;