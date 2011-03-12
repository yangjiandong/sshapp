package org.ssh.app.example.script.groovy;

import org.ssh.app.example.service.PdfGenerator;
import groovy.sql.Sql
import groovy.json.*
import org.ssh.app.util.leona.JsonUtils;
import org.ssh.app.util.leona.JsonUtils.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This Groovy implementation class is used in the examples where we use Groovy scripts.
 */
class GroovyPdfGenerator implements PdfGenerator {

  private static Logger logger = LoggerFactory.getLogger(GroovyPdfGenerator.class);

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
  testGroovySql()
  createJson()

  logger.info("GroovyPdfGenerator log info -- Hello:你好!!,我可是放在外面的哦!!," + companyName);

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

  //println toJSON(alls)

  //def bean = new Bean(true, "oooo", rr);
  //避免contact 生成json,会报错
  //JsonUtils.write(bean, null,
  //    ["parent", "contact", "hibernateLazyInitializer",
  //  "handler", "checked"
  //], "yyyy.MM.dd");

  //def slurper = new JsonSlurper()

  }

  def testGroovySql(){
    def sql = Sql.newInstance(jdbcUrl, jdbcUser, jdbcPassword, jdbcDriver);
    def test = sql.dataSet("t_test");
    sql.execute ("delete from t_test");

    sql.withTransaction{
      for (int i=0;i<100;i++){
        test.add(
            id:i,
            name:"测试-"+i
            )
      }
      //事务测试
//      test.add(
//              id:"test",
//              name:"测试-"
//              )

    }

    test.each{ println it }
    }

}


