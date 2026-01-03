/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guigpProgramming;
;

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

    // ===== CONSTRUCTOR (Without ID) =====
    public Member(String membername, String contactNum,
                  String membership, String rate, int duration) {
        this.membername = membername;
        this.contactNum = contactNum;
        this.membership = membership;
        this.rate = rate;
        this.duration = duration;
        calculateTotal();
    }
    
    

    // ===== CONSTRUCTOR (With ID) =====
    public Member(String memberid, String membername, String contactNum,
                  String membership, String rate, int duration, double total) {
        this.memberid = memberid;
        this.membername = membername;
        this.contactNum = contactNum;
        this.membership = membership;
        this.rate = rate;
        this.duration = duration;
        this.total = total;
    }

    // ===== GETTERS =====
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

    public int getDuration() {
        return duration;
    }

    public double getTotal() {
        return total;
    }

    // ===== SETTERS =====
    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public void setMembername(String membername) {
        this.membername = membername;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    public void setMembership(String membership) {
        this.membership = membership;
        calculateTotal();
    }

    public void setRate(String rate) {
        this.rate = rate;
        calculateTotal();
    }

    public void setDuration(int duration) {
        this.duration = duration;
        calculateTotal();
    }

    public void setTotal(double total) {
        this.total = total;
    }

    // ===== BUSINESS LOGIC =====
    private void calculateTotal() {
        double baseRate = 0;

        if (rate == null || membership == null) return;

        if (rate.equalsIgnoreCase("Student")) {
            switch (membership) {
                case "Basic" -> baseRate = 80;
                case "Premium" -> baseRate = 150;
                case "VIP" -> baseRate = 280;
            }
        } else { // Standard
            switch (membership) {
                case "Basic" -> baseRate = 120;
                case "Premium" -> baseRate = 200;
                case "VIP" -> baseRate = 350;
            }
        }

        this.total = baseRate * duration;
    }
}
