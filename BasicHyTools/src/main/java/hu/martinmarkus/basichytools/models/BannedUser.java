package hu.martinmarkus.basichytools.models;

public class BannedUser {
    private String name;
    private String ip;
    private String date;
    private String banTimer;
    private String reason;
    private String bannerName;
    private boolean ipBanned;

    public BannedUser(String name, String ip, String date,
                      String banTimer, String reason,
                      String bannerName, boolean ipBanned) {
        this.name = name;
        this.ip = ip;
        this.date = date;
        this.banTimer = banTimer;
        this.reason = reason;
        this.bannerName = bannerName;
        this.ipBanned = ipBanned;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBanTimer() {
        return banTimer;
    }

    public void setBanTimer(String banTimer) {
        this.banTimer = banTimer;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getBannerName() {
        return bannerName;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }

    public boolean isIpBanned() {
        return ipBanned;
    }

    public void setIpBanned(boolean ipBanned) {
        this.ipBanned = ipBanned;
    }
}
