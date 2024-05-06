function CASE_SF(){  var SourceArray = [{"name":"党政领导","jpcode":"dzld","code":"001","scode":"dangzhenglingdao"},{"name":"党政机关工作人员","jpcode":"dzjggzry","code":"002","scode":"dangzhengjiguangongzuorenyuan"},{"name":"人大代表","jpcode":"rddb","code":"003","scode":"rendadaibiao"},{"name":"政协委员","jpcode":"zxwy","code":"004","scode":"zhengxieweiyuan"},{"name":"民主党派人士","jpcode":"mzdprs","code":"005","scode":"minzhudangpairenshi"},{"name":"检法干部","jpcode":"jfgb","code":"006","scode":"jianfaganbu"},{"name":"行政执法人员","jpcode":"xzzfry","code":"007","scode":"xingzhengzhifarenyuan"},{"name":"领导子女","jpcode":"ldzn","code":"008","scode":"lingdaozinv"},{"name":"领导身边工作人员","jpcode":"ldsbgzry","code":"009","scode":"lingdaoshenbiangongzuorenyuan"},{"name":"现役军人","jpcode":"xyjr","code":"010","scode":"xianyijunren"},{"name":"复转军人","jpcode":"fzjr","code":"011","scode":"fuzhuanjunren"},{"name":"部队职工","jpcode":"bdzg","code":"012","scode":"buduizhigong"},{"name":"人民警察","jpcode":"rmjc","code":"013","scode":"renminjingcha"},{"name":"武装警察","jpcode":"wzjc","code":"014","scode":"wuzhuangjingcha"},{"name":"警务辅助人员","jpcode":"jwfzry","code":"015","scode":"jingwufuzhurenyuan"},{"name":"未录警干部","jpcode":"wljgb","code":"016","scode":"weilujingganbu"},{"name":"司法鉴定人员","jpcode":"sfjdry","code":"017","scode":"sifajiandingrenyuan"},{"name":"村居干部","jpcode":"cjgb","code":"018","scode":"cunjuganbu"},{"name":"保安人员","jpcode":"bary","code":"019","scode":"baoanrenyuan"},{"name":"联防人员","jpcode":"lfry","code":"020","scode":"lianfangrenyuan"},{"name":"治保人员","jpcode":"zbry","code":"021","scode":"zhibaorenyuan"},{"name":"在大陆工作外籍人员","jpcode":"zdlgzwjry","code":"022","scode":"zaidalugongzuowaijirenyuan"},{"name":"在大陆工作港澳台人员","jpcode":"zdlgzgatry","code":"023","scode":"zaidalugongzuogangaotairenyuan"},{"name":"在大陆留学的外籍人员","jpcode":"zdllxdwjry","code":"024","scode":"zaidaluliuxuedewaijirenyuan"},{"name":"在大陆留学的港澳台人员","jpcode":"zdllxdgatry","code":"025","scode":"zaidaluliuxuedegangaotairenyuan"},{"name":"在大陆外籍游客","jpcode":"zdlwjyk","code":"026","scode":"zaidaluwaijiyouke"},{"name":"在大陆的港澳台游客","jpcode":"zdldgatyk","code":"027","scode":"zaidaludegangaotaiyouke"},{"name":"华侨","jpcode":"hq","code":"028","scode":"huaqiao"},{"name":"侨眷侨属","jpcode":"qjqs","code":"029","scode":"qiaojuanqiaoshu"},{"name":"驻华外交官员","jpcode":"zhwjgy","code":"030","scode":"zhuhuawaijiaoguanyuan"},{"name":"驻华外交官员家属","jpcode":"zhwjgyjs","code":"031","scode":"zhuhuawaijiaoguanyuanjiashu"},{"name":"驻外外交官员","jpcode":"zwwjgy","code":"032","scode":"zhuwaiwaijiaoguanyuan"},{"name":"边民","jpcode":"bm","code":"033","scode":"bianmin"},{"name":"在大陆探亲的外籍人员","jpcode":"zdltqdwjry","code":"034","scode":"zaidalutanqindewaijirenyuan"},{"name":"外籍记者","jpcode":"wjjz","code":"035","scode":"waijijizhe"},{"name":"固定制工人","jpcode":"gdzgr","code":"036","scode":"gudingzhigongren"},{"name":"合同制工人","jpcode":"htzgr","code":"037","scode":"hetongzhigongren"},{"name":"农民","jpcode":"nm","code":"038","scode":"nongmin"},{"name":"外来务工人员","jpcode":"wlwgry","code":"039","scode":"wailaiwugongrenyuan"},{"name":"外来务工农民子女","jpcode":"wlwgnmzn","code":"040","scode":"wailaiwugongnongminzinv"},{"name":"无业人员","jpcode":"wyry","code":"041","scode":"wuyerenyuan"},{"name":"拾荒者","jpcode":"shz","code":"042","scode":"shihuangzhe"},{"name":"废旧物资回收利用人员","jpcode":"fjwzhslyry","code":"043","scode":"feijiuwuzihuishouliyongrenyuan"},{"name":"保育、家庭服务人员","jpcode":"by、jtfwry","code":"044","scode":"baoyu、jiatingfuwurenyuan"},{"name":"学生","jpcode":"xs","code":"045","scode":"xuesheng"},{"name":"离退休人员","jpcode":"ltxry","code":"046","scode":"lituixiurenyuan"},{"name":"聋哑人","jpcode":"lyr","code":"047","scode":"longyaren"},{"name":"智障人","jpcode":"zzr","code":"048","scode":"zhizhangren"},{"name":"盲人","jpcode":"mr","code":"049","scode":"mangren"},{"name":"肢体残疾人员","jpcode":"ztcjry","code":"050","scode":"zhiticanjirenyuan"},{"name":"孕妇","jpcode":"yf","code":"051","scode":"yunfu"},{"name":"哺乳期妇女","jpcode":"brqfn","code":"052","scode":"buruqifunv"},{"name":"未成年人","jpcode":"wcnr","code":"053","scode":"weichengnianren"},{"name":"企事业单位负责人","jpcode":"qsydwfzr","code":"054","scode":"qishiyedanweifuzeren"},{"name":"事业单位职员","jpcode":"sydwzy","code":"055","scode":"shiyedanweizhiyuan"},{"name":"制片人","jpcode":"zpr","code":"056","scode":"zhipianren"},{"name":"投资人","jpcode":"tzr","code":"057","scode":"touziren"},{"name":"供货商","jpcode":"ghs","code":"058","scode":"gonghuoshang"},{"name":"销售商","jpcode":"xss","code":"059","scode":"xiaoshoushang"},{"name":"房东","jpcode":"fd","code":"060","scode":"fangdong"},{"name":"律师","jpcode":"ls","code":"061","scode":"lvshi"},{"name":"文职人员","jpcode":"wzry","code":"062","scode":"wenzhirenyuan"},{"name":"文物鉴定人员","jpcode":"wwjdry","code":"063","scode":"wenwujiandingrenyuan"},{"name":"鉴定估价人员","jpcode":"jdgjry","code":"064","scode":"jiandinggujiarenyuan"},{"name":"记者","jpcode":"jz","code":"065","scode":"jizhe"},{"name":"导演","jpcode":"dy","code":"066","scode":"daoyan"},{"name":"医护人员","jpcode":"yhry","code":"067","scode":"yihurenyuan"},{"name":"教职员工","jpcode":"jzyg","code":"068","scode":"jiaozhiyuangong"},{"name":"金融业务人员","jpcode":"jrywry","code":"069","scode":"jinrongyewurenyuan"},{"name":"营销人员","jpcode":"yxry","code":"070","scode":"yingxiaorenyuan"},{"name":"采购人员","jpcode":"cgry","code":"071","scode":"caigourenyuan"},{"name":"运输服务人员","jpcode":"ysfwry","code":"072","scode":"yunshufuwurenyuan"},{"name":"社会中介服务人员","jpcode":"shzjfwry","code":"073","scode":"shehuizhongjiefuwurenyuan"},{"name":"物业管理人员","jpcode":"wyglry","code":"074","scode":"wuyeguanlirenyuan"},{"name":"在外资单位中方工作人员","jpcode":"zwzdwzfgzry","code":"075","scode":"zaiwaizidanweizhongfanggongzuorenyuan"},{"name":"个体工商业者","jpcode":"gtgsyz","code":"076","scode":"getigongshangyezhe"},{"name":"出租车驾驶员","jpcode":"czcjsy","code":"077","scode":"chuzuchejiashiyuan"},{"name":"占卜师","jpcode":"zbs","code":"078","scode":"zhanboshi"},{"name":"宗教职业者","jpcode":"zjzyz","code":"079","scode":"zongjiaozhiyezhe"},{"name":"风水师","jpcode":"fss","code":"080","scode":"fengshuishi"},{"name":"气功师","jpcode":"qgs","code":"081","scode":"qigongshi"},{"name":"模范人士","jpcode":"mfrs","code":"082","scode":"mofanrenshi"},{"name":"慈善人士","jpcode":"csrs","code":"083","scode":"cishanrenshi"},{"name":"知名人士","jpcode":"zmrs","code":"084","scode":"zhimingrenshi"},{"name":"高知高工","jpcode":"gzgg","code":"085","scode":"gaozhigaogong"},{"name":"求租人","jpcode":"qzr","code":"086","scode":"qiuzuren"},{"name":"顾客","jpcode":"gk","code":"087","scode":"guke"},{"name":"敏感身份人员","jpcode":"mgsfry","code":"088","scode":"minganshenfenrenyuan"},{"name":"流浪乞讨人员","jpcode":"llqtry","code":"089","scode":"liulangqitaorenyuan"},{"name":"刑释解教人员","jpcode":"xsjjry","code":"090","scode":"xingshijiejiaorenyuan"},{"name":"教改对象家属","jpcode":"jgdxjs","code":"091","scode":"jiaogaiduixiangjiashu"},{"name":"色情服务人员","jpcode":"sqfwry","code":"092","scode":"seqingfuwurenyuan"},{"name":"非法营运司机","jpcode":"ffyysj","code":"093","scode":"feifayingyunsiji"},{"name":"精神病人","jpcode":"jsbr","code":"094","scode":"jingshenbingren"},{"name":"同性恋人员","jpcode":"txlry","code":"095","scode":"tongxinglianrenyuan"},{"name":"吸毒人员","jpcode":"xdry","code":"096","scode":"xidurenyuan"},{"name":"参与传销人员","jpcode":"cycxry","code":"097","scode":"canyuchuanxiaorenyuan"},{"name":"参与非法集资人员","jpcode":"cyffjzry","code":"098","scode":"canyufeifajizirenyuan"},{"name":"制售、使用假发票人员","jpcode":"zs、syjfpry","code":"099","scode":"zhishou、shiyongjiafapiaorenyuan"},{"name":"侵犯知识产权和制售伪劣商品人员","jpcode":"qfzscqhzswlspry","code":"100","scode":"qinfanzhishichanquanhezhishouweilieshangpinrenyuan"},{"name":"使用伪造信用卡人员","jpcode":"sywzxykry","code":"101","scode":"shiyongweizaoxinyongkarenyuan"},{"name":"持有、使用假币人员","jpcode":"cy、syjbry","code":"102","scode":"chiyou、shiyongjiabirenyuan"},{"name":"其他特殊身份","jpcode":"qttssf","code":"999","scode":"qitateshushenfen"}]; return SourceArray;}