package danentang.app_thien_nguyen.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import danentang.app_thien_nguyen.models.DataModels.Fanpage;
import danentang.app_thien_nguyen.models.DataModels.User;
import danentang.app_thien_nguyen.models.ReqModels.FanpageRequest;
import danentang.app_thien_nguyen.models.ResModels.FanpageResponse;
import danentang.app_thien_nguyen.services.FanpageService;
import danentang.app_thien_nguyen.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@ResponseBody
public class FanpageController {

    private final FanpageService fanpageService;
    private final UserService userService;
    private final HttpServletRequest request;

    @GetMapping("/api/fanpages")
    @Operation(summary = "Get fanpages", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<?> getAllFanpages(
            @RequestParam(name = "userId", required = false) Integer userId) {
        try {

            List<Fanpage> fanpages = fanpageService.getFanpagesByCriteria(userId);
            List<FanpageResponse> fanpageResponses = new ArrayList<FanpageResponse>();
            for (Fanpage fanpage : fanpages) {
                FanpageResponse fanpageResponse = new FanpageResponse(fanpage.getFanpageName(),
                        fanpage.getLeaderId().getId(), fanpage.getLeaderId().getUsername(), fanpage.getStatus(),
                        fanpage.getCreateTime(), fanpage.getSubscriber());
                fanpageResponses.add(fanpageResponse);
            }
            return ResponseEntity.ok(fanpageResponses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/api/fanpages/{id}")
    @Operation(summary = "Get fanpages/{id}", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<?> getFanpageById(@PathVariable Integer id) {
        try {
            Fanpage fanpage = fanpageService.getFanpageById(id);
            FanpageResponse fanpageResponse = new FanpageResponse(fanpage.getFanpageName(),
                    fanpage.getLeaderId().getId(), fanpage.getLeaderId().getUsername(), id, fanpage.getCreateTime(),
                    fanpage.getSubscriber());
            return ResponseEntity.ok(fanpageResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/api/fanpages")
    @Operation(summary = "Post new fanpage", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<?> createFanpage(@RequestBody FanpageRequest fanpageRequest) {
        System.out.println("in post request fanpage");
        try {
            String leaderIdStr = request.getAttribute("userId").toString();
            Integer leaderId = Integer.valueOf(leaderIdStr);
            Optional<User> user = userService.findById(leaderId);
            Fanpage fanpage = fanpageService.saveFanpage(fanpageRequest, user.orElseThrow());
            FanpageResponse FanpageResponse = new FanpageResponse(fanpage.getFanpageName(),
                    fanpage.getLeaderId().getId(),
                    fanpage.getLeaderId().getUsername(), fanpage.getStatus(), fanpage.getCreateTime(),
                    fanpage.getSubscriber());
            return new ResponseEntity<>(FanpageResponse, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/api/fanpages/{id}")
    @Operation(summary = "Put fanpages/{id}", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Object> updateFanpage(@PathVariable Integer id, @RequestBody FanpageRequest fanpageRequest) {
        String userIdStr = request.getAttribute("userId").toString();
        Integer userId = Integer.valueOf(userIdStr);

        try {

            Fanpage existingFanpage = fanpageService.getFanpageById(id);

            if (!existingFanpage.getLeaderId().getId().equals(userId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("You don't have permission to update this fanpage.");
            }

            // Thực hiện cập nhật thông tin fanpage
            Fanpage updatedFanpage = fanpageService.updateFanpage(existingFanpage, fanpageRequest);

            // Tạo đối tượng FanpageResponse từ fanpage đã cập nhật
            FanpageResponse fanpageResponse = new FanpageResponse(updatedFanpage.getFanpageName(),
                    updatedFanpage.getLeaderId().getId(), updatedFanpage.getLeaderId().getUsername(),
                    updatedFanpage.getId(), updatedFanpage.getCreateTime(), updatedFanpage.getSubscriber());

            // Trả về đối tượng FanpageResponse đã cập nhật và mã trạng thái OK
            return ResponseEntity.ok(fanpageResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/api/fanpages/{id}")
    @Operation(summary = "Delete fanpages/{id}", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<String> deleteFanpage(@PathVariable Integer id) {
        String userIdStr = request.getAttribute("userId").toString();
        Integer userId = Integer.valueOf(userIdStr);

        try {
            Fanpage existingFanpage = fanpageService.getFanpageById(id);

            // Kiểm tra xem người dùng có quyền xóa fanpage không
            if (!existingFanpage.getLeaderId().getId().equals(userId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("You don't have permission to delete this fanpage.");
            }

            // Thực hiện xóa fanpage
            fanpageService.deleteFanpage(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return ResponseEntity.ok("Delete Successfully");
    }
}
