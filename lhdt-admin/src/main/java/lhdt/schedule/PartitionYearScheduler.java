package lhdt.schedule;

import lhdt.service.AccessLogService;
import lhdt.service.DataAdjustLogService;
import lhdt.service.DataLogService;
import lhdt.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataUnit;

/**
 * 년 단위 파티션 테이블 생성
 */
@Slf4j
@Component
public class PartitionYearScheduler {

    @Autowired
    private AccessLogService accessLogService;
    @Autowired
    private DataAdjustLogService dataAdjustLogService;
    @Autowired
    private DataLogService dataLogService;

    @Scheduled(cron = "${lhdt.schedule.year.partition}")
    public void yearPartition() throws Exception {
        // create table access_log_2021 partition of access_log for values from ('2021-01-01 00:00:00.000000') to ('2022-01-01 00:00:00.000000');
        int nextYear = Integer.parseInt(DateUtils.getToday().substring(0, 4)) + 1;
        int afterNextYear = nextYear + 1;

        String tableName = Integer.toString(nextYear);
        String startTime = nextYear + "-01-01 00:00:00.000000";
        String endTime = (afterNextYear) + "-01-01 00:00:00.000000";
        log.info(" @@@@@ tableName = {}, startTime = {}, endTime = {}", tableName, startTime, endTime);

        createAccessLog(tableName, startTime, endTime);
        createDataAdjustLog(tableName, startTime, endTime);
        createDataLog(tableName, startTime, endTime);
    }

    /**
     * access_log 내년 테이블 생성
     * @param tableName
     * @param startTime
     * @param endTime
     */
    private void createAccessLog(String tableName, String startTime, String endTime) {
        log.info("@@@@@@@@@@@@@ access_log 파티션 테이블 생성");
        accessLogService.createPartitionTable(tableName, startTime, endTime);
    }

    /**
     * data_adjust_log 내년 테이블 생성
     * @param tableName
     * @param startTime
     * @param endTime
     */
    private void createDataAdjustLog(String tableName, String startTime, String endTime) {
        log.info("@@@@@@@@@@@@@ data_adjust_log 파티션 테이블 생성");
        dataAdjustLogService.createPartitionTable(tableName, startTime, endTime);
    }

    /**
     * data_adjust_log 내년 테이블 생성
     * @param tableName
     * @param startTime
     * @param endTime
     */
    private void createDataLog(String tableName, String startTime, String endTime) {
        log.info("@@@@@@@@@@@@@ data_log 파티션 테이블 생성");
        dataLogService.createPartitionTable(tableName, startTime, endTime);
    }
}
