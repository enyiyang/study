package com.bage.study.best.practice.trial;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.bage.study.best.practice.metrics.LogCounterMetrics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/limit")
@RestController
@Slf4j
public class LimitController {

    @Autowired
    private LogCounterMetrics counterMetrics;

    @RequestMapping("/query")
    public Object query(@RequestParam("phone") String phone) {
        counterMetrics.increment("query");
        log.info("UserController query users = {}", phone);
        return 1;
    }

    @RequestMapping("/insert")
    public Object insert() {
        counterMetrics.increment("insert-no-limit");
        try (Entry entry = SphU.entry("log")) {
            // 被保护的逻辑
            counterMetrics.increment("insert");
            try {
                long end = System.currentTimeMillis();
                log.info("UserController insert when = {}", (end));
//            timerMetrics.record((end - start), TimeUnit.MILLISECONDS);
                return 1;
            }catch (Exception e){
                return 300;
            }

        } catch (BlockException ex) {
            // 处理被流控的逻辑
            log.warn("block");
            return 600;
        }

    }

}
