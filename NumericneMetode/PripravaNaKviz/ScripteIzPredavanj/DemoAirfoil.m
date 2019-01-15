%% Graphical Representation of Sparse Matrices
% This demo shows the finite element mesh for a NASA airfoil, including two 
% trailing flaps.
%
% Copyright 1984-2002 The MathWorks, Inc.
% $Revision: 5.9 $ $Date: 2002/04/12 22:57:40 $

%%
% The data is stored in the file AIRFOIL.MAT.  It holds 4253 pairs of (x,y)
% coordinates of the mesh points.  It also holds an array of 12,289 pairs of
% indices, (i,j), specifying connections between the mesh points.

load airfoil

%% The finite element mesh
% First, scale x and y by 2^(-32) to bring them into the range [0,1].  Then
% form the sparse adjacency matrix and make it positive definite.

% Scaling x and y
x = pow2(x,-32); 
y = pow2(y,-32);

% Forming the sparse adjacency matrix and making it positive definite
n = max(max(i),max(j));
A = sparse(i,j,-1,n,n);
A = A + A';
d = abs(sum(A)) + 1;
A = A + diag(sparse(d));
pct = 100 / prod(size(A));,

% Plotting the finite element mesh
gplot(A,[x y])
pause

%% Visualizing the sparsity pattern
% SPY is used to visualize sparsity pattern.  SPY(A) plots the sparsity pattern
% of the matrix A.

spy(A)
title('The adjacency matrix.')
nc(1) = nnz(A);
lblstr = sprintf('nonzeros=%d   (%.2f %%)   ', nc(1),nc(1)*pct);
set(get(gca,'XLabel'),'String',lblstr);
pause

tic, L = chol(A)'; t(1) = toc
spy(L), title('Cholesky decomposition of S')
nc(2) = nnz(L);
lblstr = sprintf('nonzeros=%d   (%.2f %%)   time=%.2f sec', nc(2),nc(2)*pct,t(1));
set(get(gca,'XLabel'),'String',lblstr);
set(gca,'layer','top');
pause

%% Symmetric reordering - Reverse Cuthill-McKee
% SYMRCM uses the Reverse Cuthill-McKee technique for reordering the adjacency
% matrix.  r = SYMRCM(A) returns a permutation vector r such that A(r,r) tends
% to have its diagonal elements closer to the diagonal than A.  This is a good
% preordering for LU or Cholesky factorization of matrices that come from "long,
% skinny" problems.  It works for both symmetric and asymmetric A.

r = symrcm(A);
spy(A(r,r))
title('Reverse Cuthill-McKee')
pause

tic, L = chol(A(r,r))'; t(2) = toc;
spy(L), title('chol(A(p,p)) after Cuthill-McKee ordering')'
nc(3) = nnz(L);
lblstr = sprintf('nonzeros=%d   (%.2f %%)   time=%.2f sec', nc(3),nc(3)*pct,t(2));
set(get(gca,'XLabel'),'String',lblstr);
set(gca,'layer','top');
pause

%% Symmetric reordering - COLPERM
% Use j = COLPERM(A) to return a permutation vector that reorders the columns of
% the sparse matrix A in non-decreasing order of non-zero count.  This is
% sometimes useful as a preordering for LU factorization: lu(A(:,j)).
    
j = colperm(A);
spy(A(j,j))
title('Column count reordering')
pause

tic, L = chol(A(j,j))'; t(3) = toc;
spy(L), title('chol(A(p,p)) after column count reordering')'
nc(4) = nnz(L);
lblstr = sprintf('nonzeros=%d   (%.2f %%)   time=%.2f sec', nc(4),nc(4)*pct,t(3));
set(get(gca,'XLabel'),'String',lblstr);
set(gca,'layer','top');
pause

%% Symmetric reordering - SYMAMD
% SYMAMD gives a symmetric minimum degree permutation.  p = SYMAMD(S), for a
% symmetric positive definite matrix A, returns the permutation vector p such
% that S(p,p) tends to have a sparser Cholesky factor than S.  Sometimes SYMMMD
% works well for symmetric indefinite matrices too.

m = symamd(A);
spy(A(m,m))
title('Minimum degree')
pause

tic, L = chol(A(m,m))'; t(4) = toc;
spy(L), title('chol(A(p,p)) after simmetric reordering - SYMAMD')'
nc(5) = nnz(L);
lblstr = sprintf('nonzeros=%d   (%.2f %%)   time=%.2f sec', nc(5),nc(5)*pct,t(4));
set(get(gca,'XLabel'),'String',lblstr);
set(gca,'layer','top');
pause