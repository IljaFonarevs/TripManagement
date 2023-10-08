## Task

Develop an information system in tourism for an agency that processes travel information (travel
identifier; city; date; number of days; price and type of vehicle). Travel information is available
stored in text in the db.csv file, when developing the program assume that the db.csv file is in the current folder (the folder where
the program is located; to access the file, in the source code of the program, we indicate only the name of the file, without the folder
name). A sample file db.csv is available in repo.

The program includes the following activities:
- view the content of the file, output the data in the form of a table;
- correct the information on the travel ticket;
- arrange the information in the file by date;
- find information about all travel tickets, the price of which does not exceed the specified price;
- calculate the average price of road tickets.

Program Requirements:
- the program must be developed as a console app (without graphical windows), the user interface requirements are
described below;
- the program should include handling of wrong actions of the user;
- Place the source code of the program in one file called Main.java;
- The program must process the db.csv file located in the current folder;

Data format requirements:
- Information about each trip is in a separate line in the db.csv file. All fields in the data file have
must be separated by semicolons. There is no semicolon after the last field. Between trips (middle of file) empty lines
are not.
- The sequence of fields in the db.csv file must be as follows: identifier, city, date, number of days, price,
type of vehicle.
- The trip identifier must be encoded as an integer of three digits. The identifier must be unique,
that is, there cannot be two different trips with the same identifier. Identifiers are optional
for consecutive numbers.
- A city is described by a character string that may contain space characters. All letters must be present
lowercase, the first letter of each word must be capitalized, regardless of how the city name was entered
the user.
- Date is recorded in DD/MM/YYYY format.
- The number of days is stored as an integer.
- The price is stored as a real number with two decimal places
- Vehicle type is coded with the words "PLANE", "BUS", "TRAIN", "BOAT" (all symbols are uppercase)
