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
    private String membership;
    private String rate;
    private double duration;
    private double total;

    public Member(String memberid, String membername, String membership, String rate, double duration, double total) {
        this.memberid = memberid;
        this.membername = membername;
        this.membership = membership;
        this.rate = rate;
        this.duration = duration;
        this.total = total;
    }

    public double getDuration() {
        return duration;
    }

    public String getMemberid() {
        return memberid;
    }

    public String getMembername() {
        return membername;
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
        if(this.rate.equalsIgnoreCase("Student"))
            if(membership.equalsIgnoreCase("Gym&Class")){
                total = 150;
                return total;
            }
        
            else if(membership.equalsIgnoreCase("Gym&Class&Trainer")){
                total = 280;
                return total;
            }
        
            else if(membership.equalsIgnoreCase("")){
                total = 80;
                return total;
            }
            else{
                if(membership.equalsIgnoreCase("Gym&Class")){
                    total = 200;
                    return total;
                }
        
                else if(membership.equalsIgnoreCase("Gym&Class&Trainer")){
                    total = 350;
                    return total;
                }
        
                else if(membership.equalsIgnoreCase("")){
                    total = 120;
                    return total;
                }
            }
        return total = 0;
    }
}
