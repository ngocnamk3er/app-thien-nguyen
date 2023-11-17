package danentang.app_thien_nguyen.controllers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import danentang.app_thien_nguyen.models.DataModels.Fanpage;
import danentang.app_thien_nguyen.models.DataModels.User;
import danentang.app_thien_nguyen.models.ReqModels.FanpageRequest;
import danentang.app_thien_nguyen.models.ResModels.FanpageResponse;
import danentang.app_thien_nguyen.services.FanpageService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fanpages")
@ResponseBody
public class FanpageController {

    private final FanpageService fanpageService;

    @GetMapping
    public ResponseEntity<List<FanpageResponse>> getAllFanpages() {
        List<Fanpage> fanpages = fanpageService.getAllFanpages();
        List<FanpageResponse> fanpageResponses = new ArrayList<FanpageResponse>();
        for (Fanpage fanpage : fanpages) {
            FanpageResponse fanpageResponse = new FanpageResponse(fanpage.getFanpageName(), fanpage.getLeaderId().getId(),fanpage.getLeaderId().getUsername(), fanpage.getStatus(), fanpage.getCreateTime(), fanpage.getSubscriber());
            fanpageResponses.add(fanpageResponse);
        }
        return ResponseEntity.ok(fanpageResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FanpageResponse> getFanpageById(@PathVariable Integer id) {
        Fanpage fanpage = fanpageService.getFanpageById(id);
        if (fanpage != null) {
            FanpageResponse fanpageResponse = new FanpageResponse(fanpage.getFanpageName(), fanpage.getLeaderId().getId(), fanpage.getLeaderId().getUsername(), id, fanpage.getCreateTime(), fanpage.getSubscriber());
            return ResponseEntity.ok(fanpageResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    

    @PostMapping
    public ResponseEntity<FanpageRequest> createFanpage(@RequestBody FanpageRequest fanpageRequest) {
        try {
            
        } catch (Exception e) {
            // TODO: handle exception
        }
        System.out.println("fanpage request: =====================================" + fanpageRequest);
        User leader = fanpageService.saveFanpage(fanpageRequest);
        return new ResponseEntity<>(fanpageRequest, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fanpage> updateFanpage(@PathVariable Integer id, @RequestBody Fanpage fanpage) {
        Fanpage existingFanpage = fanpageService.getFanpageById(id);
        if (existingFanpage != null) {
            // Set new values for the existing fanpage
            existingFanpage.setFanpageName(fanpage.getFanpageName());
            existingFanpage.setLeaderId(fanpage.getLeaderId());
            existingFanpage.setStatus(fanpage.getStatus());
            existingFanpage.setCreateTime(fanpage.getCreateTime());
            existingFanpage.setSubscriber(fanpage.getSubscriber());

            // fanpageService.saveFanpage(existingFanpage);
            return ResponseEntity.ok(existingFanpage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFanpage(@PathVariable Integer id) {
        fanpageService.deleteFanpage(id);
        return ResponseEntity.noContent().build();
    }
}
