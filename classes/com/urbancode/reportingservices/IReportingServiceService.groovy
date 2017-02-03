
public interface IReportingServicesService {

     public String[] publishReport(String reportName, String parentPath, boolean overwrite, InputStream definition);
     public void deleteItem(String itemPath);
     public void createFolder(String folderName, String parentPath)
     
}