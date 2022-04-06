package afoc.snsclonespringboot.data;

import org.aspectj.lang.annotation.After;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class DataServiceTest {
    @Autowired
    DataService dataService;

    @Autowired
    DataInfoRepository dataInfoRepository;

    @AfterEach
    public void afterEach() {
        dataInfoRepository.clear();
    }

    @Test
    void saveSingleTest() {
        DataInfo dataInfo1 = DataInfo.builder()
                .dataType(DataType.Image)
                .sourceDataPath("/src/test1")
                .saveDataPath("/save/test1")
                .build();

        Boolean isSuccess = dataService.save(dataInfo1);
        assertThat(isSuccess).isTrue();
    }

    @Test
    void saveManyTest() {
        for(int i=0;i<10;i++){
            DataType dt;
            if(i%2==0)
                dt = DataType.Text;
            else
                dt = DataType.Image;

            // build dataInfo
            DataInfo dataInfo = DataInfo.builder()
                    .dataType(dt)
                    .sourceDataPath("/src/test"+i)
                    .saveDataPath("/save/test"+i)
                    .build();

            Boolean isSuccess = dataService.save(dataInfo);
            assertThat(isSuccess).isTrue();
        }
    }

    @Test
    void loadSingleTest() {
        DataInfo dataInfo1 = DataInfo.builder()
                .dataType(DataType.Image)
                .sourceDataPath("/src/test1")
                .saveDataPath("/save/test1")
                .build();

        Boolean isSuccess = dataService.save(dataInfo1);
        assertThat(isSuccess).isTrue();

        Optional<DataInfo> foundDataInfo = dataService.load(dataInfo1.getId());
        assertThat(foundDataInfo).isPresent();
        assertThat(foundDataInfo.get()).isEqualTo(dataInfo1);
    }

    @Test
    void loadManyTest() {
        List<DataInfo> dataInfoList = new ArrayList<>();

        for(int i=0;i<10;i++){
            DataType dt;
            if(i%2==0)
                dt = DataType.Text;
            else
                dt = DataType.Image;

            // build dataInfo
            DataInfo dataInfo = DataInfo.builder()
                    .dataType(dt)
                    .sourceDataPath("/src/test"+i)
                    .saveDataPath("/save/test"+i)
                    .build();

            Boolean isSuccess = dataService.save(dataInfo);
            assertThat(isSuccess).isTrue();

            dataInfoList.add(dataInfo);
        }

        for(DataInfo dataInfo : dataInfoList){
            Optional<DataInfo> foundDataInfo = dataService.load(dataInfo.getId());
            assertThat(foundDataInfo).isPresent();
            assertThat(foundDataInfo.get()).isEqualTo(dataInfo);
        }
    }

    @Test
    void loadNotFoundTest() {
        Optional<DataInfo> foundDataInfo = dataService.load(0L);
        assertThat(foundDataInfo).isEmpty();
    }
}