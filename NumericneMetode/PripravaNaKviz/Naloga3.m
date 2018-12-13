function naloga3
  format short e;
  
  
  function A = PrepareValA(x,y)
    
    a11 = sin(x);
    a12 = 1 ./ (y.^2 .+ 3);
    a21 = log(abs(2.*y.+5));
    a22 = exp(-x.^2);
    A = a11 .* a22 .- a12 .* a21;
    
  end
  
  x = (-2:0.1:2);
  y = (-2:0.1:2);
  [X,Y] = meshgrid(x,y);
  
  
  
  n = length(x);
  f_xy = PrepareValA(X,Y);
   
  
  %close all
  %figure
  %hold on
  %grid on
  clf;
  surf (X,Y,f_xy);
  shading interp;
  
  % new graph0
  pari = zeros(n);
  contour(X, Y, f_xy, levels=[-0.4, -0.4])
  
  %for i = (1:n)
  %  for j = (1:n)
      
  
  %close all
  %figure
  %hold on
  %grid on
  %clf;
  
  
  
end