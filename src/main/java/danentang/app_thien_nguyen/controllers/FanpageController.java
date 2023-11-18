package danentang.app_thien_nguyen.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import danentang.app_thien_nguyen.models.DataModels.Fanpage;
import danentang.app_thien_nguyen.models.DataModels.User;
import danentang.app_thien_nguyen.models.ReqModels.FanpageRequest;
import danentang.app_thien_nguyen.models.ResModels.FanpageResponse;
import danentang.app_thien_nguyen.services.FanpageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fanpages")
@ResponseBody
public class FanpageController {

    private final FanpageService fanpageService;
    private final HttpServletRequest request;

    @GetMapping
    @Operation(summary = "Get all fanpages", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<FanpageResponse>> getAllFanpages() {
        List<Fanpage> fanpages = fanpageService.getAllFanpages();
        List<FanpageResponse> fanpageResponses = new ArrayList<FanpageResponse>();
        for (Fanpage fanpage : fanpages) {
            FanpageResponse fanpageResponse = new FanpageResponse(fanpage.getFanpageName(),
                    fanpage.getLeaderId().getId(), fanpage.getLeaderId().getUsername(), fanpage.getStatus(),
                    fanpage.getCreateTime(), fanpage.getSubscriber());
            fanpageResponses.add(fanpageResponse);
        }
        return ResponseEntity.ok(fanpageResponses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get fanpages/{id}", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<FanpageResponse> getFanpageById(@PathVariable Integer id) {
        Fanpage fanpage = fanpageService.getFanpageById(id);
        if (fanpage != null) {
            FanpageResponse fanpageResponse = new FanpageResponse(fanpage.getFanpageName(),
                    fanpage.getLeaderId().getId(), fanpage.getLeaderId().getUsername(), id, fanpage.getCreateTime(),
                    fanpage.getSubscriber());
            return ResponseEntity.ok(fanpageResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<FanpageResponse> createFanpage(@RequestBody FanpageRequest fanpageRequest) {
        String leaderIdStr = request.getAttribute("userId").toString();
        Integer leaderId = Integer.valueOf(leaderIdStr);
        Fanpage fanpage = fanpageService.saveFanpage(fanpageRequest, leaderId);
        FanpageResponse FanpageResponse = new FanpageResponse(fanpage.getFanpageName(), fanpage.getLeaderId().getId(),
                fanpage.getLeaderId().getUsername(), fanpage.getStatus(), fanpage.getCreateTime(),
                fanpage.getSubscriber());
        return new ResponseEntity<>(FanpageResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fanpage> updateFanpage(@PathVariable Integer id, @RequestBody Fanpage fanpage) {

        return ResponseEntity.ok(null);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFanpage(@PathVariable Integer id) throws Exception {
        String userIdStr = request.getAttribute("userId").toString();
        Integer userId = Integer.valueOf(userIdStr);
        try {
            fanpageService.deleteFanpage(id, userId);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok("Delete Successfully");
    }
}
