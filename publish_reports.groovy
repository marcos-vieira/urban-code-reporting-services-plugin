import com.urbancode.air.AirPluginTool
import java.util.Properties
import com.urbancode.reportingservices.*
import com.urbancode.filepattern.RegularExpressionFileFilter

def apTool = new AirPluginTool(this.args[0], this.args[1])
def workdir = new File('.').canonicalFile
final def props = apTool.getStepProperties()

def rsUrl = props['rsurl']
def rsUser = props['rsuser']
def rsPass = props['rspwd']
def dir = props['workdir']
def filePattern = props['filepattern']
def parent = props['parent']
def overwrite = Boolean.parseString(props['overwrite'])

if(dir!=null) {
    workdir = new File(dir).canonicalFile
    assert workdir.isDirectory()
}  

def rsProxy = RSFactory.newInstanceByEndPoint(rsUrl,rsUser,rsPass)
def regexFilter = new RegularExpressionFileFilter(filePattern)
def files = workdir.listFiles(regexFilter)

println "Found ${files.length} files in work dir."

for(file in files) {
   def input = file.newInputStream()
   def reportName = file.name.minus('.rdl')
   println "Trying to publish report definition ${file.name} with name ${reportName} and overwrite=${overwrite} and parent=${parent}"
   
   rsProxy.publishReport(reportName,parent,overwrite,input);
   
   println "Report published."
}

println "Job done."