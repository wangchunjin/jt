package com.xiaheng.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.mysql.jdbc.PreparedStatement;

public class ConnDb {
	
	
	  Connection conn = null;
	  Statement stmt = null;
	  PreparedStatement pstmt = null;
	  DataSource pool;

	// 连接mysql
	//private String driverName = "com.mysql.jdbc.Driver";
	//private String url = "jdbc:mysql://192.168.31.249:8015/quyuedong?useUnicode=true&characterEncoding=utf-8";
	//private String user = "root";
	//private String password = "11";

	 public void init()
	  {
	    try
	    {
	      Context env = (Context)new InitialContext()
	        .lookup("java:comp/env");

	      this.pool = ((DataSource)env.lookup("jdbc/qiche"));
	    } catch (NamingException localNamingException) {
	    }
	  }
	
	




	//DataSource pool;

	/*
	 * public void init() { try { Context env = (Context) new
	 * InitialContext().lookup("java:comp/env");
	 * 
	 * pool = (DataSource) env.lookup("jdbc/test");
	 * 
	 * 
	 * } catch (NamingException e) { } }
	 * 
	 * 
	 * public Connection getConnection() { init(); Connection conn; try { conn =
	 * pool.getConnection();
	 * 
	 * return conn; } catch (Exception e) { System.out.println(e); return null;
	 * } }
	 */

	  public Connection getConnection() {
		    init();
		    try {
		      Connection conn = this.pool.getConnection();
		      return conn;
		    } catch (Exception localException) {
		    }
		    return null;
		  }

	public void setcon() {

		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void addsql(String sqlstr) {
		/***************************************

		 ***************************************/

		try {
			stmt.addBatch(sqlstr);

		} catch (Exception e) {

			System.out.println(e);
		}

	}

	public int doBatch(int shu) {
		/***************************************

		 ***************************************/

		try {
			int[] updateCounts = stmt.executeBatch();

			if (shu == updateCounts.length) {
				conn.commit();
				conn.setAutoCommit(true);
				stmt.close();
				conn.close();
				return 1;
			} else {
				conn.rollback();
				conn.setAutoCommit(true);
				stmt.close();
				conn.close();
				return 0;
			}

		} catch (Exception e) {

			try {
				conn.rollback();
				conn.setAutoCommit(true);
				stmt.close();
				conn.close();
				System.out.println(e);
			} catch (Exception ee) {
				System.out.println(ee);
			}
			return 0;
		}
	}

	public Vector queryTable(String sqlstr) {

		Vector pkv = new Vector();

		try {
			conn = getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

			ResultSet rs = stmt.executeQuery(sqlstr);
			ResultSetMetaData rsmd = rs.getMetaData();

			int num = rsmd.getColumnCount();

			while (rs.next()) {
				Hashtable table = new Hashtable();
				for (int i = 1; i <= num; i++) {
					String key = rsmd.getColumnName(i);
					String value = rs.getString(i);
					if (value == null)
						value = "";
					table.put(key, value);
				}
				pkv.add(table);
			}
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println(e);
			try {
				stmt.close();
				conn.close();
			} catch (Exception ee) {
				System.out.println(ee);
			}
		}
		return pkv;
	}

	public ResultSet executeQuery(String sqlstr) {

		try {
			conn = getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sqlstr);
			return rs;
		} catch (Exception e) {
			return null;
		}
	}

	public void close() {
		try {
			stmt.close();
			conn.close();
		} catch (Exception ee) {
			System.out.println(ee);
		}
	}

	public int executeUpdate(String sqlstr) {
		/***************************************

		 ***************************************/

		try {
			conn = getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(sqlstr);
			stmt.close();
			conn.close();
			return 1;
		} catch (Exception e) {
			try {
				stmt.close();
				conn.close();
				System.out.println(e);
			} catch (Exception ee) {
				System.out.println(ee);
			}
			return 0;
		}
	}

	public void execute(String sqlstr) {

		try {
			conn = getConnection();
			stmt = conn.createStatement();
			stmt.execute(sqlstr);
			stmt.close();
			conn.close();
		} catch (Exception e) {
			try {
				stmt.close();
				conn.close();
			} catch (Exception ee) {
				System.out.println(ee);
			}
		}
	}

	public int executeQueryNum(String sql) {

		Connection con = getConnection();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();

			int queryNum = rsmd.getColumnCount();

			rs.close();
			stmt.close();
			con.close();
			return queryNum;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			try {
				stmt.close();
				con.close();
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
			return -1;
		}
	}

