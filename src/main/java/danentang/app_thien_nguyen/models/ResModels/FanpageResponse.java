package danentang.app_thien_nguyen.models.ResModels;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class FanpageResponse {
    String fanpageName;
    Integer leaderId;
    String leaderName;
    Integer status;
    Date createTime;//Date
    Integer subscriber;
}
