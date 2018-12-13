A=eye(100);
A(1:99,100)=ones(99,1)*0.1;
A(100,1:99)=ones(1,99)*0.1;
spy(A);
pause
[L,U,P]=lu(A);
spy(L);
pause
spy(U);
pause

A=eye(100);
A(1:99,100)=ones(99,1)*1.1;
A(100,1:99)=ones(1,99)*1.1;
A(20:25,5:10)=1+rand(6,6);
spy(A);
pause;
[L,U,P]=lu(A);
spy(L);
pause
spy(U);
pause

A=eye(100);
A(2:100,1)=ones(99,1)*0.1;
A(1,2:100)=ones(1,99)*0.1;
spy(A);
pause;
[L,U,P]=lu(A);
spy(L);
pause
spy(U);


