import java.util.*;

public class Izziv6 {
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

      if (realString.length() + imaginaryString.length() == 0) {
        return "0";
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

  ////// Konec Iziva 5

  private static Complex[] RazsiriArray(Complex[] arr) {
    Complex[] newArr = new Complex[arr.length * 2];
    for (int i = 0; i < newArr.length; ++i) {
      newArr[i] = new Complex();
    }

    for (int i = 0; i < arr.length; ++i) {
      newArr[i] = arr[i];
    }

    return newArr;
  }

  /// KONEC NALOGE 1

  private static Complex[] DobiKoeficiente(Complex[] arr, int zacetek, int prestop) {
    if (arr.length == 1) {
      return arr;
    }

    Complex[] r = new Complex[arr.length / prestop];
    int j = 0;
    for (int i = zacetek; i < arr.length; i += prestop) {
      r[j] = arr[i];
      j++;
    }

    return r;
  }

  private static Complex[] FFT(Complex[] arr) {
    int N = arr.length;
    if (N == 1) {
      return arr;
    }

    Complex[] soda_koeficienti = FFT(DobiKoeficiente(arr, 0, 2));
    Complex[] lihi_koeficienti = FFT(DobiKoeficiente(arr, 1, 2));

    Complex w = n_tiKoren(1, N);
    Complex wk = new Complex(1);

    Complex[] y = new Complex[N];
    for (int i = 0; i < N; ++i) {
      y[i] = new Complex();
    }

    System.out.println(
        "len" + soda_koeficienti.length + " " + lihi_koeficienti.length + " " + N / 2);
    for (int k = 0; k < N / 2; ++k) {
      Complex x1 = new Complex(soda_koeficienti[k]);
      Complex x2 = new Complex(lihi_koeficienti[k]);
      x2.krat(wk);
      x1.plus(x2);
      Complex x3 = new Complex(soda_koeficienti[k]);
      x3.minus(x2);
      y[k] = x1;
      y[k + N / 2] = x3;
      wk.krat(w);
    }

    String s = "";
    for (int i = 0; i < N; ++i) {
      s += y[i].toString() + " ";
    }

    s = s.substring(0, s.length() - 1);
    System.out.println(s);

    return y;
  }
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    int VELIKOST_TABELE = 1;
    Complex[] tabela = new Complex[VELIKOST_TABELE];
    int tabelaIndex = 0;

    while (in.hasNextInt()) {
      int vrednost = in.nextInt();

      if (tabelaIndex >= VELIKOST_TABELE) {
        tabela = RazsiriArray(tabela);
        VELIKOST_TABELE = tabela.length;
      }
      tabela[tabelaIndex] = new Complex(vrednost);
      tabelaIndex++;
    }

    Complex[] dft = FFT(tabela);
  }
}