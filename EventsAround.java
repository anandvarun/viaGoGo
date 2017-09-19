import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class EventsAround{
    
    public static boolean setUpData(){
        
        boolean dataIsCorrect = false;
        String fileName = "EventInput.txt";
        String line = null;
        
        try{
            
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            while((line = bufferedReader.readLine()) != null) {
                
                String[] values = line.split(" ");
                int x = Integer.valueOf(values[0]);
                int y = Integer.valueOf(values[1]);
                float ticketPrice = Float.valueOf(values[2]);
                int quantity = Integer.valueOf(values[3]);
                int eventId = Integer.valueOf(values[4]);
                
                if((x > 10 || x < -10 || y > 10 || y < -10)){
                    
                    dataIsCorrect = false;
                    break;
                }
                
                Event e= new Event();
                e.setEvent(x, y, eventId);
                e.setTicket(ticketPrice, quantity, eventId);
                dataIsCorrect=true;
            }
            
            bufferedReader.close(); 
            return dataIsCorrect;
        }
        
        catch(FileNotFoundException ex) {
            
            dataIsCorrect = false;
            System.out.println("The file EventInput.txt is missing");
            return false;
        }
        
        catch(IOException ex) {
            
            dataIsCorrect = false;
            System.out.println("Error reading file '");
            return false;
        }
        
        catch(Exception e){
            
            dataIsCorrect = false;
            System.out.println(e);
            return false;
        }
        
    }
    public static void seedData(){
        String fileName = "EventInput.txt";
        try{
            
            FileWriter fileWriter =  new FileWriter(fileName);
            BufferedWriter bufferWriter = new BufferedWriter(fileWriter);;

            for(int i=0;i<100;i++){

                Random random = new Random();
                int min = -10;
                int max = 10;
                int x = random.nextInt(max + 1 - min) + min;
                int y = random.nextInt(max + 1 - min) + min;
                float ticketPrice = random.nextInt(1000)+1;
                int quantity = random.nextInt(1000)+1;
                int eventId = i;   
                String value = x + " " + y + " " + ticketPrice + " " + quantity + " " + eventId ;
                bufferWriter.write(value);
                bufferWriter.newLine();
            }
            bufferWriter.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
    public static void main(String args[]){
        
        seedData();
        boolean dataIsCorrect=setUpData();
        
        if(dataIsCorrect == true){
            
            try{
                
                Scanner in= new Scanner(System.in);
                System.out.println("Enter the users x coordinate");
                int x;
                while (!in.hasNextInt()) {
                    
                    System.out.println("That's not a number!.Please Enter again");
                    in.next();
                    
                }
                x=in.nextInt();
                while(x > 10 || x < -10){
                    
                    System.out.println("Sorry the user has to be within the range 10,-10");
                    System.out.println("Enter the users x coordinate");
                    x=in.nextInt();
                    
                }

                System.out.println("Enter the users y coordinate");
                int y;
                
                while (!in.hasNextInt()) {
                    
                    System.out.println("That's not a number!.Please Enter again");
                    in.next();
                    
                }
                y= in.nextInt();
                while(y > 10 || y < -10){
                    
                    System.out.println("Sorry the user has to be within the range 10,-10");
                    System.out.println("Enter the users y coordinate");
                    y=in.nextInt();
                    
                }
                
                in.close();
                Event output= new Event();
                Location user=new Location(x,y);
                ArrayList<Integer> event= output.getClosestFiveEvents(user);
                
                for(int i=0; i <event.size();i++){
                    
                    int eventId = (int) event.get(i);
                    System.out.println( "The event Id is " + eventId + " The distance is " + output.getDistance(user, Event.events.get(eventId)) + " The Ticket Price is " + output.getMinimumTicketPrice(eventId) + " Dollars. The number of tickets are " + output.getMinimumTicketPriceQuantity(eventId));
                }
                
            }
        
            catch(Exception e){
                System.out.println("Please enter integer values only.");
            }
        }
        else{
            
            System.out.println("There seems to be some issue with the data format. Please Enter it correctly");
        }
        
    }

}
