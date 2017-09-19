public class Ticket{
    private int eventId;
    private float price;
    private int quantity;
    
    Ticket(float price,int quantity,int eventId){
        
    	this.price=price;
    	this.quantity=quantity;
        this.eventId=eventId;
        
    }
    
    float getPrice(){
        
        return this.price;
        
    }
    
    int getQuantity(){
        
        return this.quantity;
        
    }
}
