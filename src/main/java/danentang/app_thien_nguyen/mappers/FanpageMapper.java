package danentang.app_thien_nguyen.mappers;

import danentang.app_thien_nguyen.models.DataModels.Fanpage;
import danentang.app_thien_nguyen.models.DTOs.FanpageDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FanpageMapper {

    @Mappings({
            @Mapping(source = "leader.id", target = "leaderId"),
            @Mapping(source = "leader.username", target = "leaderUsername")
    })
    FanpageDTO fanpageToFanpageDTO(Fanpage fanpage);

    List<FanpageDTO> fanpagesToFanpageDTOs(List<Fanpage> fanpages);
}
