package task2.rmi.client;

import task2.rmi.server.SolverEquation;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import static java.lang.System.err;
import static java.lang.System.out;

public class Client {


    private Client() {}


    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry(1239);

            SolverEquation stub = (SolverEquation) registry.lookup("SolverEquation");

            Scanner scanner = new Scanner(System.in);

            out.println("enter values: ");
            out.print("a = ");
            float a = scanner.nextFloat();
            out.print("b = ");
            float b = scanner.nextFloat();
            out.print("c = ");
            float c = scanner.nextFloat();

            String result = stub.solveQuadratic(a, b, c);

            out.println(result);

        } catch (Exception e) {
            err.println(e);
        }
    }

}
