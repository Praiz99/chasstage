-- 修改sb表扩展参数6长度为400
alter table chas_xt_sb alter column kzcs6 type VARCHAR(400);

-- 车辆使用记录表添加绑定状态字段
ALTER table chas_yw_clsyjl add COLUMN  bindstat varchar(5);
--手动巡更
ALTER TABLE chas_yw_xgjl ADD ycqk varchar(500);

