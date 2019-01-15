function naloga2
  format long;
  function [nicla, itt] = bisekcija(f, a, b, max_itter, epsilon)
      
      for i = 1:max_itter
        c = (a + b) / 2;
        if abs(f(c)) < epsilon
          nicla = c;
          itt = i;
          return;
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
  
  function [nicla, itt] = regula falsi(f, a, b, max_itter, epsilon)
      for i = 1:max_itter
        fb = f(b);
        fa = f(a);
        c = (fb * a - fa*b) / (fb - fa);
        
        if abs(f(c)) < epsilon
          nicla = c;
          itt = i;
          return;
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
  max_itter = 100000;
  
  rez = fzero(f, b)
  
  [rez_bise, itt_b] = bisekcija(f, a, b, max_itter, epsilon)
  [rez_false, itt_f] = bisekcija(f, a, b, max_itter, epsilon)
  
  
 
  
end
