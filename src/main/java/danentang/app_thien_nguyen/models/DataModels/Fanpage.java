package danentang.app_thien_nguyen.models.DataModels;



import java.sql.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "fanpage")
@Data
@Builder
@AllArgsConstructor
public class Fanpage {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "fanpage_name", unique = true, nullable = false)
    private String fanpageName;

    
    @JoinColumn(name = "leader_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User leaderId;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "create_time", nullable = false)
    private Date createTime;

    @Column(name = "subscriber", nullable = false)
    private Integer subscriber;

}
