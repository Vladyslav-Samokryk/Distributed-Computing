package Bus;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public interface DAOGeneric extends Remote {

    Driver getBusForMoreLifetime() throws RemoteException, SQLException;

    List<ExtendedDriver> getBusForBusNumber() throws RemoteException, SQLException;

    Driver getBusForMoreMiles() throws SQLException, RemoteException;

}