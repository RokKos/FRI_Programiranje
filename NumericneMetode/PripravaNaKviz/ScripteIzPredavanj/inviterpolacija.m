function y=invinterpolacija(f,x0,x1,x2,delta,maxsteps)

% y=INVINTERPOLACIJA(f,x0,x1,x2,delta,maxsteps) dela inverzno kvadratno interpolacijo za funkcijo f 
% z zacetnimi priblizki x0, x1 in x2. Funkcija f mora biti podana v obliki inline funkcije 
% (npr. f=inline('x^2-2')). Iteracija se konca, ko se zadnja priblizka relativno
% razlikujeta za manj kot delta ali ko prekoracimo maksimalno stevilo korakov maxsteps.
% Ce zadnjih dveh argumentov ne podamo, je privzeta vrednost za delta osnovna 
% zaokrozitvena napaka eps, za maxsteps pa 50.

% Bor Plestenjak 2004

if nargin<5, delta=eps; end
if nargin<6, maxsteps=50; end

korak=0;                    						 
while (abs(x2-x1)>delta*abs(x2)) & (korak<maxsteps)
    tmp=interpolacija([f(x2) f(x1) f(x0)]',[x2 x1 x0]',0); % vrednost p(0)
    x0=x1;
    x1=x2;
    x2=tmp; 
    korak=korak+1;
    disp(sprintf('%3d: %15.15f  %15.15f  %15.15f',[korak,x2,x1,x0]));  
end   

y=x2;

% -----------------------------------------------------------------------------------------------

function y=interpolacija(X,Y,x);

% Aitken-Nevillova shema za interpolacijo
% Bor Plestenjak 2003

n=length(X);
I=zeros(n);
I(:,1)=Y;
for k=2:n
   for j=k:n
      I(j,k)=1/(X(j)-X(j-k+1))*((x-X(j-k+1))*I(j,k-1)-(x-X(j))*I(j-1,k-1));
   end
end
y=I(n,n);