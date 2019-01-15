   load('west0479.mat'),
   n=479;
   A = west0479(1:n,1:n);,
   S = A * A' + speye(n,n);,
   pct = 100 / prod(size(A));,
   spy(S), title('A Sparse Symmetric Matrix')
     n = nnz(S);,
     lblstr = sprintf('nonzeros=%d   (%.3f %%)',n,n*pct);,
     set(get(gca,'XLabel'),'String',lblstr);,
     set(gca,'layer','top');
     pause
     
     
   tic, L = chol(S)'; t(1) = toc
   spy(L), title('Cholesky decomposition of S')
   nc(1) = nnz(L);
   lblstr = sprintf('nonzeros=%d   (%.2f %%)   time=%.2f sec', nc(1),nc(1)*pct,t(1));
   set(get(gca,'XLabel'),'String',lblstr);
   set(gca,'layer','top');
   pause
   
   p = symrcm(S);
   spy(S(p,p)), title('S(p,p) after Cuthill-McKee ordering')
   n = nnz(S);
   lblstr = sprintf('nonzeros=%d   (%.3f %%)',n,n*pct);
   set(get(gca,'XLabel'),'String',lblstr);
   set(gca,'layer','top');
   pause
   
   tic, L = chol(S(p,p))'; t(2) = toc;
   spy(L), title('chol(S(p,p)) after Cuthill-McKee ordering')'
   nc(2) = nnz(L);
   lblstr = sprintf('nonzeros=%d   (%.2f %%)   time=%.2f sec', nc(2),nc(2)*pct,t(2));
   set(get(gca,'XLabel'),'String',lblstr);
   set(gca,'layer','top');
   pause
   
   q = colperm(S);
   spy(S(q,q)), title('S(q,q) after column count ordering')'
   n = nnz(S);
   lblstr = sprintf('nonzeros=%d   (%.3f %%)',n,n*pct);
   set(get(gca,'XLabel'),'String',lblstr);
   set(gca,'layer','top');
   pause
   
   tic, L = chol(S(q,q))'; t(3) = toc;
   spy(L), title('chol(S(q,q)) after column count ordering')
   nc(3) = nnz(L);
   lblstr = sprintf('nonzeros=%d   (%.2f %%)   time=%.2f sec',nc(3),nc(3)*pct,t(3));
   set(get(gca,'XLabel'),'String',lblstr);
   set(gca,'layer','top');
   pause
  %========== Slide 9 ==========

   r = symmmd(S);
   spy(S(r,r)), title('S(r,r) after minimum degree ordering')
   n = nnz(S);
   lblstr = sprintf('nonzeros=%d   (%.3f %%)',n,n*pct);
   set(get(gca,'XLabel'),'String',lblstr);
   set(gca,'layer','top');
   pause
  %========== Slide 10 ==========

   tic, L = chol(S(r,r))'; t(4) = toc;
   spy(L), title('chol(S(r,r)) after minimum degree ordering')
   nc(4) = nnz(L);
   lblstr = sprintf('nonzeros=%d   (%.2f %%)   time=%.2f sec', nc(4),nc(4)*pct,t(4));
   set(get(gca,'XLabel'),'String',lblstr);
   set(gca,'layer','top');
     