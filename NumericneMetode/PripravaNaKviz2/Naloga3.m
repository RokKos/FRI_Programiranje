function Naloga3
    x = linspace(-3, 3, 300);
    y = linspace(-3, 3, 300);

    [X, Y] = meshgrid(x, y);

    Z1 = X.^3 + Y.^3 - 3*X.*Y;
    Z2 = exp(X.^2) + Y.*exp(Y.^2);

    figure(1); clf; hold on;
    contour(X, Y, Z1, [1,1], 'b');
    contour(X, Y, Z2, linspace(1.4, 4, 10), 'r');
  
    %differentiate x^3 + y^3 - 3xy = 1 with respect to x
    %differentiate x^3 + y^3 - 3xy = 1 with respect to y
    %differentiate exp(x^2) + y*exp(y^2) = 2 with respect to x
    %differentiate exp(x^2) + y*exp(y^2) = 2 with respect to y

    function F = f_ext (x,y,r)
        F = zeros(3,1);
        F(1) = x^3 + y^3 - 3 * x * y - 1;
        F(2) = exp(x^2) + y * exp(y^2) -r;
        F(3) = ((x^2 - y) / (x - y^2)) * (exp(y^2) * (-y^2 - 1/2)/(exp(x^2) * x)) - ((y^2 - x) / (x^2 - y)) * ((2*x*exp(x^2 - y^2))/(2*y^2+1));
    end

    F_Solve = @(X) f_ext(X(1), X(2), X(3));
    F_Min = @(X) norm(f_ext(X(1), X(2), X(3)));

    x = fsolve (F_Solve, [-0.5; 0.5; 2.5])
    x2 = fminsearch(F_Min, [-0.5; 0.5; 2.5])

    r = x(3);
    presecisce_1_x = x(1)
    presecisce_1_y = x(2)
    
    figure(2); clf; hold on;

    f = @(x,y) x^3 + y^3 - 3 .* x .* y - 1;
    g = @(x,y) exp(x^2) + y .* exp(y^2) - r;

    fimplicit(f,[-10 10 -10 10])
    fimplicit(g,[-10 10 -10 10])
    
    
    function F = f_ext_2 (x,y)
        F = zeros(2,1);
        F(1) = x^3 + y^3 - 3 * x * y - 1;
        F(2) = exp(x^2) + y * exp(y^2) -r;
    end
    F_Second_Cross = @(X) f_ext_2(X(1), X(2));
    F_Second_Cross_Norm = @(X) norm(F_Second_Cross(X));

    x = fsolve (F_Second_Cross, [0.8; 0])
    x2 = fminsearch(F_Second_Cross_Norm, [0.8; 0])
    
    presecisce_1_x = x(1)
    presecisce_1_y = x(2)

    
  
end