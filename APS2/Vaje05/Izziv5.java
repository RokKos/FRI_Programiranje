import java.util.*;

public class Izziv5 {
  private static final String kMessage = "Vtipkaj ukaz (+, -, *, /, w):";

  private static class Complex {
    private double real;
    private double imaginary;

    public Complex() {
      real = 0;
      imaginary = 0;
    }

    public Complex(double _real) {
      real = _real;
      imaginary = 0;
    }

    /*public Complex(double _imaginary) {
      real = 0;
      imaginary = _imaginary;
    }*/

    public Complex(double _real, double _imaginary) {
      real = _real;
      imaginary = _imaginary;
    }

    public Complex(Complex rhs) {
      real = rhs.getReal();
      imaginary = rhs.getImaginary();
    }

    public double getReal() {
      return real;
    }

    public double getImaginary() {
      return imaginary;
    }

    public void plus(Complex rhs) {
      real += rhs.getReal();
      imaginary += rhs.getImaginary();
    }

    public void minus(Complex rhs) {
      real -= rhs.getReal();
      imaginary -= rhs.getImaginary();
    }

    public void krat(Complex rhs) {
      double a = real;
      double b = imaginary;
      double c = rhs.getReal();
      double d = rhs.getImaginary();

      real = a * c - b * d;
      imaginary = a * d + b * c;
    }

    public void deljeno(Complex rhs) {
      double a = real;
      double b = imaginary;
      double c = rhs.getReal();
      double d = rhs.getImaginary();

      double imenovalec = c * c + d * d;

      real = (a * c + b * d) / imenovalec;
      imaginary = (b * c - a * d) / imenovalec;
    }

    public String toString() {
      String realString = "";
      if (Math.abs(real) > 0.00001) {
        realString = String.format("%.5f", real);
        // 0* pomeni kolikor koli 0, $ pa pomeni od konca stringa
        realString = realString.replaceAll("0*$", "");
        if (realString.charAt(realString.length() - 1) == '.') {
          realString = realString.substring(0, realString.length() - 1);
        }
      }

      String imaginaryString = "";
      if (Math.abs(imaginary) > 0.00001) {
        String predznak = imaginary < 0 ? "-" : "+";
        imaginaryString = predznak + String.format("%.5f", Math.abs(imaginary));
        // 0* pomeni kolikor koli 0, $ pa pomeni od konca stringa
        imaginaryString = imaginaryString.replaceAll("0*$", "");
        if (imaginaryString.charAt(imaginaryString.length() - 1) == '.') {
          imaginaryString = imaginaryString.substring(0, imaginaryString.length() - 1);
        }
        imaginaryString += "i";
      }

      return realString + imaginaryString;
    }
  }

  private static Complex n_tiKoren(int j, int n) {
    double real = Math.cos(2 * Math.PI * ((double) j / (double) n));
    double imaginary = Math.sin(2 * Math.PI * ((double) j / (double) n));

    return new Complex(real, imaginary);
  }

  private static Complex parseComplex(String s) {
    s = s.substring(0, s.length() - 1);
    // System.out.println(s);

    boolean predznak = false; // false -> -, true -> +
    int split_index = 0;
    for (int i = 1; i < s.length(); ++i) {
      if (s.charAt(i) == '+' || s.charAt(i) == '-') {
        split_index = i;
        break;
      }
    }

    String realString = s.substring(0, split_index);
    String imaginaryString = s.substring(split_index, s.length());
    // System.out.println(realString + " " + imaginaryString);

    double real = Double.parseDouble(realString);
    double imaginary = Double.parseDouble(imaginaryString);

    return new Complex(real, imaginary);
  }

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    do {
      System.out.println(kMessage);
      String op = in.next();
      if (op.trim().isEmpty()) {
        break;
      } else if (op.equals("w")) {
        int N = in.nextInt();
        for (int i = 1; i < N; ++i) {
          System.out.print(n_tiKoren(i, N).toString() + " ");
        }
        System.out.println(n_tiKoren(N, N).toString());
      } else {
        Complex c1 = parseComplex(in.next());
        Complex c2 = parseComplex(in.next());

        switch (op) {
          case "+":
            c1.plus(c2);
            break;

          case "-":
            c1.minus(c2);
            break;

          case "*":
            c1.krat(c2);
            break;

          case "/":
            c1.deljeno(c2);
            break;

          default:
            System.out.println("Napacna operacija.");
            System.exit(1);
            break;
        }

        System.out.println(c1.toString());
      }

    } while (true);
  }
}