function [L,U] = lubasic3(A) 

% LU razcep matrike A brez pivotiranja
% v tej varianti imamo le se eno zanko,
% delamo s celo podmatriko naenkrat

n = size(A,2);
for j = 1 : n-1 
   A(j+1:n, j)=A(j+1:n, j) / A(j,j);
   A(j+1 : n, j+1:n) = A(j+1 : n, j+1:n) - A(j+1 : n, j) * A(j, j+1:n);
end   
U = triu(A);
L = eye(n)+tril(A,-1);
