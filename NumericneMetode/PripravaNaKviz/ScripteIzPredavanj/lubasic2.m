function [L,U] = lubasic2(A) 

% LU razcep matrike A brez pivotiranja,
% v tej varianti imamo dve vgnezdeni zanki,
% delamo z vrsticami

n = size(A,2);
for j = 1 : n-1 
   for i = j+1 : n
      A(i, j) = A(i, j) / A(j, j);
      A(i, j+1:n) = A(i, j+1:n) - A(i, j) * A(j, j+1:n);
   end
end   
U = triu(A);
L = eye(n)+tril(A,-1);
