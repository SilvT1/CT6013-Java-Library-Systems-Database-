/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author TSilva
 */
public class ModelBook {
    private String Book_ID;
    private String ISBN;
    private String Title;
    private String Copy_Number;
    private String Author_ID;

    public void setItem_Code(String Book_ID) {
        this.Book_ID = Book_ID;
    }

    public void setItem_Name(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setUnit_Price(String Title) {
        this.Title = Title;
    }

    public void setCopy_Number(String Copy_Number) {
        this.Copy_Number = Copy_Number;
    }

    public void setAuthor_ID(String Author_ID) {
        this.Author_ID = Author_ID;
    }

    public String getBook_ID() {
        return Book_ID;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getTitle() {
        return Title;
    }

    public String getCopy_Number() {
        return Copy_Number;
    }

    public String getAuthor_ID() {
        return Author_ID;
    }
}
