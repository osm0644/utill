package card.validator.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import card.validator.utils.Logger;


public class CardServlet extends HttpServlet {
	static ReentrantLock lockSeq = new ReentrantLock();
	static ReentrantLock lockEnd = new ReentrantLock();
	
	protected synchronized void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("Request : "+ req.getRequestURL());
		String [] words = req.getPathInfo().toString().split("/"); 
		String command = words[1];
		
		if (command.equals("REPORT")) {
            final String managerId = words[2];
            String strDate = words[3];

            //검사데이터 String
            String report = ValidatorReport.makeReport(strDate);
            System.out.println("========>" + report);

            //<<요구사항>> Report 작성 시 Report ID를 생성한다.
            //lockSeq.lock();
            final String reportId = ValidatorReport.increaseSeqNo().toString();
            //lockSeq.unlock();

            JsonObject resJson = new JsonObject();

            //<<요구사항>> {“Result”:”Ok”,”ReportID”:”<Report ID>”,”Report”:”<Report>”
            resJson.addProperty("Result", "Ok");
            resJson.addProperty("ReportID", reportId);
            resJson.addProperty("Report", report);

            //concurrenthashmap에 저장
            ValidatorReport.storeReport(reportId, report);

            //concurrenthashmap에 thread를 저장
            ValidatorReport.startTimer(Integer.parseInt(reportId), new Runnable() {

				@Override
				public void run() {
					System.out.println("REPORT ID : " + reportId);
					try {
						Thread.sleep(5000); // 5sec
						
						// timeout 처리
						System.out.println("TIMEOUT : " + reportId);
						//<<요구사항>>Report 파일명은 ‘TIMEOUT_ReportID.TXT’의 형태로 하고, ‘../SERVER/REPORT’폴더에 저장한
						if (ValidatorReport.saveReportFile(reportId, "TIMEOUT"))
						{
							ValidatorReport.removeReport(reportId);
						    Logger.WriteLog(managerId, "TIMEOUT", reportId);
						}						
					} catch (InterruptedException e) {
						
						System.out.println("Timeout Canceled - " + reportId); 
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}						
				}
            });

            // LOG
            Logger.WriteLog(managerId, command, reportId);

            // Console Print
            System.out.println("Report ID : " + reportId);
            System.out.println("------------- Report --------------");
            System.out.println(report);					
			
			res.setStatus(200);
			res.setContentType("application/json");
			res.getWriter().print(resJson.toString());
			res.getWriter().flush();			
		}
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("Request : "+ req.getRequestURL());
		
		Gson gson = new Gson();
		JsonObject resJson = new JsonObject();		

		// read body /////////////////////////////////////////////////////////////////////////////////
        BufferedReader input = new BufferedReader(new InputStreamReader(req.getInputStream()));
        String buffer;
        StringBuilder sb = new StringBuilder();
        while ((buffer = input.readLine()) != null) {
        	sb.append(buffer + "\n");
        }
        String strBody = sb.toString();
		input.close();		
		JsonObject jsonBody = gson.fromJson(strBody, JsonObject.class);
		String managerId = jsonBody.get("ManagerID").getAsString();
		String reportId = jsonBody.get("ReportID").getAsString();		
		///////////////////////////////////////////////////////////////////////////////////////////////
		
		String [] words = req.getPathInfo().toString().split("/"); 
		String command = words[1];
		
		switch(command) {
		case "FINISH":
			{
				lockEnd.lock();
				
				System.out.println("=================> finish");
				resJson.addProperty("Result", "Ok");
				ValidatorReport.cancelTimer(Integer.parseInt(reportId));
				ValidatorReport.saveReportFile(reportId, command);
				ValidatorReport.removeReport(reportId);
                Logger.WriteLog(managerId, command, reportId);
				
				lockEnd.unlock();
			}
			break;
		case "FAIL":
			{
				lockEnd.lock();

				resJson.addProperty("Result", "Ok");
				ValidatorReport.cancelTimer(Integer.parseInt(reportId));
				ValidatorReport.saveReportFile(reportId, command);
				ValidatorReport.removeReport(reportId);
                Logger.WriteLog(managerId, command, reportId);
				
				lockEnd.unlock();				
			}
			break;
		}		
		
		res.setStatus(200);
		res.setContentType("application/json");
		res.getWriter().print(resJson.toString());
		res.getWriter().flush();
	}
}
