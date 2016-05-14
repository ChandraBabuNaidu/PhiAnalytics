package proj.DataSet;


import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import proj.Base.Setup;




public class TXDataSet {
	
	
	Connection con;
	public Properties SQLCONFIG;
	
	/**
	 * To establish data base connection with Transactional Data base. 
	 * and getting the Connection string from the database configuration properties file.
	 * 
	 * @throws SQLException
	 * @throws IOException
	 */
	public TXDataSet() throws SQLException, IOException
	{
		try
		{
		 // TO LOAD DATABASE PROPERTIES FILE.
		FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"//src//xtbills//Configuration//DataBase.properties");
		SQLCONFIG= new Properties();
		SQLCONFIG.load(fs);
		}
		catch(Exception se)
		{
			se.printStackTrace();
			System.out.println("DataBase config file was not found");
		}
		// TO ESTABLISH DATA BASE CONNECTION.
		String ConnectionUrl = SQLCONFIG.getProperty("TXConnectionUrl");		
		DriverManager.registerDriver(new com.microsoft.jdbc.sqlserver.SQLServerDriver()); 
		con = DriverManager.getConnection(ConnectionUrl);
		Setup.APP_LOGS.info("The Tx-Data Base Connection has established");
	}

	/**
	 * To get the count of the user companies 
	 * @param Username
	 * @return count of the user companies.
	 * @throws Exception
	 */
	
	// To get count of user companies .
	public ResultSet CountofCompanies(String Username) throws Exception
	{
		String SQLtestUserpassword = "select count(*) as CountofCompanies from tblUserCompany where userid in (select UserID from tbluser where username like ? and IsActive=1) and IsActive=1 and CompanyID in (select CompanyID from tblCompany where IsActive=1)";
		PreparedStatement ps= con.prepareStatement(SQLtestUserpassword);
		ps.setString(1, Username);
		ResultSet rs=ps.executeQuery();
		return rs;
	}
	
	
	/**
	 * To get the list of user companies in Alphabetic Order.
	 * 
	 * @param Username
	 * @return List of User Companies in Alphabetic order.
	 * @throws Exception
	 */
	// To Get List of UserCompanies
	
	public ResultSet ListofCompanies(String Username) throws Exception
	{
		String SQLtestUserpassword = "select * from tblCompany where companyid in (select CompanyID from tblUserCompany where userid in (select UserID from tbluser where username like ? and IsActive=1) and IsActive=1) order by CompanyName";
		PreparedStatement ps= con.prepareStatement(SQLtestUserpassword);
		ps.setString(1, Username);
		ResultSet rs=ps.executeQuery();
		return rs;
	}
	
	/**
	 * To Verify the  user present in the database with Active status.
	 * @param Username
	 * @return
	 * @throws Exception
	 */
	
	public ResultSet Uservalid(String Username) throws Exception
	{
		String SQLtestUserUsername = "select Username from tbluser where username like ?  and isactive=1";
		PreparedStatement ps= con.prepareStatement(SQLtestUserUsername);
		ps.setString(1, Username);
		ResultSet rs=ps.executeQuery();
		return rs;
	}
	
	/**
	 * To get the User Details from TXDatabase before Activation of the user
	 * @param Username
	 * @return
	 * @throws SQLException
	 */
	public ResultSet getCompanyDetails(String Username) throws SQLException
	{
		String SQLTestUser = "select uc.FirstName,uc.LastName,u.UserName,c.CompanyName,uc.PhoneNumber,c.StreetName,c.StreetName2,c.City, s.StateName,c.Zip,c.TrialPeriodExpiresOn from  tblUserCompany uc "
				+ "join tblUser u "
				+ "on u.UserID=uc.CreatedByUserID "
				+ "join tblCompany c "
				+ "on uc.CompanyID=c.CompanyID "
				+ "join tblState s "
				+ "on c.StateID=s.StateID "
				+ "where u.UserName like ? ";
		PreparedStatement ps= con.prepareStatement(SQLTestUser);
		ps.setString(1, Username);
		ResultSet rs=ps.executeQuery();
		return rs;
	}

	/**
	 * To verify the Privilege of the Login user
	 * @param Username
	 * @param CompanyName
	 * @return
	 * @throws Exception
	 */
	
	
	public ResultSet UserPrivilege(String Username,String CompanyName) throws Exception
	{
		String SQLtestUserPrivilege = "select PrivilegeName from tblPrivilege where PrivilegeID in "
				+ "(select PrivilegeID from tblUserCompany where userid in"
				+ "(select UserID from tbluser where username like ? and isactive=1) and CompanyID in "
				+ "(select CompanyID from tblcompany where CompanyName like ? and IsActive=1))";
		PreparedStatement ps = con.prepareStatement(SQLtestUserPrivilege);
		ps.setString(1, Username);
		ps.setString(2, CompanyName);
		ResultSet rs = ps.executeQuery();		
		return rs;
	}
	
	/**
	 * To verify the UserPermissions against to the Company
	 * @param Username
	 * @param CompanyName
	 * @return
	 * @throws Exception
	 */
	 
	
	public ResultSet UserPermission(String Username, String CompanyName) throws Exception
	{
		String SQLtestUserPermission = "select * from tblCompanyPrivilege where PrivilegeID in "
				+ "(select PrivilegeID from tblUserCompany where UserID in "
				+ "(select UserID from tbluser where username like ? and isactive=1)"
				+ "and companyid in (select CompanyID from tblcompany where CompanyName like ? and IsActive=1 )) "
				+ "and companyid in (select CompanyID from tblcompany where CompanyName like ? and IsActive=1) "
				+ "and IsActive=1";
		PreparedStatement ps = con.prepareStatement(SQLtestUserPermission);
		ps.setString(1, Username);
		ps.setString(2, CompanyName);
		ps.setString(3, CompanyName);
		ResultSet rs = ps.executeQuery();
		return rs;
		
	}
	
	/*To get the data in tblBankAccount and tblBankAccountHistory tables   */
	public ResultSet getBankDtls(String bankName,String companyName) throws SQLException{
		String bank="SELECT TOP 1 * FROM tblBankAccount WHERE BankName=? and CompanyId IN (SELECT CompanyId FROM tblCompany WHERE CompanyName like ?) ORDER BY 1 DESC";
		PreparedStatement ps=con.prepareStatement(bank);
		ps.setString(1, bankName);
		ps.setString(2, companyName);
		ResultSet rs=ps.executeQuery();
		return rs;
	}
	public ResultSet getVenodrBankDtls(String bankName,String AccountNumber, String RoutingNumber,String companyName, String VendorName) throws SQLException{
		
		System.out.println(bankName+"," +AccountNumber+","+ RoutingNumber+","+ companyName+""+ VendorName );
		String bank="SELECT TOP 1 * FROM tblBankAccount WHERE BankName=? and CompanyId IN (SELECT CompanyId FROM tblCompany WHERE CompanyName like ?) ORDER BY 1 DESC";
		PreparedStatement ps=con.prepareStatement(bank);
		ps.setString(1, bankName);
		// ps.setString(1, AccountNumber);
		//ps.setString(1, RoutingNumber);
		 ps.setString(2, companyName);
		//ps.setString(5, VendorName);
		ResultSet rs=ps.executeQuery();
		System.out.println("Result set");
		int size=rs.getRow();
		System.out.println(size);
		return rs;
	}
	
	public ResultSet  getbankdetails() throws SQLException
	{
		String bank="select * from tblBankAccount where isactive=1 ";
		PreparedStatement ps=con.prepareStatement(bank);
		ResultSet rs=ps.executeQuery();
		/*int size=rs.getInt("RecordsCount");
		System.out.println(size);
		return size;*/
		return rs;
		
	}
	
	public ResultSet getBankHistory(String bankName,String companyName) throws SQLException{
		String bank="SELECT TOP 1 * FROM tblBankAccountHistory WHERE BankName=? and CompanyId IN (SELECT CompanyId FROM tblCompany WHERE CompanyName like ?) ORDER BY 1 DESC";
		PreparedStatement ps=con.prepareStatement(bank);
		ps.setString(1, bankName);
		ps.setString(2, companyName);
		ResultSet rs=ps.executeQuery();
		return rs;
	}
	
	
	/**
	 * to get the new company created details
	 * @param Username
	 * @return
	 * @throws SQLException
	 */
	
	public ResultSet getNewCompanyDetails(String Username,String CompanyName) throws SQLException
	{
		String SQLTestUser = "select uc.FirstName,uc.LastName,u.UserName,c.CompanyName,uc.PhoneNumber,c.StreetName,c.StreetName2,c.City, s.StateName,c.Zip, datediff(day,GETDATE(),c.TrialPeriodExpiresOn) as 'days' from  tblUserCompany uc "
				+ "join tblUser u "
				+ "on u.UserID=uc.CreatedByUserID "
				+ "join tblCompany c "
				+ "on uc.CompanyID=c.CompanyID "
				+ "join tblState s "
				+ "on c.StateID=s.StateID "
				+ "where u.UserName like ? "
				+ "and c.CompanyName like ? ";
		PreparedStatement ps= con.prepareStatement(SQLTestUser);
		ps.setString(1, Username);
		ps.setString(2, CompanyName);
		ResultSet rs=ps.executeQuery();
		return rs;
	}
	

	/**
	 * to get the details of the bill status and count
	 * for the login user and checking company name 
	 * with create user of the company.
	 * 
	 * @param Username
	 * @param CompanyName
	 * @param Ownername
	 * @return
	 * @throws Exception
	 */
	public ResultSet getBillStatusandCount(String Username,String CompanyName, String Ownername) throws Exception
	{
		String SQLtestBillStausandCount = "select bs.BillStatusID,bs.StatusName,Count(b.BillStatusID) as 'bills count' from tblBill b "
				+ "join tblBillStatus bs "
				+ "on b.BillStatusID=bs.BillStatusID "
				+ "join tblCompany c "
				+ "on c.CompanyID=b.CompanyID j"
				+ "oin tblUserCompany uc "
				+ "on uc.CompanyID = c.CompanyID "
				+ "join tblUser u "
				+ "on u.UserID = uc.UserID "
				+ "where b.IsVendorCredit=0 "
				+ "and bs.BillStatusID in (31,33,34,36,37,38,51,52) "
				+ "and c.CompanyName like ? "
				+ "and u.UserName like ? "
				+ "and c.CreatedByUserID  in (select UserID from tblUser where UserName like ?) "
				+ "group by bs.BillStatusID,bs.StatusName"; 
		PreparedStatement ps = con.prepareStatement(SQLtestBillStausandCount);
		ps.setString(1, CompanyName);
		ps.setString(2, Username);
		ps.setString(3, Ownername);
		ResultSet rs = ps.executeQuery();		
		return rs;
	}
	
	/**
	 * to get the details of the Vendor Credit status and count
	 * for the login user and checking company name 
	 * with create user of the company.
	 * 
	 * 
	 * @param Username
	 * @param CompanyName
	 * @param Ownername
	 * @return
	 * @throws Exception
	 */
	public ResultSet getVCStatusandCount(String Username,String CompanyName, String Ownername) throws Exception
	{
		String SQLtestVCStausandCount = "select bs.BillStatusID,bs.StatusName,Count(b.BillStatusID) as 'bills count' from tblBill b "
				+ "join tblBillStatus bs "
				+ "on b.BillStatusID=bs.BillStatusID "
				+ "join tblCompany c "
				+ "on c.CompanyID=b.CompanyID j"
				+ "oin tblUserCompany uc "
				+ "on uc.CompanyID = c.CompanyID "
				+ "join tblUser u "
				+ "on u.UserID = uc.UserID "
				+ "where b.IsVendorCredit=1 "
				+ "and bs.BillStatusID in (31,33,34,36,37) "
				+ "and c.CompanyName like ? "
				+ "and u.UserName like ? "
				+ "and c.CreatedByUserID  in (select UserID from tblUser where UserName like ?) "
				+ "group by bs.BillStatusID,bs.StatusName"; 
		PreparedStatement ps = con.prepareStatement(SQLtestVCStausandCount);
		ps.setString(1, CompanyName);
		ps.setString(2, Username);
		ps.setString(3, Ownername);
		ResultSet rs = ps.executeQuery();		
		return rs;
	}
	
	/**
	 * to get the details of the bank status which are in pending verification status (93)
	 * for the login user and checking company name '
	 * along with created user of the company.
	 * only for the vendor banks
	 *  
	 * @param Username
	 * @param CompanyName
	 * @param Ownername
	 * @return
	 * @throws Exception
	 */
	
	public ResultSet getVendorBankStaus(String Username,String CompanyName, String Ownername) throws Exception
	{
		String SQLtestBankPendingVerification = "select count(b.CompanyID) as 'CountofVendorBank' from tblBankAccount b "
				+ "join tblUserCompany uc "
				+ "on uc.CompanyID= b.CompanyID "
				+ "join tblUser u on u.UserID = uc.UserID "
				+ "join tblCompany c "
				+ "on c.CompanyID=uc.CompanyID "
				+ "where u.UserName like ? "
				+ "and c.CompanyName= ? "
				+ "and b.VendorID is not null "
				+ "and c.CreatedByUserID  in (select UserID from tblUser where UserName like ?) "
				+ "and b.BankAccountStatusID = 93"; 
		PreparedStatement ps = con.prepareStatement(SQLtestBankPendingVerification);
		ps.setString(1, Username);
		ps.setString(2, CompanyName);
		ps.setString(3, Ownername);
		ResultSet rs = ps.executeQuery();		
		return rs;
	}
	
	/**
	 * to get the details of the bank status which are in pending verification status (93)
	 * for the login user and checking company name '
	 * along with created user of the company.
	 * only for the customer bank.
	 * 
	 * 
	 * @param Username
	 * @param CompanyName
	 * @param Ownername
	 * @return
	 * @throws Exception
	 */
	public ResultSet getCustomerBankStaus(String Username,String CompanyName, String Ownername) throws Exception
	{
		String SQLtestBankPendingVerification = "select count(b.CompanyID)  as 'CountofCustomerBank' from tblBankAccount b "
				+ "join tblUserCompany uc "
				+ "on uc.CompanyID= b.CompanyID "
				+ "join tblUser u "
				+ "on u.UserID = uc.UserID "
				+ "join tblCompany c "
				+ "on c.CompanyID=uc.CompanyID "
				+ "where u.UserName like ? "
				+ "and c.CompanyName= ? "
				+ "and b.VendorID is null "
				+ "and c.CreatedByUserID  in (select UserID from tblUser where UserName like ? ) "
				+ "and b.BankAccountStatusID = 93"; 
		PreparedStatement ps = con.prepareStatement(SQLtestBankPendingVerification);
		ps.setString(1, Username);
		ps.setString(2, CompanyName);
		ps.setString(3, Ownername);
		ResultSet rs = ps.executeQuery();		
		return rs;
	}
	
	/**
	 * to verify company email is setup or not
	 * @param Username
	 * @param CompanyName
	 * @param Ownername
	 * @return mail id, if found notification should not come
	 * @throws Exception
	 */
	public ResultSet getIsCompanyEmailSetup(String Username,String CompanyName, String Ownername) throws Exception
	{
		String SQLtestIsCompanyEmailSetup = "select i.EamilID from tblInboundEmail i "
				+ "join tblCompany c on c.CompanyID = i.CompanyID "
				+ "join tblUserCompany uc on uc.CompanyID = c.CompanyID "
				+ "join tbluser u on u.UserID = uc.UserID "
				+ "where u.UserName like ? "
				+ "and c.CompanyName= ? "
				+ "and c.CreatedByUserID  in (select UserID from tblUser where UserName like ?) "
				+ "and i.IsActive=1"; 
		PreparedStatement ps = con.prepareStatement(SQLtestIsCompanyEmailSetup);
		ps.setString(1, Username);
		ps.setString(2, CompanyName);
		ps.setString(3, Ownername);
		ResultSet rs = ps.executeQuery();		
		return rs;
	}
	
	/**
	 * to get company Expire Date.
	 * @param Username
	 * @param CompanyName
	 * @param Ownername
	 * @return
	 * @throws Exception
	 */
	public ResultSet getCompanyExpireDays(String Username,String CompanyName, String Ownername) throws Exception
	{
		String SQLtestCompanyExpireDays = "select datediff(day,GETDATE(),c.TrialPeriodExpiresOn) as 'Days Remaining'  from tblCompany c "
				+ "join tblUserCompany uc "
				+ "on uc.CompanyID = c.CompanyID "
				+ "join tbluser u "
				+ "on u.UserID = uc.UserID "
				+ "where u.UserName like ? "
				+ "and c.CompanyName= ? "
				+ "and c.CreatedByUserID  in (select UserID from tblUser where UserName like ?)";  
		PreparedStatement ps = con.prepareStatement(SQLtestCompanyExpireDays);
		ps.setString(1, Username);
		ps.setString(2, CompanyName);
		ps.setString(3, Ownername);
		ResultSet rs = ps.executeQuery();		
		return rs;
	}
	
	/**
	 * to get no of conflicts
	 * @param Username
	 * @param CompanyName
	 * @param Ownername
	 * @return
	 * @throws Exception
	 */
	public ResultSet getNoofConflicts(String Username,String CompanyName, String Ownername) throws Exception
	{
		String SQLtestConflicts = "select count(sy.CompanyID) as 'Conflicts' from tblSyncDetail sy "
				+ "join tblCompany c "
				+ "on c.CompanyID = sy.CompanyID "
				+ "join tblUserCompany uc "
				+ "on uc.CompanyID = c.CompanyID "
				+ "join tblUser u "
				+ "on u.UserID = uc.UserID "
				+ "where u.UserName like ? "
				+ "and c.CompanyName= ? "
				+ "and c.CreatedByUserID  in (select UserID from tblUser where UserName like ? ) "
				+ "and sy.SyncHistoryID = (Select top 1 SyncHistoryID from tblSyncHistory where CompanyID = c.CompanyID order by 1 desc)";  
		PreparedStatement ps = con.prepareStatement(SQLtestConflicts);
		ps.setString(1, Username);
		ps.setString(2, CompanyName);
		ps.setString(3, Ownername);
		ResultSet rs = ps.executeQuery();		
		return rs;
	}
	
	
	/**
	 * to get no of days left to QB Expire
	 * @param Username
	 * @param CompanyName
	 * @param Ownername
	 * @return
	 * @throws Exception
	 */
	public ResultSet getQBExpireDays(String Username,String CompanyName, String Ownername) throws Exception
	{
		String SQLtestQBExpireDays = "select datediff(day,GETDATE(),at.TokenExpiresOn) as 'QBExpires' from tblAccessToken at "
				+ "join tblCompany c "
				+ "on c.CompanyID = at.CompanyID "
				+ "join tblUserCompany uc "
				+ "on uc.CompanyID = c.CompanyID "
				+ "join tblUser u "
				+ "on u.UserID = uc.UserID "
				+ "where u.UserName like ? "
				+ "and c.CompanyName= ? "
				+ "and c.CreatedByUserID  in (select UserID from tblUser where UserName like ? ) ";  
		PreparedStatement ps = con.prepareStatement(SQLtestQBExpireDays);
		ps.setString(1, Username);
		ps.setString(2, CompanyName);
		ps.setString(3, Ownername);
		ResultSet rs = ps.executeQuery();		
		return rs;
	}
	
	/**
	 * to get the user permissions 
	 * @param Username
	 * @param CompanyName
	 * @param Ownername
	 * @return
	 * @throws Exception
	 */
	
	public ResultSet getUserPermission(String UserName,String CompanyName,String OwnerName) throws Exception
	{
		String SQLtestgetUserPermissions = "select cp.* from tblCompanyPrivilege cp "
				+ "join tblUserCompany uc "
				+ "on cp.PrivilegeID = uc.PrivilegeID "
				+ "join tblUser u "
				+ "on uc.UserID = u.UserID "
				+ "join tblCompany c "
				+ "on c.CompanyID = uc.CompanyID "
				+ "and u.UserName like ? "
				+ "and c.CompanyName like ? "
				+ "and uc.IsActive=1 "
				+ "and c.CreatedByUserID  in (select UserID from tblUser where UserName like ?)"
				+ "and cp.CompanyID=c.CompanyID ";  
		PreparedStatement ps = con.prepareStatement(SQLtestgetUserPermissions);
		ps.setString(1, UserName);
		ps.setString(2, CompanyName);
		ps.setString(3, OwnerName);
		ResultSet rs = ps.executeQuery();		
		return rs;
	}
	
	public ResultSet getddlUserTypeList() throws SQLException
	{
		String SQLPrivilege = "select PrivilegeName from tblPrivilege where isactive=1";
		PreparedStatement ps = con.prepareStatement(SQLPrivilege);
		ResultSet rs = ps.executeQuery();
		return rs;
	}
	
	public ResultSet getUserStatus(String UserName) throws SQLException
	{
		String SQLtestUserStatus = "select StatusName  from tblUserStatus where UserStatusID in "
				+ "(select UserStatusID from tbluser where username = ?)";
		PreparedStatement pp = con.prepareStatement(SQLtestUserStatus);
		pp.setString(1, UserName);
		ResultSet rs = pp.executeQuery();
		return rs;
	}
	
	public ResultSet getActiveUserDataofCompany(String Companyname) throws SQLException
	{
		String SQLtestUserData = "select UC.FirstName+' '+UC.LastName as Name,UC.PhoneNumber,U.UserName ,P.PrivilegeName as UserType,US.StatusName as Status  from tblUserCompany UC "
				+ "join tblPrivilege P "
				+ "on UC.PrivilegeID=P.PrivilegeID "
				+ "join tbluser U "
				+ "on U.UserID=UC.UserID "
				+ "join tblUserStatus US "
				+ "on U.UserStatusID=US.UserStatusID "
				+ "join tblCompany C "
				+ "on C.CompanyID=UC.CompanyID "
				+ "where UC.IsActive=1 and p.IsActive=1 and U.IsActive=1 and C.CompanyName=? "
				+ "order by UC.FirstName asc ";
		PreparedStatement pp = con.prepareStatement(SQLtestUserData);
		pp.setString(1, Companyname);
		ResultSet rs = pp.executeQuery();
		return rs;
	}
	
	public ResultSet getINActiveUserDataofCompany(String Companyname) throws SQLException
	{
		String SQLtestUserData = "select UC.FirstName+' '+UC.LastName as Name,UC.PhoneNumber,U.UserName ,P.PrivilegeName as UserType,US.StatusName as Status  from tblUserCompany UC "
				+ "join tblPrivilege P "
				+ "on UC.PrivilegeID=P.PrivilegeID "
				+ "join tbluser U "
				+ "on U.UserID=UC.UserID "
				+ "join tblUserStatus US "
				+ "on U.UserStatusID=US.UserStatusID "
				+ "join tblCompany C "
				+ "on C.CompanyID=UC.CompanyID "
				+ "where UC.IsActive=0 and p.IsActive=1 and U.IsActive=1 and C.CompanyName=? "
				+ "order by UC.FirstName asc ";
		PreparedStatement pp = con.prepareStatement(SQLtestUserData);
		pp.setString(1, Companyname);
		ResultSet rs = pp.executeQuery();
		return rs;
	}
	
	public ResultSet getActiveandInactiveUserDataofCompany(String Companyname) throws SQLException
	{
		String SQLtestUserData = "select UC.FirstName+' '+UC.LastName as Name,UC.PhoneNumber,U.UserName ,P.PrivilegeName as UserType,US.StatusName as Status,UC.IsActive  from tblUserCompany UC "
				+ "join tblPrivilege P "
				+ "on UC.PrivilegeID=P.PrivilegeID "
				+ "join tbluser U "
				+ "on U.UserID=UC.UserID "
				+ "join tblUserStatus US "
				+ "on U.UserStatusID=US.UserStatusID "
				+ "join tblCompany C "
				+ "on C.CompanyID=UC.CompanyID "
				+ "where p.IsActive=1 and U.IsActive=1 and C.CompanyName=? "
				+ "order by UC.FirstName asc ";
		PreparedStatement pp = con.prepareStatement(SQLtestUserData);
		pp.setString(1, Companyname);
		ResultSet rs = pp.executeQuery();
		return rs;
	}
	
	public ResultSet getActiveandInactiveUsersofthecompany(String Companyname) throws SQLException
	{
		String SQLtestUserData = "select count (*) as count from tblUserCompany UC  where companyid in "
				+ "(select companyid from tblcompany where CompanyName = ?) ";
		PreparedStatement pp = con.prepareStatement(SQLtestUserData);
		pp.setString(1, Companyname);
		ResultSet rs = pp.executeQuery();
		return rs;
	}
	
	public ResultSet getcountddlUserTypeList() throws SQLException
	{
		String SQLPrivileges = "select count(PrivilegeName) as PrivilegeName from tblPrivilege where isactive=1";
		PreparedStatement pp = con.prepareStatement(SQLPrivileges);
		ResultSet rs = pp.executeQuery();
		return rs;
	}
	
	/**
	 * to get Bill Ready for Payment and PArtially Paid count status 
	 * @param Username
	 * @param CompanyName
	 * @param Ownername
	 * @return
	 * @throws Exception
	 */
	public ResultSet getRFPandPPBillStatusandCount(String Username,String CompanyName, String Ownername) throws Exception
	{
		String SQLtestBillStausandCount = "select bs.BillStatusID,bs.StatusName,Count(b.BillStatusID) as 'bills count' from tblBill b "
				+ "join tblBillStatus bs "
				+ "on b.BillStatusID=bs.BillStatusID "
				+ "join tblCompany c "
				+ "on c.CompanyID=b.CompanyID "
				+ "join tblUserCompany uc "
				+ "on uc.CompanyID = c.CompanyID "
				+ "join tblUser u "
				+ "on u.UserID = uc.UserID "
				+ "where b.IsVendorCredit=0 "
				+ "and bs.BillStatusID in (38,52) "
				+ "and c.CompanyName like ? "
				+ "and u.UserName like ? "
				+ "and c.CreatedByUserID  in (select UserID from tblUser where UserName like ?) "
				+ "and b.isactive=1 and b.TotalAmountDue <>0 and b.ApprovedAmount <>0 "
				+ "group by bs.BillStatusID,bs.StatusName"; 
		PreparedStatement ps = con.prepareStatement(SQLtestBillStausandCount);
		ps.setString(1, CompanyName);
		ps.setString(2, Username);
		ps.setString(3, Ownername);
		ResultSet rs = ps.executeQuery();		
		return rs;
	}
		
	public ResultSet  getVendorBankHistoryDetails(String bankName, String AccountNumber, String RoutingNumber, String companyName, String VendorName, int Statusid) throws SQLException
	{
		//String company="UHG";
		ResultSet rs=null;
		
		System.out.println(bankName+" "+companyName);
		if((AccountNumber != null)&&(RoutingNumber !=null)){
			
		
		String bank="select Top 1 * from tblBankAccounthistory where  BankName = ? " +
				"and AccountNumber=? " +
				"and RoutingNumber like ? " +
				"and CompanyID in (select CompanyID from tblCompany where CompanyName like ? and BankAccountStatusID=?)";
		
		PreparedStatement ps=con.prepareStatement(bank);
		ps.setString(1, bankName);
		ps.setString(2, AccountNumber);
		ps.setString(3, RoutingNumber);
		ps.setString(4, companyName);
		ps.setInt(5, Statusid);
		 rs=ps.executeQuery();
		
		}
		else{
			System.out.println("Entered into the Else block");
			String bank="select * from tblBankAccounthistory where  BankName = ? " +
						"and CompanyID in (select CompanyID from tblCompany where CompanyName like ?) and vendorid in (select VendorID from tblVendor where VendorName like ? and IsActive=1)  and BankAccountStatusID=? ";
			PreparedStatement ps=con.prepareStatement(bank);
			ps.setString(1, bankName);
			ps.setString(2, companyName);
			ps.setString(3, VendorName);
			ps.setInt(4, Statusid);
			rs=ps.executeQuery();
		}
		return rs;
	}
	public ResultSet  getAmounts(String bankName, String companyName, String VendorName) throws SQLException
	{
		System.out.println(bankName+companyName+VendorName);
		String BankAmts	="select Top 1 * from tblBankAccount " +
		"where BankName like ? and  CompanyID in " +
		"(select CompanyID from tblCompany where CompanyName like ?)" +
		" and VendorID in (select VendorID from tblVendor where VendorName like ?) " +
		"and DebitAmount1 is not null and CreditAmount is not null and DebitAmount2 is not null";
			
		PreparedStatement ps=con.prepareStatement(BankAmts);
		ps.setString(1, bankName);
		ps.setString(2, companyName);
		ps.setString(3, VendorName);
		
		//ps.setString(5, VendorName);
		ResultSet rs=ps.executeQuery();
		return rs;
	}
	public ResultSet  getRecordAfterVerfication(String bankName, String companyName, String VendorName) throws SQLException
	{
		String BankAmts	="select Top 1 CreditAmount,DebitAmount1,DebitAmount2,BANKACCOUNTSTATUSID from tblBankAccount " +
		"where BankName like ? and  CompanyID in " +
		"(select CompanyID from tblCompany where CompanyName like ?)" +
		" and VendorID in (select VendorID from tblVendor where VendorName like ?) " +
		//"and DebitAmount1 is not null and CreditAmount is not null and DebitAmount2 is not null " +
		"AND BANKACCOUNTSTATUSID=92";
						
		PreparedStatement ps=con.prepareStatement(BankAmts);
		ps.setString(1, bankName);
		ps.setString(2, companyName);
		ps.setString(3, VendorName);
		
		//ps.setString(5, VendorName);
		ResultSet rs=ps.executeQuery();
		return rs;
	}


	public ResultSet  getVendorBankDetails(String bankName,String companyName, String VendorName,int statusid) throws SQLException
	{
		//String company="UHG";
		System.out.println(bankName+" "+companyName+VendorName+statusid);
		String bank="select * from tblBankAccount where  BankName = ?	and CompanyID in (select CompanyID from tblCompany where CompanyName like ?) and vendorid in (select VendorID from tblVendor where VendorName like ? and IsActive=1) and BankAccountStatusID = ?  ";
		/*String bank=" SELECT TOP 1 * FROM tblBankAccount WHERE BankName = ? " +
				"and CompanyId IN (SELECT CompanyId FROM tblCompany WHERE CompanyName = ?) ORDER BY 1 DESC";*/
		//String bank1="select Top 1* from tblBankAccount where  BankName like ? and CompanyID in (select CompanyID from tblCompany where CompanyName like ?)"; 
				//"and AccountNumber like ? " +
				//"and RoutingNumber like ? " +
			
		PreparedStatement ps=con.prepareStatement(bank);
		System.out.println(bank);
		ps.setString(1, bankName);
		ps.setString(2, companyName);
		ps.setString(3, VendorName);
		ps.setInt(4, statusid);
		
		ResultSet rs=ps.executeQuery();
		return rs;
	}


	public ResultSet getActiveBankRecords(String Vendorname, String CompanyName) throws SQLException
	{
		String bank= "select count(*)TotalCount from tblBankAccount where VendorID in (select VendorID from tblVendor where VendorName like ?) " +
					" and IsActive=1 and BankAccountStatusID not in (90,96) and CompanyID in (select CompanyID from tblCompany where IsActive=1 and CompanyName like ?) ";
		PreparedStatement ps=con.prepareStatement(bank);
		ps.setString(1, Vendorname);
		ps.setString(2, CompanyName);
		ResultSet rs=ps.executeQuery();
		return rs;
	}
	
	public ResultSet  getVendorBankData(String bankName, String companyName, String VendorName, int Status) throws SQLException
	{
		System.out.println(bankName+companyName+VendorName+Status);
		
		String BankAmts	="select  * from tblBankAccount " +
		"where BankName like ? and  CompanyID in " +
		"(select CompanyID from tblCompany where CompanyName like ?)" +
	" and VendorID in (select VendorID from tblVendor where VendorName like ?) and BankAccountStatusID=?" ;
		
		PreparedStatement ps=con.prepareStatement(BankAmts);
		ps.setString(1, bankName);
		ps.setString(2, companyName);
		ps.setString(3, VendorName);
		ps.setInt(4, Status);
		ResultSet rs=ps.executeQuery();
		return rs;
	}
	public ResultSet getVendorBankDataInAscendingOrder(String companyName, String VendorName, String Reffrencevalue) throws SQLException
	{
		System.out.println(companyName+VendorName);
		ResultSet rs = null;
		if(Reffrencevalue.contentEquals("Bank Name")){
		
		String BankAmts	="select * from tblBankAccount BA " 
				+ "join tblBankAccountStatus BAS " 
				+ "on BA.BankAccountStatusID=BAS.BankAccountStatusID " 
				+ "where  CompanyID in " 
				+ "(select CompanyID from tblCompany where CompanyName like ? and isactive=1) " 
				+ " and VendorID in (select VendorID from tblVendor where VendorName like ? and isactive=1) and isactive=1 order by bankName " ;
		PreparedStatement ps=con.prepareStatement(BankAmts);
		ps.setString(1, companyName);
		ps.setString(2, VendorName);
		rs=ps.executeQuery();
		}
		else if(Reffrencevalue.contentEquals("Status")){
			String BankAmts	="select * from tblBankAccount BA " 
					+ "join tblBankAccountStatus BAS " 
					+ "on BA.BankAccountStatusID=BAS.BankAccountStatusID " 
					+ "where  CompanyID in " 
					+ "(select CompanyID from tblCompany where CompanyName like ? and isactive=1) " 
					+ " and VendorID in (select VendorID from tblVendor where VendorName like ? and isactive=1) and isactive=1 order by StatusName " ;
			PreparedStatement ps=con.prepareStatement(BankAmts);
			ps.setString(1, companyName);
			ps.setString(2, VendorName);
			rs=ps.executeQuery();
		}
		return rs;
	}
	public ResultSet getVendorBankDataInDescendingOrder(String companyName, String VendorName, String Reffrencevalue) throws SQLException
	{
		System.out.println(companyName+VendorName+Reffrencevalue);
		ResultSet rs = null;
		if(Reffrencevalue.contentEquals("Bank Name")){
		
		String BankAmts	="select * from tblBankAccount BA " 
				+ "join tblBankAccountStatus BAS " 
				+ "on BA.BankAccountStatusID=BAS.BankAccountStatusID " 
				+ "where  CompanyID in " 
				+ "(select CompanyID from tblCompany where CompanyName like ? and isactive=1) " 
				+ " and VendorID in (select VendorID from tblVendor where VendorName like ? and isactive=1) and isactive=1 order by bankName desc " ;
		PreparedStatement ps=con.prepareStatement(BankAmts);
		ps.setString(1, companyName);
		ps.setString(2, VendorName);
		rs=ps.executeQuery();
		}
		else if(Reffrencevalue.contentEquals("Status")){
			String BankAmts	="select * from tblBankAccount BA " 
					+ "join tblBankAccountStatus BAS " 
					+ "on BA.BankAccountStatusID=BAS.BankAccountStatusID " 
					+ "where  CompanyID in " 
					+ "(select CompanyID from tblCompany where CompanyName like ? and isactive=1) " 
					+ " and VendorID in (select VendorID from tblVendor where VendorName like ? and isactive=1) and isactive=1 order by StatusName desc" ;
			PreparedStatement ps=con.prepareStatement(BankAmts);
			ps.setString(1, companyName);
			ps.setString(2, VendorName);
			rs=ps.executeQuery();
		}
		return rs;
	}
	
	/**
	 * To get Last Sync Details.
	 * @param companyName
	 * @return
	 * @throws SQLException
	 */
	
	public ResultSet  getLastSyncDetails(String companyName) throws SQLException
	{		
		String LastSync	="select Top 1 * from tblSyncHistory where CompanyId in "
				+ "(select CompanyID from tblCompany where CompanyName=?) order by 1 desc" ;
		PreparedStatement ps=con.prepareStatement(LastSync);
		ps.setString(1, companyName);		
		ResultSet rs=ps.executeQuery();
		return rs;
	}
