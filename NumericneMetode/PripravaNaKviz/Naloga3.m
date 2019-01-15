function naloga3
  format short e;
  
  
  function A = PrepareValA(x,y)
    
    a11 = sin(x);
    a12 = 1 ./ (y.^2 .+ 3);
    a21 = log(abs(2.*y.+5));
    a22 = exp(-x.^2);
    A = a11 .* a22 .- a12 .* a21;
    
  end
  
  function A = PrepareA(x,y)
    
    a11 = sin(x);
    a12 = 1 ./ (y.^2 .+ 3);
    a21 = log(abs(2.*y.+5));
    a22 = exp(-x.^2);
    A = [a11 , a12; a21, a22];
    
  end
  
  x = (-2:0.1:2);
  y = (-2:0.1:2);
  [X,Y] = meshgrid(x,y);
  
  
  
  n = length(x);
  f_xy = PrepareValA(X,Y);
   
  
  %close all
  figure(1);
  %hold on
  %grid on
  clf;
  subplot(1, 3, 1);
  surf (X,Y,f_xy);
  shading interp;
  
  % new graph
  subplot(1, 3, 2);
  M = contour(X, Y, f_xy, levels=[-0.4, -0.4])
  num_krivulje = sum(M(1,:) == -0.4)
  
  
  % norme
    function r = norm_1(x)
        r=sum(abs(x));
    end

    function r = norm_2(x)
        r=sqrt(sum((x.^2)));
    end

    function r = norm_inf(x)
        r=max(abs(x));
    end

    function r = mat_norm_1(A)
        r=max(max(sum(abs(A))));
    end

    function r = mat_norm_inf(A)
        A = A.';
        r = mat_norm_1(A);
    end

    function r = mat_norm_frobenius(A)
        r = sqrt(sum(sum(A.^2)));
    end

    function r = n_inf_mat(A)
        r = max(max(abs(A)));
    end
  
    m_norm1 = zeros(n,1);
    m_normInf = zeros(n,1);
    m_norm2 = zeros(n,1);
    m_normForb = zeros(n,1);
    for i = (1:n)
      x_i = x(i); 
      X_A = PrepareA(x_i,x_i);
      m_norm1(i) = mat_norm_1(X_A);
      m_normInf(i) = mat_norm_inf(X_A);
      m_norm2 = norm(X_A);
      m_normForb = mat_norm_frobenius(X_A);
    
    end
    figure(4); clf;
    
    subplot(1, 4, 1); plot(x, m_norm1); title("norm1");
    subplot(1, 4, 2); plot(x, m_norm2); title("norm2");
    subplot(1, 4, 3); plot(x, m_normForb); title("normF");
    subplot(1, 4, 4); plot(x, m_normInf); title("normInf");
  
    ujemanje_x = m_norm1 == m_normForb
  
end