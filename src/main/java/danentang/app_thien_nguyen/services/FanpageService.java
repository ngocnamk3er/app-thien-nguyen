package danentang.app_thien_nguyen.services;

import org.springframework.stereotype.Service;

import danentang.app_thien_nguyen.models.DataModels.Fanpage;
import danentang.app_thien_nguyen.models.DataModels.User;
import danentang.app_thien_nguyen.models.ReqModels.FanpageRequest;
import danentang.app_thien_nguyen.models.ResModels.FanpageResponse;
import danentang.app_thien_nguyen.repositories.FanpageRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FanpageService {

    private final FanpageRepository fanpageRepository;
    private final UserService userService;

    public List<Fanpage> getAllFanpages() {
        return fanpageRepository.findAll();
    }

    public Fanpage getFanpageById(Integer id) {
        return fanpageRepository.findById(id).orElse(null);
    }

    public Fanpage saveFanpage(FanpageRequest fanpage, Integer leaderId) {
        User leader = userService.findById(leaderId);
        Fanpage newFanpage = Fanpage.builder().fanpageName(fanpage.getFanpageName()).leaderId(leader)
                .status(fanpage.getStatus()).createTime(fanpage.getCreateTime()).subscriber(fanpage.getSubscriber())
                .build();
        fanpageRepository.save(newFanpage);
        return newFanpage;
    }

    public void deleteFanpage(Integer id, Integer userid) throws Exception {
        Fanpage fanpage = fanpageRepository.findById(id).orElse(null);
        if (userid.equals(fanpage.getLeaderId().getId())) {
            fanpageRepository.deleteById(id);
        } else {
            throw new Exception("Access denied");
        }

    }
}
