package task2.rmi.server;

import java.rmi.RemoteException;

import static java.lang.Math.sqrt;

public class SolverEquationImpl implements SolverEquation {

    public String solveQuadratic(float a, float b, float c) throws RemoteException {
        float d = b * b - 4 * a * c;

        if (d < 0) {
            return "no roots";
        } else if (d == 0) {
            return String.valueOf((-b) / (2 * a));
        } else {
            double x1 = (-b + sqrt(d)) / (2 * a);
            double x2 = (-b - sqrt(d)) / (2 * a);

            return x1 + ";" + x2 + ";";
        }
    }

}
