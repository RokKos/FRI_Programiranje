function y=tangentna(f,fprime,x0,delta,maxsteps)

% y=TANGENTNA(f,fprime,x0,delta,maxsteps) dela tangentno metodo za funkcijo f 
% in odvod fprime z zacetnim priblizkom x0. Funkciji f in fprime morata biti podani 
% v obliki inline funkcij (npr. f=inline('x^2-2')). Iteracija se konca, ko se zadnja 
% priblizka relativno razlikujeta za manj kot delta ali ko prekoracimo maksimalno 
% stevilo korakov maxsteps. Ce zadnjih dveh argumentov ne podamo, je privzeta vrednost 
% za delta osnovna zaokrozitvena napaka eps, za maxsteps pa 50.

% Bor Plestenjak 2004

if nargin<4, delta=eps; end
if nargin<5, maxsteps=50; end

xn=x0;
x0=Inf;
korak=0;                    						 
while (abs(xn-x0)>delta*abs(xn)) & (korak<maxsteps)
    korak=korak+1;           						    
    x0=xn;
    xn=x0-f(x0)/fprime(x0);
    if (imag(xn)~=0)
        disp(sprintf('%3d: %15.15f %+15.15fi',[korak,real(xn),imag(xn)]));  
    else
        disp(sprintf('%3d: %15.15f',[korak,xn]));  
    end
end   

y=xn;