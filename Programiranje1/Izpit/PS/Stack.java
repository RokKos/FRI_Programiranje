import java.util.Stack;

// Deluje na principu LIFO (last in first out)
Stack s = new Stack();
int a = 4;
s.push(a);  // Doda na vrh sklada
int b = s.pop();  // Vzame z vrha sklada
int b = s.peek();  // Pogleda na vrh sklada
boolean prazen = s.empty();
