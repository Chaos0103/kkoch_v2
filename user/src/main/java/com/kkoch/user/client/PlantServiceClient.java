package com.kkoch.user.client;

import com.kkoch.user.client.response.PlantResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@FeignClient(name = "admin-service")
public interface PlantServiceClient {

    @GetMapping("/admin-service/plants/reservation")
    Integer searchPlantIdBy(@SpringQueryMap Map<String, String> param);

    @GetMapping("/admin-service/plants/names")
    List<PlantResponse> searchPlantsBy(@SpringQueryMap Map<String, List<Integer>> param);
}
