/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fitzonefitnesssystem;

/**
 *
 * @author Aleph
 */
public class Member {
    private String memberid;
    private String membername;
    private String contactNum;
    private String membership;
    private String rate;
    private int duration;
    private double total;

    public Member(String membername, String contactNum, String membership, String rate, int duration) {
        this.membername = membername;
        this.contactNum = contactNum;
        this.membership = membership;
        this.rate = rate;
        this.duration = duration;
        determinrate(membership, rate);
    }
    
    public Member(String memberid, String membername, String contactNum, String membership, String rate, int duration, double total) {
        this.memberid = memberid;
        this.membername = membername;
        this.contactNum = contactNum;
        this.membership = membership;
        this.rate = rate;
        this.duration = duration;
        this.total = total;
    }

    public int getDuration() {
        return duration;
    }

    public String getMemberid() {
        return memberid;
    }

    public String getMembername() {
        return membername;
    }

    public String getContactNum() {
        return contactNum;
    }
    
    public String getMembership() {
        return membership;
    }

    public String getRate() {
        return rate;
    }

    public double getTotal() {
        return total;
    }
    
    
    
    public double determinrate(String membership, String rate) {
        if(this.rate.equalsIgnoreCase("Student")){
            if(membership.equalsIgnoreCase("Premium")){
                total = 150;
                
            }
        
            else if(membership.equalsIgnoreCase("Vip")){
                total = 280;
                
            }
        
            else if(membership.equalsIgnoreCase("Basic")){
                total = 80;
                
            }
            }else{
                if(membership.equalsIgnoreCase("Premium")){
                    total = 200;
                    
                }
        
                else if(membership.equalsIgnoreCase("Vip")){
                    total = 350;
                    
                }
        
                else if(membership.equalsIgnoreCase("Basic")){
                    total = 120;
                    
                }
            }
        return this.total = total*this.duration;
    }
}
