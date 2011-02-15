import org.ssh.app.example.service.PdfGenerator;
import groovy.sql.Sql
import groovy.json.*

/**
 * This Groovy implementation class is used in the examples where we use Groovy scripts.
 */
class GroovyPdfGenerator implements PdfGenerator {

  String companyName
  String jdbcDriver
  String jdbcUrl
  String jdbcUser
  String jdbcPassword

  public String pdfFor() {
	//    println jdbcUrl
	//    println jdbcUser
	//    println jdbcPassword
	//    println jdbcDriver
	createJson()
	return "Hello:ooo你好00000,我可是放在外面的哦!!," + companyName
  }

  def createJson(){
	def sql = Sql.newInstance(jdbcUrl, jdbcUser, jdbcPassword, jdbcDriver)
	def alls=[]
	sql.eachRow("select * from t_book",
	 { println "Gromit likes ${it.title}"
	alls << it.toRowResult()
	   }
	 )
	def rr = [books:alls, count:alls.size()]

  //def slurper = new JsonSlurper()

  }
}


