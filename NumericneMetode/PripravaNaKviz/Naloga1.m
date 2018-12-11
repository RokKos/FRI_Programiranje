function naloga_1
  %format long;
  
  
  function r = norm_2(x)
      r=sqrt(sum((x.^2)));
  end
  
  %function v = g(x)
  %  x2 = A*x;
  %  v = x2 + b;
  %end
    
  function [sez_priblizki, itt] = navadna_splosna_iteracija(g, x_0, epsilon, max_iteration)
      n = size(x_0)
      sez_priblizki = zeros(max_iteration, n(1));
      sez_priblizki(1, :) = x_0;
      for i = 2:max_iteration
          sez_priblizki(i, :) = g(transpose(sez_priblizki(i-1, :)));
          if (abs(norm_2(sez_priblizki(i,:)) - norm_2(sez_priblizki(i-1, :))) < epsilon )
              sez_priblizki = sez_priblizki(1:i, :);
              itt = i;
              disp('Nasli smo dovolj dober priblizek')
              return
          end
      end
      disp('Dosegli smo max iteration.')
      itt = max_iteration;
  end
  
  %test od 1 do 10
  epsilon = 1e-6;
  
  v_iteration = zeros(10,1);
  
  for n = (1:10)
  
    b = (1:n);
    d = 1 ./ (b .+ 1);
    b = transpose(b);
    A = fliplr(diag(d))
    x_0 = zeros(n,1);
    g = @(x) (A*x) + b;
    
    [priblizki, itt] = navadna_splosna_iteracija(g, x_0, epsilon, 1000)
    v_iteration(n) = itt;
    rez = -A \ b
  end
  
  close all
  figure
  hold on
  grid on
  
  plot (v_iteration, 'sk');
    
  xlabel('n')
  ylabel('iter')
  
  
  
  
end