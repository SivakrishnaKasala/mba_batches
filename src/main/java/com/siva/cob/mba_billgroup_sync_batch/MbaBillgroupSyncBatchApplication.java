package com.siva.cob.mba_billgroup_sync_batch;

import com.siva.cob.mba_billgroup_sync_batch.service.BillGroupSyncService;
import com.siva.cob.mba_billgroup_sync_batch.util.BatchExecution;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationPid;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileSystems;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@SpringBootApplication
public class MbaBillgroupSyncBatchApplication {



	public static void main(String[] args) {


		String logPath = System.getenv("MBCOLOG") + File.separator;  // e.g. C:\final\log\
		String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
		String pid = String.valueOf(ProcessHandle.current().pid());
		String logFileName = "mba_billgroup_sync_batch_" + timestamp + "_" + pid + ".log";
		System.setProperty("LOG_PATH", logPath+logFileName);
		System.setProperty("LOG_FILE", logFileName);
		System.setProperty("LOG_LEVEL", "INFO"); // or DEBUG, ERROR, etc.
		System.out.println(logPath+logFileName);
		System.out.println(logFileName);
//		configureLogger("mba_gdpr_bulk_delete_preprocessor.ksh",".log","MBA_GDPR_BULK_DELETE_PREPROCESSOR_LOG_LEVEL");
		try(ConfigurableApplicationContext context=SpringApplication.run(MbaBillgroupSyncBatchApplication.class, args)){
			BatchExecution batchExecution=new BatchExecution();
			System.out.println(batchExecution);
			BillGroupSyncService service=context.getBean(BillGroupSyncService.class);
			service.doSync();

		}catch (FileNotFoundException e){
			e.printStackTrace();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	public static void configureLogger(String prefix,String suffix,String logLevelEnvVar){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd-HHmmss");
		String pid=(new ApplicationPid()).toString();
		System.out.println(prefix+" started with PID "+pid);
		StringBuilder sb=new StringBuilder();
//        sb.append(System.getenv("MBALOG"));
		String logDir = "C:\\july";
		sb.append(logDir);
		sb.append(FileSystems.getDefault().getSeparator());
		sb.append(prefix);
		sb.append("-");
		sb.append(sdf.format(new Date()));
		sb.append("-");
		sb.append(pid);
		sb.append(suffix);
		String logLevel=System.getenv(logLevelEnvVar);
		if (logLevel==null ){
			System.out.println(logLevelEnvVar+" is not set, setting LOG level INFO");
			logLevel="INFO";
		}
	}

}
