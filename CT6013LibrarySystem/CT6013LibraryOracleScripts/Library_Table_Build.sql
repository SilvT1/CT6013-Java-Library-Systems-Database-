/* Drop table
   Create Table
   Create primary key */
   
-- tblCustomers
   
DROP TABLE tbl_student CASCADE CONSTRAINTS;   

CREATE TABLE tbl_Student
( student_number varchar2(10) NOT NULL,
  first_name varchar2(25) NOT NULL,
  last_name varchar2(25) NOT NULL, -- Length 25 should be enough??
  date_of_birth DATE NOT NULL,
  city varchar2(25),
  postcode varchar2(10)
);

ALTER TABLE tbl_Student
ADD CONSTRAINT pk_tbl_Student PRIMARY KEY (student_number);


    
-- tblAuthor

DROP TABLE tbl_Author CASCADE CONSTRAINTS;

CREATE TABLE tbl_Author
( author_id varchar2(10) NOT NULL,
  first_name varchar2(10) NOT NULL,
  last_name varchar2(10) NOT NULL
);

ALTER TABLE tbl_Author
ADD CONSTRAINT pk_tbl_Author PRIMARY KEY (author_id );

    
--tblBooks
DROP TABLE tbl_Books CASCADE CONSTRAINTS;
CREATE TABLE tbl_Books (
    book_ID VARCHAR2 (10) NOT NULL,
    ISBN NUMBER(10) NOT NULL,
    title VARCHAR2(50) NOT NULL,
    copy_Number NUMBER (2) NOT NULL,
    author_ID VARCHAR2  (10) NOT NULL
);
ALTER TABLE tbl_Books
    ADD CONSTRAINT tbl_Books PRIMARY KEY (book_ID);

-- Add in Foreign KEys

ALTER TABLE tbl_Books
    ADD CONSTRAINT fk1_tbl_Author_tbl_Books
    FOREIGN KEY (author_id )
    REFERENCES tbl_Author (author_id );



--tblCopy
DROP TABLE tbl_Copy CASCADE CONSTRAINTS;
CREATE TABLE tbl_Copy (
    copy_ID NUMBER NOT NULL,
    book_ID VARCHAR2(10) NOT NULL,
    barcode VARCHAR2(10) NOT NULL
);
ALTER TABLE tbl_Copy
    ADD CONSTRAINT tbl_Copy PRIMARY KEY (copy_ID);

-- Add in Foreign KEys

ALTER TABLE tbl_Copy
    ADD CONSTRAINT fk1_tbl_Books_tbl_Copy  
    FOREIGN KEY (book_ID)
    REFERENCES tbl_Books(book_ID);





--tblLoan_Booking
DROP TABLE tbl_Loan_Booking CASCADE CONSTRAINTS;
CREATE TABLE tbl_Loan_Booking (
    booking_Loan_ID varchar2(10) NOT NULL,
    student_number varchar2(10) NOT NULL,
    copy_ID Number NOT NULL,
    start_Loan_Date date,
    expected_Return_Date date,
    actual_Return_Date date
);
ALTER TABLE tbl_Loan_Booking
    ADD CONSTRAINT tbl_Loan_Booking PRIMARY KEY (booking_Loan_ID, copy_ID);

-- Add in Foreign KEys

ALTER TABLE tbl_Loan_Booking
    ADD CONSTRAINT fk1_tblStudent_tbl_LoanBooking   
    FOREIGN KEY (student_number)
    REFERENCES tbl_student(student_number);

ALTER TABLE tbl_Loan_Booking
    ADD CONSTRAINT fk1_tblCopy_tbl_LoanBooking   
    FOREIGN KEY (copy_ID)
    REFERENCES tbl_Copy(copy_ID);


--tblFine
DROP TABLE tbl_Fine CASCADE CONSTRAINTS;
CREATE TABLE tbl_Fine(
    fine_ID VARCHAR2(10) NOT NULL,
    booking_Loan_ID VARCHAR2(10) NOT NULL,
    copy_ID NUMBER NOT NULL,
    book_Overdue NUMBER(3) NOT NULL,
    total_Fine Number(4,2) Not Null,
    fine_Paid Number(4,2) Not Null,
    date_fine_paid Date
);
ALTER TABLE tbl_Fine
    ADD CONSTRAINT tbl_Fine PRIMARY KEY (fine_ID);

-- Add in Foreign KEys

ALTER TABLE tbl_Fine
    ADD CONSTRAINT fk1_tbl_Loan_Booking_tbl_Fine   
    FOREIGN KEY (booking_Loan_ID, copy_ID)
    REFERENCES tbl_Loan_Booking(booking_Loan_ID, copy_ID);