package task2.rmi.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static java.lang.System.err;
import static java.lang.System.out;

public class Server {

    public static void main(String[] args) {
        try {
            SolverEquation stub = (SolverEquation) UnicastRemoteObject.exportObject(new SolverEquationImpl(), 0);

            Registry registry = LocateRegistry.createRegistry(1239);
            registry.rebind("SolverEquation", stub);

            out.println("Server is ready!");
        } catch (Exception e) {
            err.println(e.getMessage());
        }
    }

}
