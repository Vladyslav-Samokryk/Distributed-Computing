package RMI;

import Bus.DAO;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerRmi {
    public static void main(String[] args) throws RemoteException {
        DAO serverRMIImpl = new DAO();
        Registry registry = LocateRegistry.createRegistry(111);
        registry.rebind("serverRMI", serverRMIImpl);
        System.out.println("Server started!");
    }
}
