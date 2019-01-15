% Tocke iz obmocja v kompleksni ravnini [-1,1] x [-1,1] uporabimo za 
% zacetne priblizke tangentne metode za resevanje enacbe x^3 + 1 = 0, 
% ki ima tri resitve. Vsako tocko pobarvamo glede na to, h kateri izmed 
% treh resitev konvergira tangentna metoda. Dobimo lepo fraktalno sliko.

% Bor Plestenjak 2014

n = 500;   % na koliko tock razdelimo realni in imaginarni interval
step = 30; % koliko korakov tangentne metode izvedemo
x = linspace(-1,1,n); % tabliramo realne dele
y = linspace(-1,1,n); % tabeliramo imaginarne dele
f = @(x) (2*x - x.^(-2))/3; % funkcija za en korak tangentna metode
M = kron(x,ones(n,1))-i*kron(y,ones(n,1))'; % matrika za celo mrezo
for r=1:step % iteriramo tangentno metodo na celi mrezi
    M = f(M);
end
    
z = roots([1 0 0 1]); % tri nicle 
M1 = abs(M-z(1)*ones(1,1));
M2 = abs(M-z(2)*ones(1,1));
M3 = abs(M-z(3)*ones(1,1));

T = min(min(M1,M2),M3);

figure('Position',[50 50 650 650]) 
C = zeros(n,n,3);
C(:,:,1) =  T==M1;
C(:,:,2) =  T==M2;
C(:,:,3) =  T==M3;
image(C)