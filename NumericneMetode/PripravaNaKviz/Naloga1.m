function naloga_1
  %format long;
  
  
  function r = norm_2(x)
      r=sqrt(sum((x.^2)));
  end
  
  function A = prepareA (size) 
    b = (1:size);
    d = 1 ./ (b .+ 1);
    b = transpose(b);
    A = fliplr(diag(d));
  end
  
  g = @(x) prepareA(length(x))*x + (1:length(x))';
  
  %function v = g(x)
  %  x2 = A*x;
  %  v = x2 + b;
  %end
    
  function [x, itt] = navadna_splosna_iteracija(g, x_0, rez, epsilon, max_iteration)
      n = size(x_0);
      x = x_0;
      for i = 2:max_iteration
          x = g(x);
          if (abs(norm_2(x - rez)) < epsilon )
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
  
    x_0 = zeros(n,1);  
    rez = (eye(n) - prepareA(n))\b
    
    [priblizki, itt] = navadna_splosna_iteracija(g, x_0, rez, epsilon, 100)
    v_iteration(n) = itt;
    
  end
  
  close all
  figure
  hold on
  grid on
  
  plot (v_iteration, 'sk');
    
  xlabel('n')
  ylabel('iter')
  
  
  
  
end