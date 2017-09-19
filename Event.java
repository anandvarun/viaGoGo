
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Event{   
    
    static HashMap<Integer,Location> events = new HashMap<Integer,Location>(); // The event ID is unique every event can be held in only one location.
    static HashMap<Integer,ArrayList<Ticket>> tickets= new HashMap<>(); // An event can contain multiple tickets, The tickets are represented in an arrayList.

    public void setEvent(float x,float y,int eventId){ //Sets ups the location in which the event is to going to be held.
        
        Location coordinates = new Location(x,y);
        events.put(eventId, coordinates);
        
    }

    public void setTicket(float price,int quantity,int eventId){ // For every event the tickets are set up.
        
        Ticket newTicket = new Ticket(price,quantity,eventId);
        
        if(!tickets.containsKey(eventId)){	// If a new event is added a new arraylist of tickets are created.
            
            ArrayList<Ticket> ticketList =new ArrayList<>();
            ticketList.add(newTicket);
            tickets.put(eventId, ticketList);
            
        }
        
        else{	// Adds tickets to the existing event.
            
            tickets.get(eventId).add(newTicket);
        }
        
    }
    
    public float getMinimumTicketPrice(int eventId){
        float minimum=Float.MAX_VALUE;
        ArrayList<Ticket> ticketList = tickets.get(eventId);
        
        for(int i=0;i< ticketList.size();i++){ //Iterates through the entire list of ticket in the event and returns the minimally priced ticket.
            
            if(minimum > ticketList.get(i).getPrice()){
                minimum=ticketList.get(i).getPrice();
            }
            
        }
        return minimum;
    }
    
    public float getMinimumTicketPriceQuantity(int eventId){ //Gives the number of tickets(Quantity of tickets) present for the ticket who's price is minimum for that event.
        
        float minimumPrice=Float.MAX_VALUE;
        int quantity=0;
        ArrayList<Ticket> ticketList = tickets.get(eventId);
        
        for(int i=0;i< ticketList.size();i++){
            
            if(minimumPrice > ticketList.get(i).getPrice()){
                
                minimumPrice=ticketList.get(i).getPrice();
                quantity=ticketList.get(i).getQuantity();
                
            }
        }
        
        return quantity;
    }
    
    public ArrayList<Ticket> getTicket(int eventId){
        
        return tickets.get(eventId); 
        
    }

    public float getDistance(Location source,Location destination){ //Calculates the Manhattan distance
        
        return (float) (Math.abs(source.getX() - destination.getX()) + Math.abs(source.getY() - destination.getY()));
        
    }
    
    public ArrayList<Integer> getClosestFiveEvents(Location userLocation){
        
        HashMap <Integer,Integer> distanceFromUser = new HashMap<Integer,Integer>(); // The key here is the eventID and the value is the distance of the event from the user.
        
        for(Integer event: events.keySet()){ 
            
            Location eventLocation = events.get(event);
            distanceFromUser.put(event,(int) getDistance(userLocation,eventLocation));
            
        }

        LocationComparator comp = new LocationComparator(distanceFromUser);// Sorts By Value. I.E sorts by their distances. If two locations have the same distance the event with the smaller Event ID is returned.
        TreeMap<Integer,Integer> sortedMap = new TreeMap<Integer,Integer>(comp); 
        sortedMap.putAll(distanceFromUser);


        ArrayList<Integer> topFiveEvents = new ArrayList<Integer>();
        int count=1;
        
        for(Integer i : sortedMap.keySet()) {
            
            if(count > 5){
                break; 	
            }
            
            topFiveEvents.add(i);
            count++;
        }
        
        return topFiveEvents;
    }
    
    final class LocationComparator implements Comparator<Integer> {
        
        Map<Integer,Integer> referenceMap;
        public LocationComparator(Map<Integer,Integer> base) {
            
            this.referenceMap = base;
        }

        @Override
        public int compare(Integer k1, Integer k2) {
            
            Integer val1 = referenceMap.get(k1);
            Integer val2 = referenceMap.get(k2);
            int num = val1.compareTo(val2);
            return  num == 0 ? k1.compareTo(k2)   : num;
            
        }
    }




}
