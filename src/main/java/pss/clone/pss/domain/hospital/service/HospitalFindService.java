package pss.clone.pss.domain.hospital.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pss.clone.pss.domain.hospital.domain.Room;
import pss.clone.pss.domain.hospital.domain.Ward;
import pss.clone.pss.global.common.Commons;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HospitalFindService {

    private final RestTemplate localTestTemplate;

    public List<Ward> wards() {
        Ward[] wards = localTestTemplate
                .getForObject(
                        Commons.getVpnUrl("/api/wards"),
                        Ward[].class);

        return Arrays.asList(wards);

    }

    public List<Room> rooms() {
        Room[] rooms = localTestTemplate
                .getForObject(
                        Commons.getVpnUrl("/api/rooms"),
                        Room[].class);

        return Arrays.asList(rooms);
    }
}
