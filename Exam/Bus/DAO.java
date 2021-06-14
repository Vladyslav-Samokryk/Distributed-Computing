package Bus;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.sql.Driver;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DAO extends UnicastRemoteObject implements DAOGeneric {

    private Connection con;
    private Statement stmt;

    public DAO() throws RemoteException {
        super();
        String url = "jdbc:mysql://localhost:3306/bus";
        String userName = "root";
        String password = "root";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(url, userName, password);
            stmt = con.createStatement();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws SQLException, RemoteException {
        DAO dao = new DAO();

    }

    @Override
    public List<ExtendedDriver> getBusForBusNumber() throws SQLException, RemoteException {
        List<ExtendedDriver> result = new ArrayList<>();
        String sql1 = "SELECT * FROM driver";

        ResultSet rs = null;
        try {
            con.setAutoCommit(false);
            rs = stmt.executeQuery(sql1);

            while (rs.next()) {
                long id = rs.getLong("id");
                String username = rs.getString("surname");
                String name = rs.getString("name");
                Driver driverFromDb = new Driver(id, username, name);
                result.add(new ExtendedDriver(driverFromDb, 0, 0));
            }

            String sql2 = "SELECT * FROM bus";
            rs = stmt.executeQuery(sql2);

            while (rs.next()) {
                long number = rs.getLong("number");

                for (ExtendedDriver extendedDriver : result) {
                    if (extendedDriver.getDriver().getId() == number) {
                        extendedDriver.setCountBus(extendedDriver.getCountBus());
                    }
                }
            }

            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            con.rollback();
        } finally {
            if (rs != null) {
                rs.close();
            }
            con.setAutoCommit(true);
        }
        return result;
    }

    @Override
    public Driver getBusForMoreLifetime() throws SQLException, RemoteException {
        Driver result = new Driver();
        long BusNum = 0;
        int value = 123000;
        int bigMileage = value;

        String sql1 = "SELECT * FROM bus";

        ResultSet rs = null;

        try {
            con.setAutoCommit(false);
            rs = stmt.executeQuery(sql1);

            while (rs.next()) {
                int currMileage = rs.getString("mileage").length();

                if (currMileage > bigMileage) {
                    bigMileage = currMileage;
                    BusNum = rs.getLong("number");
                }
            }

            String sql2 = "SELECT * FROM user WHERE id = '" + BusNum + "'";
            rs = stmt.executeQuery(sql2);

            if (rs.next()) {
                int number = Integer.parseInt(rs.getString("number"));
                int route = Integer.parseInt(rs.getString("route"));
                String model = rs.getString("model");
                int mileage = Integer.parseInt(rs.getString("mileage"));
                LocalDate startUsing = rs.getObject("startUsing", LocalDate.class);

                result = new Driver(number, route, model, mileage, startUsing);
            }

            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            con.rollback();
        } finally {
            if (rs != null) {
                rs.close();
            }
            con.setAutoCommit(true);
        }

        return result;
    }

    @Override
    public Driver getBusForMoreMiles() throws SQLException, RemoteException {
        Driver result = new Driver();
        long BusNum = 0;
        int maxLifetime = 10;
        int currLifetime = maxLifetime;

        String sql1 = "SELECT * FROM bus";

        ResultSet rs = null;

        try {
            con.setAutoCommit(false);
            rs = stmt.executeQuery(sql1);

            while (rs.next()) {
                int currMileage = rs.getString("mileage").length();

                if (maxLifetime > currLifetime) {
                    BusNum = rs.getLong("number");
                }
            }

            String sql2 = "SELECT * FROM user WHERE id = '" + BusNum + "'";
            rs = stmt.executeQuery(sql2);

            if (rs.next()) {
                int number = Integer.parseInt(rs.getString("number"));
                int route = Integer.parseInt(rs.getString("route"));
                String model = rs.getString("model");
                int mileage = Integer.parseInt(rs.getString("mileage"));
                LocalDate startUsing = rs.getObject("startUsing", LocalDate.class);

                result = new Driver(number, route, model, mileage, startUsing);
            }

            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            con.rollback();
        } finally {
            if (rs != null) {
                rs.close();
            }
            con.setAutoCommit(true);
        }

        return result;
    }



}
