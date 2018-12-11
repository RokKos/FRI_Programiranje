function naloga2
  
  function [nicla, itt] = bisekcija(f, a, b, max_itter, epsilon)
      
      for i = 1:max_itter
        c = (a + b) / 2;
        if abs(f(c)) < epsilon
          nicla = c;
          itt = i;
        end
        
        if f(c) > 0    
          b = c;
        else
          a = c;
        end
       
      end
      
      
      nicla = c;
      itt = max_itter;
      
  end
  
  
  epsilon = 1e-8;
  f = @(x) 3*x^3 - exp(sin(x));
  a = 0;
  b = 2;
  max_itter = 1000;
  
  [rez_bise, itt_b] = bisekcija(f, a, b, max_itter, epsilon)
  
  rez = fzero(f, b)
 
  
end
