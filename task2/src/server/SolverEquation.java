package task2.rmi.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SolverEquation extends Remote {

    String solveQuadratic(float a, float b, float c) throws RemoteException;

}
