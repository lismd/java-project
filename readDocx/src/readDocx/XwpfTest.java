package readDocx;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Section;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class XwpfTest {  
	   
	   /** 
	    * ͨ��XWPFDocument�����ݽ��з��ʡ�����XWPF�ĵ����ԣ������ַ�ʽ���ж��������ѡ� 
	    * @throws Exception 
	    */    
	   public void testReadByDoc() throws Exception {
		  String filePath ="C:/Users/Dimitri/Desktop/GB/demo.docx";
	      InputStream is = new FileInputStream(filePath);  
	      XWPFDocument doc = new XWPFDocument(is);  
	      List<XWPFParagraph> paras = doc.getParagraphs();  
	      for (XWPFParagraph para : paras) {  
	         //��ǰ���������  
//	       CTPPr pr = para.getCTP().getPPr();  
	         System.out.println(para.getText());  
	      }  
	      //��ȡ�ĵ������еı��  
	      List<XWPFTable> tables = doc.getTables();  
	      List<XWPFTableRow> rows;  
	      List<XWPFTableCell> cells;  
	      for (XWPFTable table : tables) {  
	         //�������  
//	       CTTblPr pr = table.getCTTbl().getTblPr();  
	         //��ȡ����Ӧ����  
	         rows = table.getRows();  
	         for (XWPFTableRow row : rows) {  
	            //��ȡ�ж�Ӧ�ĵ�Ԫ��  
	            cells = row.getTableCells();  
	            for (XWPFTableCell cell : cells) {  
	                System.out.println(cell.getText());;  
	            }  
	         }  
	      }  
	      this.close(is);  
	   }  
	    
	   /** 
	    * �ر������� 
	    * @param is 
	    */  
	   private void close(InputStream is) {  
	      if (is != null) {  
	         try {  
	            is.close();  
	         } catch (IOException e) {  
	            e.printStackTrace();  
	         }  
	      }  
	   }  
	   
	   public static void main(String[] args) throws Exception {
		   XwpfTest xwpf= new XwpfTest();
		   xwpf.testReadByDoc();
	   }
	}  