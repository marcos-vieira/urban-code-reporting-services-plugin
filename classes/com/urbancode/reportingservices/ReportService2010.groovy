public class ReportService2010 implements IReportingServicesService {
     
     private String user,password,url;
     
     public ReportService2010(String url, String user, String password) {
         this.user = user;
         this.password = password;
         this.url = url;
     
     }
     
     // Only basic authentication is implemented at this moment
     private SOAPClient newSOAPClient() {
         SOAPClient client = new SOAPClient(url);
         client.httpClient.sslTrustAllCerts=true;
         client.authorization = new HTTPBasicAuthorization(user, password);
     }
     
     
     public void createFolder(String folderName, String parentPath) {
         def client = newSOAPClient();
         
         // Send SOAP message (for now we won't capture catalog information)
         try {
            def response = client.send(SOAPAction: "http://schemas.microsoft.com/sqlserver/reporting/2010/03/01/ReportServer/CreateFolder") {
                version SOAPVersion.V1_1
                soapNamespacePrefix "SOAP" 
                encoding "UTF-8"
                body {
                    CreateFolder("xmlns" : "http://schemas.microsoft.com/sqlserver/reporting/2010/03/01/ReportServer") {
                        Folder(folderName)
                        Parent(parentName)
                    }
                }
            }
         }
         catch(Exception e) {
             e.printStackTrace();
             
         }
     
     }
     
     public void deleteItem(String itemPath) {
         def client = newSOAPClient();
         
         try {
             def response = client.send(SOAPAction: "http://schemas.microsoft.com/sqlserver/reporting/2010/03/01/ReportServer/DeleteItem") {
                version SOAPVersion.V1_1
                soapNamespacePrefix "SOAP" 
                encoding "UTF-8"
                body {
                   DeleteItem("xmlns" : "http://schemas.microsoft.com/sqlserver/reporting/2010/03/01/ReportServer") {
                       ItemPath(itemPath)
                   }
                }
         }
         catch(Exception e) {
             e.printStackTrace();
         }
     }


     public String[] publishReport(String reportName, String parentPath, boolean overwrite, InputStream definition) {
          
          def client = newSOAPClient();
          def base64DefString = definition.getBytes().encodeBase64().toString();
     
          try {
              def response = client.send(SOAPAction: "http://schemas.microsoft.com/sqlserver/reporting/2010/03/01/ReportServer/CreateCatalogItem") {
                  version SOAPVersion.V1_1
                  soapNamespacePrefix "SOAP" 
                  encoding "UTF-8"
                  body {
                      CreateCatalogItem("xmlns" : "http://schemas.microsoft.com/sqlserver/reporting/2010/03/01/ReportServer") {
                          ItemType("Report")
                          Name(reportName)
                          Parent(parentPath)
                          Overwrite(overwrite)
                          Definition(base64DefString)
                      }
                  }
              }
              
              def warnings = response.body.Warnings;
              println "Warnings:  ${warnings}";
              
          }
          catch (Exception e) {
              e.printStackTrace();
          }
     }

}