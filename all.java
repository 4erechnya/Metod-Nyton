import java.util.Scanner;

class NewtonChord {

    
    static double f(double x) {
        return 2 * x - 1.0 / Math.tan(x);
    }

   
    static double df(double x) {
        double s = Math.sin(x);
        return 2 + 1.0 / (s * s);
    }

    
    static void newton(double x0, double eps, int maxIter) {
        System.out.println("\n=== Метод Ньютона ===");
        System.out.printf("%-6s %-15s %-15s %-15s%n", "Итер", "x", "f(x)", "|x - x_prev|");

        double x = x0;
        double xPrev;
        int iter = 0;

        while (iter < maxIter) {
            iter++;
            xPrev = x;
            x = xPrev - f(xPrev) / df(xPrev);

            System.out.printf("%-6d %-15.8f %-15.8f %-15.8f%n",
                    iter, x, f(x), Math.abs(x - xPrev));

            if (Math.abs(x - xPrev) < eps) {
                System.out.println("Достигнута заданная точность.");
                break;
            }
        }

        System.out.printf("Найденный корень: x = %.8f%n", x);
        System.out.printf("Значение f(x) = %.8f%n", f(x));
        System.out.printf("Количество итераций: %d%n", iter);
    }

    
    static void chord(double a, double b, double eps, int maxIter) {
        System.out.println("\n=== Метод Хорд ===");
        System.out.printf("%-6s %-15s %-15s %-15s%n", "Итер", "a", "b", "|b - a|");

        double fa = f(a);
        double fb = f(b);
        double x = a;
        int iter = 0;

        while (iter < maxIter) {
            iter++;
            x = a - fa * (b - a) / (fb - fa);
            double fx = f(x);

            System.out.printf("%-6d %-15.8f %-15.8f %-15.8f%n",
                    iter, a, b, Math.abs(b - a));

            if (Math.abs(b - a) < eps || Math.abs(fx) < eps) {
                System.out.println("Достигнута заданная точность.");
                break;
            }

            if (fa * fx < 0) {
                b = x;
                fb = fx;
            } else {
                a = x;
                fa = fx;
            }
        }

        System.out.printf("Найденный корень: x = %.8f%n", x);
        System.out.printf("Значение f(x) = %.8f%n", f(x));
        System.out.printf("Количество итераций: %d%n", iter);
    }

    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Решение уравнения: f(x) = 2x - 1/tan(x) = 0");
        System.out.println("Отрезок по умолчанию: [0.1; 1]  (в точке 0 функция не определена)");

        System.out.print("Введите левую границу a (Enter — по умолчанию 0.1): ");
        String sA = sc.nextLine().trim();
        double a = sA.isEmpty() ? 0.1 : Double.parseDouble(sA);

        System.out.print("Введите правую границу b (Enter — по умолчанию 1.0): ");
        String sB = sc.nextLine().trim();
        double b = sB.isEmpty() ? 1.0 : Double.parseDouble(sB);

        System.out.print("Введите точность eps (Enter — по умолчанию 0.0001): ");
        String sEps = sc.nextLine().trim();
        double eps = sEps.isEmpty() ? 0.0001 : Double.parseDouble(sEps);

        System.out.print("Введите начальное приближение x0 (Enter — по умолчанию 0.5): ");
        String sX0 = sc.nextLine().trim();
        double x0 = sX0.isEmpty() ? 0.5 : Double.parseDouble(sX0);

        System.out.print("Введите максимальное число итераций (Enter — по умолчанию 100): ");
        String sN = sc.nextLine().trim();
        int maxIter = sN.isEmpty() ? 100 : Integer.parseInt(sN);

        if (f(a) * f(b) > 0) {
            System.out.println("\n⚠ Внимание: на концах отрезка функция не меняет знак!");
            System.out.println("Возможно, корня на этом отрезке нет. Продолжаем всё равно...");
        }

        newton(x0, eps, maxIter);
        chord(a, b, eps, maxIter);

        sc.close();
    }
}
