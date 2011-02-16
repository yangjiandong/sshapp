package org.ssh.app.example.script.groovy

import org.ssh.app.example.service.PdfGenerator;
import groovy.sql.Sql
import groovy.json.*
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This Groovy implementation class is used in the examples where we use Groovy scripts.
 */
class GroovyPdfGenerator implements PdfGenerator {

  private static Logger logger = LoggerFactory.getLogger(GroovyPdfGenerator.class)

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

  logger.info("GroovyPdfGenerator log info -- Hello:你好!!,我可是放在外面的哦!!," + companyName)

  return "Hello:你好!!,我可是放在外面的哦!!," + companyName
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