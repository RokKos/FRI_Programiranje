function kviz1
    format long

    function r = mat_norm_frobenius(A)
        r = sqrt(sum(sum(A.^2)));
    end

    seme = 2; 
    rand('seed', seme);
    b = rand(1);

    A = eye(50);
    for i = 1:50
    	for j = 1:50
            A(i,j) = A(i,j) + sin(b*i/j);
        end
    end
    %nujno z vejicami outputaj
    f = mat_norm_frobenius(A)
    
    
    
    function [L,U] = lu_razcep(A)
        [n,m] = size(A);
        L = eye(n);
        for j = (1:n-1)
            a_jj = A(j,j);
            %if (a_jj < eps) warning('Ni veljavna matrika') end
           for i = (j+1:n)
               l_ij = A(i,j)./a_jj;
               L(i,j) = l_ij;
               for k = (j+1:n)
                  A(i,k) = A(i,k) - l_ij*A(j,k);
               end
           end
        end
        U = triu(A);
    end


    function x = resi_z_lu(L, U, b)
        %Ly = b 
        n = length(b);
        y = zeros(n,1)
        for i = (1:n)
            k = (1:i-1);
            s = sum (L(i,k)*y(k));
            y(i) = b(i) - s;
        end
        
        
        %Ux = by
        x = zeros(n,1)
        for i = (n:-1:1)
           k = (i+1:n);
           s = sum (U(i,k)*x(k));
           
           x(i) = (1 / U(i,i)) * (y(i) - s); 
        end
        
        
    end


    [L,U] = lu_razcep(A)
    enke = ones(50,1)
    x = resi_z_lu(L,U, enke)
    x
    max(x)
end