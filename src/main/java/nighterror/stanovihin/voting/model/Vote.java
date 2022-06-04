package nighterror.stanovihin.voting.model;


import javax.validation.constraints.NotNull;

public class Vote {

    private String phone;
    private String artist;

    public Vote(String phone, String artist) {
        this.phone = phone;
        this.artist = artist;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "phone='" + phone + '\'' +
                ", artist='" + artist + '\'' +
                '}';
    }
}
