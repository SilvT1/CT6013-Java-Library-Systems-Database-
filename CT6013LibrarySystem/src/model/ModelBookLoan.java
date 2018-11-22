/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author TSilva
 */
public class ModelBookLoan {
    private String Booking_Loan_ID;
    private ModelCopy Copy_ID;
    private ModelStudent Student_Number;
    private Date Start_Loan_Date;
    private ArrayList<ModelBook> Book_ID;

    public String getBooking_Loan_ID() {
        return Booking_Loan_ID;
    }
    
    public ModelCopy getCopy_ID() {
        return Copy_ID;
    }

    public ModelStudent getStudent_Number() {
        return Student_Number;
    }

    public Date getStart_Loan_Date() {
        return Start_Loan_Date;
    }

    public ArrayList<ModelBook> getBook_ID() {
        return Book_ID;
    }

    public void setBooking_Loan_ID(String Booking_Loan_ID) {
        this.Booking_Loan_ID = Booking_Loan_ID;
    }
    
    public void setCopy_ID(ModelCopy Copy_ID) {
        this.Copy_ID = Copy_ID;
    }

    public void setStudent_Number(ModelStudent Student_Number) {
        this.Student_Number = Student_Number;
    }

    public void setStart_Loan_Date(Date Start_Loan_Date) {
        this.Start_Loan_Date = Start_Loan_Date;
    }

    public void setBook_ID(ArrayList<ModelBook> Book_ID) {
        this.Book_ID = Book_ID;
    }
}
