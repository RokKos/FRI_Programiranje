function [x,k,kstep]=durand(p,x0,eps,maxsteps)

% [y,k]=Durand(p,x0,eps,maxsteps) izvaja Durand-Kernerjevo 
% metodo za racunanje nicel polinoma

n=length(x0); % stevilo priblizkov

kon=zeros(n,1);  % oznacimo tiste, ki so ze skonvergirali
stkon=0; % stevilo ze skonvergiranih vrednosti
iter=0; % stevilo iteracij
x=x0;
kstep=zeros(n,1);

alfa=p(1); % vodilni koeficient
while (iter<maxsteps) & (stkon<n)
   disp(x);
   for j=1:n
        if kon(j)==0
            iter=iter+1;
            kstep(j)=kstep(j)+1;
            f=polyval(p,x(j));
            if f ~= 0
                dif=1;
                for k=1:n
                    if k~=j
                        dif=dif*(x(j)-x(k));
                    end
                end
                novo=x(j)-f/(alfa*dif);
             else
                 novo=x(j);
             end
             razlika=abs(novo-x(j))/abs(novo);
             x(j)=novo;
             if razlika<eps
                 stkon=stkon+1;
                 kon(j)=1;
             end
         end
     end
end
k=iter;


   