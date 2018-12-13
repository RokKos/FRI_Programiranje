function [L,U] = lubasic(A) 

% LU razcep matrike A brez pivotiranja,
% v tej osnovni varianti imamo tri vgnezdne zanke,
% delamo s posameznimi elementi
%
% pred testi vkljuci: feature accel off

n = size(A,2);
for j = 1 : n-1 
   for i = j+1 : n
      A(i,j) = A(i,j)/A(j,j);
      for k = j+1 : n
         A(i,k)=A(i,k)-A(i,j)*A(j,k);
      end
   end
end   
U = triu(A);
L = eye(n)+tril(A,-1);
