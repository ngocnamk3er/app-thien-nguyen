package danentang.app_thien_nguyen.models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FanpageDTO {

    private Integer id;
    private String fanpageName;
    private Integer leaderId;
    private String leaderUsername;  // Assuming leaderUsername is available in UserDTO
    private Integer status;
    private Date createTime;
    private Integer subscriber;
}
