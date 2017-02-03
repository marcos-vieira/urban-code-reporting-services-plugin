public class RSFactory {

    public static IReportingServicesService newInstanceByEndPoint(String url, String user, String password) {
         if(url.contains("ReportService2010.asmx") {
             return new ReportService2010(url, user, password);
         }
         
         
         throw new Exception("Factory can not find a handler for this URL");
         
    }

}