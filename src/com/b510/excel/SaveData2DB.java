/**
 * 
 */
package com.b510.excel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.b510.common.Common;
import com.b510.excel.util.DbUtil;
import com.b510.excel.vo.Student;

import com.epmis.sys.dao.BaseJdbcDao;

/**
 * @author Hongten
 * @created 18
 */

public class SaveData2DB {
//	private DmtService dmtService;
	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;
	
	@SuppressWarnings({ "rawtypes" })
	public void save() throws IOException, SQLException {
//		ReadExcel xlsMain = new ReadExcel();
//		Dmt dmt = null;
//		String path="C:/Users/Administrator/Desktop/student_info.xls";
//		List<Dmt> list = xlsMain.readXls(path);
//
//		for (int i = 0; i < list.size(); i++) {
//			dmt = list.get(i);
//			
//			int count=dmtService.count(dmt.getCid());
//			
//			List l = DbUtil.selectOne(Common.SELECT_STUDENT_SQL + "'%" + student.getName() + "%'", student);
//			if (count<1) {
//				dmtService.save(dmt);
//				DbUtil.insert(Common.INSERT_STUDENT_SQL, student);
//			} else {
//				System.out.println("The Record was Exist : No. = " + dmt.getCid() + " , and has been throw away!");
//			}
//		}
	}
}
