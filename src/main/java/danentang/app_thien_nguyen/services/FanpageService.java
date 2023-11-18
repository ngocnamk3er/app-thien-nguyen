package danentang.app_thien_nguyen.services;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
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
        return fanpageRepository.save(newFanpage);
    }

    public void deleteFanpage(Integer id) throws Exception {
        fanpageRepository.deleteById(id);
    }

    public Fanpage updateFanpage(Fanpage existingFanpage, FanpageRequest fanpageRequest) throws Exception {

        // Thực hiện cập nhật thông tin Fanpage
        existingFanpage.setFanpageName(fanpageRequest.getFanpageName());
        existingFanpage.setSubscriber(fanpageRequest.getSubscriber());
        // Cập nhật các trường khác nếu cần

        // Lưu Fanpage đã cập nhật
        return fanpageRepository.save(existingFanpage);

    }
}
