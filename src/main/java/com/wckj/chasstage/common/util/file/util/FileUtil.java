package com.wckj.chasstage.common.util.file.util;

import com.wckj.chasstage.common.util.DateTimeUtils;
import com.wckj.chasstage.common.util.StringUtil;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Date;

/**
 * 删除文件和目录
 *
 */
public class FileUtil {
	final static Logger log = Logger.getLogger(FileUtil.class);
	/**
	 * 保存byte数据到本地
	 * @param bfile 文件数据
	 * @param filePath 文件路径
	 * @param fileName 文件名称
	 */
	public static String  byte2File(byte[] bfile,String filePath,String fileName){
		BufferedOutputStream bos=null;
		FileOutputStream fos=null;
		File file=null;
		try{
			File dir=new File(filePath);
			if(!dir.exists() && !dir.isDirectory()){//判断文件目录是否存在
				dir.mkdirs();
			}
			file=new File(filePath+fileName);
			fos=new FileOutputStream(file);
			bos=new BufferedOutputStream(fos);
			bos.write(bfile);
			return filePath+fileName;
		}
		catch(Exception e){
			e.printStackTrace();
			log.error("保存文件至本地出错",e);
		}
		finally{
			try{
				if(bos != null){
					bos.close();
				}
				if(fos != null){
					fos.close();
				}
			}
			catch(Exception e){
				e.printStackTrace();
				log.error("关闭文件流出错",e);
			}
		}
		return null;
	}

	/**
	 * 删除文件，可以是文件或文件夹
	 *
	 * @param fileName
	 *            要删除的文件名
	 * @return 删除成功返回true，否则返回false
	 */
	public static boolean delete(String fileName) {
		if(StringUtil.isEmpty(fileName)){
			return false;
		}
		File file = new File(fileName);
		if (!file.exists()) {
			log.debug("删除文件失败:" + fileName + "不存在！");
			return false;
		} else {
			if (file.isFile())
				return deleteFile(fileName);
			else
				return deleteDirectory(fileName);
		}
	}

	/**
	 * 删除单个文件
	 *
	 * @param fileName
	 *            要删除的文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 删除目录及目录下的文件
	 *
	 * @param dir
	 *            要删除的目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(String dir) {
		// 如果dir不以文件分隔符结尾，自动添加文件分隔符
		if (!dir.endsWith(File.separator))
			dir = dir + File.separator;
		File dirFile = new File(dir);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
			log.debug("删除目录失败：" + dir + "不存在！");
			return false;
		}
		boolean flag = true;
		// 删除文件夹中的所有文件包括子目录
//		String dateName=DateTimeUtils.getDateStr(new Date(), 17 );
//		File[] files = dirFile.listFiles((File dir1, String name) ->
//			!dateName.equalsIgnoreCase(name)
//		);
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
			// 删除子目录
			else if (files[i].isDirectory()) {
				flag = deleteDirectory(files[i]
						.getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag) {
			return false;
		}
		// 删除当前目录
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}
	 public static byte[] toByteArray(String filename) throws IOException {

	        File f = new File(filename);
	        if (!f.exists()) {
	            throw new FileNotFoundException(filename);
	        }

	        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
	        BufferedInputStream in = null;
	        try {
	            in = new BufferedInputStream(new FileInputStream(f));
	            int buf_size = 1024;
	            byte[] buffer = new byte[buf_size];
	            int len = 0;
	            while (-1 != (len = in.read(buffer, 0, buf_size))) {
	                bos.write(buffer, 0, len);
	            }
	            return bos.toByteArray();
	        } catch (IOException e) {
	            e.printStackTrace();
	            throw e;
	        } finally {
	            try {
	                in.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	            bos.close();
	        }
	    }

	public static void main(String[] args) {
		delete("E:\\test");
	}
}
