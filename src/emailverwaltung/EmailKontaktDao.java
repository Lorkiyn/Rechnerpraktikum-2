package emailverwaltung;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class EmailKontaktDao {

	private final static String DBNAME = "Email";
	private final static String PATH = "jdbc:sqlite:" +System.getenv("APPDATA") +"\\JavaExamples\\" +DBNAME +".db";
	private static final String FOLDERPATH = System.getenv("APPDATA") +"\\JavaExamples\\";
	//	private final static String PATH = "jdbc:sqlite:H:\\" +DBNAME +".db";
	private static String defaultConnType = "SQLite";
	private static boolean isConnected = false;

	public EmailKontaktDao() {

	}

	public static EmailKontakt create() { 
		return null;

	}

	public static void setConnTypeSQLite() {
		defaultConnType = "SQLite";
	}

	public static void setConnTypeAS400() {
		defaultConnType = "AS400";
	}

	public static void printDB(String type) {
		Connection conn = getConn(type);
		Statement s = null;
		String sql = "SELECT * FROM " +DBNAME;

		try {
			s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);

			System.out.println("ID\tVorname\t\tNachname\tEmail");
			while(rs.next()) {
				System.out.print(rs.getString("id") + "\t");
				System.out.print(rs.getString("vorname") + "\t\t");
				System.out.print(rs.getString("nachname") + "\t\t");
				System.out.print(rs.getString("email") + "\n");
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			try {
				conn.close();

			} catch (SQLException e) {
				e.printStackTrace();

			}

		}


	}

	public static void delete(EmailKontakt contact) {
		Connection conn = getConn(defaultConnType);
		String sql = "DELETE FROM " +DBNAME +" WHERE ID=?";

		try {
			PreparedStatement s = conn.prepareStatement(sql);
			s.setString(1, String.valueOf(contact.getId()));
			s.executeUpdate();

			System.out.println("[MSG] [DAO] Deleted row in " +defaultConnType);

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			try {
				conn.close();

			} catch (SQLException e) {
				e.printStackTrace();

			}

		}

	}

	public static int getNextId() {
		try {
			Statement s = getConn(defaultConnType).createStatement();

			String sql = "SELECT MIN(t1.ID + 1) AS nextID "
					+ "FROM " + DBNAME + " t1 "
					+ "LEFT JOIN " + DBNAME + " t2 ON t1.ID + 1 = t2.ID "
					+ "WHERE t2.ID IS NULL";

			ResultSet rs = s.executeQuery(sql);
			int id = rs.getInt("nextID");

			if(id == 0) {
				id = 1;
			}

			s.close();
			return id;

		} catch (SQLException e) {
			e.printStackTrace();

		}

		return 1;

	}

	public static int insert(EmailKontakt contact) {
		Connection conn = getConn(defaultConnType);
		String sql = "INSERT INTO " +DBNAME +" (id, vorname, nachname, email) VALUES(?, ?, ?, ?)";

		try {
			PreparedStatement s = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			s.setInt(1, getNextId());
			s.setString(2, contact.getFirstName());
			s.setString(3, contact.getName());
			s.setString(4, contact.getEmail());
			s.executeUpdate();

			System.out.println("[MSG] [DAO] Added row in " +defaultConnType);

			try (ResultSet generatedKeys = s.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return generatedKeys.getInt(1);

				} else {
					throw new SQLException("Creating user failed, no ID obtained.");
				}

			}


		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			try {
				conn.close();

			} catch (SQLException e) {
				e.printStackTrace();

			}

		}

		return 0;

	}

	public static void update(EmailKontakt contact) {
		Connection conn = getConn(defaultConnType);
		String sql = "UPDATE " +DBNAME +" SET vorname=?, nachname=?, email=? WHERE id=?";

		try {
			PreparedStatement s = conn.prepareStatement(sql);
			s.setString(1, contact.getFirstName());
			s.setString(2, contact.getName());
			s.setString(3, contact.getEmail());
			s.setInt(4, contact.getId());
			s.executeUpdate();

			System.out.println("[MSG] [DAO] Updated contact in " +defaultConnType);

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			try {
				conn.close();

			} catch (SQLException e) {
				e.printStackTrace();

			}

		}

	}

	public static EmailKontakt next(EmailKontakt contact) {
		int id = contact.getId();
		Connection conn = getConn(defaultConnType);
		String sql = "SELECT * FROM " +DBNAME +" WHERE ID>?";
		EmailKontakt cont = null;

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeQuery();

			ResultSet rs = ps.executeQuery();
			if(defaultConnType.equals("AS400")) {
				rs.next();
			}
			cont = new EmailKontakt(rs.getString("vorname"), rs.getString("nachname"), rs.getString("email"));
			cont.setId(rs.getInt("id"));

		} catch (SQLException e) {
			System.out.println("[ERR] [DAO] " +e.getMessage());

		} finally {
			try {
				conn.close();

			} catch (SQLException e) {
				e.printStackTrace();

			}

		}

		return cont;

	}

	public static EmailKontakt previous(EmailKontakt contact) {
		try {
			if(contact.getId() == first().getId()) {
				return null;
			}

		} catch (NullPointerException e1) {
			System.out.println("[ERR] [DAO] No data found!");
			JFrameEmailverwaltung.clear();
			return null;
		}

		int id = contact.getId();
		Connection conn = getConn(defaultConnType);
		String sql = "SELECT * FROM " +DBNAME +" WHERE ID<? ORDER BY ID DESC";
		EmailKontakt cont = null;

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeQuery();

			ResultSet rs = ps.executeQuery();
			if(defaultConnType.equals("AS400")) {
				rs.next();
			}
			cont = new EmailKontakt(rs.getString("vorname"), rs.getString("nachname"), rs.getString("email"));
			cont.setId(rs.getInt("id"));

		} catch (SQLException e) {
			System.out.println("[ERR] [DAO] " +e.getMessage());

		} finally {
			try {
				conn.close();

			} catch (SQLException e) {
				e.printStackTrace();

			}

		}

		return cont;

	}

	public static EmailKontakt first() {
		Connection conn = getConn(defaultConnType);
		String sql = "SELECT * FROM " +DBNAME +" WHERE ID=(SELECT MIN(ID) FROM " +DBNAME +")";
		EmailKontakt contact = null;

		try {
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			if(defaultConnType.equals("AS400")) {
				rs.next();
			}
			contact = new EmailKontakt(rs.getString("vorname"), rs.getString("nachname"), rs.getString("email"));
			contact.setId(rs.getInt("id"));

		} catch (SQLException e) {


		} finally {
			try {
				conn.close();

			} catch (SQLException e) {
				System.out.println("[ERR] [DAO] " +e.getMessage());

			}

		}

		return contact;


	}

	public static EmailKontakt last() {
		Connection conn = getConn(defaultConnType);
		String sql = "SELECT * FROM " +DBNAME +" WHERE ID=(SELECT MAX(ID) FROM " +DBNAME +")";
		EmailKontakt contact = null;

		try {
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			if(defaultConnType.equals("AS400")) {
				rs.next();
			}
			contact = new EmailKontakt(rs.getString("vorname"), rs.getString("nachname"), rs.getString("email"));
			contact.setId(rs.getInt("id"));

		} catch (SQLException e) {
			System.out.println("[ERR] [DAO] " +e.getMessage());

		} finally {
			try {
				conn.close();

			} catch (SQLException e) {
				e.printStackTrace();

			}

		}

		return contact;

	}

	public static String[] getColumnNames() {
		return new String[] {
				"ID",
				"Vorname",
				"Nachname",
				"Email"
		};

	}

	public static Object[][] getTableData() {
		if(getDataCount() == 0) {
			return new Object[0][4];

		}

		Object[][] data = new Object[getDataCount()][getColumnNames().length];

		Connection conn = getConn(defaultConnType);
		String sql = "SELECT * FROM " +DBNAME;

		int count = 0;

		try {
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);

			if(defaultConnType.equals("AS400")) {
				rs.next();
			}

			while(rs.next()) {
				data[count][0] = rs.getString("id");
				data[count][1] = rs.getString("vorname");
				data[count][2] = rs.getString("nachname");
				data[count][3] = rs.getString("email");

				count++;

			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			try {
				conn.close();

			} catch (SQLException e) {
				e.printStackTrace();

			}

		}

		return data;

	}

	public static int getDataCount() {
		Connection conn = getConn(defaultConnType);
		String sql = "SELECT COUNT(EMAIL) AS COUNT FROM " +DBNAME;
		int count = 0;

		try {
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);

			if(defaultConnType.equals("AS400")) {
				rs.next();
			}

			count = rs.getInt("COUNT");

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			try {
				conn.close();

			} catch (SQLException e) {
				e.printStackTrace();

			}

		}

		return count;

	}

	public static Object[][] select(String id, String firstName, String lastName, String email) {
		Connection conn = getConn(defaultConnType);
		StringBuilder mainStr = new StringBuilder();
		StringBuilder str = new StringBuilder();
		mainStr.append("SELECT * FROM " +DBNAME);
		ArrayList<String> arrlist = new ArrayList<String>();

		boolean bId = false;
		boolean bFirstName = false;
		boolean bLastName = false;
		boolean bEmail = false;

		Object[][] newData = null;
		ArrayList<Object> objList = new ArrayList<Object>();

		if(!id.equals("")) {
			str.append("ID LIKE '%" +id +"%'");
			bId = true;
			arrlist.add(id);

		} if(!firstName.equals("")) {
			if(bId) {
				str.append(" AND ");

			}
			str.append("vorname LIKE '%" +firstName +"%'");
			bFirstName = true;
			arrlist.add(firstName);

		} if(!lastName.equals("")) {
			if(bFirstName || bId) {
				str.append(" AND ");

			}
			str.append("nachname LIKE '%" +lastName +"%'");
			bLastName = true;
			arrlist.add(lastName);

		} if(!email.equals("")) {
			if(bFirstName || bId || bLastName) {
				str.append(" AND ");

			}
			str.append("email LIKE '%" +email +"%'");
			bEmail = true;
			arrlist.add(email);

		}

		if(bFirstName || bId || bLastName || bEmail) {
			mainStr.append(" WHERE " +str.toString());
		}

		String sql = mainStr.toString();
		
		try {
			Statement ps = conn.createStatement();

			ResultSet rs = ps.executeQuery(sql);
			if(defaultConnType.equals("AS400")) {
				rs.next();
			}


			while(rs.next()) {
				Object[] obj = {rs.getInt("id"), rs.getString("vorname"), rs.getString("nachname"), rs.getString("email")};
				objList.add(obj);

			}

			newData = new Object[objList.size()][4];

			for(int i = 0; i < newData.length; i++) {
				newData[i] = (Object[]) objList.get(i);

			}


		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			try {
				conn.close();

			} catch (SQLException e) {
				e.printStackTrace();

			}

		}

		return newData;

	}

	public static EmailKontakt select(int id) {
		Connection conn = getConn(defaultConnType);
		String sql = "SELECT * FROM " +DBNAME +" WHERE ID=?";
		EmailKontakt contact = null;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeQuery();

			ResultSet rs = ps.executeQuery();
			if(defaultConnType.equals("AS400")) {
				rs.next();
			}
			contact = new EmailKontakt(rs.getString("vorname"), rs.getString("nachname"), rs.getString("email"));
			contact.setId(rs.getInt("id"));

		} catch (SQLException e) {
			if(id > 0) {
				JOptionPane.showMessageDialog(null, "Die ID " +id +" ist nicht vergeben.");

			}

		} finally {
			try {
				conn.close();

			} catch (SQLException e) {
				e.printStackTrace();

			}

		}

		return contact;

	}

	private static Connection getConn(String connType) {
		Connection conn = null;
		Statement s = null;

		if(connType == null) {
			connType = "SQLite";

		}

		if(connType.equals("AS400")) {
			try {
				Class.forName("com.ibm.as400.access.AS400JDBCDriver");
				String server = "sys3";
				String url = "jdbc:as400://" + server + "/";
				System.out.println("[MSG] [DAO] Succesfully connected to DB: " +DBNAME  +" at: AS400");

				conn = DriverManager.getConnection(url, Password.getUser(), "");

			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();

			}


		} else {

			try {
				File folder = new File(FOLDERPATH);
				if(!folder.exists()) {
					folder.mkdir();
				}

				Class.forName("org.sqlite.JDBC");
				conn = DriverManager.getConnection(PATH);
				s = conn.createStatement();

				String table = "CREATE TABLE IF NOT EXISTS " +DBNAME
						+ "("
						+ "id INTEGER PRIMARY KEY NOT NULL,"
						+ "vorname VARCHAR(30) NOT NULL,"
						+ "nachname VARCHAR(30) NOT NULL,"
						+ "email VARCHAR(50) NOT NULL"
						+ ")";

				s.executeUpdate(table);
				s.close();

				if(!isConnected) {
					System.out.println("[MSG] [DAO] Database successfully created/loaded.");
					System.out.println("[MSG] [DAO] Succesfully connected to DB: " +DBNAME  +" at: " +PATH);
					isConnected = true;
				}


			} catch (ClassNotFoundException e) {
				e.printStackTrace();

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return conn;

	}

}
