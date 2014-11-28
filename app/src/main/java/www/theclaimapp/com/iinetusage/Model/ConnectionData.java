package www.theclaimapp.com.iinetusage.Model;


public class ConnectionData {

    private String anniversary;
    private String days_remaining;
    private String ip;
    private String on_since;
    private String used;
    private String name;
    private String allocation;


    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAllocation() {
        return allocation;
    }

    public void setAllocation(String allocation) {
        this.allocation = allocation;
    }


    public String getAnniversary() {
        return anniversary;
    }

    public void setAnniversary(String anniversary) {
        this.anniversary = anniversary;
    }

    public String getDays_remaining() {
        return days_remaining;
    }

    public void setDays_remaining(String days_remaining) {
        this.days_remaining = days_remaining;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOn_since() {
        return on_since;
    }

    public void setOn_since(String on_since) {
        this.on_since = on_since;
    }

}




