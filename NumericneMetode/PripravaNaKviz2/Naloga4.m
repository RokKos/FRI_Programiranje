function Naloga4

    function [P, U] = PolarniRazcepSVD(A)
        [W,S,V] = svd(A);
        P = V * S * V';
        U = W * V';
    end

    function [P,U] = Iterativno(A, epsilon, max_iter)
        Uk = A;
        for itt = 1:max_iter
              U = (Uk + inv(Uk)') / 2;
              
              if (abs(norm(Uk - U, 'fro')) < epsilon )
                  %disp('Nasli smo dovolj dober priblizek')
                  P_tilda = U' * A;
                  P = (P_tilda + P_tilda') / 2;
                  return
              end

              Uk = U;
          end
    %disp('Dosegli MAX_ITTER')
    end

A = rand(10); epsilon = 1e-10; max_iter = 100000;

%Prva metoda
[P, U] = PolarniRazcepSVD(A)
A_polar = U * P;
A - A_polar

%Druga metoda
[P, U] = Iterativno(A, epsilon, max_iter)
A_polar = U * P;
A - A_polar


durations = zeros(91, 2);
error_a = zeros(91, 2);
error_u = zeros(91, 2);

for i = 1:100
    for n = 10:100
    A = rand(n);
  
      for j = 1:2
        tic();
        if j == 1
          [U, P] = PolarniRazcepSVD(A);
        else
          [U, P] = Iterativno(A, epsilon, max_iter);
        end
        durations(n-9, j) = durations(n-9, j) + toc();
        error_a(n-9, j) = error_a(n-9, j) + norm(A - U*P);
        error_u(n-9, j) = error_u(n-9, j) + norm(U*U' - eye(size(U, 1)));
      end
    end
end

for n = 10:100
    durations(n-9, j) = durations(n-9, j) / 100;
    error_a(n-9, j) = error_a(n-9, j) / 100;
    error_u(n-9, j) = error_u(n-9, j) / 100;
end


figure(1); clf;

plot((10:100), durations)
title('mean execution duration');
legend('svd','iteration')
w = waitforbuttonpress;

plot((10:100), error_a)
title('mean norm(A - U*P)'); 
legend('svd','iteration')
w = waitforbuttonpress;

plot((10:100), error_u)
legend('svd','iteration')
title("mean norm(U*U\' - I"); 



end