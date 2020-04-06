package messageDay.entities;

import javax.persistence.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.sql.Timestamp;

@Entity
@Table(name = "messages", schema = "public", catalog = "messageDay")
public class MessagesEntity {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
    private Integer id;
    private String text;
    private Date dateCreate;
//    private String dataCreate;
    private int autor_id;


    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Basic
    @Column(name = "date_create")
    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public int getAutor_id() {
        return autor_id;
    }

    public void setAutor_id(int autor_id) {
        this.autor_id = autor_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessagesEntity that = (MessagesEntity) o;
        return id == that.id &&
                Objects.equals(text, that.text) &&
                Objects.equals(dateCreate, that.dateCreate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, dateCreate);
    }
}
