function Naloga2

format shortE;

P = load('podatki.txt');
x = P(:,1);
y = P(:,2);

% vector z = [a1, alpha, a3 + a2, b1, b2, beta, a4]
C = [x.^2, x.^2 .* y, x.*y, x, y, x.*y.^2, y.^2];
% vector d = [c, ..., c]
d = ones(length(x),1);

% Resi predolocen sistem, kjer hocemo minimizirat norm(C*z - d)
z = C \ d


rezidual = C*z - d
rez_norm = norm(rezidual)

% A is simetrical so we know that a2 = a3
A = [z(1), z(3) / 2;
    z(3)/2, z(7)]

b = [z(4), z(5)]

alpha = z(2)
beta = z(6)


end