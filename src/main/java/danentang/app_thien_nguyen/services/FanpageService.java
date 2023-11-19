package danentang.app_thien_nguyen.services;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import danentang.app_thien_nguyen.models.DataModels.Fanpage;
import danentang.app_thien_nguyen.models.DataModels.User;
import danentang.app_thien_nguyen.models.ReqModels.FanpageRequest;
import danentang.app_thien_nguyen.models.ResModels.FanpageResponse;
import danentang.app_thien_nguyen.repositories.FanpageRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
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

    public List<Fanpage> getFanpagesByCriteria(Integer leaderId) {
        Fanpage fanpageExample = new Fanpage();

        if (leaderId != null) {
            Optional<User> leader = userService.findById(leaderId);
            fanpageExample.setLeaderId(leader.orElseThrow());
        }

        ExampleMatcher matcher = ExampleMatcher.matching();
        Example<Fanpage> example = Example.of(fanpageExample, matcher);

        return fanpageRepository.findAll(example);
    }

    public Fanpage getFanpageById(Integer id) {
        return fanpageRepository.findById(id).orElseThrow();
    }

    public Fanpage saveFanpage(FanpageRequest fanpage, User leader) {
        Fanpage newFanpage = Fanpage.builder().fanpageName(fanpage.getFanpageName()).leaderId(leader)
                .status(fanpage.getStatus()).createTime(fanpage.getCreateTime()).subscriber(fanpage.getSubscriber())
                .build();
        return fanpageRepository.save(newFanpage);
    }

    public void deleteFanpage(Integer id) throws Exception {
        fanpageRepository.deleteById(id);
    }

    public Fanpage updateFanpage(Fanpage existingFanpage, FanpageRequest fanpageRequest) {

        // Thực hiện cập nhật thông tin Fanpage
        existingFanpage.setFanpageName(fanpageRequest.getFanpageName());
        existingFanpage.setSubscriber(fanpageRequest.getSubscriber());
        // Cập nhật các trường khác nếu cần

        // Lưu Fanpage đã cập nhật
        return fanpageRepository.save(existingFanpage);

    }

    public List<Fanpage> getFanpagesByUserId(Integer userId) throws Exception {
        Optional<User> user = userService.findById(userId);
        if (user != null) {
            return user.orElseThrow().getFanpages();
        } else {
            // Handle the case where the user with the given ID is not found
            throw new Exception("User not found with ID: " + userId);
        }
    }

}
