package socialnetwork.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Mesaje extends Entity<Long>{

    private Long from;
    private List<Long> to;
    private String mesaj;
    private LocalDateTime data;
    private List<Long> reply;

    public Mesaje(Long from, String mesaj, LocalDateTime data)
    {
        this.from = from;
        this.mesaj = mesaj;
        this.data = data;
        this.to = new ArrayList<>();
        this.reply = new ArrayList<>();
    }

    public Long getFrom(){return this.from;}
    public String getMesaj(){return this.mesaj;}
    public LocalDateTime getData(){return this.data;}
    public List<Long> getTo() {
        return this.to;
    }
    public List<Long> getReply() {
        return this.reply;
    }
    public void setFrom(Long from){this.from = from;}
    public void setMesaj(String mesaj){this.mesaj = mesaj;}
    public void setData(LocalDateTime data){this.data = data;}
}
