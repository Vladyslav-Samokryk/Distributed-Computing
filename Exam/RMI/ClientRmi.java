package RMI;

import Bus.ExtendedDriver;
import Bus.Driver;
import Bus.DAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public class ClientRmi {
    private final static String URL = "//localhost:111/RMI";

    public ClientRmi() throws RemoteException, NotBoundException, MalformedURLException {
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Client  Menu: \n" +
                "1. getBusForBusNumber \n" +
                "2. getBusForMoreLifetime \n" +
                "3. getBusForMoreMiles \n");
        try {
            while (true) {
                System.out.println("> ");
                String operation = keyboard.readLine();
                String[] arg = operation.split(" ");
                handleOperation(arg);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void handleOperation(String[] arg) throws IOException, ClassNotFoundException, SQLException, NotBoundException {
        DAO serverRMI = (DAO) Naming.lookup(URL);
        switch (Integer.parseInt(arg[0])) {
            case 1: {
                Driver bus = (Driver) serverRMI.getBusForMoreMiles();
                System.out.println("Bus for route number: " + bus);
                break;
            }
            case 2: {
                List<ExtendedDriver> users = serverRMI.getBusForBusNumber();
                users.forEach(extendedUser -> {
                    System.out.println("Driver: " + ExtendedDriver.getDriver());
                    System.out.println("Bus which is using for more lifetime that have to: " + ExtendedDriver.getLifetime());
                });
                break;
            }
            case 3: {
                List<ExtendedDriver> drivers = (List<ExtendedDriver>) serverRMI.getBusForMoreMiles();
                System.out.println("Bus which have mileage more than x: " + ExtendedDriver.getCountBus());
                drivers.forEach(System.out::println);
                break;
            }

            default: {
                System.out.println("Unknown operation");
            }
        }
    }
}
