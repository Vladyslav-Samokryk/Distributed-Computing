package Socket;

import Bus.Bus;
import Bus.ExtendedDriver;
import Bus.Driver;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientSocket {
    private static ObjectOutputStream out = null;
    private static ObjectInputStream in = null;

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Socket socket = new Socket("127.0.0.1", 8282);
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

        System.out.println("Client Menu: \n" +
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

    private static void handleOperation(String[] arg) throws IOException, ClassNotFoundException {
        switch (Integer.parseInt(arg[0])) {
            case 1: {
                out.writeInt(1);
                out.flush();
                Bus bus = (Bus) in.readObject();
                System.out.println("Bus for route number: " + bus);
                break;
            }
            case 2: {
                out.writeInt(2);
                out.flush();
                List<ExtendedDriver> users = (List<ExtendedDriver>) in.readObject();
                users.forEach(ExtendedDriver -> {
                    System.out.println("Driver: " + ExtendedDriver.getDriver());
                    System.out.println("Bus which is using for more lifetime that have to: " + ExtendedDriver.getLifetime());
                });
                break;
            }
            case 3: {
                out.writeInt(3);
                out.flush();
                List<Driver> drivers = (List<Driver>) in.readObject();
                Bus bus = (Bus) in.readObject();
                System.out.println("Bus which have mileage more than x: " + ExtendedDriver.getCountBus());
                break;
            }

            default: {
                System.out.println("Unknown operation");
            }
        }
        }
    }