public ResultSet getVendorDetails(String CompanyName, String VendorName) throws SQLException{
	
	String vendorinfo= "select * from tblVendor where CompanyID in " 
			+"(select CompanyID from tblCompany where CompanyName like ? " 
			+"and IsActive=1)" 
			+"and VendorName like ? " 
			+"and IsActive=1";
	System.out.println(vendorinfo);
	PreparedStatement ps=con.prepareStatement(vendorinfo);
	ps.setString(1, CompanyName);	
	ps.setString(2, VendorName);
	ResultSet rs=ps.executeQuery();
	return rs;
}
public ResultSet getAllVendorDetails(String CompanyName) throws SQLException{
	
	String vendorinfo= "select * from tblVendor where CompanyID in " 
			+"(select CompanyID from tblCompany where CompanyName like ? " 
			+"and IsActive=1)" 
			+"and IsActive=1";
	System.out.println(vendorinfo);
	PreparedStatement ps=con.prepareStatement(vendorinfo);
	ps.setString(1, CompanyName);	
	ResultSet rs=ps.executeQuery();
	return rs;
}



/**
 * to get the company privilege setting for the user 
 * based on the given user privilege name.
 * @param CompanyName
 * @param OwnerName
 * @return
 * @throws SQLException
 */
public ResultSet getCompanyPrivileges(String CompanyName,String OwnerName, String PrivilegeName) throws SQLException{
	
	String companyprivilege= "select p.PrivilegeName,cp.* from tblCompanyPrivilege cp "
			+ "join tblCompany c "
			+ "on c.CompanyID = cp.CompanyID "
			+ "join tblUser u "
			+ "on c.CreatedByUserID = u.UserID "
			+ "join tblPrivilege p "
			+ "on cp.PrivilegeID=p.PrivilegeID "
			+ "where p.PrivilegeName like ? "
			+ "and c.CompanyName like ? "
			+ "and c.CreatedByUserID  in (select UserID from tblUser where UserName like ?)";
	PreparedStatement ps=con.prepareStatement(companyprivilege);
	ps.setString(1, PrivilegeName);
	ps.setString(2, CompanyName);
	ps.setString(3, OwnerName);
	ResultSet rs=ps.executeQuery();
	System.out.println("The Company Privilege query excuted");
	return rs;
}

	public void connectionclose() throws SQLException
	{
		con.close();
	}
}
