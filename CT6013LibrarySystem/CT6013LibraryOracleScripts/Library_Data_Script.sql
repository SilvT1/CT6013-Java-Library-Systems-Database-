DELETE FROM TBL_Student;

INSERT INTO TBL_Student(Student_Number, First_Name, Last_Name, Date_Of_Birth, City, Postcode)
VALUES ('S1402345', 'Adam', 'Orange', '01-01-1997', 'Cheltenham', 'GL50 4AQ');

INSERT INTO TBL_Student(Student_Number, First_Name, Last_Name, Date_Of_Birth, City, Postcode)
VALUES ('S1610456', 'Bell', 'Green', '01-01-1998', 'Cheltenham', 'GL50 6QR');

INSERT INTO TBL_Student(Student_Number, First_Name, Last_Name, Date_Of_Birth, City, Postcode)
VALUES ('S1701012', 'Matt', 'Purple', '01-01-1999', 'Cheltenham', 'GL50 7MS'); 




DELETE FROM TBL_AUTHOR;

INSERT INTO TBL_AUTHOR (AUTHOR_ID, FIRST_NAME, LAST_NAME)
VALUES ('AID0001', 'Silva', 'Red');
    
INSERT INTO TBL_AUTHOR (AUTHOR_ID, FIRST_NAME, LAST_NAME)
VALUES ('AID0002', 'Bazer', 'Blue');
    
INSERT INTO TBL_AUTHOR (AUTHOR_ID, FIRST_NAME, LAST_NAME)
VALUES ('AID0003', 'Amy', 'Yellow');
    
INSERT INTO TBL_AUTHOR (AUTHOR_ID, FIRST_NAME, LAST_NAME)
VALUES ('AID0004', 'Simon', 'Silver');





DELETE FROM TBL_BOOKS;

INSERT INTO TBL_BOOKS (Book_ID, ISBN, Title, Copy_Number, Author_ID)
VALUES ('BID0001', '10001001', 'Agile Methods', '001', 'AID0001');

INSERT INTO TBL_BOOKS (Book_ID, ISBN, Title, Copy_Number, Author_ID)
VALUES ('BID0002', '10001001', 'Agile Methods', '002', 'AID0001');

INSERT INTO TBL_BOOKS (Book_ID, ISBN, Title, Copy_Number, Author_ID)
VALUES ('BID0003', '10001011', 'Scrum', '001', 'AID0002');

INSERT INTO TBL_BOOKS (Book_ID, ISBN, Title, Copy_Number, Author_ID)
VALUES ('BID0004', '10001011', 'Scrum', '002', 'AID0002');

INSERT INTO TBL_BOOKS (Book_ID, ISBN, Title, Copy_Number, Author_ID)
VALUES ('BID0005', '10001111', 'Agile Method: Extreme Programming (XP)', '001', 'AID0003');

INSERT INTO TBL_BOOKS (Book_ID, ISBN, Title, Copy_Number, Author_ID)
VALUES ('BID0006', '10001111', 'Agile Method: Extreme Programming (XP)', '002', 'AID0003');

INSERT INTO TBL_BOOKS (Book_ID, ISBN, Title, Copy_Number, Author_ID)
VALUES ('BID0007', '10001002', 'Clean code', '001', 'AID0004');    

INSERT INTO TBL_BOOKS (Book_ID, ISBN, Title, Copy_Number, Author_ID)
VALUES ('BID0008', '10001002', 'Clean code', '002', 'AID0004');   





DELETE FROM TBL_COPY;

INSERT INTO TBL_COPY (Copy_ID, Book_ID, Barcode)
VALUES ('0001', 'BID0001', '1000000000');

INSERT INTO TBL_COPY(Copy_ID, Book_ID, Barcode)
VALUES ('0002', 'BID0002', '1000000001');

INSERT INTO TBL_COPY(Copy_ID, Book_ID, Barcode)
VALUES ('0003', 'BID0003', '1000000002');

INSERT INTO TBL_COPY(Copy_ID, Book_ID, Barcode)
VALUES ('0004', 'BID0004', '1000000003');

INSERT INTO TBL_COPY(Copy_ID, Book_ID, Barcode)
VALUES ('0005', 'BID0005', '1000000004');

INSERT INTO TBL_COPY(Copy_ID, Book_ID, Barcode)
VALUES ('0006', 'BID0006', '1000000005');

INSERT INTO TBL_COPY(Copy_ID, Book_ID, Barcode)
VALUES ('0007', 'BID0007', '1000000006');    

INSERT INTO TBL_COPY(Copy_ID, Book_ID, Barcode)
VALUES ('0008', 'BID0008', '1000000007');   