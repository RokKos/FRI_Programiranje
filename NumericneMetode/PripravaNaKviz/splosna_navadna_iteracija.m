function sez_priblizki = navadna_iteracija(g, x_0, epsilon, max_iteration)
    sez_priblizki = zeros(max_iteration, max_iteration);
    sez_priblizki(1, :) = x_0;
    for i = 2:max_iteration
        sez_priblizki(i) = g(sez_priblizki(i-1));
        if (abs(sez_priblizki(i) - sez_priblizki(i-1)) < epsilon * sez_priblizki(i-1))
            sez_priblizki = sez_priblizki(1:i)
            disp('Nasli smo dovolj dober priblizek')
            return
        end
    end
    disp('Dosegli smo max iteration.')
end