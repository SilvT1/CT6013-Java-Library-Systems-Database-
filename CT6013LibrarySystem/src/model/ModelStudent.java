/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author TSilva
 */
public class ModelStudent {
    private String Student_Number;
    private String First_Name;
    private String Last_Name;
    private String Date_Of_Birth;
    private String City;
    private String Postcode;

    public void setStudent_Number(String Student_Number) {
        this.Student_Number = Student_Number;
    }

    public void setFirst_Name(String First_Name) {
        this.First_Name = First_Name;
    }

    public void setLast_Name(String Last_Name) {
        this.Last_Name = Last_Name;
    }

    public void setDate_Of_Birth(String Date_Of_Birth) {
        this.Date_Of_Birth = Date_Of_Birth;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public void setPostcode(String Postcode) {
        this.Postcode = Postcode;
    }

    
    public String getStudent_Number() {
        return Student_Number;
    }

    public String getFirst_Name() {
        return First_Name;
    }

    public String getLast_Name() {
        return Last_Name;
    }

    public String getDate_Of_Birth() {
        return Date_Of_Birth;
    }

    public String getCity() {
        return City;
    }

    public String getPostcode() {
        return Postcode;
    }
}
