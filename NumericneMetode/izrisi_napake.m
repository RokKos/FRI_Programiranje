function a = izrisi_napake()
    close all
    
    
    g1 = @(x) sqrt(-log(x));
    g2 = @(x) exp(-x.^2);
    g3 = @(x) (x + exp(-x.^2)) / 2;
    g4 = @(x) (2 .* x.^3 + exp(-x.^2)) / (1 + 2 .* x.^2);
    
    nicla = fzero(@(x) x .^2 + log(x), 0.5);
    
    tol = 1e-6;
    x0 = 0.5;
    max_itteration = 100;
    
    
    x1 = navadna_iteracija(g1, x0, tol, max_itteration);
    x2 = navadna_iteracija(g2, x0, tol, max_itteration);
    x3 = navadna_iteracija(g3, x0, tol, max_itteration);
    x4 = navadna_iteracija(g4, x0, tol, max_itteration);
    
    figure
    hold on
    plot (abs(x1 - nicla), 'sk');
    plot (abs(x2 - nicla), 'or');
    plot (abs(x3 - nicla), 'xg');
    plot (abs(x4 - nicla), 'vb');
    
    set(gca, 'yscale', 'log')
    
    xlabel('iter')
    ylabel('napaka')
    
end