	public int executeCounts(String sql) {
		/*************************************************************************
	
		 *************************************************************************/
		Connection con = getConnection();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs != null) {
				rs.next();
				int queryNum = rs.getInt(1);
				rs.close();
				stmt.close();
				con.close();
				return queryNum;
			} else {
				stmt.close();
				con.close();
				return -1;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			try {
				stmt.close();
				con.close();
			} catch (Exception ex) {

			}
			return 0;
		}
	}


	
	private Hashtable getTheValue(ResultSet rs, ResultSetMetaData rsmd) {

		Hashtable hstab = new Hashtable();
		try {
			if (rs != null) {
				int maxrows = rsmd.getColumnCount();
				for (int i = 1; i <= maxrows; i++) {
					String key = rsmd.getColumnName(i);
					String value = rs.getString(i);

					String alias = rsmd.getColumnLabel(i);// 字段别名:20160908(ty)
					if (alias != null && !alias.equals("")) {
						key = alias;
					}

					if (value == null) {
						value = "";
					}

					hstab.put(key, value);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return hstab;
		}
		return hstab;
	}

	public ArrayList getList(String sqlstr, int page, int pageSize) {

		ArrayList pkv = new ArrayList();

		try {
			conn = getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sqlstr);
			ResultSetMetaData rsmd = rs.getMetaData();
			int num = rsmd.getColumnCount();
			int rowbegin = 0, j = 0;
			rowbegin = (page - 1) * pageSize + 1;
			rs.absolute(rowbegin);
			if (rs.getRow() == 0) {
				rs.close();
				stmt.close();
				conn.close();
				return null;
			}
			while (j < pageSize) {
				Hashtable table = new Hashtable();
				for (int i = 1; i <= num; i++) {
					String key = rsmd.getColumnName(i);
					String value = rs.getString(i);
					if (value == null)
						value = "";
					table.put(key, value);
				}
				pkv.add(table);
				if (!rs.next()) {
					break;
				}
				;
				j++;
			}
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println(e + "");
			try {
				stmt.close();
				conn.close();
			} catch (Exception ee) {
				System.out.println(ee);
			}
		}
		return pkv;
	}

	public ArrayList getList(String sqlString) {

		ArrayList pkv = new ArrayList();

		try {
			conn = getConnection();
			stmt = conn.createStatement();
			ResultSet rs = this.stmt.executeQuery(sqlString);
			ResultSetMetaData rsmd = rs.getMetaData();
			int num = rsmd.getColumnCount();
			while (rs.next()) {
				Hashtable table = new Hashtable();
				for (int i = 1; i <= num; i++) {
					String key = rsmd.getColumnName(i);
					String value = rs.getString(i);

					String alias = rsmd.getColumnLabel(i);// 字段别名:20160908(ty)
					if (alias != null && !alias.equals("")) {
						key = alias;
					}

					if (value == null)
						value = "";
					table.put(key, value);
				}
				pkv.add(table);
			}
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println(e);
			try {
				stmt.close();
				conn.close();
			} catch (Exception ee) {
				System.out.println(ee);
			}
		}
		return pkv;
	}

	public Vector queryTable(String sqlstr, int page, int pageSize) {

		Vector pkv = new Vector();

		try {
			conn = getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sqlstr);
			ResultSetMetaData rsmd = rs.getMetaData();
			int num = rsmd.getColumnCount();
			int rowbegin = 0, j = 0;
			rowbegin = (page - 1) * pageSize + 1;
			rs.absolute(rowbegin);
			while (j < pageSize) {
				Hashtable table = new Hashtable();
				for (int i = 1; i <= num; i++) {
					String key = rsmd.getColumnName(i);
					String value = rs.getString(i);
					if (value == null)
						value = "";
					table.put(key, value);
				}
				pkv.add(table);
				if (!rs.next()) {
					break;
				}
				;
				j++;
			}
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println(e);
			try {
				stmt.close();
				conn.close();
			} catch (Exception ee) {
				System.out.println(ee);
			}
		}
		return pkv;
	}

	public Hashtable getValue(String sql) {

		Hashtable hstab = new Hashtable();

		Connection con = getConnection();
		Statement stmt = null;

		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			if (rs.next()) {
				hstab = this.getTheValue(rs, rsmd);
			}
			rs.close();
			stmt.close();
			con.close();
			return hstab;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			try {
				stmt.close();
				con.close();
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
			return hstab;
		}
	}

	public String[] execProcedure(String procedureName, String[] inParam, int outParamCount) {

		Connection conn = getConnection();
		java.sql.CallableStatement cstmt = null;
		String strProcedure = "";

		int i;
		int nIn = 0;

		if (inParam != null) {
			nIn = inParam.length;
		}

		for (i = 0; i < outParamCount; i++) {
			strProcedure = strProcedure + ",?";
		}

		if (strProcedure.length() > 0) {
			strProcedure = strProcedure.substring(1, strProcedure.length());
		}

		strProcedure = "{call" + " " + procedureName + "(" + strProcedure + ")}";
		System.out.println(strProcedure);
		// *******************************************//
		try {
			cstmt = conn.prepareCall(strProcedure);

			for (i = 0; i < nIn; i++) {
				cstmt.setString(i + 1, inParam[i]);
			}
			// ************************************//

			// ************注锟结传锟斤拷锟斤拷锟斤拷*************//
			for (i = nIn; i <= nIn + outParamCount; i++) {
				cstmt.registerOutParameter(i + 1, Types.OTHER);
			}
			// ************************************//

			cstmt.executeUpdate();

			if (outParamCount == 0) {
				String[] strReturn = new String[1];
				strReturn[0] = "";
				cstmt.close();
				conn.close();
				return strReturn;
			} else {
				String[] strReturn = new String[outParamCount];
				for (i = nIn; i < nIn + outParamCount; i++) {
					strReturn[i - nIn] = cstmt.getString(i + 1);
				}
				cstmt.close();
				conn.close();
				return strReturn;
			}
			// *******************************************//
		} catch (SQLException e) {
			String[] strReturn = new String[1];
			strReturn[0] = "XHNET" + e;
			System.out.println(e);
			try {
				cstmt.close();
				conn.close();
			} catch (SQLException ee) {
				System.out.println(ee);
			}
			return strReturn;
		}
	}

	public ResultSet ProcedureQuery(String procedureName, String inParam1, String inParam2, String inParam3) {

		Connection conn = getConnection();
		java.sql.CallableStatement cstmt = null;
		String strProcedure = "";
		int i;
		int nIn = 0;
		strProcedure = "{call" + " " + procedureName + "(?,?,?)}";
		// System.out.println(strProcedure);
		// *******************************************//
		try {
			cstmt = conn.prepareCall(strProcedure);

			cstmt.setString(1, inParam1);
			cstmt.setString(2, inParam2);
			cstmt.setString(3, inParam3);
			ResultSet rs = cstmt.executeQuery();
			return rs;
			// *******************************************//
		} catch (SQLException e) {
			String[] strReturn = new String[1];
			strReturn[0] = "XHNET" + e;
			System.out.println(e);
			try {
				cstmt.close();
				conn.close();
			} catch (SQLException ee) {
				System.out.println(ee);
			}
			return null;
		}
	}

	public ResultSet ProcedureQuery_jian(String procedureName, String inParam1, String inParam2, String inParam3, String inParam4) {

		Connection conn = getConnection();
		java.sql.CallableStatement cstmt = null;
		String strProcedure = "";
		int i;
		int nIn = 0;
		strProcedure = "{call" + " " + procedureName + "(?,?,?,?)}";
		// System.out.println(strProcedure);
		// *******************************************//
		try {
			cstmt = conn.prepareCall(strProcedure);

			cstmt.setString(1, inParam1);
			cstmt.setString(2, inParam2);
			cstmt.setString(3, inParam3);
			cstmt.setString(4, inParam4);
			ResultSet rs = cstmt.executeQuery();
			return rs;
			// *******************************************//
		} catch (SQLException e) {
			String[] strReturn = new String[1];
			strReturn[0] = "XHNET" + e;
			System.out.println(e);
			try {
				cstmt.close();
				conn.close();
			} catch (SQLException ee) {
				System.out.println(ee);
			}
			return null;
		}
	}

	public ResultSet ProcedureQuery_jian2(String procedureName, String inParam1, String inParam2, String inParam3, String inParam4, int inParam5) {
		Connection conn = getConnection();
		java.sql.CallableStatement cstmt = null;
		String strProcedure = "";
		int i;
		int nIn = 0;
		strProcedure = "{call" + " " + procedureName + "(?,?,?,?,?)}";
		// System.out.println(strProcedure);
		// *******************************************//
		try {
			cstmt = conn.prepareCall(strProcedure);

			cstmt.setString(1, inParam1);
			cstmt.setString(2, inParam2);
			cstmt.setString(3, inParam3);
			cstmt.setString(4, inParam4);
			cstmt.setInt(5, inParam5);
			ResultSet rs = cstmt.executeQuery();
			return rs;
			// *******************************************//
		} catch (SQLException e) {
			String[] strReturn = new String[1];
			strReturn[0] = "XHNET" + e;
			System.out.println(e);
			try {
				cstmt.close();
				conn.close();
			} catch (SQLException ee) {
				System.out.println(ee);
			}
			return null;
		}
	}

	public int getId(String tableName, String fieldName) {

		Connection con = getConnection();
		Statement stmt = null;
		int id = -1;
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT nvl(MAX(" + fieldName + "),0) FROM " + tableName);
			if (rs.next()) {
				id = rs.getInt(1) + 1;
			}
			rs.close();
			stmt.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			try {
				stmt.close();
				con.close();
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
		return id;
	}

	public int getId(String tableName, String fieldName, String strWhere) {

		int nRet = 0;
		String strTmp = "";
		Connection con = getConnection();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT Max(" + fieldName + ") KeyID FROM " + tableName + " " + strWhere);
			if (rs.next()) {
				strTmp = rs.getString("KeyID");
			}
			if (strTmp == null) {
				nRet = 1;
			} else {
				nRet = Integer.parseInt(strTmp) + 1;
			}
			rs.close();
			stmt.close();
			con.close();
			return nRet;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			try {
				stmt.close();
				con.close();
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
			return -1;
		}
	}






	public Statement createStatement() {
		// TODO Auto-generated method stub
		return null;
	}

}
