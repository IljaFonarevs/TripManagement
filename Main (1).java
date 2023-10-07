// Ilja Fonarevs 3.grupa 221RDB217

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;

//================================================

class Trip implements Serializable {

  //Class containing information about one trip
  
  int id;
  String city;
  LocalDate date;
  int days;
  double price;
  Vehicles vehicle;

  Trip(int id1, String city1, String date, int days1, double price1, String vehicle1, LinkedList<Trip> list) {
    //Class Constructor that at the same time check if input data is correct 
    if (id1 > 999 || id1 < 100) {
      System.out.println("wrong id");
      return;
    }
    for (Trip t : list) {
      if (id1 == t.id) {
        System.out.println("wrong id");
        return;
      }
    }
    this.id = id1;

    //Ensures the first letter of a city is in uppercase
    char[] cityFirstLetter = city1.toCharArray();
    cityFirstLetter[0] = Character.toUpperCase(cityFirstLetter[0]);
    city1 = String.valueOf(cityFirstLetter);
    this.city = city1;

    //Ensures the correct format of a date
    String[] separatedDate = date.split("/");
    LocalDate tempDate = LocalDate.parse(date,
        DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).ofPattern("dd/MM/yyyy"));
    if (Integer.parseInt(separatedDate[0]) < 1 || Integer.parseInt(separatedDate[0]) > 31) {
      System.out.println("wrong date");
      return;
    }
    if (Integer.parseInt(separatedDate[1]) < 1 || Integer.parseInt(separatedDate[1]) > 12) {
      System.out.println("wrong date");
      return;
    }
    this.date = tempDate;
    
    this.days = days1;
    this.price = price1;

    /*Fetching vechile names from enum, although not very practical, my primary task was to tinker
    with enums and their uses so I did this anyway*/
    vehicle1 = vehicle1.toUpperCase();
    switch (vehicle1) {
      case "BUS":
        this.vehicle = Vehicles.BUS;
        break;
      case "PLANE":
        this.vehicle = Vehicles.PLANE;
        break;
      case "TRAIN":
        this.vehicle = Vehicles.TRAIN;
        break;
      case "BOAT":
        this.vehicle = Vehicles.BOAT;
        break;
      default:
        System.out.println("wrong vehicle");
    }

  }

  
  //Setters with the same functionaly as in the class constructor with the same checks/
  public void setId(int id1, LinkedList<Trip> list) {
    if (id1 > 999 || id1 < 100) {
      System.out.println("wrong id");
      return;
    }
    for (Trip t : list) {
      if (id1 == t.id) {
        System.out.println("wrong id");
        return;
      }
    }
    this.id = id1;
  }

  public void setCity(String city) {
    char[] cityFirstLetter = city.toCharArray();
    cityFirstLetter[0] = Character.toUpperCase(cityFirstLetter[0]);
    city = String.valueOf(cityFirstLetter);
    this.city = city;
  }

  public void setDate(String date) {
    String[] separatedDate = date.split("/");
    LocalDate tempDate = null;
    try {
      tempDate = LocalDate.parse(date, DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).ofPattern("dd/MM/yyyy"));
    } catch (Exception e) {
      System.out.println("wrong date");
    }
    if (Integer.parseInt(separatedDate[0]) < 1 || Integer.parseInt(separatedDate[0]) > 31) {
      System.out.println("wrong date");
      return;
    }
    if (Integer.parseInt(separatedDate[1]) < 1 || Integer.parseInt(separatedDate[1]) > 12) {
      System.out.println("wrong date");
      return;
    }
    this.date = tempDate;
  }

  public void setDays(int days) {
    this.days = days;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public void setVehicle(String vehicle1) {
    vehicle1 = vehicle1.toUpperCase();
    switch (vehicle1) {
      case "BUS":
        this.vehicle = Vehicles.BUS;
        break;
      case "PLANE":
        this.vehicle = Vehicles.PLANE;
        break;
      case "TRAIN":
        this.vehicle = Vehicles.TRAIN;
        break;
      case "BOAT":
        this.vehicle = Vehicles.BOAT;
        break;
      default:

        break;
    }
  }
}

