% integrale sin(x)*x^(k-1) na [0,1] izračunamo numerično

for k=1:20
    tekst=['sin(x).*(x.^' int2str(k-1) ')'];
    f=inline(tekst);
    c(k,1)=quadl(f,0,1);
end

% dobimo polinome najboljše enakomerne aproksimacije za sin(x) na [0,1]
% za stopnje 1,3,5,7,9,11,13


tmp = hilb(2)\c(1:2); 
a1 = tmp(end:-1:1);

tmp = hilb(4)\c(1:4); 
a2 = tmp(end:-1:1);

tmp = hilb(6)\c(1:6); 
a3 = tmp(end:-1:1);

tmp = hilb(8)\c(1:8); 
a4 = tmp(end:-1:1);

tmp = hilb(10)\c(1:10); 
a5 = tmp(end:-1:1);

tmp = hilb(12)\c(1:12); 
a6 = tmp(end:-1:1);

x=linspace(0,1,200);

plot(x,sin(x),x,polyval(a1,x),'LineWidth',2); title('Aproksimacija sin(x) s polinomom stopnje 1','FontSize',16); pause
plot(x,sin(x),x,polyval(a2,x),'LineWidth',2); title('Aproksimacija sin(x) s polinomom stopnje 3','FontSize',16); pause
plot(x,sin(x),x,polyval(a3,x),'LineWidth',2); title('Aproksimacija sin(x) s polinomom stopnje 5','FontSize',16); pause
plot(x,sin(x),x,polyval(a4,x),'LineWidth',2); title('Aproksimacija sin(x) s polinomom stopnje 7','FontSize',16); pause
plot(x,sin(x),x,polyval(a5,x),'LineWidth',2); title('Aproksimacija sin(x) s polinomom stopnje 9','FontSize',16); pause
plot(x,sin(x),x,polyval(a6,x),'LineWidth',2); title('Aproksimacija sin(x) s polinomom stopnje 11','FontSize',16);  pause

plot(x,polyval(a1,x)-sin(x),'LineWidth',2); title('Napaka pri aproksimaciji sin(x) s polinomom stopnje 1','FontSize',16); pause
plot(x,polyval(a2,x)-sin(x),'LineWidth',2); title('Napaka pri aproksimaciji sin(x) s polinomom stopnje 3','FontSize',16); pause
plot(x,polyval(a3,x)-sin(x),'LineWidth',2); title('Napaka pri aproksimaciji sin(x) s polinomom stopnje 5','FontSize',16); pause
plot(x,polyval(a4,x)-sin(x),'LineWidth',2); title('Napaka pri aproksimaciji sin(x) s polinomom stopnje 7','FontSize',16); pause
plot(x,polyval(a5,x)-sin(x),'LineWidth',2); title('Napaka pri aproksimaciji sin(x) s polinomom stopnje 9','FontSize',16); pause
plot(x,polyval(a6,x)-sin(x),'LineWidth',2); title('Napaka pri aproksimaciji sin(x) s polinomom stopnje 11','FontSize',16); 






