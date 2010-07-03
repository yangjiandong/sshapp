package org.ssh.app.common.service;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ssh.app.common.dao.HzDao;
import org.ssh.app.common.entity.Hz;

//Spring Service Bean的标识.
@Service("hzService")
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class HzService {
    // public class HzService {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    private HzDao hzDao;

    @Autowired
    public void setHzDao(HzDao hzDao) {
        this.hzDao = hzDao;
    }

    /**
     * 获取字符串的五笔助记符和拼音助记符,其中的英文字母和阿拉伯数字保持不变
     */
    @Transactional(readOnly = true)
    public Map<String, String> getMemo(String hzStr) {
        return this.hzDao.getMemo(hzStr);

    }

    public void initData() {
        if (this.hzDao.getHzCount() != 0)
            return;

        logger.debug("开始装载汉字库数据");

        File resourcetxt = new File(this.getClass().getResource("/data/hzk.txt").getFile());

        try {
            FileInputStream fis = new FileInputStream(resourcetxt);
            String thisLine;

            DataInputStream myInput = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(new InputStreamReader(myInput,"UTF-8"));

            Hz re = new Hz();
            this.hzDao.batchExecute("delete from " + Hz.class.getName());
            int line=1;
            while ((thisLine=br.readLine())!=null){
                if (line==1) {
                    line++;
                    continue;
                }
                String star[]=thisLine.split(",");
                //for(int j=0;j<star.length;j++){
                //    System.out.println(star[j]);
                //}

                if (star[1].equals("")) continue;

                re = new Hz();
                re.setOid(new Long(star[0]));
                re.setHz(star[1]);
                re.setPy(star[2]);
                re.setWb(star[3]);
                this.hzDao.save(re);
            }
//
//            CSVReader reader = new CSVReader(new InputStreamReader(resourcetxt,"UTF-8"),',');
//
//            String[] nextLine;
//            while ((nextLine = reader.readNext()) != null) {
//                System.out.println("Name: [" + nextLine[1] + "]\npy: [" + nextLine[2]
//                        + "]\nwb: [" + nextLine[3] + "]");
//            }

        } catch (Exception e) {
            logger.error("装载汉字数据出错:" + e);
            throw new ServiceException("导入汉字库时，服务器发生异常");
        } finally {

        }
    }
}