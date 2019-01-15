function Naloga1
  format compact;  
  function V = razcepCholeskega(A) 
    n = length(A);
    V = zeros(n);
    for j = 1:n
      sum_vjk = 0;
      for k = 1:j-1
        sum_vjk = sum_vjk + V(j,k)^2;
      end
      
      V(j,j) = sqrt(A(j,j) - sum_vjk);  
      
      inv_vjj = 1 / V(j,j);
      
      for i = (j+1):n
        sum_v_ik_v_jk = 0; 
        for k = 1:j-1
          sum_v_ik_v_jk = sum_v_ik_v_jk + V(i,k)*V(j,k);
        end
        
        V(i,j) = inv_vjj * (A(i,j) - sum_v_ik_v_jk);
      end
    end
  end
  
  function x = GaussSeidel(A, b, x_0, epsilon, max_itter)
    n = length(b);
    x = zeros(n,1);
    for itt = 2:max_itter
          for i = 1:n
            sum_r_pp = 0;
            for j = 1:(i-1)
              sum_r_pp = sum_r_pp + A(i,j) * x(j);              
            end
            
            sum_r = 0;
            for j = (i+1):n
              sum_r = sum_r + A(i,j) * x_0(j);              
            end
            
            x(i) = 1 / A(i,i) * (b(i) - sum_r_pp - sum_r);
          end
          if (abs(norm(x) - norm(x_0)) < epsilon )
              disp('Nasli smo dovolj dober priblizek')
              return
          end
          
          x_0 = x;
      end
    disp('Dosegli MAX_ITTER')
    
  end
  
  A = [14, -6, 4, 2;
       -6, 10, -5, -2;
       4, -5, 9, 0;
       2, -2, 0, 8]
       
  b = [1,-2,0,3]'
  
  V_matlab = chol(A)'
  % Z razcepom choleskega
  V = razcepCholeskega(A)
  
  nazaj_A = V*V';
  
  y = V\b
  x = V'\y
  
  x_seidel = GaussSeidel(A, b, [1,1,1,1]', 1e-10, 1000)
  
  
  % Preverjanje
  x_matlab = A\b
  
end
