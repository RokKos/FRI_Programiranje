function Naloga1
  format compact;  
  function V = razcepCholeskega(A) 
    n = length(A);
    V = zeros(n);
    for j = 1:n
      sum_vjk = 0;
      for k = 1:j-1
        sum_vjk += V(j,k)^2;
      end
      
      V(j,j) = sqrt(A(j,j) - sum_vjk);  
      
      inv_vjj = 1 / V(j,j);
      
      for i = (j+1):n
        sum_v_ik_v_jk = 0; 
        for k = 1:j-1
          sum_v_ik_v_jk += V(i,k)*V(j,k);
        end
        
        V(i,j) = inv_vjj * (A(i,j) - sum_v_ik_v_jk);
      end
    end
  end
  
  A = [14, -6, 4, 2;
       -6, 10, -5, -2;
       4, -5, 9, 0;
       2, -2, 0, 8]
       
  b = [1,-2,0,3]'
  
  V_matlab = chol(A)'
  V = razcepCholeskega(A)
  
  nazaj_A = V*V'
  
  y = V\b
  x = V'\y
  
  x_matlab = A\b
  
end
