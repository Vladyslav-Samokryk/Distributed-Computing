package Socket;

import Bus.ExtendedDriver;
import Bus.Driver;
import Bus.DAO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public class ClientHandler implements Runnable {
    private Socket client = null;
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;
    private DAO dao = new DAO();

    public ClientHandler(Socket clientSocket) throws RemoteException {
        try {
            client = clientSocket;
            out = new ObjectOutputStream(client.getOutputStream());
            in = new ObjectInputStream(client.getInputStream());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                int request = in.readInt();
                handleRequest(request);
            }
        } catch (IOException | SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleRequest(int request) throws SQLException, IOException, ClassNotFoundException {
        switch (request) {
            case 1: {
                List<ExtendedDriver> bus = dao.getBusForBusNumber();
                out.writeObject(bus);
                out.flush();
                break;
            }
            case 2: {
                String driver = (String) in.readObject();
                List<Driver> bus = (List<Driver>) dao.getBusForMoreLifetime();
                out.writeObject(bus);
                out.flush();
                break;
            }
            case 3: {
                Driver bus = (Driver) dao.getBusForMoreMiles();
                out.writeObject(bus);
                out.flush();
                break;
            }
            default: {
                System.out.println("Unknown request");
            }
        }
    }
}
