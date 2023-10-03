package Sql;
import java.sql.Date;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.sql.*;


public class Sql {
    private static final String DB_URL= "jdbc:sqlserver://localhost;databaseName=hm;encrypt=true;trustServerCertificate=true;";
    private static final String USERNAME = "abey";
    private static final String PASSWORD = "abey";
    private static Connection connection ;
    private static PreparedStatement statement;
//    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;
//    public Sql(){
    static {
          try {
              connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
              if (connection != null)
                  System.out.println("Connected");
          } catch (SQLException e) {
              System.out.println(e);
          }
        }
//        }// end of the constructor

    public void closeConnection(){
        try{
            connection.close();
        }catch(SQLException e){
            System.out.println(e);
        }
    }

        public static DefaultTableModel filterGuests(String query, String value) throws SQLException, NumberFormatException
        {
            String sql = "select * from guestdetail where " + query + " like (?) order by paymentDate";
                statement = connection.prepareStatement(sql);
                switch (query.toLowerCase()){
                    case "roomnumber":
                        int i = Integer.parseInt(value);
                        statement.setString(1,  "%" + value +"%");
                        break;
                    case "fullname":
                    case "address":
                        statement.setString(1, "%" + value +"%" );
                        break;

                }
                resultSet = statement.executeQuery();
            try {
                return buildTableModel(resultSet);
            }catch (SQLException e){
                System.out.println(e);
                return new DefaultTableModel();
            }

        }

    public static DefaultTableModel getAllGuests() throws SQLException
    {
        String sql = "select * from guestDetail order by paymentdate";
        statement = connection.prepareStatement(sql);
        resultSet = statement.executeQuery();
        try{
           return buildTableModel(resultSet);
        }catch(SQLException e){
            return new DefaultTableModel();
        }
    }
    public static DefaultTableModel filterStaffs(String query, String value) throws SQLException
        {
            String sql = "select * from staffdetail where " + query + " like (?) order by fullname";
                statement = connection.prepareStatement(sql);
                statement.setString(1, "%" + value +"%" );
                resultSet = statement.executeQuery();
            try {
                return buildTableModel(resultSet);
            }catch (SQLException e){
                return new DefaultTableModel();
            }

        }


    public static DefaultTableModel getAllStaffs() throws SQLException
    {
        String sql = "select * from staffdetail order by fullname";
        statement = connection.prepareStatement(sql);
        resultSet = statement.executeQuery();
        try{
//            System.out.println(resultSet.getString(1));
            return buildTableModel(resultSet);
        }catch(SQLException e){
            return new DefaultTableModel();
        }
    }// getAllStaffs
    public static DefaultTableModel getAllRooms() throws SQLException
    {
        String sql = "select * from roomDetail";
       statement = connection.prepareStatement(sql);
       resultSet = statement.executeQuery();
       try{
           return buildTableModel(resultSet);
       }catch(SQLException e){
           return new DefaultTableModel();
       }
    }// end of getallroom
    public static DefaultTableModel filterRooms(String query, String value) throws SQLException, NumberFormatException
    {
           String sql = "select * from roomdetail where " + query + " like (?) order by " + query;
                statement = connection.prepareStatement(sql);
                switch (query.toLowerCase()){
                    case "roomnumber":
                    case "cost":
                    case "cansmoke":
                        int i = Integer.parseInt(value);
                        statement.setString(1,  "%" + value +"%");
                        break;
                    case "roomtype":
                        statement.setString(1, "%" + value +"%" );
                        break;

                }
                resultSet = statement.executeQuery();
            try {
                return buildTableModel(resultSet);
            }catch (SQLException e){
                System.out.println(e);
                return new DefaultTableModel();
            }
    }
    public static DefaultTableModel buildTableModel(ResultSet rs)
        throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for(int i = 1; i <= columnCount; i++)
            columnNames.add(metaData.getColumnName(i));

        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        Vector<Object> row;
        while(rs.next()){
            row = new Vector<Object>();
            for(int i = 1; i <= columnCount ; i++)
                row.add(rs.getObject(i));
            data.add(row);
        }
        return new DefaultTableModel(data, columnNames);
    }


    // pay for the customer using firstname and lastname
    public static void pay(String firstname, String lastname, String paymentMethod, String description)
    {
            try {

                String sql =
                         "insert into payment values(getdate(), (?), (?), (select top 1 guest.guestid " +
                          "from guest left join payment on guest.guestid = payment.GuestId left join reservation on " +
                          "guest.guestid = reservation.guestid where firstname = (?) and lastname = (?) order by " +
                          "guest.guestid desc), (select top 1 reservation.roomid from guest left join payment on " +
                          "guest.guestid = payment.GuestId left join reservation on guest.guestid = reservation.guestid where " +
                          "firstname = (?) and lastname = (?) order by guest.guestid desc))";
                statement = connection.prepareStatement(sql);
                statement.setString(1, paymentMethod);
                statement.setString(2, description);
                statement.setString(3, firstname);
                statement.setString(4, lastname);
                statement.setString(5, firstname);
                statement.setString(6, lastname);
                statement.executeUpdate();
            }catch (SQLException e){
                     System.out.println("error in paying");
                     System.out.println(e);
                 }

    }
    public static void unpay(String firstname, String lastname)
    {
        try {
            String sql =
                    "delete from payment where guestid = " +
                    "(select top 1 guestid from guest where firstname = (?) and lastname = (?) order by guestid desc)";
             statement = connection.prepareStatement(sql);
             statement.setString(1, firstname);
             statement.setString(2, lastname);
            System.out.println(statement.executeUpdate());
        }catch (SQLException e){
                System.out.println("error in unpaying");
            System.out.println(e);
             }
    }// end of unpay
    public static void changeProfilePicture(String firstname, String lastName, String to){
      String sql =  "update staff " +
                    " set profileImage = (?)" +
                    "where firstname = (?) and lastname = (?)";
      try{
          statement = connection.prepareStatement(sql);
          statement.setString(1, to);
          statement.setString(2, firstname);
          statement.setString(3, lastName);
          System.out.println(statement.executeUpdate());
      }catch (SQLException e){
          System.out.println(e);
      }
    }