// ================================================

public class Main {

  static Scanner sc = new Scanner(System.in);
  static String filename = "db.csv";
  static LinkedList<Trip> list = new LinkedList<>();

  public static void main(String[] args) {
    Scanner scan = new Scanner(filename);
    list = readFile(list, scan); 
    /*method to read information from .csv file into a linked list contatining objects of a Trip           class*/
    String command = "";

    //A loop to keep programm running
    while (!command.equals("exit")) {
      command = sc.nextLine();
      String[] command_spec = command.split(" ");
      switch (command_spec[0]) {
        case "add":
          add(command_spec[1], list);
          break;
        case "del":
          del(command_spec[1], list);
          break;
        case "edit":
          edit(command_spec[1], list);
          break;
        case "print":
          print(list);
          break;
        case "find":
          find(command_spec[1], list);
          break;
        case "avg":
          avg(list);
          break;
        case "sort":
          sort(list);
          break;
        default:
          System.out.println("wrong command");
      }

    }
    sc.close();

  }
  //Method that tries to add a Trip object to a Linked list, if not it will show error 
  static void add(String command, LinkedList<Trip> list) {
    String[] data = command.split(";");
    if (data.length != 6) {
      System.out.println("wrong field count");
      return;
    }
    Trip tripToAdd = null;
    if (!isStringInt(data[3])) {
      System.out.println("wrong day count");
      return;
    }
    try {
      double temp = Double.parseDouble(data[4]);
    } catch (Exception e) {
      System.out.println("wrong price");
    }
    try {
      tripToAdd = new Trip(Integer.parseInt(data[0]), data[1], data[2], Integer.parseInt(data[3]),
          Double.parseDouble(data[4]), data[5], list);

    } catch (Exception e) {
      System.out.println(e.getMessage());
      System.out.println("wrong field count");
      return;
    }
    list.add(tripToAdd);
    System.out.println("added");

  }
  //Method that deletes a Trip object from a LinkedList(or shows error in case such object doesn't exist)
  static void del(String command, LinkedList<Trip> list) {

    int id1 = Integer.parseInt(command);
    if (id1 > 999 || id1 < 100) {
      System.out.println("wrong id");
      return;
    }
    boolean deleted = false;
    for (int i = 0; i < list.size(); i++) {
      if (id1 == list.get(i).id) {
        list.remove(i);
        System.out.println("deleted");
        deleted = true;
      }

    }
    if (!deleted)
      System.out.println("wrong id");

  }
  //Method to edit an existing object in a linked list
  static void edit(String command, LinkedList<Trip> list) {
    int index = 0;
    ;
    String[] arguments = command.split(";");
    if (arguments.length != 6) {
      System.out.println("wrong field count");
      return;
    }
    if (!arguments[4].equals("")) {
      try {
        double temp = Double.parseDouble(arguments[4]);
      } catch (Exception e) {
        System.out.println("wrong price");
      }
    }
    if (Integer.parseInt(arguments[0]) > 999 || Integer.parseInt(arguments[0]) < 100) {
      System.out.println("wrong id");
      return;
    }
    if (!arguments[3].equals("")) {
      if (!isStringInt(arguments[3])) {
        System.out.println("wrong day count");
        return;
      }
    }
    for (Trip t : list) {
      if (Integer.parseInt(arguments[0]) == t.id) {
        index = list.indexOf(t);
        break;
      } else {
        System.out.println("wrong id");
        return;
      }
    }
    arguments[5] = arguments[5].toUpperCase();

    if (!(arguments[5].equals("BUS") || arguments[5].equals("PLANE") || arguments[5].equals("TRAIN")
        || arguments[5].equals("BOAT"))) {
      System.out.println("wrong vehicle");
      return;
    }

    if (!arguments[1].equals(""))
      list.get(index).setCity(arguments[1]);
    if (!arguments[2].equals(""))
      list.get(index).setDate(arguments[2]);
    if (!arguments[3].equals(""))
      list.get(index).setDays(Integer.parseInt(arguments[3]));
    if (!arguments[4].equals(""))
      list.get(index).setPrice(Double.parseDouble(arguments[4]));
    if (!arguments[5].equals(""))
      list.get(index).setVehicle(arguments[5]);
    System.out.println("changed");

  }

