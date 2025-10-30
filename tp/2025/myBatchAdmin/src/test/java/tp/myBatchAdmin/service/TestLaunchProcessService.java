package tp.myBatchAdmin.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Slf4j
public class TestLaunchProcessService {

    @Autowired
    LaunchProcessService launchProcessService;

    @Test
    public void testLaunchProcess(){

        long pid = launchProcessService.launchProcess( new String[] { "notepad.exe" } );
        assertTrue(pid > 0);
        log.info("pid="+pid);

    }
}