// recruit new staff
  public static int recruit(
          String firstname , String lastname, int age, String gender, String address,
          String email,  int jobId, String mainPhone, String optionalPhone
  )
  {
      try{
           String sql = "insert into staff(firstname, lastname, age, gender, address, " +
                      "email, jobid) values(?,?,?,?,?,?,?)";
        statement = connection.prepareStatement(sql);
        statement.setString(1, firstname);
        statement.setString(2, lastname);
        statement.setInt(3, age);
        statement.setString(4, gender);
        statement.setString(5, address);
        statement.setString(6, email);
        statement.setInt(7, jobId);
        statement.executeUpdate();
        int id = search(firstname, lastname, "staff");
          System.out.println("the id is" + id);
        sql = "insert into StaffphoneNumber values (?,?,?)";
        statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.setString(2, mainPhone);
        statement.setString(3, optionalPhone);;
        return statement.executeUpdate();
      }catch (SQLException e){
          System.out.println(e);
          return -1;
      }


  }

  // search a guest/ staff based on the firstname and lastname and pick the first top/ last
    public static int search(String firstname, String lastname, String from) throws SQLException
    {
        String id, sql;
        if (from.toLowerCase().equals("staff"))
        {
            id = "staffid";
            sql = "select top 1 " + id + " from " + from  + " where firstname like (?) and lastname like (?) and status = 1 order by " + id   + " desc ";
        }else
        {
            id = "guestid";
             sql = "select top 1 " + id + " from " + from  + " where firstname like (?) and lastname like (?) order by " + id   + " desc ";
        };
        statement = connection.prepareStatement(sql);
        statement.setString(1, "%" +  firstname + "%");
        statement.setString(2,  "%" + lastname + "%");
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        System.out.println("id " + resultSet.getInt(id));
        return resultSet.getInt(id);
    }

    // delete a staff based on a given firstname and lastname
    public static void deleteRecord(String firstname, String lastname, String from) throws SQLException
    {
        int id = search(firstname, lastname, from);
        String sql = "update  " +  from + " set status = 0 "+  " where " + " Staffid = " + id;
        statement = connection.prepareStatement(sql);
        System.out.println(statement.executeUpdate());
    }

        public static int guestRegistration(String firstname, String lastname, String gender,String country,
                                            String city, String tel, Date dateTo,  int age, int roomid)
        {
            try {
                String sql = "insert into guest(firstname, lastname, gender, country, city, age, tel) values(?,?,?,?,?,?,?)";
                statement = connection.prepareStatement(sql);
                statement.setString(1, firstname);
                statement.setString(2, lastname);
                statement.setString(3, gender);
                statement.setString(4, country);
                statement.setString(5, city);
                statement.setInt(6, age);
                statement.setString(7, tel);
                System.out.println("guest insert " + statement.executeUpdate());
                int guestid = search(firstname, lastname, "guest");
                System.out.println("id " + guestid);
                sql = "insert into reservation(dateto, guestid, roomid) values(?,?,?)";
                statement = connection.prepareStatement(sql);
                statement.setDate(1, dateTo);
                statement.setInt(2, guestid);
                statement.setInt(3, roomid);
                statement.executeUpdate();
                System.out.println("reserved" + statement.executeUpdate());
                sql = "update room set isreserved = 1 where roomid = (?)";
                statement = connection.prepareStatement(sql);
                statement.setInt(1,roomid);
                return statement.executeUpdate();
            }catch (SQLException ex){
                System.out.println(ex);
                return -1;
            }

        }// end of guestRegistration

    public static boolean isAccountExist(String username){
        try{
        String sql = "select * from account where username = (?)";
        statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        ResultSet rs = statement.executeQuery();
        if (rs.next()){
            System.out.println("next row");
            return true;
        }else{
            return false;
        }
//        return result == 0 ? true : false;
        }catch (SQLException ex){
            System.out.println(ex);
            return false;
        }


    }
//    register a new Account
    public static void createAccount(String username, String password, int staffid) throws SQLException
    {
            String hashed_password = Password.hashPassword(password);
            String sql = "insert into account(username, password, staffid) values (?,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, hashed_password);
            statement.setInt(3, staffid);
            statement.executeUpdate();
        }// end of createAccount
    public static boolean authenticate(String username, String password)
    {
        try {
            String sql = "select top 1 * from account where username = (?) and password = (?) order by accountid desc";
            statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, Password.hashPassword(password));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                System.out.println("authenticated");
                return true;
            } else {
                return false;
            }
        }catch(SQLException ex){
            return false;
        }
    }




}// end of the class sql




