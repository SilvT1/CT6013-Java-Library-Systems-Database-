/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ct6013librarysystem;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import org.bson.Document;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Iterator;

/**
 *
 * @author TSilva
 */
public class CT6013LibrarySystem {
    
    
    // getConnection is to help connect to the Oracle database using Oracle Driver called ojdbc6.jar 
    public Connection getConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
            return null;
        }

        System.out.println("Oracle JDBC Driver Registered!");

        Connection connection = null;
        
        
        // DriverManager is using the namespace and password to connect to the database
        try {
            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@apollo01.glos.ac.uk:1521:orcl", "nameSpace",
                    "nameSpace"); //Namespace currently does not exist
                  
                    
            if (connection != null) {
                System.out.println("Database connection successful!");
                return connection;
            } else {
                System.out.println("Database connection failed!");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return null;
        }        
    }
    
    
    
    
    // createBooking is for creating the loan and fine record by using Booking_Loan_ID and Fine_ID for each table which also both use Copy_ID and Student Number
    public void createBooking(Document booking) {
        String insertBooking =  "INSERT INTO TBL_LOAN_BOOKING" 
                + "(BOOKING_LOAN_ID, COPY_ID, STUDENT_NUMBER, START_LOAN_DATE, EXPECTED_RETURN_DATE, ACTUAL_RETURN_DATE)" + "VALUES ("
                + " '" + booking.get("Booking_Loan_ID") + "', " 
                + " '" + booking.get("Copy_ID") + "', " 
                + " '" + booking.get("Student_Number") + "', "
                + " sysdate" + ", "
                + " add_months(SYSDATE, 1) " + ", "
                + " null " + ")";
        // insertBooking inserts data about the loan being made using Booking_Loan_ID as the primary key and copy_id and student_number as foreign keys
        // insertBooking also looks at inserting the current date for start_loan_date and actual return date is a set amount of days away from the start_loan_date
        
        String insertFine = "INSERT INTO TBL_FINE" 
				+ "(FINE_ID, BOOKING_LOAN_ID, COPY_ID, BOOK_OVERDUE, TOTAL_FINE, FINE_PAID, DATE_FINE_PAID) " + "VALUES ("
				+ " '" + booking.get("Fine_ID") + "', " 
                                + " '" + booking.get("Booking_Loan_ID") + "', " 
                                + " '" + booking.get("Copy_ID") + "', " 
                                + " null " + ", "
                                + " null " + ", "
                                + " null " + ", "
                                + " null " + ")";
        // insertFine inserts data about the fine being made using Fine_ID as the primary key and copy_id and student_number as foreign keys
        // insertFine also looks at inserting the null values for the other values 
        
        ArrayList arr = new ArrayList();
        Statement stmt = null;
        
        try {
            Connection con = getConnection();
            stmt = con.createStatement();
            
            System.out.println(insertBooking);
            // Line 108 and 109 are telling the code to execute the insertBooking SQL
            stmt.executeUpdate(insertBooking);

            System.out.println(insertFine);
            // Line 112 and 114 are telling the code to execute the insertFine SQL
            stmt.executeUpdate(insertFine);
    

            
            stmt.close();
            
        } catch (SQLException e ) {
            e.printStackTrace();
        } 
    }
       
      
          
        
    
    public Document getBook(String copyID) {
        String bookCopy = "select c.copy_ID ,b.* " + " from TBL_BOOKS b"
                + " FULL OUTER JOIN TBL_Copy C ON B.Book_ID = C.Book_ID "
                + "where C.Copy_ID = '" + copyID + "'";
        // bookCopy gathers data from two different tables to allow me to make a query showing the copy_id of a book and all the other book details
        try {
        Connection con = getConnection();
        Statement stmt = con.createStatement();
        
        ResultSet rs = stmt.executeQuery(bookCopy);
        //Line 138 tells the code to execute the SQL query that bookCopy is connected to
        while(rs.next()) {
        Document book = new Document();
        book.append("Copy_ID", rs.getString("Copy_ID"));
        book.append("Book_ID", rs.getString("Book_ID"));
        book.append("ISBN", rs.getString("ISBN"));
        book.append("Title", rs.getString("Title"));
        book.append("Copy_Number", rs.getString("Copy_Number"));
        book.append("Author_ID", rs.getString("Author_ID"));
        // document book is getting the data gathered in the sql and puting it in a document which will be for
        // the table to display in the application for FormLibraryBooking/tblBookList
        return book;
        }
        } catch (SQLException sqle) {
        
        }
        
        return null;        
    }
    
    
    
    
    public void returningBook(Document returning) {
        String updateBooking =  "UPDATE TBL_LOAN_BOOKING " 
                + "SET ACTUAL_RETURN_DATE = " + " sysdate "
                + "WHERE BOOKING_LOAN_ID = " + "'" + returning.get("Booking_Loan_ID") + "'";
        // the updateBooking is using a script to update loans by updating the actual_return_date depending on the booking_loan_id
        //value
        String updateFine = "UPDATE TBL_FINE " 
				+ " SET BOOK_OVERDUE = (select round(lb.actual_return_date - lb.EXPECTED_RETURN_DATE) from TBL_FINE f, TBL_LOAN_BOOKING lb "
                        + " where f.BOOKING_LOAN_ID=lb.BOOKING_LOAN_ID and lb.COPY_ID=f.COPY_ID " 
                        + " AND FINE_ID = "+ "'" + returning.get("Fine_ID") +"') "
                        + " WHERE FINE_ID = " + "'" + returning.get("Fine_ID") + "'";
        // the updateFine is using a script to update fines by updating the book_overdue depending on the fine_id
        //value        
        String updateFine2 = "UPDATE TBL_FINE " 
				+ " SET TOTAL_FINE = (SELECT (CASE  WHEN BOOK_OVERDUE <= -1 THEN 0 + 10  WHEN BOOK_OVERDUE >= 0 THEN BOOK_OVERDUE + 10 END) as calculate  FROM TBL_FINE"
                                + " WHERE FINE_ID = "+ "'" + returning.get("Fine_ID") +"') "
                                + " WHERE FINE_ID = " + "'" + returning.get("Fine_ID") + "'";
        // the updateFine2 is using a script for another seperate fine update for total fine using book_overdue with a case condition
        // where is book_overdue is less than 0 than book_overdue must return 0
        // a second fine script was made because when the first ad second where combined total fine gave a different value in the application
        // than oracle did
        
        
        ArrayList arr = new ArrayList();
        Statement stmt = null;
        
        try {
            Connection con = getConnection();
            stmt = con.createStatement();
            
            System.out.println(updateBooking);
            // Line 192 and 194 are telling the code to execute the updateBooking SQL
            stmt.executeUpdate(updateBooking);
            
            System.out.println(updateFine);
                // Line 196 and 198 are telling the code to execute the updateFine SQL
            stmt.executeUpdate(updateFine);
                
            System.out.println(updateFine2);
                // Line 120 and 122 are telling the code to execute the updateFine2 SQL
            stmt.executeUpdate(updateFine2);
            

                      
            stmt.close();
            
            
        } catch (SQLException e ) {
            e.printStackTrace();
        } 
        
        
            
        
    }                   
    
    
    
    public Document checkLoan(String fineID, String studentNumber) {
        String checkLoanByFine = "select f.fine_ID , l.* " + " from TBL_FINE f, TBL_LOAN_BOOKING l "
                + " where f.BOOKING_LOAN_ID = l.BOOKING_LOAN_ID and l.COPY_ID=f.COPY_ID "
                + "and f.Fine_ID = '" + fineID + "'"
                + "and l.student_number = '" + studentNumber + "'";
        // checkLoanByFine is equal to an sql script to gather and display data from two different tables from the
        //fine table and loan table for fine_id and loan details, using fine_ID and the student number as a search critera/condition
  
        
        try {
        Connection con = getConnection();
        Statement stmt = con.createStatement();
        
        ResultSet rs = stmt.executeQuery(checkLoanByFine);
        //Line 234 tells the code to execute the SQL query that checkLoanByFine is connected to
        while(rs.next()) {
        Document loanBooking = new Document();
        loanBooking.append("Booking_Loan_ID", rs.getString("Booking_Loan_ID"));
        loanBooking.append("Copy_ID", rs.getString("Copy_ID"));
        loanBooking.append("Start_Loan_Date", rs.getString("Start_Loan_Date"));
        loanBooking.append("Expected_Return_Date", rs.getString("Expected_Return_Date"));
        loanBooking.append("Actual_Return_Date", rs.getString("Actual_Return_Date"));
        return loanBooking;
        }
        } catch (SQLException sqle) {
        
        }
        // document loanBooking is getting the data gathered in the checkLoanByFine sql query and puting it 
        // in a document which will be for the table to display in the application for FormLibraryFinePayments/tblLoanList
        // if a catch exception for an error from the sql is caught than the code must return null
        return null;
    }
    
                       
        
        public Document getFineInfo(String fineID, String bookLoanID, String copyID) { 
        String viewFineDetails = "select fine_id, book_overdue, total_fine, fine_paid from tbl_fine " 
                       + " where fine_id = " + "'" + fineID + "'"
                       + " and booking_loan_id = " + "'" + bookLoanID + "'"
                       + " and copy_id = " + " '" + copyID + "'";
        // viewFineTable is equal to an sql script to gather and display data from the fine table for fine_id 
        // and loan details, using fine_ID and the student booking_loan_id and copyID as a search critera/condition        
        try {
        Connection con = getConnection();
        Statement stmt = con.createStatement();
        
        ResultSet rs = stmt.executeQuery(viewFineDetails);
        // execute the query for the sql that the viewFineDetails is equal to 
        while(rs.next()) {
        Document fine_Info = new Document();
        fine_Info.append("Fine_ID", rs.getString("Fine_ID"));
        fine_Info.append("Book_Overdue", rs.getString("Book_Overdue"));
        fine_Info.append("Total_Fine", rs.getString("Total_Fine"));
        fine_Info.append("Fine_Paid", rs.getString("Fine_Paid"));
        return fine_Info;
        }
        } catch (SQLException sqle) {
        
        }
        // document fine_Info is getting the data gathered in the viewFineDetails sql query and puting it 
        // in a document which will be for the table to display in the application for FormLibraryFinePayments/tblFineList
        // if a catch exception for an error from the sql is caught than the code must return null
            
        return null;
    }
        
        
        
        
        
        
        
        public void finePayment(Document returning) {
        String payingFine =  "UPDATE TBL_Fine " 
                + " SET FINE_PAID = TOTAL_FINE, DATE_FINE_PAID = " + " sysdate "
                + " WHERE Fine_ID = " + "'" + returning.get("Fine_ID") + "'";
        // payingFine is equal to an sql script to gather and display data from the fine table for fine_id 
        // and loan details, using fine_ID as a search critera/condition 
        ArrayList arr = new ArrayList();
        Statement stmt = null;
        
        try {
            Connection con = getConnection();
            stmt = con.createStatement();
            
            System.out.println(payingFine);
            // Line 305 and 307 are telling the code to execute the payingFine SQL
            stmt.executeUpdate(payingFine);
                         
            stmt.close();
            
            
        } catch (SQLException e ) {
            e.printStackTrace();
        } 
        
   
    }                  
    
    
        
        
        
        
        
        public ArrayList<Document> getBookLoanHistory(Date fromDate, Date toDate, String student_number) {
        String bookLoanHistory = "Select TBL_LOAN_BOOKING.Booking_loan_ID, TBL_COPY.copy_ID ,TBL_STUDENT.student_number, first_name, last_name, Date_Of_Birth, city, postcode, start_loan_date, tbl_Books.Book_ID, ISBN, title, copy_number, author_id " +
                       " from TBL_STUDENT " +
                       " inner join TBL_LOAN_BOOKING " +
                       " on TBL_STUDENT.student_number = TBL_LOAN_BOOKING.student_number " +
                       "inner join tbl_fine on TBL_LOAN_BOOKING.copy_ID = tbl_fine.copy_ID" +
                       " inner join TBL_COPY " +
                       " on TBL_LOAN_BOOKING.copy_id = TBL_COPY.copy_id " +
                       " inner join TBL_BOOKS " +
                       " on TBL_COPY.BOOK_ID = TBL_BOOKS.BOOK_ID  " +
                       " WHERE TBL_STUDENT.student_number = '" + student_number + "'" +
                       " AND Start_Loan_Date BETWEEN TO_DATE('" + new SimpleDateFormat("MM/dd/yyyy").format(fromDate) + "', 'mm/dd/yyyy') \n" +
                       " AND TO_DATE('" + new SimpleDateFormat("MM/dd/yyyy").format(toDate) + "', 'mm/dd/yyyy')";
        // bookLoanHistory is equal to an sql script to gather and display data from multiple tables from the student,
        // loan, fine, copy and books table using student_number and to dates from a calander as the search 
        // critera/condition to find and display the data
        
        System.out.println(bookLoanHistory);
        
        ArrayList bookLoan_history = new ArrayList();
        
        Statement stmt = null;
        try {
            Connection con = getConnection();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(bookLoanHistory);
            // Line 351 helps to execute a print command to display the query I have recieved from the bookLoanHistory query
            
            Document loanDetails = new Document();
            String current_loan_no = "";
            String loan_in_rs_row;
            while (rs.next()) {
                
                
                Document loanBook = new Document();
                loanBook.append("Book_ID", rs.getString("Book_ID"));
                loanBook.append("ISBN", rs.getString("ISBN"));
                loanBook.append("Title", rs.getString("Title"));
                loanBook.append("Copy_Number", rs.getString("Copy_Number"));
                loanBook.append("Author_ID", rs.getString("Author_ID"));
                // The document loanBook is to help display the information gather from the queryto put into
                // a document to display in FormViewLoanHistory for txtLoan_History
                loan_in_rs_row = rs.getString("Booking_Loan_ID");
                
                ArrayList<Document> loan_books;
                if (loan_in_rs_row.equalsIgnoreCase(current_loan_no) == false) {
                    loanDetails = new Document();
                    
                    loanDetails.append("Booking_Loan_ID", loan_in_rs_row);
                    loanDetails.append("Copy_ID", rs.getString("Copy_ID"));
                    loanDetails.append("Student_Number", rs.getString("Student_Number"));
                    loanDetails.append("First_Name", rs.getString("First_Name"));
                    loanDetails.append("Last_Name", rs.getString("Last_Name"));
                    loanDetails.append("Date_Of_Birth", rs.getString("Date_Of_Birth"));
                    loanDetails.append("City", rs.getString("City"));
                    loanDetails.append("Postcode", rs.getString("Postcode"));
                    loanDetails.append("Start_Loan_Date", rs.getString("Start_Loan_Date"));
                    
                    loan_books = new ArrayList();
                    loan_books.add(loanBook);
                    loanDetails.append("Book_Loan_List", loan_books);
                    
                    // The document loanDetails is a different and seperate layer of data gathered from the bookLoanHistory
                    // sql query to go together with the above document loanBook is to help display the information 
                    // gather from the query to put into a document to display in FormViewLoanHistory
                    // for txtLoan_History
                    
                    current_loan_no = loan_in_rs_row;
                    bookLoan_history.add(loanDetails);
                } else {
                    
                    ((ArrayList)loanDetails.get("Book_Loan_List")).add(loanBook);
                }
                
                
                
            }
            
            stmt.close();
            
            return bookLoan_history;
            
        } catch (SQLException e ) {
            e.printStackTrace();
        } 
        
        return null;
    }
        
        
        
        
        
        
        
        
        public double getTotalFineSpent(Date fromDate, Date toDate, String student_number) {
        String totalFine = "Select SUM(FINE_PAID) as Total  " +
                       " from tbl_Fine " +
                       " inner join TBL_LOAN_BOOKING " +
                       " on tbl_Fine.copy_id = TBL_LOAN_BOOKING.copy_id " +
                       " WHERE TBL_LOAN_BOOKING.student_number = '" + student_number + "'" +
                       " AND DATE_FINE_PAID BETWEEN TO_DATE('" + new SimpleDateFormat("MM/dd/yyyy").format(fromDate) + "', 'mm/dd/yyyy') \n" +
                       " AND TO_DATE('" + new SimpleDateFormat("MM/dd/yyyy").format(toDate) + "', 'mm/dd/yyyy')";
        // totalFine is equal to an sql script to gather and display data from two tables (fine and loan table
        // using the student_number and to dates from a calander as the search 
        // critera/condition to find and display the data               
        
        System.out.println(totalFine);
        // Line 426 helps to execute a print command to display the query I have recieved from the total_Fine query
        ArrayList fine_list = new ArrayList();
        
        Statement stmt = null;
        try {
            Connection con = getConnection();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(totalFine);
            
            while (rs.next()) {
                double total_fine_spent = Double.parseDouble(rs.getString("Total"));
                return total_fine_spent;
            }
           // rs.next helps to return "Total" as the sum value from the toalFine sql query into the text area in
           //FormViewTotalFinePaid called txt_Fines_Paid
        } catch (SQLException e ) {
            e.printStackTrace();
        } 
        
        return 0;
    }
        
        
        
        
        

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        CT6013LibrarySystem dTest = new CT6013LibrarySystem();
        dTest.getBook("BID0001");
    }
    
}
