package danentang.app_thien_nguyen.models.ReqModels;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FanpageRequest {
    String fanpageName;
    Integer leaderId;
    Integer status;
    Date createTime;//Date
    Integer subscriber;
}
