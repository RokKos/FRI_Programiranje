
  r = linspace(1.5,4,300);
  
  f2 = @(x,y) x.^3 + y.^3 - 3 .* x .* y - 1;
  g = @(x,y) exp(x.^2) + y .* exp(y.^2) - r;
  
  
  %differentiate x^3 + y^3 - 3xy = 1 with respect to x
  %differentiate x^3 + y^3 - 3xy = 1 with respect to y
  %differentiate exp(x^2) + y*exp(y^2) = 2 with respect to x
  %differentiate exp(x^2) + y*exp(y^2) = 2 with respect to y
  
  function F = f (x)
    F = zeros(3,1);
    F(1) = x(1).^3 + x(2).^3 - 3 .* x(1) .* x(2) - 1;
    F(2) = exp(x(1).^2) + x(2) .* exp(x(2).^2) - x(3);
    F(3) = ((x(1).^2 - x(2)) / (x(1) - x(2).^2)) * (exp(x(2).^2) * (-x(2).^2 - 1/2)/(exp(x(1).^2)) .* x(1))  - ((x(2).^2 - x(1)) / (x(1) - x(2).^2)) * ((2*x(1).*exp(x(1).^2 - x(2).^2))/(2*x(2).^2+1)) 
  end
  
  %clear all;
  %figure
  %hold on
  %fimplicit(f,[-3 3 -3 3])
  %fimplicit(g,[-3 3 -3 3])
  
  
  [x, fval, info] = fsolve (@f, [1; 2;3])

  
