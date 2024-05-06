package com.wckj.chasstage.common.util;


public class ConvertNumberToChinese {
    // 整数部分
    private static String integerPart;
    // 小数部分
    private static String floatPart;
    // 将数字转化为汉字的数组,因为各个实例都要使用所以设为静态
    private static final char[] cnNumbers={'零','壹','贰','叁','肆','伍','陆','柒','捌','玖'};
    // 供分级转化的数组,因为各个实例都要使用所以设为静态
    private static final char[] series={'元','拾','百','仟','万','拾','百','仟','亿'};
	
	/**
	 * 将数字转化为中文大写
	 * @param num 转化的数字
	 * @return
	 */
	public static String ToChinese(String num) {
		
        String n[] = {"零","壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};        
        String unit[] = { "",  "拾" , "佰","仟"};
        String unit1[] = { "亿", "万" };
        StringBuilder chi = new StringBuilder();
 
        for (int i = 0; i < num.length(); i++) {
            chi.append(n[Integer.parseInt(String.valueOf(num.charAt(i)))]);
            chi.append(unit[(num.length()-i-1) % 4]);     
            if ((num.length()-i) % 4 == 1)                
            {
                chi.append(unit1[(int) Math.floor((double) (String.valueOf(num).length()-i) / 4) % 2]);
            }
        }
        String ch = chi.toString();    
        ch = ch.replaceAll("零仟", "零");
        ch = ch.replaceAll("零佰", "零");
        ch = ch.replaceAll("零拾", "零");        
        while(ch.indexOf("零零")>0){
            ch = ch.replaceAll("零零", "零");
        }
        ch = ch.replaceAll("零万", "万");
        ch = ch.replaceAll("零亿", "亿");
        ch = ch.replaceAll("亿万", "亿");
        
        if(ch.length() > 0){
        	return ch.substring(0, ch.length()-1);
        }
        
        return "";
    }

    public static String CnUpperCaser(String original){
        integerPart="";
        floatPart="";
        if(original.contains(".")){
            int dotIndex=original.indexOf(".");
            integerPart=original.substring(0,dotIndex);
            floatPart=original.substring(dotIndex+1);
        }
        else{
            integerPart=original;
        }
        if("".equals(getCnString())){
            return "";
        }else{
            return getCnString().replaceAll("元","");
        }
    }
    /**
     * 取得大写形式的字符串
     * @return
     */
    public static String getCnString(){
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<integerPart.length();i++){
            int number=getNumber(integerPart.charAt(i));
            sb.append(cnNumbers[number]);
            sb.append(series[integerPart.length()-1-i]);
        }
        if(floatPart.length()>0){
            sb.append("点");
            for(int i=0;i<floatPart.length();i++){
                int number=getNumber(floatPart.charAt(i));
                sb.append(cnNumbers[number]);
            }
        }
        return sb.toString();
    }
    /**
     * 将字符形式的数字转化为整形数字
     * 因为所有实例都要用到所以用静态修饰
     * @param c
     * @return
     */
    private static int getNumber(char c){
        String str=String.valueOf(c);
        return Integer.parseInt(str);
    }
}