  //Method to print out a list in a readible way(may be SIGNIFICANTLY simplified with a use of printf)
  static void print(LinkedList<Trip> list) {
    String dashs_60 = "------------------------------------------------------------";
    String names = "ID  City                 Date         Days     Price Vehicle";
    System.out.println(dashs_60);
    System.out.println(names);
    System.out.println(dashs_60);
    String temp;
    String[] dates;
    char[] arrForLength;
    int length;
    for (Trip t : list) {
      temp = null;
      length = 0;
      arrForLength = null;

      temp = t.id + " ";

      arrForLength = (t.city).toCharArray();
      length = 21 - arrForLength.length;
      temp = temp + t.city;
      for (int i = 0; i < length; i++)
        temp = temp + " ";

      dates = String.valueOf(t.date).split("-");
      for (int i = 2; i > 0; i--)
        temp = temp + dates[i] + "/";
      temp = temp + dates[0] + " ";

      arrForLength = String.valueOf(t.days).toCharArray();
      length = 6 - arrForLength.length;
      for (int i = 0; i < length; i++)
        temp = temp + " ";
      temp = temp + String.valueOf(t.days);

      arrForLength = String.valueOf(t.price).toCharArray();
      length = 10 - arrForLength.length;
      for (int i = 0; i < length - 1; i++)
        temp = temp + " ";
      temp = temp + String.valueOf(t.price) + "0";

      temp = temp + " " + t.vehicle;

      System.out.println(temp);
    }
    System.out.println(dashs_60);
  }

  //Method to found a trip with a price lower or equal to the price user has entered
  static void find(String command, LinkedList<Trip> list) {
    double priceToFind = 0;
    try {
      priceToFind = Double.parseDouble(command);
    } catch (Exception e) {
      System.out.println("wrong price");
      return;

    }
    LinkedList<Trip> foundedTrips = new LinkedList<>();
    for (Trip t : list) {
      if (t.price <= priceToFind) {
        foundedTrips.add(t);
      }
    }
    print(foundedTrips);
  }

  //Simple method to calculate average price for the Trip on the lists
  static void avg(LinkedList<Trip> list) {
    double average = 0;
    for (Trip t : list) {
      average += t.price;
    }
    System.out.printf("average=%.2f\n", average / (double) (list.size()));
  }
  /* Method to sort trips by date, uses a very simple and unefficient bubble-sort, must be rewriten if this programm will be used for real-world cases */
  static void sort(LinkedList<Trip> list) {
    Trip temp = null;
    
    for (int i = list.size() - 1; i > 0; i--) {
      for (int j = 0; j < i; j++) {
        if (list.get(j).date.getDayOfYear() > list.get(j + 1).date.getDayOfYear()) {
          temp = list.get(j);
          list.set(j, list.get(j + 1));
          list.set(j + 1, temp);
        }
      }
    }
    
    System.out.println("sorted");
  }
  //Simpe typecasting check
  static boolean isStringInt(String s) {
    try {
      Integer.parseInt(s);
      return true;
    } catch (NumberFormatException ex) {
      return false;
    }
  }
  //Method to read a file and transform data from it to a Trip class object
  static LinkedList<Trip> readFile(LinkedList<Trip> list, Scanner sc) {
    try {
      File myFile = new File(filename);
      sc = new Scanner(myFile);
      while (sc.hasNextLine()) {
        String data = sc.nextLine();
        String[] splittedData = data.split(";");
        Trip readedTrip = new Trip(Integer.parseInt(splittedData[0]), splittedData[1], splittedData[2],
            Integer.parseInt(splittedData[3]), Double.parseDouble(splittedData[4]), splittedData[5], list);
        list.add(readedTrip);
      }
      return list;
    } catch (FileNotFoundException e) {
      System.out.println("error in reading file");
      return null;
    }
  }
}

enum Vehicles {
  BOAT, PLANE, TRAIN, BUS
}