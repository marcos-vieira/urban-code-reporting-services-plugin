import com.urbancode.air.AirPluginTool
import java.util.Properties
import com.urbancode.reportingservices.*
import com.urbancode.filepattern.RegularExpressionFileFilter

def apTool = new AirPluginTool(this.args[0], this.args[1])
final def props = apTool.getStepProperties()

def rsUrl = props['rsurl']
def rsUser = props['rsuser']
def rsPass = props['rspwd']
def itemPath = props[ítemPath']

def rsProxy = RSFactory.newInstanceByEndPoint(rsUrl,rsUser,rsPass)

rsProxy.deleteItem(itemPath)