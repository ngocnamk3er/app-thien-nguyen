package danentang.app_thien_nguyen.repositories;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import danentang.app_thien_nguyen.models.DataModels.Fanpage;

public interface FanpageRepository extends JpaRepository<Fanpage, Integer> {

}